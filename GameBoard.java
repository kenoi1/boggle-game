package boggle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import javax.swing.*;

public class GameBoard {
	
	static private String[] diceData = {
			"AAAFRS", "AEEGMU", "CEIILT", "DHHNOT", "FIPRSY",
			"AAEEEE", "AEGMNN", "CEILPT", "DHLNOR", "GORRVW",
			"AAFIRS", "AFIRSY", "CEIPST", "EIIITT", "HIPRRY",
			"ADENNN", "BJKQXZ", "DDLNOR", "EMOTTT", "NOOTUW",
			"AEEEEM", "CCNSTW", "DHHLOR", "ENSSSU", "OOOTTU"
	};
	
	private static ArrayList<String> dictionary = new ArrayList<String>();
	private static Dice[][] diceGrid;

	public static Dice[][] getDiceGrid() {
		return diceGrid;
	}
	
	public GameBoard() throws Exception {
		fillDiceGrid();
		LoadDictionary(dictionary);
	}
	
	/**
	 * Fills the diceGrid 2d array with 25 dice.
	 */
	static public void fillDiceGrid() {
		diceGrid = new Dice[5][5];
		
		// shuffle the dice data
		Collections.shuffle(Arrays.asList(diceData));
		
		// used to iterate through the dice letter data
		int n = 0;
		
		// loop through all entries of dice grid
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				// Create a dice object with the letter data and put into dice grid
				diceGrid[i][j] = new Dice(diceData[n].toCharArray());
				diceGrid[i][j].rollDice();
				n++;
			}
		}
	}
	
	/**
	 * Checks if a word is on the grid. If so, it returns a list of coordinates of the squares
	 * which form the word.
	 * Otherwise, it returns null
	 * @param word
	 */
	static public ArrayList<Coordinates> validateWord(String word) {
		word = word.toUpperCase();
		
		// Coordinate list
		ArrayList<Coordinates> Coords = new ArrayList<Coordinates>();
		
		// Loop through all 25 dice
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				// If dice letter is the first letter of word	
				if (diceGrid[i][j].getLetter() == word.charAt(0)) {
					// Start searching for a path which creates a word
					ArrayList<Coordinates> newBranch = searchLetter(word, new ArrayList<Coordinates>(), i, j);
					// If this is the longest path found, save it
					if (newBranch.size() > Coords.size()) {
						Coords = newBranch;
					}
				}
			}
		}
		
		if (Coords.size() < word.length()) return null;
		return Coords;
	}
	
	/**
	 * Helper method for the validateWord method. It 
	 * @param word
	 * @param Coords
	 * @param xCurrent
	 * @param yCurrent
	 * @return
	 */
	static private ArrayList<Coordinates> searchLetter(String word, ArrayList<Coordinates> Coords, int xCurrent, int yCurrent) {
		Coords.add(new Coordinates(xCurrent, yCurrent)); // add current coordinates to list
		
		word = word.substring(1); // cut off the beginning letter
		if (word.length() == 0) {
			return Coords; // return once all letters are found
		}
		
		// Copy of list used to prevent pass-by-reference issues
		final ArrayList<Coordinates> staticCoords = new ArrayList<Coordinates>(Coords);
		
		// Iterate through all squares in a 3x3 area around the current square
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				if (dx == 0 && dy == 0) continue; // skip self in 3x3 area
				
				if (xCurrent+dx < 0 || xCurrent+dx > 4) continue; // skip out of bounds
				if (yCurrent+dy < 0 || yCurrent+dy > 4) continue; // skip out of bounds
				
				boolean skip = false;
				for (Coordinates pastCoordinate : Coords) { // skip letters which are already part of the word
					if (xCurrent+dx == pastCoordinate.x && yCurrent+dy == pastCoordinate.y) {
						skip = true;
						break;
					}
				}
				if (skip) continue;
				
				// Copy of list used to prevent pass-by-reference issues
				ArrayList<Coordinates> newCoords = new ArrayList<Coordinates>(staticCoords);
				
				// If the nearby letter is the next letter in the word, use a recursive call,
				// with that letter being the new center letter to search from.
				if (diceGrid[xCurrent+dx][yCurrent+dy].getLetter() == word.charAt(0)) {
					ArrayList<Coordinates> newBranch = searchLetter(word, newCoords, xCurrent+dx, yCurrent+dy);
					// If this is the longest path found, save it
					if (newBranch.size() > Coords.size()) {
						Coords = new ArrayList<Coordinates>(newBranch);
					}
				}
			}
		}
		return Coords;
	}
	
	/**
	 * Coordinate class. Contains an x position and y position.
	 */
	static class Coordinates {
		int x;
		int y;
		Coordinates(int x, int y) {
			this.x = x;
			this.y = y;

		}
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
	 * getIndex returns index of a string in a given ArrayList (sublist) by using a recursive binary algorithm search.
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

	public static ArrayList<String> getDictionary() {
		return dictionary;
	}
}