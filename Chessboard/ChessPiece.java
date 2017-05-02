
public class ChessPiece {
	int row;
	int col;
	char type;
	public ChessPiece(){
		
	}
	public ChessPiece(int c, int r, char t) {
		type = t;
		col = c;
		row = r;
	}

	boolean canAttack(ChessPiece c) {
		return true;
	}
	boolean canMove(ChessPiece c, int row, int col){
		return true;
	}
	boolean isBlocked(ChessPiece c){
		return true;
	}
	public String getPosition(){
		String position = col + " " + row;
		return position;
		
	}
	public char getType(){
		return type;
	}
	
	public String toString() { //Prints the piece's position in the format "col row"
		return (type) + " "+ (col + 1) + " " + (row + 1) + " ";
	}
}
