package website_bot;
import org.apache.commons.codec.digest.*;
import java.io.*;
import java.util.*;
import java.net.*;
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
	private static String deletedStr;
	
	
	public static void main(String[] args) throws InterruptedException, IOException{
		//email.sendEmail();
				while (true){
					//contains the link and the compnay name
					Scanner file = new Scanner(new File ("inputs.txt"));
					while (file.hasNextLine()){
						String line = file.nextLine().trim() + "";
						String[] inputs = line.split("\\s+");
						String url = inputs[0];
						String company = inputs[1];
						PrintWriter fileStream = new PrintWriter(new FileWriter(company + "_Parsed_HTML.txt", true));
						checkSHAdiff(url, company, run_number, fileStream);
					}
					run_number += 1;
					//divide by 1000 to determine times in seconds
		            Thread.sleep(10000);
		            file.close();
				}
		
		
	}


	private static void checkSHAdiff(String url, String company, int run_number2, PrintWriter fileStream) {
		initialData = loadHTML(url, initialData);
		
		
		
		
		
	}
	private static boolean hashtablesEqual(Hashtable<String, Integer> initialData2,
			Hashtable<String, Integer> changedData2) {
			System.out.println("Entered Hashtable Equal method");
			changedStr = "";
			System.out.println(initialData2.keySet().toString());
			System.out.println(changedData2.keySet().toString());
			Enumeration<String> e = changedData2.keys();
			Enumeration<String> s = initialData2.keys();
			boolean updated1 = false;
			boolean updated2=false;
			while(e.hasMoreElements()){
				String key = e.nextElement();
				if(!initialData2.containsKey(key)){
					changedStr += key + ", ";
				}else if(!e.hasMoreElements() && changedStr.isEmpty()){
					System.out.println("All the same");
					updated1 = true;
				}
			}
			while(s.hasMoreElements()){
				String value = s.nextElement();
				if(!changedData2.containsKey(value)){
					deletedStr += value + ",";
				} else if (!s.hasMoreElements() && deletedStr.isEmpty()){
					System.out.println("All the same");
					updated2 = true;
				}
			}
			if (updated1 && updated2){
				return true;
			}
			return false;
		}
	private static Hashtable<String, Integer> loadHTML(String url, Hashtable<String, Integer> updatedHash){
		try{
			Document doc = Jsoup.connect(url).get(); 
			Elements containers = doc.select("h3");
			for (Element c: containers){
				if(!c.text().isEmpty()){
					incrementValues(c.text(),updatedHash);
				}
			}
		} catch (Exception e){
			System.out.println(e);
			System.exit(1);
		}
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
