#ifndef __THREAD_POOL_H__
#define __THREAD_POOL_H__
#include "Queue.h"
#include <sys/stat.h>

//the structure of the flags.
typedef struct Flags{
   bool threads_given;
   bool first_time;
   bool read_permission;
}Flags;


//the structure of the thread_pool.
typedef struct ThreadPool{
   unsigned int block_size;
   Queue* q;
   pthread_cond_t cond_var; 
   pthread_t* threads_array; 
   int number_threads; 
   char* first_file; 
   //int working_threads;
   unsigned int time_count;
   Flags* flags;
}ThreadPool; 




/**
 * @brief                  The thread function while creating threads we will pass that function
 *                         as the argument.
 * 
 * @param p thread pool      It will be the thread pool which will be passed as the argument.
 */
void* thread_function (void* p);




/**
 * @brief                        In that function we will count the size of the directory by using threads.
 * 
 * @param path_name              The directory name.
 * @param number_threads         The number of threads.
 * @param size                   Current size of the directory.
 */
void count_dir_size_by_threads(char* path_name, int number_threads);




/**
 * @brief                     Ir will create a thread pool by taking given arguments.
 * 
 * @param path_name           The file name or the directory name.
 * @param number_threads      Total number of threads.
 * @param size                Current size of the directory or the file.
 */
ThreadPool* creates_thread_pool(int number_threads, char* path_name); 


/**
 * @brief                     It will check if the given argument is file or not.
 * 
 * @param arg                 The argument.
 * @return                    It will return true if the argument is file otherwise false.
 */
bool check_if_arg_is_file(struct stat* buffer);


/**
 * @brief                  It will check if the given argument is directory or not.
 * 
 * @param arg              The argument.
 * @return                 It will return true if the argument is directory otherwise false.
 */
bool check_if_directory(struct stat* st);


/**
 * @brief                  It will check if the given argument has the read permissions or not.
 * 
 * @param path_name        The argument.
 * @return                 It will return true if the argument has the read permissions otherwise false.
 */
bool check_if_read_permissions(const char* path_name);



/**
 * @brief                  It will check if the given argument contains the symbolic link or not.
 * 
 * @param path_name        The argument.
 * @return                 It will return true if the argument contains the symbolic link otherwise false.
 */
bool check_symbolic_link(struct stat* buffer);

#endif