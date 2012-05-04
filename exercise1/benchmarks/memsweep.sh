#!/bin/bash

echo "Compiling memsweep.c (requires GNU compiler collection) "
gcc -O -o memsweep memsweep.c -lm

echo "Running memsweep benchmark"

if [ "$SYSTEMROOT" = "C:\Windows" ] ; then
	./memsweep.exe
else
	./memsweep
fi
