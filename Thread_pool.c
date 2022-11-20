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
 * @brief            it destroys those dynamically allocated memory for the task.
 * 
 * @param task       a task object.
 */
static void destroy_task(Task *task){
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
   pthread_cond_destroy(&pool->q->cond_var);
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
   pool->flags = safe_malloc(sizeof(Flags) *1, pool->flags);
   pool->flags->read_permission = true;
   pool->q = create_Queue(path_name);

   if(pthread_cond_init(&pool->q->cond_var,NULL) != 0){
      perror("pthread_cond_init failed");
      exit(EXIT_FAILURE);
   }

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
 * @brief                     In that function we will handle the error cases when we try to read a directory
* and                         which is not allowed read.
 * 
 * @param buff                The structure of the stat.
 * @param full_path           The directory name.
 * @param pool                The thread pool structure.
 */
static void permission_denied (struct stat buff, char* full_path, ThreadPool* pool){

   pthread_mutex_lock(&pool->q->lock);
   counting_size(&buff,&pool->block_size);
   pool->flags->read_permission = false;
   fprintf(stderr, "du: cannot read directory '%s': Permission denied\n",full_path);
   pthread_mutex_unlock(&pool->q->lock);

   return;   
}


/**
 * @brief                  In that function we will synchronized the directory's size.
 * 
 * @param temp_size        The task. 
 * @param pool             The thread pool structure.
 */
static void synchronization_of_dir_size(unsigned int* temp_size, ThreadPool* pool){

   pthread_mutex_lock(&pool->q->lock);
   pool->block_size = pool->block_size + *temp_size;
   pool->q->tasks_pending--;
   pthread_cond_broadcast(&pool->q->cond_var);
   pthread_mutex_unlock(&pool->q->lock);

   return;

}


/**
 * @brief                  In that function a directory will be traversed by threads. Meanwhile, the 
 *                         size of the directory will get synchronized.
 * 
 * @param buffer           The stat structure. 
 * @param task             The task to. 
 * @param pool             The thread pool structure.
 */
static void traversing_of_directory(struct stat buffer, Task* task, ThreadPool* pool){

   struct dirent* dir_read;
   struct stat buff;
   unsigned int temp_size = 0;

   DIR* dir = opendir(task->file_name);
   if(dir == NULL){
      perror("something went wrong\n");
      exit(EXIT_FAILURE);
   }

   counting_size(&buffer,&temp_size);
   
   // traversing of the directory.
   while((dir_read = readdir(dir)) != NULL){
      //getting full path 
      char full_path[PATH_MAX +1 ] = {0};
      snprintf(full_path,sizeof(full_path)-1, "%s/%s",task->file_name,dir_read->d_name);
      if(lstat(full_path, &buff) != 0){
         perror("stat failed\n");
         exit(EXIT_FAILURE);
      }

      if(check_if_directory(&buff)){
         if (strcmp(dir_read->d_name, "..") != 0 && strcmp(dir_read->d_name, ".") != 0) {
            if(check_if_read_permissions(full_path)){
               enqueue_task_into_queue(pool->q,full_path);
            }
            else{
               permission_denied(buff,full_path, pool);               
            }
         }
      }

      else {
         counting_size(&buff,&temp_size);
      }
   }
   // synchronization of dir size
   synchronization_of_dir_size(&temp_size, pool);
   closedir(dir);

   return;

}


/**
 * @brief                        it will execute each task which will execute the specified task which will be
 *                               given by the thread.
 * 
 * @param task                   The task to execute.
 * @param pool                   The thread pool.
 */
static void execution_of_task (Task* task, ThreadPool* pool){

   struct stat buffer;
   if(lstat(task->file_name,&buffer) != 0){
      perror(" stat failed");
      exit(EXIT_FAILURE);
   }

   // in case first element is the a file.
   if(!check_if_directory(&buffer) && check_if_read_permissions(task->file_name)){
      pthread_mutex_lock(&pool->q->lock);
      counting_size(&buffer,&pool->block_size);
      pool->q->tasks_pending--;
      pthread_mutex_unlock(&pool->q->lock);
      destroy_task(task);
      return;
   }
   // now we can assume that it is a directory.
   else if(check_if_directory(&buffer) && check_if_read_permissions(task->file_name)){
      traversing_of_directory(buffer, task, pool);
      destroy_task(task);
      return;
   }

}


void* thread_function (void* p){

   ThreadPool* pool = (ThreadPool*) p;

   while(1){
     // to ensure that nothing can manipulate the pool's member.
      pthread_mutex_lock(&pool->q->lock);
      while(check_if_queue_is_empty(pool->q)){
         // that means there is no task to do and the function will return NULL.
         if(pool->q->tasks_pending == 0){
            pthread_mutex_unlock(&pool->q->lock);
            return NULL;
         }
         // otherwise, let the threads to wait..aka sleep.
         else{
            pthread_cond_wait(&pool->q->cond_var, &pool->q->lock);
         }
      }
      // get a task from the queue.
      Task* task = dequeue_task_from_queue(pool->q);
      // now we can release the mutex so that an another thread enter into the critical section.
      pthread_mutex_unlock(&pool->q->lock);  
      execution_of_task(task, pool);
   }

   return NULL;
}



bool check_symbolic_link(struct stat* buffer){
   return S_ISLNK(buffer->st_mode);
}
