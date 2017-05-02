/*
 * Maverick Ho, Sanjana Natraj
 * CMPS12B
 * Goal is to do the NQUEEN problem.
 * 
 * Used code in the algorithm backtracking part from this site
 * "http://www.geeksforgeeks.org/backtracking-set-3-n-queen-problem/"
 * However, I have commented out what I did and changed the logic heavily.
 * 
 *Only solves for the Original NQueenProblem, only takes in size of the board and first column.
 *The row input is completely ignored
 */



import java.io.*;
public class NQueens{
	
	//so it only prints one solution
	private static int answers = 0;
	public static void main (String args[]) throws IOException{
		FileWriter f0 = new FileWriter("solution.txt");
		String newLine = System.getProperty("line.separator");
		int length = 0;
		int iCol = 0; //initial queen placed on a column
		int iRow = 0; //initial queen placed on a row
		if (args.length == 3){
			try{
				length = Integer.parseInt(args[0]);
				iCol = Integer.parseInt(args[1]);
				iRow = Integer.parseInt(args[2]);
			} catch (NumberFormatException e){
				System.out.println("Please put an integer.");
			}
		}
		if (length < 4 || iCol < 1){
			f0.write("No Solution" + newLine);
			f0.close();
			System.exit(1);
		} 
		
		int[] board = new int[length]; //int array to make board
		boolean[] empty = new boolean[length]; //boolean array to see if you can put a queen	
		for (int i = 0; i < length; i++){
			empty[i] = true; /*Initially put everything on the board as true
			therefore can put a queen */
		}
		//Calls the Recursion Algorithm method
		//(n-1) from class
		placequeen(empty, board, iCol - 1, length - 1, iRow - 1);
		
	}
	
	

	//Given that col is column, length is row
	private static void placequeen(boolean[] empty, int[] board, int col, int queens, int row) throws IOException {
		/*The basecase, once the amount of col's is gone it means
		the code has succeeded.*/
		if (col > queens){
				if(answers < 1){
					printBoard(board);
				}
			answers++;
		}
		else{
			
			for (row = 0; row <= queens; row++){
				//Right, so if it is empty, it is safe to place
				if (empty[row] == true){
					boolean safe = true;
					for (int i = 0; i < col; i++){
						//covers the places you can't put the queen
						if (row == board[i] + col - i || row == board[i] - col + i){
							safe = false;
							break;
						}
					}
					if (safe){
						empty[row] = false;
						//places queen 
						board[col] = row; 
						//goes to the next column
						placequeen(empty, board, col + 1, queens,row);
						empty[row] = true;
					}
				}
			}
		}
	}//placequeen

	private static void printBoard(int[] board) throws IOException {
		//A way to create the output of the for loop to a text file
		//uses method getProperty to use line.separator
		FileWriter f0 = new FileWriter("solution.txt");
		String newLine = System.getProperty("line.separator");
		
		for (int i = 0; i <board.length; i++){
			for (int j = 0; j < board.length; j++){
				//prints out the coordinates
				if (board[i] == j){
					//makes output in the file
					f0.write((board[j]+1) +" " + (board[i]+1 + newLine));
				}
			}
		}
		f0.close();
		// Prints out the Board
		System.out.println();
		//Loops over the columns of the board
		//for (int j = 0; j < board.length; j++){
		for (int i = 0; i <board.length; i++){
			System.out.print("|");
			//loops over the rows
			for (int j = 0; j < board.length; j++){
				if (board[j] == i){
					//Q represents Queen
					System.out.print("Q");
				}
				else{
					//Represents empty places
					System.out.print("_");
				}
				//Prints out another "wall" or "divider"
				System.out.print("|");
			}
			System.out.println();
		}
	}
}