package website_bot;

import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class Website_Data_Extraction {
	static String words;
	public static void main(String[] arg) throws IOException{
		try{
			Document doc = Jsoup.connect("http://www.cloudharmonicsdistribution.com/eco-system/blue-coat-systems/blue-coat-products/").get(); 
			Elements containers = doc.select("h3");
			for (Element c: containers){
				System.out.println(c.text());
			}
			words = doc.text();
		} catch (Exception e){
			System.out.println(e);
			System.exit(1);
		}
		//System.out.println(words);
	}
}
