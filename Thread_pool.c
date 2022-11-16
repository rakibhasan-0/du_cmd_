#include "Thread_pool.h"
#include <string.h>
#include <pthread.h>
#include <unistd.h>
#include <sys/wait.h>
#include <dirent.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <stdlib.h>
#include <stdbool.h>
#include <stdio.h>
#include <linux/limits.h>
#include <time.h>
#include <sys/resource.h>




/**
 * @brief 
 * 
 * @param task 
 */
static void free_task(Task *task){
   free(task->file_name);
   free(task);
}


/**
 * @brief                That function destroys those memories which has been dynamically allocated, 
 * 
 * @param pool           The thread pool.
 */
static void destroy_thread_pool(ThreadPool *pool){
   pthread_mutex_destroy(&pool->q->lock);
   pthread_cond_destroy(&pool->cond_var);
   pthread_cond_destroy(&pool->q->Queue_signal);
   free(pool->q);
   free(pool->flags);
   free(pool->threads_array);
   free(pool);
   return;
}



void count_dir_size_by_threads(char* path_name, int number_threads){

   ThreadPool* pool = creates_thread_pool(number_threads, path_name);

   for(int i = 0; i < number_threads; i++){
      pthread_join(pool->threads_array[i], NULL);
   }

   if(!pool->flags->read_permission){
      printf("%d\t%s\n",pool->block_size, path_name);
      destroy_thread_pool(pool);
      exit(EXIT_FAILURE);
   }
   else{
      printf("%d\t%s\n",pool->block_size, path_name);
   }

   destroy_thread_pool(pool);
   return;
}


bool check_if_read_permissions(const char* path_name){
   if(access(path_name, R_OK) == -1){
      return false;
   }
   return true;
}


bool check_if_arg_is_file(struct stat* buffer){
   return S_ISREG(buffer->st_mode);
}


bool check_if_directory(struct stat* st){
   return S_ISDIR(st->st_mode);
}


ThreadPool* creates_thread_pool(int number_threads, char* path_name){

   ThreadPool* pool = NULL;
   pool = safe_malloc(sizeof(ThreadPool)*1,pool);

   pool->number_threads= number_threads;
   pool->threads_array = safe_malloc(sizeof(pthread_t) * number_threads, pool);
   pool->block_size = 0;
   //pool->working_threads = 0;
   //TODO: create queue func and move safe malloc to queue.h

   pool->flags = safe_malloc(sizeof(Flags) *1, pool);
   pool->flags->read_permission = true;
   pool->q = create_Queue(path_name);

   pthread_cond_init(&pool->cond_var,NULL);
   pthread_cond_init(&pool->q->Queue_signal, NULL);

   if(pthread_mutex_init(&pool->q->lock, NULL) != 0){
      perror("pthread_mutex failed\n");
      exit(EXIT_FAILURE);
   }

   for(int i = 0; i < number_threads; i++){
      if((pthread_create(&pool->threads_array[i], NULL,thread_function ,pool)) != 0){
         perror("pthread failure");
         exit(EXIT_FAILURE);
      }
   }
   return pool;
}



/**
 * @brief                     it counts the size if the given directory or file.
 * 
 * @param temp_size           Current size of the directory or file. 
 * @param full_path           The name of the directory or file.
 */
static void counting_size (struct stat* buffer,unsigned int* temp_size){
   *temp_size = buffer->st_blocks + *temp_size;
}




/**
 * @brief                        it will execute each task which will execute the specified task which will be
 *                               given by the thread.
 * 
 * @param task                   The task to execute.
 * @param pool                   The thread pool.
 */
static void executing_task_function (Task* task, ThreadPool* pool){

   // in case first element is the a file.
   struct stat buffer;
   lstat(task->file_name,&buffer);

   if(!check_if_directory(&buffer) && check_if_read_permissions(task->file_name)){
      // refactor that piece of code. 
      counting_size(&buffer,&pool->block_size);
      pthread_mutex_lock(&pool->q->lock);
      pool->q->tasks_pending--;
      pthread_mutex_unlock(&pool->q->lock);
      free_task(task);
      return;
   }

   else if(check_if_directory(&buffer) && check_if_read_permissions(task->file_name)){
      
      struct dirent* dir_read;
      DIR* dir = opendir(task->file_name);

      if(dir == NULL){
         perror("something went wrong\n");
         exit(EXIT_FAILURE);
      }

      unsigned int temp_size = 0;
      counting_size(&buffer,&temp_size);
      
      // traversing of the directory.
      struct stat buff;
      while((dir_read = readdir(dir)) != NULL){
         //getting full path 
         char full_path[PATH_MAX +1 ] = {0};
         snprintf(full_path,sizeof(full_path)-1, "%s/%s",task->file_name,dir_read->d_name);
         lstat(full_path, &buff);

         if(check_if_directory(&buff)){
            if (strcmp(dir_read->d_name, "..") != 0 && strcmp(dir_read->d_name, ".") != 0) {
               if(check_if_read_permissions(full_path)){
                  // those lock can be held in the enqueue_task_into_queue function.
                  pthread_mutex_lock(&pool->q->lock);
                  enqueue_task_into_queue(pool->q,full_path);
                  pthread_cond_signal(&pool->cond_var);
                  pthread_mutex_unlock(&pool->q->lock);
               }
               else{
                  pthread_mutex_lock(&pool->q->lock);
                  counting_size(&buff,&pool->block_size);
                  pool->flags->read_permission = false;
                  pthread_mutex_unlock(&pool->q->lock);
                  fprintf(stderr, "du: cannot read directory '%s': Permission denied\n",full_path);                  
               }
            }
         }

         else {
            counting_size(&buff,&temp_size);
         }

      }
      // synchronization of file size
      pthread_mutex_lock(&pool->q->lock);
      pool->block_size = pool->block_size + temp_size;
      pool->q->tasks_pending--;
      pthread_cond_broadcast(&pool->cond_var);
      pthread_mutex_unlock(&pool->q->lock);
      closedir(dir);
      free_task(task);

      return;
   }

}



void* thread_function (void* p){

   ThreadPool* pool = (ThreadPool*) p;

   while(1){
     // to ensure that nothing can manipulate the pool's member.
      pthread_mutex_lock(&pool->q->lock);
      while(check_if_queue_is_empty(pool->q)){
         // that means there is no task to to do.
         if(pool->q->tasks_pending == 0){
            pthread_mutex_unlock(&pool->q->lock);
            return NULL;
         }
         // otherwise, let the threads to wait..aka sleep.
         else{
            pthread_cond_wait(&pool->cond_var, &pool->q->lock);
         }
      }
      // we can assume that there is work to do, so increment the current_working_thread var.
      // get the task from the queue.
      Task* task = dequeue_task_from_queue(pool->q);
      // let other threads to work so we choose to the it unlock.
      pthread_mutex_unlock(&pool->q->lock);  
      executing_task_function(task, pool);
   }

   return NULL;
}



bool check_symbolic_link(struct stat* buffer){
   return S_ISLNK(buffer->st_mode);
}
