/*
**
** fork.c        Benchmark that forks 1 million processes in sequence
**
** To compile:  gcc -O -o fork fork.c -lm
**
*/

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include <float.h>

double second(void)
{
    return ((double)((double)clock()/(double)CLOCKS_PER_SEC));
}

void main(void)
{
  unsigned int i;
  double time;

  time = second();
  for (i = 0; i < 1000000; i++) {
    if (fork() == 0)
    {
      return;
    }
  }
  time = second() - time;
  printf("Time for 1 million forks: %.3f seconds\n", time);
}
