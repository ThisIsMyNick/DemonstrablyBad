#!/bin/bash

img=""
from=""
to=""
transcribe=false
shellmode=false

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


java -cp ./bin/:./lib/tess4j-1.3.0.jar translate.Driver --img="$img" --from="$from" --to="$to" --transcribe="$transcribe" --shell="$shellmode"
