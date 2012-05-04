#!/bin/bash

echo "Compiling fork.c (requires GNU compiler collection)"
gcc -O -o fork fork.c -lm

echo "Running fork benchmark"

if [ "$SYSTEMROOT" = "C:\Windows" ] ; then
	./fork.exe
else
	./fork
fi
