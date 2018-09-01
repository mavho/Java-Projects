#!/bin/bash
#while true; do sleep 1; echo -e "$(date)\n$(cat /proc/meminfo | egrep -w "Dirty|Writeback")" >> dirty.csv; done &

#headers
echo -n "Time,"
cat /proc/meminfo| egrep "Dirty:|Writeback" | awk -vORS=, ' {print $1} ' | sed 's/,$/\n/'
while true; do
	NOW=$(date +"%T")
	echo -n "$NOW,"
	#sed gets rid of the trailing, very useful
	cat /proc/meminfo| egrep "Dirty:|Writeback" | awk -vORS=, ' {print $2} ' | sed 's/,$/\n/'
	#cat /proc/meminfo | awk -vORS=, ' {print $2} ' | sed 's/,$/\n/'
	sleep 1
done &
#while true; do sleep 1; cat /data/mgr/html/output_stat.txt | grep -w "Port1"| python check_overflow.py; done &
