/*
 * Maverick Ho, Sanjana Natraj
 * Goal of the assignment is to read from a file
 * chess positions to see if they can attack each other
 * and identify what piece is at a certain position. 
 *
 *
 *Does not work for query position, works for validity and checking Attacks
 */
import java.io.*;
import java.util.*;

public class Chessboard {
	
	
	public static void main(String[] args) throws IOException{
		//reads the file called "input.txt"
		Scanner in = new Scanner(new File("input.txt"));
		System.out.println();
		//makes a link list of pieces, that will be continually added.
		//LinkedList <ChessPiece> board = new LinkedList <ChessPiece>();
		//LinkedList board = new LinkedList();
		FileWriter f0 = new FileWriter("analysis.txt");
		LinkedList board = new LinkedList();
		int lineNumber = 0;
			while (in.hasNextLine()){
				lineNumber++;
				//Separates the characters into tokens
				String line = in.nextLine().trim() + " ";
				String[] inputs = line.split("\\s+");
				//System.out.println(line);
				int j = inputs.length;
				//makes the size of the board
				if(lineNumber % 2 == 1){
				board = new LinkedList();
				int size = Integer.parseInt(inputs[0]);
				//looks at the tokens, puts them into a String array and
				//puts the pieces into their respective class
					for (int i = 1; i < j; i = i + 3){
					//creates "nodes", in this case pieces, in the LinkedList board
					if(inputs[i].equals("q")){
						Queen q = new Queen(Integer.parseInt(inputs[i+1]), Integer.parseInt(inputs[i+2]),('q'));
						board.insert(q);
						}
						else if(inputs[i].equals("Q")){
							Queen q = new Queen(Integer.parseInt(inputs[i+1]), Integer.parseInt(inputs[i+2]),('Q'));
							board.insert(q);
						}
						else if(inputs[i].equals("k")){
							King k = new King(Integer.parseInt(inputs[i+1]), Integer.parseInt(inputs[i+2]),('k'));
							board.insert(k);
						}		
						else if(inputs[i].equals("K")){
							King k = new King(Integer.parseInt(inputs[i+1]), Integer.parseInt(inputs[i+2]),('K'));
							board.insert(k);
						}
						else if(inputs[i].equals("b")){
							Bishop b = new Bishop(Integer.parseInt(inputs[i+1]), Integer.parseInt(inputs[i+2]),('b'));
							board.insert(b);
						}
						else if(inputs[i].equals("B")){
							Bishop b = new Bishop(Integer.parseInt(inputs[i+1]), Integer.parseInt(inputs[i+2]),('B'));
							board.insert(b);
						}
						else if(inputs[i].equals("r")){
							Rook r = new Rook(Integer.parseInt(inputs[i+1]), Integer.parseInt(inputs[i+2]), ('r'));
							board.insert(r);
						}
						else if(inputs[i].equals("R")){
							Rook r = new Rook(Integer.parseInt(inputs[i+1]), Integer.parseInt(inputs[i+2]),('R'));
							board.insert(r);
						}
						else if(inputs[i].equals("n")){
							Knight n = new Knight(Integer.parseInt(inputs[i+1]), Integer.parseInt(inputs[i+2]), ('n'));
							board.insert(n);
						}
						else if(inputs[i].equals("N")){
							Knight n = new Knight(Integer.parseInt(inputs[i+1]), Integer.parseInt(inputs[i+2]),('N'));
							board.insert(n);
						}
					} //end for loop
					
				}else {
					int qcol = Integer.parseInt(inputs[0]);
					int qrow = Integer.parseInt(inputs[1]);
					//System.out.println(qcol + " "+ qrow);
					if(board.checkValidity() == false){
						f0.write("invalid "+ "\n");
					}else{
					f0.write(board.find(qcol, qrow));//checks query piece, does not work for some reason
					if(board.verifyAttack() != null){
						//if the board is valid verify attack
						f0.write(board.verifyAttack());
					}else {
						f0.write("-" + "\n");
					}
					}
				}
			} //end while
			f0.close();
	} //end main

	/*private static boolean checkValidity(String[] array) {
			for(int i = 0 ; i < array.length; i++){
				for(int j = 0; j < array.length; j++){
					if(array[i].equals("k") && array[j].equals("K")){
						return true;
					}
				}
			}
		return false;
	}//checks validity*/
} //end class
