import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class TextGenerator {

	public static void main(String[] args) throws FileNotFoundException {
		int k = Integer.parseInt(args[0]);
		int M = Integer.parseInt(args[1]);
		File file = new File(args[2]);
		String lastk = ""; // last k characters generated by markov model
		
		//MARKOV MODEL
		System.out.println("Initializing Markov Model of order " + k + " on file " + file);
		int order = k; //order of markov model is first arg
		Scanner inputScan = new Scanner(file);
		MyHashMap<String,ArrayList<String>> hashTable = new MyHashMap<String,ArrayList<String>>();
		String firstk = "";
		int firstkCounter = 0;
		while(inputScan.hasNextLine()) {
			String line = inputScan.nextLine();
			if(firstkCounter == 0) { //feed first k characters to firstk for later use
				firstk = line.substring(0, k);
				System.out.println("firstk: " + firstk);
			}
			for(int index = 0; index < line.length()-order; index++) {
				String subString = line.substring(index, index+order);
				if(hashTable.get(subString) != null) {
					hashTable.get(subString).add(line.substring(index+order, index+(order+1)));
				}
				else {
					ArrayList<String> list = new ArrayList<String>();
					list.add(line.substring(index+order, index+(order+1)));
					hashTable.put(subString, list);
				}
			}
			//System.out.println(hashTable.size() + "  " + "Distinct Keys"); //get number of distinct keys
			//Iterator<Map.Entry<String, ArrayList<String>>> it = hashTable.entries();
			//while(it.hasNext()) {
				//Map.Entry<String, ArrayList<String>> temp = it.next();
				//System.out.println(temp.getValue().size() + " " + temp.getKey());
			//}
			firstkCounter++;
		}
		
		//POST HASHTABLE CONSTRUCTION
		inputScan.close();
		//System.out.println(hashTable.toString());
	
		//PSEUDORANDOM NG
		System.out.println("Hash Table Successfully Constructed, Beginning pRNG String Generation");
		System.out.println("first " + k + " characters of model: " + firstk);
		String Final = "";
		Final = firstk; //initialize final output as starting with first k characters
		for(int i = 0; i < M; i++) {
			lastk = Final.substring(Final.length()-k); //update lastk
			if(hashTable.get(lastk) == (null)) { //if lookup from hashTable is null, terminate output
				System.out.println("A Null Was Encountered, Terminating at Null");
				break;
			}
			ArrayList<String> bucket = hashTable.get(lastk); //get bucket from last k characters entered
			int random = (int) (Math.random() * bucket.size()); //generate pRNG value
			String lookup = bucket.get(random);	//get index random from bucket
			Final+=lookup; //add pRNG text to final
		}
		System.out.println(Final); //final output
	}
}
