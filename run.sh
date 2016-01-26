#!/bin/bash

img=""
from=""
to=""
transcribe=false

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
done

if [[ $transcribe == true ]]
then
    java -cp ./bin/:./lib/tess4j-1.3.0.jar translate.Driver "$img" --from="$from" --to="$to" --transcribe
else
    java -cp ./bin/:./lib/tess4j-1.3.0.jar translate.Driver "$img" --from="$from" --to="$to"
fi
