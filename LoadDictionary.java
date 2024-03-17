package boggle;

import java.io.*;
import java.util.*;

public class LoadDictionary {
	public static void main(String[] args) throws Exception{
		ArrayList<String> dictionary = new ArrayList<String>();
		LoadDictionary(dictionary);
		System.out.println(dictionary);
		
		String word = "derek";
		String word2 = "abandoned";
		String word3 = "able";
		binaryWordSearch(dictionary, word);
		binaryWordSearch(dictionary, word2);
		binaryWordSearch(dictionary, word3);
		binaryWordSearch(dictionary, "zulu");
	}
	
/**
 * This method loads a word bank from a dictionary file.
 * @param dictionary
 * @throws Exception
 */
	public static void LoadDictionary(ArrayList<String> dictionary) throws Exception{
		File file = new java.io.File("WordDictionary.txt");
		Scanner scan = new Scanner(file);

		while (scan.hasNext()) {
			String word = scan.next();
			dictionary.add(word);
		}
		scan.close();
	}
	/**
	 * This method determines whether the word is found or not by calling getIndex()
	 * @param dictionary
	 * @param word
	 */
	public static boolean binaryWordSearch(ArrayList<String> dictionary, String word) {
		int wordIndex = getIndex(dictionary, word);
		if (wordIndex == -1) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * getIndex returns index of a string in a given ArrayList by using a recursive binary algorithm search.
	 * @param dictionary
	 * @param x
	 * @return
	 */
	public static int getIndex(ArrayList<String> dictionary, String word)  {
	    int start = 0;
	    int end = dictionary.size();
	    int mid = end / 2;

	    if(end <= start){
	        return -1;
	    } else if(word.equals(dictionary.get(mid))) {
	        return mid;
	    } else if(word.compareTo(dictionary.get(mid)) < 0) {
	    	ArrayList<String> split = new ArrayList<String>();
	        for(int i = 0; i < mid; i++){
	            split.add(dictionary.get(i));
	        }
	        return getIndex(split,word);
	    } else {
	        ArrayList<String> split = new ArrayList<String>();

	        for(int i = 0; i < end - mid - 1; i++){
	        	split.add(dictionary.get(mid+i+1));
	        }
	        return getIndex(split, word);
	    }
	}
	/**
	 * Iterative binary search algorithm for finding index
	 */
//	public static int getIndex(ArrayList<String> dictionary, String word) { 
//        int l = 0, r = dictionary.size() - 1; 
//  
//        // Loop to implement Binary Search 
//        while (l <= r) { 
//  
//            // Calculatiing mid 
//            int m = l + (r - l) / 2; 
//  
//            int res = word.compareTo(dictionary.get(m)); 
//  
//            // Check if x is present at mid 
//            if (res == 0) 
//                return m; 
//  
//            // If x greater, ignore left half 
//            if (res > 0) 
//                l = m + 1; 
//  
//            // If x is smaller, ignore right half 
//            else
//                r = m - 1; 
//        } 
//  
//        return -1; 
//    } 
	

}
