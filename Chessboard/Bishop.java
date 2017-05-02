
public class Bishop extends ChessPiece {
	public Bishop(int c, int r, char t) {
		type = t;
		col = c;
		row = r;
	}
	
	public boolean canAttack(ChessPiece c) {
		//Check if other piece is diagonal to this piece
		if (Math.abs(c.col - this.col) == Math.abs(c.row - this.row)) {
			//System.out.println("Can attack diagonally");
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
