#!/bin/bash
pushd $(dirname $0) > /dev/null
SCRIPTPATH=$(pwd)
popd > /dev/null

pushd "$SCRIPTPATH" > /dev/null

nohup java -cp lib/prime-service-0.1.jar:lib/cxf-manifest.jar de.tu_berlin.cit.prime.server.Main </dev/null >server.log 2>&1  &

popd > /dev/null
