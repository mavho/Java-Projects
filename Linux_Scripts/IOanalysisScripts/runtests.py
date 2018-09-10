#@author: Maverick Ho


import sys
import csv
import time
import timeit
import signal
import datetime

#run iostat from outside
def main():
	global start
	start = timeit.default_timer()
	if sys.argv[1] == 'io':
		iotest()
	elif sys.argv[1] == 'vm':
		vmtest()
	else:
		print "Not a valid configuration, specify io or vm"
	

def interrupt_handler(sig, frame):
	fd = open(sys.argv[2]+'.csv', 'a')
	stop = timeit.default_timer()
	seconds = stop - start
	fd.write(str(seconds))
	print("Exiting program")
	sys.exit(1)

def iotest():
#	start = timeit.default_timer()
	with open(sys.argv[2] + '.csv', 'wb') as f:
		iofieldnames  = ['time','%user', '%nice', '%system', '%iowait', '%steal', '%idle','device', 'tps', 'MB_read/s','MB_wrtn/s', 'MB_read', 'MB_wrtn']
		#fieldnames = []
		dw = csv.writer(f)
		dw.writerow([datetime.datetime.now()])
		dw.writerow(iofieldnames)
		final = []
		for line in iter(sys.stdin.readline,""):
			signal.signal(signal.SIGINT,interrupt_handler)
			line.strip()
			check = line.split()
			try:
				#print check
				if(check[0] == 'avg-cpu:'):
					info = sys.stdin.readline()
					info = info.strip().split()
					#print(info)
					final=[time.strftime("%H:%M:%S:"), info[0],info[1], info[2],info[3], info[4], info[5]]
				elif (check[0] == 'Device:'):
					deviceInfo =  sys.stdin.readline()
					deviceInfo = deviceInfo.strip().split()
					if(deviceInfo[0] == 'sda'):
						#print deviceInfo
						final.extend(deviceInfo)
						dw.writerow(final)
						del final[7:]
					#print deviceInfo
			
			except IndexError:
				pass

def vmtest():
	with open(sys.argv[2] + '.csv', 'wb') as f:
		vmfieldnames = ['time','r', 'b', 'swpd', 'free','buff','cache', 'si', 'so', 'bi', 'bo', 'in', 'cs', 'us', 'sy', 'id', 'wa', 'st']
		iw = csv.writer(f)
		iw.writerow([datetime.datetime.now()])
		iw.writerow(vmfieldnames)
		for line in iter(sys.stdin.readline, ""):
			signal.signal(signal.SIGINT,interrupt_handler)
			line.strip()
			data = line.split()
			#print(data)
			if data[0] in ['procs', 'r']:
				continue
			iw.writerow([time.strftime("%H:%M:%S")] + data)	
		
if __name__ == "__main__":
	main()
