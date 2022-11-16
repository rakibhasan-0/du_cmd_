CC = gcc
CFLAGS = -g -std=gnu11 -Wall -Wextra -Werror 

all: mdu

mdu: queue.o mdu.o thread_pool.o
	$(CC) $(CFLAGS) -o mdu mdu.o thread_pool.o queue.o -lpthread

mdu.o: mdu.c 
	$(CC) $(CFLAGS) -c mdu.c -o mdu.o 

thread_pool.o: Thread_pool.c Thread_pool.h 
	$(CC) $(CFLAGS) -c Thread_pool.c -o thread_pool.o

queue.o: Queue.c Queue.h
	$(CC) $(CFLAGS) -c Queue.c -o queue.o
	
clean:
	rm -rf *.o 
