#!/bin/bash

DST=$1
WAITTIME=900
EXECDISK=disk.sh
OUTDISK=disk
OLD=$(pwd)
DATE=$(date +%m-%d-%y_%T)
for i in $(seq 1 1 $2)
do
	./${EXECDISK} 2>&1 | ${OLD}/timestamp.sh | tee ${OLD}/${OUTDISK}-${DATE}.out
	sleep ${WAITTIME}
done
cd ${OLD}
