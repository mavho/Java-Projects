NQueens: NQueens.class
	echo Main-class: NQueens > temp
	jar cvfm NQueens.jar temp NQueens.class
	rm temp

NQueens.class: NQueens.java
	javac -Xlint NQueens.java

clean:
	rm -f NQueens.jar NQueens.class
