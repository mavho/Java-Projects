#!/bin/bash
#The arguements go as follow, iostatfile vmstatfile meminfofile outputstatdumpfile
echo "Starting tests..."
iostat -m 1 | python runtests.py io $1 &
vmstat -S m 1 | python runtests.py vm $2 &
./meminfodump.sh >> $3.csv &
./outputstatdump.sh >> $4.csv &

echo "The generated files are $1.csv $2.csv $3.csv $4.csv"
while true 
do
	read -r -p 'Type (y/n) to stop the tests and continue to log phase?' command
	case "$command" in
		y|Y ) 
	echo "Next steps"
	break
	;;
		n|N ) 
	echo "No"
	;;
		* ) 
	echo "Type either y or n"
	;;
	esac
done

echo "Killing $1"
#Need only the first output like a stack
kill -2 $(pgrep -f 'runtests' | tail -2 | head -n 1)
echo "Killing $2"
kill -2 $(pgrep -f 'runtests' | tail -2 | head -n 1)
echo "Killing $3"
kill -9 $(pgrep -f 'meminfodump')
echo "Killing $4"
kill -9 $(pgrep -f 'outputstatdump')

while true
do
	read -r -p 'Do you want to generate a capmainanalysis file? You need pcstat in order to do this.(y/n)' answer
	case "$answer" in 
		y|Y )
	read -p "Name of the file?" filename
	echo "Generating pcstat file..."
	pcstat --terse /cifs/capmain/capmainshare/*.pcap > $filename
	echo "$filename generated"
	break
	;;
		n|N )
	echo "No pcstat file generated, moving on..."
	break
	;;
		* )
	echo "Type either y or n"
	;;
	esac
done

read -p "What would you like to name the log file?" log_file
touch $log_file
echo $(date '+%d/%m/%Y %H:%M:%S') >> $log_file

sysctl -a | grep dirty >> $log_file echo "Generating vmtouch statistics..."
vmtouch /cifs/capmain/capmainshare/*.pcap >> $log_file
echo "vmtouch statistics finished"

echo "Generating logfile..."
#Handle comma splitting
IFS=$','
#/data/mgr/html/output_stat.txt analysis.
{
read
overflowlistport0=()
no_overflow_port0=true
overflowvalport0=0

overflowvalport1=0
no_overflow_port1=true
overflowlistport1=()
while read time port0pkt port0bytes port0pktnofilter port0nofilterbytes port0drops port1pkt port1bytes port1pktnofilter port1bytenofilter port1drops
do
	if [ "$port0pkt" -ne "$overflowvalport0" ]
	then
		if [ "$no_overflow_port0" = true ]
			then
			echo "port0 overflowed at $time, dropping $port0pkt packets and $port0bytes bytes" >> $log_file	
			no_overflow_port0=false
		fi	
		overflowlistport0+=("{ $port0pkt : $time }")	
		overflowvalport0=$port0pkt
	fi
	
	if [ "$port1pkt" -ne "$overflowvalport1" ] 
	then
		if [ "$no_overflow_port1" = true ]
			then
			no_overflow_port1=false
			echo "port1 overflowed at $time, dropping $port1pkt packets and $port1bytes bytes" >> $log_file	
		fi
		overflowlistport1+=("{ $port1pkt : $time }")	
		overflowvalport1=$port1pkt
	fi
done

echo "Port0 overflow intervals:" ${overflowlistport0[@]} >> $log_file
echo "Port1 overflow intervals:" ${overflowlistport1[@]} >> $log_file

if [ "$no_overflow_port0" = true ]
then
	echo "No overflow was detected for port0" >> $log_file
fi

if [ "$no_overflow_port1" = true ]
then
	echo "No overflow was detected for port1" >> $log_file
fi

} < $4.csv

#this section will track the iostat csv file

{
read #timestamp
read #headers
value=0
while read time user nice system iowait steal idle device tps MBreadps MBwrtnps MBread MBwrtn
do
	value=$(echo "$value + $MBwrtnps" | bc)
	#echo $value >> $log_file
done
totallines=$(($(wc -l < captureio.csv) - 3))

average=$(echo "scale=2; $value/$totallines"| bc -l) 
echo Average Megabytes written per second: $average >> $log_file
} < $1.csv
unset totallines
unset average

#This section keep strack of /proc/sys/meminfo csv file
{
read #headers
dirtyvalue=0
writebackvalue=0
while read time dirty writeback writebacktmp
do
	dirtyvalue=$((dirtyvalue + dirty))
	writebackvalue=$((writebackvalue + writeback))
	#echo $value >> $log_file
done
totallines=$(($(wc -l < capturemem.csv) - 1))
echo $totallines
dirtyaverage=$(echo "scale=2; $dirtyvalue/$totallines"| bc -l) 
writebackaverage=$(echo "scale=2; $writebackvalue/$totallines"| bc -l) 
echo Average amount of dirty data in kB: $dirtyaverage >> $log_file
echo Average writeback in kB: $writebackaverage >> $log_file
} < $3.csv

echo "Logfile $log_file finished."
