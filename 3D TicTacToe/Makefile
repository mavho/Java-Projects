NQueens: TicTacToe.class
	echo Main-class: TicTacToe > temp
	jar cvfm TicTacToe.jar temp TicTacToe.class
	rm temp

TicTacToe.class: TicTacToe.java
	javac -Xlint TicTacToe.java

clean:
	rm -f TicTacToe.class
