/*
 * Maverick Ho (mmho@ucsc.edu)
 *********************************************
 * The goal of this project is to create a
 * 4x4x4 tic tac toe game against a computer.
 * The player moves by typing a three digit
 * integer, which marks a space into the board.
 */

import java.io.*;
import java.util.*;

public class TicTacToe {
	/*basically the computer's turn
	makes sure the computer doesn't move
	twice trying to block a winning fork*/
	static boolean computerMoved = false;
	static int[] sums = new int[76];
	//array for the 4x4x4 board
	static int [][][] board = new int [4][4][4];
	//computer's move
	static String move = "no where.";
	//Possible ways of winning
    static final int[][][] lines = {
	{{0,0,0},{0,0,1},{0,0,2},{0,0,3}},  //lev 0; row 0   rows in each level     
	{{0,1,0},{0,1,1},{0,1,2},{0,1,3}},  //       row 1     
	{{0,2,0},{0,2,1},{0,2,2},{0,2,3}},  //       row 2     
	{{0,3,0},{0,3,1},{0,3,2},{0,3,3}},  //       row 3     
	{{1,0,0},{1,0,1},{1,0,2},{1,0,3}},  //lev 1; row 0     
	{{1,1,0},{1,1,1},{1,1,2},{1,1,3}},  //       row 1     
	{{1,2,0},{1,2,1},{1,2,2},{1,2,3}},  //       row 2     
	{{1,3,0},{1,3,1},{1,3,2},{1,3,3}},  //       row 3     
	{{2,0,0},{2,0,1},{2,0,2},{2,0,3}},  //lev 2; row 0     
	{{2,1,0},{2,1,1},{2,1,2},{2,1,3}},  //       row 1     
	{{2,2,0},{2,2,1},{2,2,2},{2,2,3}},  //       row 2       
	{{2,3,0},{2,3,1},{2,3,2},{2,3,3}},  //       row 3     
	{{3,0,0},{3,0,1},{3,0,2},{3,0,3}},  //lev 3; row 0     
	{{3,1,0},{3,1,1},{3,1,2},{3,1,3}},  //       row 1 
	{{3,2,0},{3,2,1},{3,2,2},{3,2,3}},  //       row 2       
	{{3,3,0},{3,3,1},{3,3,2},{3,3,3}},  //       row 3           
	{{0,0,0},{0,1,0},{0,2,0},{0,3,0}},  //lev 0; col 0   columns in each level  
	{{0,0,1},{0,1,1},{0,2,1},{0,3,1}},  //       col 1    
	{{0,0,2},{0,1,2},{0,2,2},{0,3,2}},  //       col 2    
	{{0,0,3},{0,1,3},{0,2,3},{0,3,3}},  //       col 3    
	{{1,0,0},{1,1,0},{1,2,0},{1,3,0}},  //lev 1; col 0     
	{{1,0,1},{1,1,1},{1,2,1},{1,3,1}},  //       col 1    
	{{1,0,2},{1,1,2},{1,2,2},{1,3,2}},  //       col 2    
	{{1,0,3},{1,1,3},{1,2,3},{1,3,3}},  //       col 3    
	{{2,0,0},{2,1,0},{2,2,0},{2,3,0}},  //lev 2; col 0     
	{{2,0,1},{2,1,1},{2,2,1},{2,3,1}},  //       col 1    
	{{2,0,2},{2,1,2},{2,2,2},{2,3,2}},  //       col 2    
	{{2,0,3},{2,1,3},{2,2,3},{2,3,3}},  //       col 3    
	{{3,0,0},{3,1,0},{3,2,0},{3,3,0}},  //lev 3; col 0     
	{{3,0,1},{3,1,1},{3,2,1},{3,3,1}},  //       col 1
	{{3,0,2},{3,1,2},{3,2,2},{3,3,2}},  //       col 2
	{{3,0,3},{3,1,3},{3,2,3},{3,3,3}},  //       col 3
    {{0,0,0},{1,0,0},{2,0,0},{3,0,0}},  //cols in vert plane in front
    {{0,0,1},{1,0,1},{2,0,1},{3,0,1}},
    {{0,0,2},{1,0,2},{2,0,2},{3,0,2}},
    {{0,0,3},{1,0,3},{2,0,3},{3,0,3}},
    {{0,1,0},{1,1,0},{2,1,0},{3,1,0}},  //cols in vert plane one back
    {{0,1,1},{1,1,1},{2,1,1},{3,1,1}},
    {{0,1,2},{1,1,2},{2,1,2},{3,1,2}},
    {{0,1,3},{1,1,3},{2,1,3},{3,1,3}},
    {{0,2,0},{1,2,0},{2,2,0},{3,2,0}},  //cols in vert plane two back
    {{0,2,1},{1,2,1},{2,2,1},{3,2,1}},
    {{0,2,2},{1,2,2},{2,2,2},{3,2,2}},
    {{0,2,3},{1,2,3},{2,2,3},{3,2,3}},
    {{0,3,0},{1,3,0},{2,3,0},{3,3,0}},  //cols in vert plane in rear
        {{0,3,1},{1,3,1},{2,3,1},{3,3,1}},
        {{0,3,2},{1,3,2},{2,3,2},{3,3,2}},
        {{0,3,3},{1,3,3},{2,3,3},{3,3,3}},
        {{0,0,0},{0,1,1},{0,2,2},{0,3,3}},  //diags in lev 0
        {{0,3,0},{0,2,1},{0,1,2},{0,0,3}},
        {{1,0,0},{1,1,1},{1,2,2},{1,3,3}},  //diags in lev 1
        {{1,3,0},{1,2,1},{1,1,2},{1,0,3}},
        {{2,0,0},{2,1,1},{2,2,2},{2,3,3}},  //diags in lev 2
        {{2,3,0},{2,2,1},{2,1,2},{2,0,3}},
        {{3,0,0},{3,1,1},{3,2,2},{3,3,3}},  //diags in lev 3
        {{3,3,0},{3,2,1},{3,1,2},{3,0,3}},
        {{0,0,0},{1,0,1},{2,0,2},{3,0,3}},  //diags in vert plane in front
        {{3,0,0},{2,0,1},{1,0,2},{0,0,3}},
        {{0,1,0},{1,1,1},{2,1,2},{3,1,3}},  //diags in vert plane one back
        {{3,1,0},{2,1,1},{1,1,2},{0,1,3}},
        {{0,2,0},{1,2,1},{2,2,2},{3,2,3}},  //diags in vert plane two back
        {{3,2,0},{2,2,1},{1,2,2},{0,2,3}},
        {{0,3,0},{1,3,1},{2,3,2},{3,3,3}},  //diags in vert plane in rear
        {{3,3,0},{2,3,1},{1,3,2},{0,3,3}},
        {{0,0,0},{1,1,0},{2,2,0},{3,3,0}},  //diags left slice      
        {{3,0,0},{2,1,0},{1,2,0},{0,3,0}},        
        {{0,0,1},{1,1,1},{2,2,1},{3,3,1}},  //diags slice one to right
        {{3,0,1},{2,1,1},{1,2,1},{0,3,1}},        
        {{0,0,2},{1,1,2},{2,2,2},{3,3,2}},  //diags slice two to right      
        {{3,0,2},{2,1,2},{1,2,2},{0,3,2}},        
        {{0,0,3},{1,1,3},{2,2,3},{3,3,3}},  //diags right slice      
        {{3,0,3},{2,1,3},{1,2,3},{0,3,3}},        
        {{0,0,0},{1,1,1},{2,2,2},{3,3,3}},  //cube vertex diags
        {{3,0,0},{2,1,1},{1,2,2},{0,3,3}},
        {{0,3,0},{1,2,1},{2,1,2},{3,0,3}},
        {{3,3,0},{2,2,1},{1,1,2},{0,0,3}}        
    };
    public static void main(String[] args) throws FileNotFoundException{
    	//Gets positions from the setup files
		if (args.length != 0) {
			System.out.println("Getting initial setup");
			Scanner scanner = new Scanner(new FileInputStream(args[0]));
			int size = scanner.nextInt();
			for (int i = 0; i < size; i++) {
				int lvl = scanner.nextInt() % 4;
				int row = scanner.nextInt() % 4;
				int col = scanner.nextInt() % 4;
				int move = scanner.nextInt();
				turn(lvl * 100 + row * 10 + col, move);
			}
			//closes the scanner
			scanner.close();
		}
		showBoard(board);
		//next turn
		while(true){
			while(true){
				computerMoved = false;
				@SuppressWarnings("resource")
				Scanner scan = new Scanner(System.in);
				int x = 0;
				int y = 0;
				int z = 0;
				System.out.println("Type a 3 digit number for your move");
				int playerMove = scan.nextInt();
				//x = levels
				//y = rows
				//z = columns
				x = playerMove/100;
				y = (playerMove%100)/10;
				z = (playerMove %100)%10;
				try {
					if (playerMove < 0 || playerMove > 333){
						System.out.println("Next time, enter a 3 digit number, 0-3");
					}
					if (board[x][y][z]!= 0){
						System.out.println("OCCUPIED");
						break;
					}
					board[x][y][z] = 5;
					checkBoard(board);
				} catch (ArrayIndexOutOfBoundsException e){
					showBoard(board);
				}
			}
		}
	}
    
