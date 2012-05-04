#!/bin/bash

DST=$1
WAITTIME=5
EXECDISK=disk.sh
OUTDISK=disk.out
OLD=$(pwd)

cd ${DST}
for i in $(seq 1 1 $2)
do
	./${EXECDISK} | ${OLD}/timestamp.sh | tee ${OLD}/${OUTDISK}
	sleep ${WAITTIME}
done
cd ${OLD}
