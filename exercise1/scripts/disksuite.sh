#!/bin/bash

DST=$1
WAITTIME=900
EXECDISK=disk.sh
OUTDISK=disk
OLD=$(pwd)
DATE=$(date +%m-%d-%y-%T)

cd ${DST}
for i in $(seq 1 1 $2)
do
	./${EXECDISK} | ${OLD}/timestamp.sh | tee ${OLD}/${OUTDISK}-${DATE}.out
	sleep ${WAITTIME}
done
cd ${OLD}
