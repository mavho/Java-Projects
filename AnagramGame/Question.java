
public class Question {
	String word;
	String anagram;
	String answer;
	//well i want an image file to correspond to the question
	String file;
	public Question(){};
	public Question(String word, String anagram, String file){
		this.word = word;
		this.anagram = anagram;
		this.file = file;
		this.answer = word;
	}
	public String getWord(){
		return this.word;
	}
	public String getAnagram(){
		return this.anagram;
	}
	public String getAnswer(){
		return this.answer;
	}
	public String getJPEG(){
		return "images/" + this.file + ".jpg";
	}
	public boolean isCorrect(){
		if(answer.equals(word)){
			return true;
		} else{
			return false;
		}
	}
}
