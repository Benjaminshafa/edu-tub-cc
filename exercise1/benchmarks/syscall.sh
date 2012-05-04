#!/bin/bash

echo "Compiling syscall.c (requires GNU compiler collection)"
gcc -O -o syscall syscall.c -lm

echo "Running syscall benchmark"

if [ "$SYSTEMROOT" = "C:\Windows" ] ; then
	./syscall.exe
else
	./syscall
fi
