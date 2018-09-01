import java.util.Comparator;

public class Player  {
	String name;
	Integer score;
	public Player(){}
	
	public Player(String name, Integer Score){
		this.name = name;
		this.score = Score;
	}
	public Integer getScore(){
		return this.score;
	}
	public String getName(){
		return this.name;
	}
	
	class MyComparator implements Comparator<Player>{

		@Override
		public int compare(Player o1, Player o2) {
			// TODO Auto-generated method stub
			if (o1.getScore() > o2.getScore()) {
		        return -1;
		    } else if (o1.getScore() < o2.getScore()) {
		        return 1;
		    }
			return 0;
		}
		
	}
}
