
public class Rook extends ChessPiece{
	public Rook(int c, int r, char t) {
		type = t;
		col = c;
		row = r;
	}
	
	public boolean canAttack(ChessPiece c) {
		//Check if other piece is on the same row or column as this piece
		if ((c.col == this.col) || (c.row == this.row)) {
			//System.out.println("Can attack by row/col");
			return true;
		}
		return false;
	}
	
	public String toString() { //Prints the piece's position in the format "col row"
		return (type) + " " +(col) + " " + (row)+ " ";
	}
	public String getPosition(){
		String position = col + " " + row;
		return position;
		
	}
	public char getType(){
		return type;
	}
}
