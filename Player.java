package boggle;

import java.util.ArrayList;

public class Player {
	private int points;
	private String name;
	private ArrayList<String> guessedWords;
	
	public Player() {
		guessedWords = new ArrayList<String>();
	}
	
	public int getPoints() {
		return points;
	}
	
	public void setPoints(int points) {
		this.points = points;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Return true if a word is already guessed. Return false if it is not guessed
	 * @param word
	 * @return boolean
	 */
	public boolean checkIfGuessed(String word) {
		for (String oldWord : guessedWords) {
			if (word.equalsIgnoreCase(oldWord)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Add a word to the guessed words list 
	 * @param word
	 */
	public void addGuessedWord(String word) {
		guessedWords.add(word);
	}
	/**
	 * return arrayList of the guessed words
	 * @return guessedWords
	 */
	public ArrayList<String> getGuessedWords() {
		
		return guessedWords;
	}

}