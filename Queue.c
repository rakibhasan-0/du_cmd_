#include "Queue.h"


void enqueue_task_into_queue (Queue* queue, char* file_name){

   //pthread_mutex_lock(&queue->lock);
   // if first element.
   Task* task = create_task(strdup(file_name));

   if(check_if_queue_is_empty(queue)){
      queue->head = task; 
      queue->tail = task; 
   }
   // for other cases..
   else{
      //task->prev_task = queue->tail; 
      queue->tail->next_task = task; 
      queue->tail = task; 
   }
   //pthread_cond_signal(&queue->Queue_signal);
   //pthread_mutex_unlock(&queue->lock); 

} 

Task* dequeue_task_from_queue(Queue* queue){

   Task* temp_task = NULL; 
   //pthread_mutex_lock(&queue->Queue_lock);

   if(!check_if_queue_is_empty(queue)){

      //pthread_mutex_lock(&queue->Queue_lock);
      temp_task = queue->head; 
      queue->head = temp_task->next_task; 
      queue->tasks_pending++; 
      temp_task->next_task = NULL; 
      //pthread_mutex_unlock(&queue->Queue_lock);

   }
   //pthread_mutex_unlock(&queue->Queue_lock);
   return temp_task;
}


bool check_if_queue_is_empty(Queue* queue){

   if(queue->head == NULL){
      return true;
   }
   return false;
}


Task* create_task(char* path_name){
   
   Task* t = (Task*)malloc(sizeof(Task));

   t->file_name = path_name;
   t->next_task = NULL;
   //t->prev_task = NULL;

   return t;  
}