	private static void computerMove(int[][][] board) {
		//seed is already inputed
		Random rand = new Random();
		for (int i = 0; i < 76; i++){
			for (int j = 0; j < 4; j++){
				//Random spot on the board
				int m = rand.nextInt(3) + 1;
				int o = rand.nextInt(3) + 1;
				int p = rand.nextInt(3) + 1;
				move = m+ "" +""+ o +""+ p;
				if (board[m][o][p] == 0 && computerMoved == false){
					board[m][o][p] = 1;
					//plays a spot not played before
					showBoard(board);
					System.out.println("Computer has moved " + move);
					computerMoved = true;	
				}
			}
		}
	}
	//The computer checking the board.
	//if it can't win, block wins, make or block forks
	//then it makes a random move
	private static void checkBoard(int[][][] board) {
		for (int i = 0; i < 76; i++){
			int sum = 0;
			for (int j = 0; j < 4; j++){
				int a = lines[i][j][0];
				int b = lines[i][j][1];
				int c = lines[i][j][2];
				sum += board[a][b][c];
				//adds the spots in the lines
			}
			if(sum == 3 && computerMoved == false){
				for (int j = 0; j < 4; j++){
					int a = lines[i][j][0];
					int b = lines[i][j][1];
					int c = lines[i][j][2];
					//find an immediate win, a line
					if (board[a][b][c] == 0){
						board[a][b][c] = 1;
						showBoard(board);
						System.out.println("Computer Wins, playing the winning position "+ a +""+b+""+c);
						System.exit(1);
					}
				}
			}
			else if (sum == 15 && computerMoved == false){
				//blocks the player from winning
				for (int j = 0; j < 4 ; j++){
					int a = lines[i][j][0];
					int b = lines[i][j][1];
					int c = lines[i][j][2];
					sum += board[a][b][c];
					move = a + "" + b +""+ c +", blocking your move!";
					if (board[a][b][c] == 0){
						board[a][b][c] = 1;
						showBoard(board);
						System.out.println("Computer has moved " + move);
						computerMoved = true;
					}
				}
			}
			else if (sum == 20 && computerMoved == false){
				//concedes to the player
				showBoard(board);
				System.out.println("You win!");
				System.exit(1);
			}else if (checkFork(1) != -1) { //If computer can make a fork
				turn(checkFork(1), 1);
				showBoard(board);
			} else if (checkFork(5) != -1) { //If player can make a fork
				turn(checkFork(5), 1);
				showBoard(board);
			}
		//end of first for loop
		}
		/*if (checkFork(1) != -1) { //If computer can make a fork
			turn(checkFork(1), 1);
			showBoard(board);
		} else if (checkFork(5) != -1) { //If player can make a fork
			turn(checkFork(5), 1);
			showBoard(board);
		}*/
		computerMove(board);
	}
	//basically the previous code but I made a
	//method for it so I can checkForks easier
	public static void turn(int pos, int turn){
		int i = pos / 100; //first digit
		int j = (pos / 10) % 10; //second digit
		int k = pos % 10; //third digit	
		board[i][j][k] = turn; //Place a tile at the given position
		int sum = 0;
		for (int d = 0; d < 76; d++) {
			for (int[] space : lines[d]) {
				sum += board[space[0]][space[1]][space[2]];
			}
			sums[d] = sum; //Update the sums array
			sum = 0;
		}
	}
	public static int checkFork(int turn) {
		//forks are made from two separate lines that have two positions filled
		//if you connect those two lines, you create a condition that is winnable
		for (int i = 0; i < lines.length; i++) {
			for (int j = 0; j < lines.length; j++) {
				if (sums[i] == sums[j] && sums[i] == 2 * turn) {
					for (int[] k : lines[i]) {
						for (int[] l : lines[j]) {
							if (Arrays.equals(k, l) && board[k[0]][k[1]][k[2]] == 0) {
								return k[0] * 100 + k[1] * 10 + k[2];
							}
						}
					}
				}
			}
		}
		return -1;
	}
	public static void showBoard(int[][][] board) {
		//print out the 4x4x4 tic tac toe board
		//i is level, j is row, h is "column"
		for (int i = 3; i >= 0; i-- ){
			for (int j = 3; j >= 0; j--){
				for (int h = j + 1; h > 0; h--){
					System.out.print(" ");
                }
				//prints out the plane # and the row #
				System.out.printf(i + "" + j + "");
		for (int k = 0; k < 4; k++){
			//space isn't occupied, value is 0
			if (board[i][j][k] == 0)
				System.out.print(" _ ");
			if (board[i][j][k] == 1)
			//value of the computer move is 1
				System.out.print(" o ");
			
			if (board[i][j][k] == 5){
			//value of a user move is 5
				System.out.print(" x ");
			}
		  }
			System.out.println();
		}
			System.out.println();
	   }
		System.out.println("   0  1  2  3 \n");
		//System.out.println("Computer has moved " + move);
	}
}
	

