#!/bin/bash

git pull

img=""
from=""
to=""
transcribe=false
shellmode=false

# Parse arguments and pass to java
for arg in "$@"
do
    if [[ $arg == --img=* ]]
    then
        img=${arg:6}
    fi
    if [[ $arg == --from=* ]]
    then
        from=${arg:7}
    fi
    if [[ $arg == --to=* ]]
    then
        to=${arg:5}
    fi
    if [[ $arg == --tr ]]
    then
        transcribe=true
    fi
    if [[ $arg == --shell ]]
    then
        shellmode=true
    fi
done

export TESSDATA_PREFIX="`dirname $0`"
java -cp ./bin/:./lib/tess4j-1.3.0.jar translate.Driver --img="$img" --from="$from" --to="$to" --transcribe="$transcribe" --shell="$shellmode"
