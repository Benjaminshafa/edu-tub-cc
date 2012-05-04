/*
**
** syscall.c        Benchmark that measures the time to do 50 million syscalls
**
** To compile:  gcc -O -o syscall syscall.c -lm
**
*/

#include <unistd.h>
#include <sys/syscall.h>
#include <sys/types.h>
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include <float.h>

static double second(void)
{
    return ((double)((double)clock()/(double)CLOCKS_PER_SEC));
}

void main(void)
{
  pid_t tid;
  unsigned int i;
  double time;

  time = second();
  for(i= 0; i< 50000000; i++)
  {
    tid = syscall(SYS_getpid);
  }
  time = second() - time;
  printf("Time for 50 million syscalls: %.3f\n", time);
}
