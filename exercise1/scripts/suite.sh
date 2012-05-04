#!/bin/bash

DST=$1
NUMLIN=$2
NUMMEMSWEEP=$2
NUMSYSCALL=$2
NUMFORK=$2
OUTLIN=lin
OUTMEMSWEEP=memsweep
OUTSYSCALL=sys
OUTFORK=fork
EXECLIN=linpack.sh
EXECMEMSWEEP=memsweep.sh
EXECSYSCALL=syscall.sh
EXECFORK=fork.sh
DATE=$(date +%m-%d-%y)

SUFLIN=$(ls ${OUTLIN}*.out 2>/dev/null | wc -l)
SUFMEMSWEEP=$(ls ${OUTMEMSWEEP}*.out 2>/dev/null | wc -l)
SUFSYSCALL=$(ls ${OUTSYSCALL}*.out 2>/dev/null | wc -l)
SUFFORK=$(ls ${OUTFORK}*.out 2>/dev/null | wc -l)

OLD=$(pwd)

cd ${DST}
${OLD}/execn.sh ${NUMLIN} ./${EXECLIN} | ${OLD}/timestamp.sh | tee ${OLD}/${OUTLIN_${DATE}_${SUFLIN}.out
${OLD}/execn.sh ${NUMMEMSWEEP} ./${EXECMEMSWEEP} | ${OLD}/timestamp.sh | tee ${OLD}/${OUTMEMSWEEP}_${DATE}_${SUFMEMSWEEP}.out
${OLD}/execn.sh ${NUMSYSCALL} ./${EXECSYSCALL} | ${OLD}/timestamp.sh | tee ${OLD}/${OUTSYSCALL}_${DATE}_${SUFSYSCALL}.out
${OLD}/execn.sh ${NUMFORK} ./${EXECFORK} | ${OLD}/timestamp.sh | tee ${OLD}/${OUTFORK}_${DATE}_${SUFFORK}.out

cd ${OLD}

