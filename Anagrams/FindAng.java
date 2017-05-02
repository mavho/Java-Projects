import java.util.*;
import java.io.*;
/* Maverick Ho
 * Gets a string of letters from an user input and finds anagrams of the string in a text file.
 * mmho@ucsc.edu
 */
class FindAng {
  public static void main(String []args) throws FileNotFoundException{
	 int SEED = 1234; //makes it constant
      Random rng = new Random(SEED);
      int[] hashValues = new int[26]; //for hash values of letters a thru z	create hash values for the hashValues array
      	for (int i=0; i<hashValues.length; i++) {
      		hashValues[i] = rng.nextInt();  //picks random ints in range -2147483648 to 2147483647 inclusive
      	}    		  
      Scanner in = new Scanner(new FileInputStream(args[0]));
      int size = in.nextInt();
      String[] listWord = new String[size];//new array the size of the word list
      	for (int i = 0; i < size; i++ ){
      	listWord[i] = in.next();
      }
      int[] codeeWrd = new int[size];
      	for (int i = 0; i <listWord.length; i++){
      		int word = 0;
      		for ( int k = 0; k < listWord[i].length(); k++){
      			int x = listWord[i].charAt(k);
      			word += hashValues[x - 97];
      			}
      		codeeWrd[i] = word;
      	}
     boolean answer = true;
   	  while(answer) {
   	  Scanner scan = new Scanner(System.in); //creates a new scanner
   	  System.out.println("Type in a word you want");
   	  String Wrd = scan.nextLine(); 
      int sum = 0;
    	for (int i= 0; i < Wrd.length(); i++) {
  	  int x = Wrd.charAt(i); //makes a new integer from the user-inputed string
  	  sum += hashValues[x - 97];  // need to find the actual corresponding array value. x-97 to find the corresponding placeholder 
    }			
      for (int i = 0; i < codeeWrd.length; i++){ 
    	  if (codeeWrd[i] == sum){ //if the sums are equal
    		  if (!Wrd.equals(listWord[i])){ //excludes the user inputed string and takes it out of the output
        		  System.out.println(listWord[i]);    			  
    		  }
    	  }
      }
      System.out.println("Another word? (y/n)"); 
      	String  x = scan.next();
      	if (!x.equals("y")) //if the user inputs anything not a 'y', it breaks the code
      		answer = false;	
      }
    }
}

  
