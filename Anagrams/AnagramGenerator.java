
import java.lang.*;
import java.util.*;
import java.io.*;
import java.io.File;
import java.lang.Object;
import java.util.Random;
public class AnagramGenerator 
/*Anagram Word Generator
 * Purpose:
 * To generate an anagram from a word list.
 * Author: Maverick Ho
 * 
 */
{
	public static void main(String[] args) throws FileNotFoundException
	{
        Scanner in = new Scanner(new FileInputStream(args[0])); //detects a txt file from the command line
		int size = in.nextInt();
		Random rndword = new Random();
		int i = 0;
		int value = rndword.nextInt(size);
		while (i < value){
			in.next();
			i++;
		}
		String Wrd = in.next(); //gets a word from a word list
		StringBuffer New = new StringBuffer(Wrd);
		int WordLength = New.length();
		while (WordLength > 0) {
		int index = rndword.nextInt(New.length()-1);
			char temp = New.charAt(index);  //saves letter
			New.deleteCharAt(index);// deletes a letter
			New.append(temp); //moves letter to back of the word
			WordLength--;
			}
		System.out.println(New);//prints out the anagram
	}
}

