
public class Knight extends ChessPiece{
	
	public Knight(int c, int r, char t){
		type = t;
		col = c;
		row = r;
	}
	public boolean canAttack(ChessPiece c) {
		//Check if other piece is on the same row or column as this piece
		if (Math.abs(c.col - this.col) == 2 && Math.abs(c.row - this.row) == 1 || Math.abs(c.col - this.col) == 1 && Math.abs(c.row - this.row) == 2){
			//System.out.println("Can attack by row/col");
			return true;
		}
		
		//System.out.println("Can't attack");
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
