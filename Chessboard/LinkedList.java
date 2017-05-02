import org.omg.Messaging.SyncScopeHelper;

public class LinkedList extends ChessPiece {
	Node head;
	LinkedList(){
		head = null;
	}
	void insert(ChessPiece p){
		Node latest = new Node(p);
		latest.next = head;
		head = latest;
	}
	//traverses through the linkedlist, compares every node to each other to see if it can attack
	String verifyAttack(){
		Node ultimateFirst = head;
		Node first = head;
		Node current = head;
		while (first != null){
			while (current.next != null){	
				if(first.n.canAttack(current.next.n) == true){
					String attackingPieces = (" "+ first.n.toString() + ""+ current.next.n.toString()+ "\n");
					return attackingPieces;
				} else if(current.next.n.canAttack(first.n) == true){
					String otherAttackingP = (" " + current.next.n.toString() + ""+ first.n.toString()+ "\n");
					return otherAttackingP;
				 }
				//System.out.println(current.n.toString());
				current = current.next;
				if (current == null){
					break;
				}
			}//end of 2nd while
			first = first.next;
			current = first.next;
			if (first.next == null){
				break;
			}
			
		}//end of 1st while
		head = ultimateFirst;
		return null;
	}
	//fines the query piece in the linked list, does not work NO CLUE WHY
	char find(int col, int row){
		Node current = head;
		while (current.next != null){
			if (current.n.col == col && current.n.row == row) {//Check if this piece is in the correct coordinates
				char type = current.n.getType();
				return type;
			} 
			current = current.next;
			if (current.next == null){
				char empty = '-';
				return empty;
			}
		}
		return '-';
	}
	public boolean checkValidity() {
	    if(!twoPiecesOccupySamePosition() && countPiecesOfType('k') == 1 && countPiecesOfType('K') == 1) {
	      return true;
	    }
	    else {
	      return false;
	    }
	  }
	public boolean twoPiecesOccupySamePosition() {
	    Node current = head;
	    // loop through and see if any of the pieces overlap
	    while(current != null) {
	      if(countPiecesInLocation(current.n.row, current.n.col) > 1) {
	        return true;
	      }
	      current = current.next;
	     
	    }
	    return false;
	  }
	public int countPiecesInLocation(int row, int col) {
	    Node current = head;
	    int pieceCtr = 0;
	    // loop through to check if any two pieces overlap
	    while(current != null) {
	      if(current.n.row == row && current.n.col == col) {
	        pieceCtr++;
	      }
	      current = current.next;
	      
	    }
	    return pieceCtr;
	  }
	public int countPiecesOfType(char pieceType) {
	    Node piece = head.next;
	    int pieceCtr = 0;
	    // loop through to check if the same piece type is found
	    while(piece != null) {
	      if(piece.n.getType() == pieceType) {
	        pieceCtr++;
	      }
	      piece = piece.next;
	      }
	    return pieceCtr;
	  }
	public String toString(){
			String position = col + " " + row;
			return position;
	}	
}