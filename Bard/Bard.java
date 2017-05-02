/*
 * THIS ONE FUCKING WORKS!!!
 * 
 * 
 * 
 * 
 */
import java.util.*;
import java.io.*;
public class Bard {
	//creates a hashtable called dictionary that will contain all the words in the play "The Bard".
	private static Hashtable<String,Integer> dictionary = new Hashtable<String,Integer>();//stores all the words in the file with frequency
	private static Hashtable<String, Integer> wordLengths = new Hashtable<String, Integer>();//will store all words in the file with length
	private static Hashtable<String, Integer> wordLengthFr = new Hashtable<String, Integer>(); //frequency of words of a specified length
	public static void main(String[] args) throws IOException{
		Scanner bard = new Scanner(new File("shakespeare.txt"));
		FileWriter f0 = new FileWriter("analysis.txt");
		while(bard.hasNextLine()){
			String line = bard.nextLine().trim().replaceAll("[(),:;?!.]", " ");
			//trims all the whitespace out again
			line = line.trim();
			String[] words = line.split("\\s+");
			int j = words.length;
			for (int i = 0; i < j; i++){
				//for every word in that array, we will put it in the hashtable,
				//go to the incrementValues method for more info
				//trim out all the CAPS characters
				if (isAllUpperCase(words[i])== true){
					words[i] = "";
				}
				//make words that passed the allUpperCase test lower case
				incrementValues(words[i].toLowerCase());
				calculateLengths(words[i].toLowerCase());
			}
		} //end while
		Scanner in = new Scanner(new File("input.txt"));
		while(in.hasNextLine()){
			//so we can go through the dictionary hashtable
			Enumeration<String> d = dictionary.keys();
			String line = in.nextLine().trim() + "";
			String[] inputs = line.split("\\s+");
			//System.out.println(inputs.length);
			for (int i =0; i < inputs.length; i += 2){
				try {
					//System.out.println("i is " + i);
					int l = Integer.parseInt(inputs[i]); //length of the words
					//System.out.println("l is " + l);
					int k = Integer.parseInt(inputs[i+1]); //print k words
					//System.out.println("k is " + k);
					//do stuff with them
					int count = 0;
					int element = 0;
					//put the frequencies of dictionary into wordLengthFr
					while(d.hasMoreElements()){
						String key = d.nextElement();
						if(wordLengths.get(key) == l){
							count = dictionary.get(key);
							wordLengthFr.put(key, count);//this hastable has words of specified length, key is string, value is integer
							element++;
						}
					} //end while
					int w = 0;
					//so we can iterate through wordLengthFr
					Set<String> keySet = wordLengthFr.keySet();
					Iterator<String> keySetIterator = keySet.iterator();
					int[] lenFr = new int[element];
					//store the frequencies into an array
					while (keySetIterator.hasNext()){
						String key = keySetIterator.next();
						lenFr[w] = wordLengthFr.get(key);
						w++;
					}
					//sort the array from smallest to greatest
					Arrays.sort(lenFr);
					int lengthOfArray = lenFr.length -1;
					//another enumeration to go through wordLengthFr
					Enumeration<String> f2 = wordLengthFr.keys();
					int count2 = 0;
					//Get the last elements of the array and match up those frequencies to their respective key
					for(int e = lengthOfArray;  e >= lengthOfArray - k; e--){
						//if there are no more elements in the enumeration, break
						if (f2.hasMoreElements() == false){
							break;
						}
						count2++;
						//while count is less than count2, which is basically k
			        	 while (count2 <= k){
			        		 String key = f2.nextElement();
			        		 //if wordLengthFr has this key, and if the values pair up
			        		 if(wordLengthFr.get(key) == lenFr[e] && wordLengthFr.containsKey(key)){
			        			 //write it out
			        			 f0.write(key + " ");
								 break;
			        		 }
			        		 //key will go onto the next key in the enumeration and see if its value
			        		 //is in the next array index
			           }
					   f2 = wordLengthFr.keys();
			      }
					f0.write("\n");
					//clear that array so the next input number pair can use it
					wordLengthFr.clear();
				} //end try
				//If it is not a number, perform the next operations
				catch (NumberFormatException e){
					//System.out.println("it's a word");
					//input's word
					String lookWord = inputs[i];
					//if the hashtable contains the same key
					if (dictionary.containsKey(lookWord) == true){
						//System.out.println("found same key");
						//writes out the word's value, which is the frequency of the word
						f0.write( dictionary.get(lookWord) + "\n");
					} else {
						f0.write("0\n");
					}
				} //end catch
			}
		}	
		in.close();
		bard.close();
		f0.close();

	}
	//inserts words into the dictionary, increase its value if there are duplicates
	private static void incrementValues(String words) {
		//Word w  = new Word(frequency,length);
		//if the key is the same, if there are duplicate keys
		if (dictionary.containsKey(words)) {
			//increment the value of that key
			dictionary.put(words, dictionary.get(words) + 1);
		} else {
			//each unique word has the value of one
			dictionary.put(words, 1);
		}
	}
	//checks if a word is all upper case
	public static boolean isAllUpperCase(String word){
		for (int i = 0; i < word.length(); i++){
			if (Character.isLowerCase(word.charAt(i))){
				return false;
			}
		}
		return true;
	}
	//get all the word lengths and put them into wordLengths
	private static void calculateLengths(String words){
		int length = words.length();
		wordLengths.put(words, length);
	}
}
