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
 * @date    2022-11-16
 */


/**
 * @brief                  That function counts the size of directory.
 * 
 * @param path_name        The name of the directory 
 * @param total_size       Current size of the directory.
 * @param flags            The flags.
 */
void count_directory_size(char* path_name, long int* total_size, Flags* flags);



/**
 * @brief                  That function determines the size of a directory or a file in 512 bytes without
 *                         using any threads.
 * 
 * @param argc             The augments array. 
 * @param argv             The arguments counter.
 * @param flags            The flags.
 */
void count_directory_and_file_size_without_thread(int argc, char** argv, Flags* flags);




int main(int argc, char** argv){

   int opt; 
   int number_threads; 
   bool threads_given = false;
   //Flags* flags = (Flags*) malloc(sizeof(Flags));

   //flags->threads_given = false;
   //flags->first_time = true;
   //flags->read_permission = true;
 
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
         count_dir_size_by_threads (argv[i],1); 
      }
   }
   else{
      for(int i = optind; i < argc; i++){
         count_dir_size_by_threads (argv[i],number_threads); 
      }
   }

   //free(flags);
   return 0; 
}





void count_directory_size(char* path_name, long int* total_size, Flags* flags){

   struct dirent* dir_read; 
   struct stat buffer;

   DIR* dir = opendir(path_name);
   if(dir == NULL){
      perror("dir could not be opened\n");
      *total_size = -1;
      return;
   }

   lstat(path_name, &buffer);

   if(flags->first_time & !check_symbolic_link(&buffer) & check_if_read_permissions(path_name)){
      *total_size = buffer.st_blocks + *total_size;
      flags->first_time = false;
   }


   struct stat statbuf; 

   while((dir_read = readdir(dir)) != NULL){
      
      char full_path[PATH_MAX +1] = {0};
      snprintf(full_path,sizeof(full_path)-1, "%s/%s",path_name,dir_read->d_name);

      lstat(full_path,&statbuf);

      bool isDir = check_if_directory(&statbuf);
      bool read_permissions = check_if_read_permissions(full_path);
      
      if(!read_permissions){
         fprintf(stderr, "du: cannot read directory '%s': Permission denied\n",full_path);
         flags->read_permission = false;
      }

      if(isDir) {
         if (strcmp(dir_read->d_name, "..") != 0 && strcmp(dir_read->d_name, ".") != 0) {
            if(!check_symbolic_link(&statbuf)){
               *total_size = statbuf.st_blocks + *total_size;
            }
            else{
               closedir(dir);
               return;
            }
            if(read_permissions){
               count_directory_size(full_path,total_size, flags);
            }
         }
      }
      
      else{
         if(check_if_read_permissions(full_path) && !check_symbolic_link(&statbuf)){ 
            *total_size = statbuf.st_blocks + *total_size;
         }
      }
   }

   closedir(dir);
  
   return;
}



void count_directory_and_file_size_without_thread(int argc, char** argv, Flags* flags){

   for(int i = optind; i < argc; i++){  

      char* path_name = strdup(argv[i]);
      struct stat buffer;
      stat(path_name, &buffer);

      bool file_exists = check_if_arg_is_file(&buffer);
      bool dir_exists = check_if_directory(&buffer);
      bool read_permissions = check_if_read_permissions(path_name);
      long int total_size = 0;

      if(file_exists && read_permissions && !check_symbolic_link(&buffer)){
         //printf("file_exists %d\n", file_exists);
         struct stat file_st; 
         lstat(argv[i],&file_st);
         total_size += file_st.st_blocks;
         //printf("%ld\t%s\n",file_st.st_blocks,argv[i]); 
      }

      if(dir_exists) {
         //printf("dir_exists %d\n", dir_exists);       
         count_directory_size(argv[i],&total_size,flags); 
         flags->first_time = true;
      }
      if(total_size != -1 && read_permissions){
         if(flags->read_permission){
             printf("%ld\t%s\n",total_size,argv[i]);
         }
         if(!flags->read_permission){
            printf("%ld\t%s\n",total_size,argv[i]);
            free(path_name);
            free(flags);
            exit(EXIT_FAILURE);
         }
      }
      free(path_name);
   }
}
