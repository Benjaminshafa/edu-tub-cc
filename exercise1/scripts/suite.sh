#!/bin/bash

DST=$1
NUMLIN=$2
NUMMEMSWEEP=$2
NUMSYSCALL=$2
NUMFORK=$2
OUTLIN=lin.out
OUTMEMSWEEP=memsweep.out
OUTSYSCALL=sys.out
OUTFORK=fork.out
EXECLIN=linpack.sh
EXECMEMSWEEP=memsweep.sh
EXECSYSCALL=syscall.sh
EXECFORK=fork.sh

OLD=$(pwd)

cd ${DST}
echo ${OLD}
${OLD}/execn.sh ${NUMLIN} ./${EXECLIN} | ${OLD}/timestamp.sh | tee ${OLD}/${OUTLIN}
${OLD}/execn.sh ${NUMMEMSWEEP} ./${EXECMEMSWEEP} | ${OLD}/timestamp.sh | tee ${OLD}/${OUTMEMSWEEP}
${OLD}/execn.sh ${NUMSYSCALL} ./${EXECSYSCALL} | ${OLD}/timestamp.sh | tee ${OLD}/${OUTSYSCALL}
${OLD}/execn.sh ${NUMFORK} ./${EXECFORK} | ${OLD}/timestamp.sh | tee ${OLD}/${OUTFORK}

cd ${OLD}

