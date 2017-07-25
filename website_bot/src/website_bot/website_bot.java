package website_bot;
/*
 * Website Product Tracker Bot
 * 
 * Goal is to track the products in a specified link and email the updates to me
 * 
 * current version 1: computes SHA values of the, which mark the changes and send an email to me notifying me of the changes
 * 
 * current working version 1.5: adding comparing of html
 * 
 * Working verison 1.5.2: html comparison works
 * str attached to email is fine
 * very first startup works
 * 
 * TODO:
 * 	find a way to capture deletions in the HTML parse file
 * fix bugs that I find
 * 
 * Test out various situations, log them down
 * 
 * Startup not logging in updates for somereason??????????!?!?!??!?!?!?!?
 */

import org.apache.commons.codec.digest.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;




public class website_bot {
	website_bot() {super();}
	
	//stores the link and the corresponding SHA value
	static Map<String, String> checkSumDB = new HashMap<String, String>();
	static int run_number;
	static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	static LocalDateTime now = LocalDateTime.now();
	static Hashtable<String, Integer> initialData = new Hashtable<String, Integer>();
	static Hashtable<String, Integer> changedData = new Hashtable<String, Integer>();
	public static String changedStr;
	
	
	public static void main (String[] args) throws IOException, InterruptedException{
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
	private static void checkSHAdiff(String siteStr, String company, int startup2, PrintWriter fileStream) throws IOException, InterruptedException {
		FileWriter f0 = new FileWriter(company +"_SHA5_Values.txt", true);
		BufferedReader br = new BufferedReader(new FileReader(company + "_SHA5_Values.txt"));
		//PrintStream fileStream = new PrintStream(company + "_Parsed_HTML.txt");
		String url = siteStr;
		String lastChecksum = checkSumDB.get(url);
        // get current checksum using static utility method
        String currentChecksum = getChecksumForURL(url);
        loadHTML(siteStr,initialData);
    	System.out.println("This is the initial for "+ company + " " + initialData.keySet().toString());

        startup2 = 0;
        //we only want the startup function to run once
        if (startup2 == run_number){
        	//if statement for the condition of a not comparing lastChecksum to an empty hashmap
        	if (!currentChecksum.equals(lastChecksum)){
        		if (br.readLine() == null){
        			f0.write(currentChecksum + " : NEW START UP AT " + dtf.format(now) + '\n');
        			f0.close();
        		}
        		checkSumDB.put(url, currentChecksum);
    			System.out.println("First startup when nothing is in the hashmap");
        		br.close();
        	}
        	startup(company, currentChecksum, siteStr, f0, fileStream);
        }else if (currentChecksum.equals(lastChecksum)) {
            System.out.println(company + " hasn't been updated");
        } else if (!currentChecksum.equals(lastChecksum)){
        	System.out.println(currentChecksum + " " + lastChecksum);
        	checkSumDB.put(url, currentChecksum);
        	//Thread.sleep(5000);
        	loadHTML(url, changedData);
        	if (!hashtablesEqual(initialData, changedData)){
        		// persist this checksum to map
        		f0.write(currentChecksum + " : " + dtf.format(now) + '\n');
        		System.out.println(company + " content have changed..., sending email");
        		email.sendEmail(company, siteStr, changedStr); 
        		//System.out.println(checkSumDB);
        		PrintStream updatedList = new PrintStream(company + "_Parsed_HTML.txt");
        		Enumeration<String> e = changedData.keys();
        	        while(e.hasMoreElements()){
        	        	String key = e.nextElement();
        	        	updatedList.println(key);
        	        }
        	   updatedList.close();
        		//swap
        	} else{
        	System.out.println("headers did not change, therefore there may not be a change"
					+ "in a product or there was a deletion : " + dtf.format(now));
        	}
        }
        initialData.clear();
        changedData.clear();
        f0.close();
        fileStream.close();
	}
	private static void startup(String company, String SHA_value, String url, FileWriter f0, PrintWriter fileStream) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(company + "_Parsed_HTML.txt"));
		// TODO Auto-generated method stub
		System.out.println("Entered Startup");
		Scanner scan = new Scanner(new File(company +"_SHA5_Values.txt"));
		Scanner sc = new Scanner(company + "_Parsed_HTML.txt");
		//linked list of all the SHA codes from the generated SHA file
		LinkedList<String> SHACodes = new LinkedList<String>();
		while (scan.hasNextLine()){
			SHACodes.add(scan.next());
			scan.nextLine();
		}
		String lastFileVal = SHACodes.getLast();
		String values = "";
		if((values = br.readLine()) == null){
			Enumeration<String> e  = initialData.keys();
			while(e.hasMoreElements()){
				String key = e.nextElement();
				fileStream.println(key);
			}
		} else {
			incrementValues(values, changedData);
		}
		while((values = br.readLine()) != null){
			if (values != null){
				incrementValues(values, changedData);
			}
		}
		System.out.println(initialData.keySet().toString());
		System.out.println(changedData.keySet().toString());
		//get the most recent one and update if different 
		if (SHA_value.equals(lastFileVal)){
			System.out.println(SHA_value + " "+ lastFileVal);
			System.out.println(" Startup: nothing has changed in " + company);
		}else if (!SHA_value.equals(lastFileVal)){
			loadHTML(url, changedData);
			if (!hashtablesEqual(initialData, changedData)){
        		// persist this checksum to map
        		checkSumDB.put(url, SHA_value);
        		f0.write(SHA_value + " : " + dtf.format(now) + '\n');
        		System.out.println("Startup " + company + " content have changed..., sending email");
        		email.sendEmail(company, url, changedStr); 
        		//System.out.println(" Startup: " + checkSumDB);
        		PrintStream updatedList = new PrintStream(company + "_Parsed_HTML.txt");
        		Enumeration<String> e = changedData.keys();
        	        while(e.hasMoreElements()){
        	        	String key = e.nextElement();
        	        	updatedList.println(key);
        	        }
        	    updatedList.close();
        		//swap
        	}
		} else{
			System.out.println("headers did not change, therefore there may not be a change"
					+ "in a product or there was a deletion : " + dtf.format(now));
		}
		//System.out.println(SHA_value + " "+ lastFileVal);
		scan.close();
		SHACodes.clear();
		changedData.clear();
		sc.close();
	}
	
	//need to check if changedData has deleted values from initial
	private static boolean hashtablesEqual(Hashtable<String, Integer> initialData2,
		Hashtable<String, Integer> changedData2) {
		System.out.println("Entered Hashtable Equal method");
		changedStr = "";
		System.out.println(initialData2.keySet().toString());
		System.out.println(changedData2.keySet().toString());
		Enumeration<String> e = changedData2.keys();
		while(e.hasMoreElements()){
			String key = e.nextElement();
			if(!initialData2.containsKey(key)){
				changedStr += key + ", ";
			}else if(!e.hasMoreElements() && changedStr.isEmpty()){
				System.out.println("All the same");
				return true;
			}
		}
		//System.out.println(initialData2.keySet().toString());
		//System.out.println(changedStr);
		return false;
	}
	//load the HTML into a specified hashtable
	private static void loadHTML(String url, Hashtable<String, Integer> updatedHash){
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
		//System.out.println(words);
	}
	
	public static void sendEmail(){
		sendEmail();
	}
	private static void incrementValues(String words, Hashtable<String, Integer> updatedHash) {
		//Word w  = new Word(frequency,length);
		//if the key is the same, if there are duplicate keys
		if (updatedHash.containsKey(words)) {
			//increment the value of that key
			updatedHash.put(words, updatedHash.get(words) + 1);
		} else {
			//each unique word has the value of one
			updatedHash.put(words, 1);
		}
	}
	//generates the sha256hex code of the site
	 private static String getChecksumForURL(String spec) throws IOException {
	        URL u = new URL(spec);
	        HttpURLConnection huc = (HttpURLConnection) u.openConnection();
	        huc.setRequestMethod("GET");
	        huc.setDoOutput(true);
	        huc.connect();
	        return DigestUtils.sha256Hex(huc.getInputStream()); 
	 }
}
