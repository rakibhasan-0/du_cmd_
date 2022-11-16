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

   ThreadPool* pool = (ThreadPool*) malloc(sizeof(ThreadPool));

   pool->number_threads= number_threads;
   pool->threads_array = malloc(sizeof(pthread_t) * pool->number_threads);
   pool->block_size = 0;
   //pool->working_threads = 0;
   pool->flags = (Flags*) malloc(sizeof(Flags) * 1);
   pool->flags->read_permission = true;

   //pool->flags = flags;

   pool->q = (Queue*) malloc(sizeof(Queue) * 1);
   pool->q->task_content = NULL;

   Task* t = create_task(strdup(path_name));
   pool->q->head = t;
   pool->q->tail = t;
   pool->q->tasks_pending = 0;
   //pool->flags->read_permission = false;
   //printf("is queue empty %d\n", check_if_queue_is_empty(pool->q));

   pthread_cond_init(&pool->cond_var,NULL);
   pthread_cond_init(&pool->q->Queue_signal, NULL);

   if(pthread_mutex_init(&pool->q->lock, NULL) != 0){
      perror("pthread_mutex failed\n");
      exit(EXIT_FAILURE);
   }
   //pthread_mutex_init(&pool->q->Queue_lock, NULL);

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
   //lstat(full_path,&buffer);
   *temp_size = buffer->st_blocks + *temp_size;
   //printf("The directory's  file_counted [%s]  ___  ", full_path);
   //printf("size ==  %ld\n",buffer.st_blocks);
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
   //printf("res %d", check_if_arg_is_file(buffer));

   if(!check_if_directory(&buffer) && check_if_read_permissions(task->file_name)){
      // refactor that piece of code. 
      counting_size(&buffer,&pool->block_size);
      pthread_mutex_lock(&pool->q->lock);
      //printf("\nfile_name [it should be file not Dir] %s\n",task->file_name);
      pool->q->tasks_pending--;
      //pool->working_threads--;
      //pthread_cond_broadcast(&pool->cond_var);
      pthread_mutex_unlock(&pool->q->lock);
      free(task->file_name);
      free(task);
      return;
   }

   else if(check_if_directory(&buffer) && check_if_read_permissions(task->file_name)){
      
      //printf("check_if_directory %d ", check_if_directory(buffer));
      struct dirent* dir_read;
      //printf("file _name_after_dequeue = %s\n", task->file_name);

      DIR* dir = opendir(task->file_name);

      if(dir == NULL){
         perror("something went wrong\n");
         exit(EXIT_FAILURE);
      }
      //pthread_mutex_lock(&pool->q->lock);

      unsigned int temp_size = 0;
      //lstat(task->file_name,&buff);
     
      counting_size(&buffer,&temp_size);
      
      //pthread_mutex_unlock(&pool->q->lock);
   
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
                  // flag can be used to indicate permission has been denied.
               }
            }
         }
         // we will only count the directory size when it is readable, no symbolic link.
         else {
            counting_size(&buff,&temp_size);
         }
      }
      // synchronization of file size
      //pthread_mutex_lock(&pool->q->lock);
      pthread_mutex_lock(&pool->q->lock);
      pool->block_size = pool->block_size + temp_size;
      //printf("current_size == %d\n",pool->block_size);
      pool->q->tasks_pending--;
      //pool->working_threads--;
      pthread_cond_broadcast(&pool->cond_var);
      pthread_mutex_unlock(&pool->q->lock);
      closedir(dir);
      free(task->file_name);
      free(task);
      //pthread_mutex_unlock(&pool->q->lock);
      return;
   }

}

void* thread_function (void* p){

   ThreadPool* pool = (ThreadPool*) p;

   while(1){
     // to ensure that nothing can manipulate the pool's member.
      pthread_mutex_lock(&pool->q->lock);
      //clock_t start = clock();
      //printf("the the thread is running\n");
      while(check_if_queue_is_empty(pool->q)){
         // that means there is no task to to do.
         //printf("the queue is empty ___ task_pending %d\n", pool->q->tasks_pending);
         if(pool->q->tasks_pending == 0){
            pthread_mutex_unlock(&pool->q->lock);
            //printf("exiting from the thread pool\n");
            //clock_t finish = clock();
            //printf("the time it dir or size for a thread is %lf (PER_SEC) \n", (double)(finish-start) / CLOCKS_PER_SEC);
            return NULL;
         }
         // otherwise, let the threads to wait..aka sleep.
         else{
            pthread_cond_wait(&pool->cond_var, &pool->q->lock);
         }
      }
      // we can assume that there is work to do, so increment the current_working_thread var.
      //pool->working_threads++;
      //pool->q->tasks_pending++;
      // get the task from the queue.
      Task* task = dequeue_task_from_queue(pool->q);
      //printf("task_pending %s", task->file_name);
      // let other threads to work so we choose to the it unlock.

      //clock_t begin = clock();
      pthread_mutex_unlock(&pool->q->lock);  
      executing_task_function(task, pool);
      //clock_t end = clock();
      //printf("time taken to execute a task is %lf per_sec \n", (double) (end - begin) /CLOCKS_PER_SEC);
   

      //free(task);
      //pthread_mutex_unlock(&pool->q->lock);
   }

   return NULL;
}



bool check_symbolic_link(struct stat* buffer){
   //struct stat buffer;
   //lstat(path_name, &buffer);
   return S_ISLNK(buffer->st_mode);
}
