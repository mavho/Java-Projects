#!/bin/bash
echo -n "Time,"
cat /data/mgr/html/output_stat.txt | egrep "Drops:|Overflow|overflow|NoFilter" | awk -vORS=, ' {if($3 ~ /^[0-9]+$/) {print $1 $2}else {print $1 $2 $3}} ' | sed 's/,$/\n/'
while true; do
	NOW=$(date +"%T")
	echo -n "$NOW,"
	cat /data/mgr/html/output_stat.txt | egrep "Drops:|Overflow|overflow|NoFilter" | awk -vORS=, ' {if($3 ~ /^[0-9]+$/) {print $3} else {print $4}} ' | sed 's/,$/\n/'
	sleep 1
done &
