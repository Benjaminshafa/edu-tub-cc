#!/bin/bash

DST=$1
WAITTIME=900
EXECDISK=disk.sh
OUTDISK=disk
OLD=$(pwd)
DATE=$(date +%m-%d-%y)
NUM=$(ls ${OUTDISK}*.out 2>/dev/null | wc -l)

cd ${DST}
for i in $(seq 1 1 $2)
do
	./${EXECDISK} 2>&1 | ${OLD}/timestamp.sh | tee -a ${OLD}/${OUTDISK}_${DATE}_${NUM}.out
	sleep ${WAITTIME}
done
cd ${OLD}
