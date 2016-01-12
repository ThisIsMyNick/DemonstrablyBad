#!/bin/bash

DEVLOG="devlog.txt"

function group_commits_by_date() {
    while read date; do
        echo "[$date]"
        cat <(git log --no-merges --format="    [%h] %cn: %s" --since="$date 00:00:00" --until="$date 24:00:00" | sed 's/ThisIsMyNick/Nobel Gautam/g')
        echo  ""
    done < <(git log --no-merges --format="%cd" --date=short | sort --unique --reverse)
}

printf "Devlog\n------\n\n" > $DEVLOG

group_commits_by_date >> $DEVLOG
echo "$(tac $DEVLOG | tac)" > $DEVLOG # Strip trailing new line
