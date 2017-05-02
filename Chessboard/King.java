
public class King extends ChessPiece {
	public King(int c, int r, char t){
		type = t;
		col = c;
		row = r;
	}
	public boolean canAttack(ChessPiece c) {
		//Check if other piece is diagonal to this piece
		if ((c.col == this.col) && Math.abs(c.row - this.row) == 1) {
			//System.out.println("Can attack by row/col");
			return true;
		} else if ((c.row == this.row) && Math.abs(c.col - this.col) == 1){
			return true;
		}
	else if (Math.abs(c.col - this.col) == 1 && Math.abs(c.row - this.row) == 1) {
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
