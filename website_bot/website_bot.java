package website_bot;
/*
 * Website Product Tracker Bot
 * 
 * Goal is to track the products in a specified link and email the updates to me
 * 
 *  Working version 2:
 *  Everything works, just handle exceptions, etc.
 * TODO:
 * Error Handling, log and note down crashes
 * 
 * 
 */

import java.io.*;
import java.net.UnknownHostException;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;


public class website_botV2 {
	
	static Map<String, String> checkSumDB = new HashMap<String, String>();
	static int run_number;
	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	static LocalDateTime now = LocalDateTime.now();
	static Hashtable<String, Integer> initialData = new Hashtable<String, Integer>();
	static Hashtable<String, Integer> changedData = new Hashtable<String, Integer>();
	public static String changedStr;
	public static String deletedStr;
	
	
	public static void main(String[] args) throws InterruptedException, IOException{
		//email.sendEmail();
				while (true){
					//contains the link and the company name
					try{
					Scanner file = new Scanner(new File ("inputs.txt"));
					while (file.hasNextLine()){
						String line = file.nextLine().trim() + "";
						String[] inputs = line.split("\\s+");
						String url = inputs[0];
						String company = inputs[1];
						checkSHAdiff(url, company, run_number);
					}
					run_number += 1;
					//divide by 1000 to determine times in seconds
		            Thread.sleep(10800000);
		            file.close();
					} catch (FileNotFoundException e){
						System.out.println("inputs.txt not found");
						System.exit(1);
					}
				}
	}

	private static void checkSHAdiff(String url, String company, int run_number2) throws InterruptedException, IOException {
		PrintWriter fileStream = new PrintWriter(new FileWriter(company + "_Parsed_HTML.txt",true));
		loadHTML(url, changedData);
		//write initialData to text file on very first startup
		if(run_number == 0){
			BufferedReader br = new BufferedReader(new FileReader(company + "_Parsed_HTML.txt"));
			if (br.readLine() == null){
				Enumeration<String> s = changedData.keys();
				while (s.hasMoreElements()){
					String value = s.nextElement();
					fileStream.println(value);
				}
			}
			br.close();
		}
		fileStream.close();
        //put lines in the text file into changed data
		Scanner sc = new Scanner(new File(company + "_Parsed_HTML.txt"));
		txtToHASH(sc, initialData);
		Enumeration<String> e = changedData.keys();
        //check if they equal to each other
        if (!hashtablesEqual(changedData, initialData)){
    		//System.out.println(company + " content have changed..., sending email");
    		email.sendEmail(company, url, changedStr, deletedStr);
    		PrintStream updatedList = new PrintStream(company + "_Parsed_HTML.txt");
    		while(e.hasMoreElements()){
            	String key = e.nextElement();
            	updatedList.println(key);
            }
			updatedList.close();
        }
        sc.close();
        fileStream.close();
		initialData.clear();
		changedData.clear();
		
	}
	private static void txtToHASH(Scanner sc, Hashtable<String, Integer> initialData2) throws IOException {
		String values = "";
		while (sc.hasNextLine()){
			values = sc.nextLine();
			//System.out.println(values);
			incrementValues(values, initialData2);
		}
	}
	//get deletions and updates from the hashtables
	private static boolean hashtablesEqual(Hashtable<String, Integer> changedData2,
			Hashtable<String, Integer> initialData2) {
			changedStr = "";
			deletedStr = "";
			Enumeration<String> e = changedData2.keys();
			Enumeration<String> s = initialData2.keys();
			boolean updated1 = false;
			boolean updated2=false;
			while(e.hasMoreElements()){
				String key = e.nextElement();
				if(!initialData2.containsKey(key)){
					changedStr += key + ", ";
				}else if(!e.hasMoreElements() && changedStr.isEmpty()){
					//System.out.println("No updates");
					updated1 = true;
				}
			}
			while(s.hasMoreElements()){
				String value = s.nextElement();
				if(!changedData2.containsKey(value)){
					deletedStr += value + ",";
				} else if (!s.hasMoreElements() && deletedStr.isEmpty()){
					//System.out.println("No Deletions");
					updated2 = true;
				}
			}
			if (updated1 && updated2){
				return true;
			}
			return false;
		}
	//load up the a href attributes text from the html from the website and loads them into a specified hashtable. Ignores Non Latin characters
	private static void loadHTML(String url, Hashtable<String, Integer> updatedHash) throws IOException{
		PrintWriter errorLog = new PrintWriter(new FileWriter("ErrorLog.txt",true));
		try{
			Document doc = Jsoup.connect(url).get(); 
			Elements containers = doc.select("a");
			for (Element c: containers){
				String value = c.text().toLowerCase();
				//ignores non-latin characters
				value = value.replaceAll("[\\x{100}-\\x{10FFFF}]+", "");
				if(!value.isEmpty() && (value.length() < 30)){
					incrementValues(value,updatedHash);
				}
			}
		} catch (UnknownHostException e){
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			errorLog.println(e +" : " + dtf.format(now));
		}
		errorLog.close();
	}
	private static void incrementValues(String words, Hashtable<String, Integer> updatedHash) {
		//if the key is the same, if there are duplicate keys
		if (updatedHash.containsKey(words)) {
			//increment the value of that key
			updatedHash.put(words, updatedHash.get(words) + 1);
		} else {
			//each unique word has the value of one
			updatedHash.put(words, 1);
		}
	}
	public static void sendEmail(){
		sendEmail();
	}
}
