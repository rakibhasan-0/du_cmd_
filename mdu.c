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
#include "Thread_pool.h"


/**
 * @brief   In that program we will determine the size of the directory or a file with threads or without threads.
 *          It was equivelent to the du -l -s -B512 file/dir cmd line in the terminal. 
 * 
 * 
 * 
 * @author  Gazi Md Rakibul Hasan.[ens20ghn]
 * @date    2022-11-19
 */

int main(int argc, char** argv){

   int opt, number_threads; 
   bool threads_given = false;
 
   while ((opt = getopt(argc, argv, "j:")) != -1){
      switch(opt){
         case 'j':
            number_threads = atoi(optarg);
            threads_given = true;
            break; 
         case '?': 
            perror("something wrong has been passed\n");
            exit(EXIT_FAILURE); 
      }
   }
   
   if(!threads_given){
      //count_directory_and_file_size_without_thread(argc, argv, flags);
      for(int i = optind; i < argc; i++){
         count_dir_size_by_threads(argv[i],1); 
      }
   }
   else{
      for(int i = optind; i < argc; i++){
         count_dir_size_by_threads(argv[i],number_threads); 
      }
   }
   return 0; 
}

