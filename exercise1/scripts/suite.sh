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
DATE=$(date +%D-%T)

OLD=$(pwd)

cd ${DST}
echo ${OLD}
${OLD}/execn.sh ${NUMLIN} ./${EXECLIN} | ${OLD}/timestamp.sh | tee ${OLD}/${OUTLIN}-${DATE}.out
${OLD}/execn.sh ${NUMMEMSWEEP} ./${EXECMEMSWEEP} | ${OLD}/timestamp.sh | tee ${OLD}/${OUTMEMSWEEP}-${DATE}.out
${OLD}/execn.sh ${NUMSYSCALL} ./${EXECSYSCALL} | ${OLD}/timestamp.sh | tee ${OLD}/${OUTSYSCALL}-${DATE}.out
${OLD}/execn.sh ${NUMFORK} ./${EXECFORK} | ${OLD}/timestamp.sh | tee ${OLD}/${OUTFORK}-${DATE}.out

cd ${OLD}

