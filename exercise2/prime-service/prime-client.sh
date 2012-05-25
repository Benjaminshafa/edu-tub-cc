#!/bin/bash
pushd $(dirname $0) > /dev/null
SCRIPTPATH=$(pwd)
popd > /dev/null

java -cp $SCRIPTPATH/lib/prime-service-0.1.jar:$SCRIPTPATH/lib/cxf-manifest.jar de.tu_berlin.cit.prime.client.Main $1 $2
