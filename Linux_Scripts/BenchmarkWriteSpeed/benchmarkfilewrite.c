//@author: Maverick Ho
 

#include<stdlib.h>
#include<stdio.h>
#include<fcntl.h>
#include<sys/timeb.h>

//Write 1M chunks
#define BUFFERSIZE (1024*1024)
//For reeadability. But really the same
#define MB (1024*1024)

main(int argc, char* argv[]){
	long megabyte, a, b;
	FILE *outfile;
	char buffer[BUFFERSIZE];
	//We want the time before file is created, and then after
	struct timeb before, after;

	if (argc != 3){
		printf("Put in two arguements, first being the out file and the next being size in MB\n");
		return 0;
	}

	for (a = 0; a < BUFFERSIZE; a++){
		buffer[a] = '0';
	}
	//For reference for the next part, 'A book on C', page 516
	outfile = fopen(argv[1],"w");
	megabyte = atol(argv[2]);
	
	//start timeer
	ftime(&before);
	//Write to the file.
	for(b = 0; b < ((MB/BUFFERSIZE)*megabyte); b++){
		fwrite(buffer, 1, BUFFERSIZE,outfile);
	}
	ftime(&after);
	long finish_time = after.time - before.time;
	
	printf("Bytes per second written to file is: %u\n Time to finish writting the file is: %ld\n", (MB*megabyte)/finish_time, finish_time);

	fclose(outfile);
	return 0;
}
