#include "Queue.h"


void* safe_malloc(size_t size, void* ptr){
   ptr = malloc(size);
   if(ptr == NULL){
      perror("something failed with malloc");
      exit(EXIT_FAILURE);
   }
   return ptr;
}


Queue* create_Queue(const char* path){

   Queue* q = NULL;
   q = safe_malloc(sizeof(Queue)* 1, q);
   Task* t = create_task(strdup(path));

   q->head = t;
   q->tail = t;
   q->tasks_pending = 0;
   
   return q;
}

void enqueue_task_into_queue (Queue* queue, char* file_name){

   pthread_mutex_lock(&queue->lock);
   // if first element.
   Task* task = create_task(strdup(file_name));

   if(check_if_queue_is_empty(queue)){
      queue->head = task; 
      queue->tail = task; 
   }
   // for other cases..
   else{

      queue->tail->next_task = task; 
      queue->tail = task; 
   }
   pthread_cond_signal(&queue->cond_var);
   pthread_mutex_unlock(&queue->lock); 

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
   
   Task* t = NULL;
   t = safe_malloc(sizeof(Task)* 1, t);
   t->file_name = path_name;
   t->next_task = NULL;
   return t;  
}
