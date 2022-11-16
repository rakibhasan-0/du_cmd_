#ifndef __QUEUE_H__
#define __QUEUE_H__

#include <pthread.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// the type of task.
typedef struct Task{
   char* file_name; 
   struct Task* next_task;
}Task; 


// the type of queue.
typedef struct Queue{
   pthread_mutex_t lock;  
   Task* head; 
   Task* tail; 
   int tasks_pending;
   pthread_mutex_t Queue_lock; 
   pthread_cond_t Queue_signal; 
}Queue; 



/**
 * @brief            it checks whether malloc can can allocated successfully or not.
 *
 * @param size       The given size to be allocated
 * @param ptr        a void pointer.
 * @return           if we can successfully allocated the memory then it will return the allocated memory a pointer t
 *                   points to that memory.
 */

void* safe_malloc(size_t size, void* ptr);


/**
 * @brief                     Enqueue a task into the queue.
 * 
 * @param queue               The queue.
 * @param file_name           The name of the directory. 
 */
void enqueue_task_into_queue (Queue* queue, char* file_name);



/**
 * @brief                     The task that will be dequeued form the queue.
 * 
 * @param queue               The queue.
 * @return                    The task which will be returned after dequeuing.
 */
Task* dequeue_task_from_queue(Queue* queue);




/**
 * @brief                     It check whether the queue is empty or not. 
 * 
 * @param queue               The queue.
 * @return                    It will return true if the queue is empty, otherwise, it will
 *                            return false.
 */
bool check_if_queue_is_empty(Queue* queue);



/**
 * @brief                     It creates a  task by taking a specific directory or file name.
 * 
 * @param path_name           The file or directory name.
 * @return                    It will create a task successfully in return.
 */
Task* create_task(char* path_name);



/**
 * @brief Create a Queue object
 * 
 * @param path 
 * @return Queue* 
 */
Queue* create_Queue(const char* path);


#endif