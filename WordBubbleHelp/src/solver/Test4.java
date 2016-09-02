package solver;
import java.util.ArrayList;
import java.util.Vector;
import java.util.function.Predicate;
import java.io.File;

/**
 * Only does 1 word at a time, and will only work if the letterPool is the same size as the word length (works!)
 * @author Grayson Spidle
 *
 */
public class Test4 {

	
	private static File wordListFile = new File("wordlist/wordlist.txt");
	private static final Vector<Character> LETTER_POOL = new Vector<Character>(); // The letters that we will be dealing with.
	private static Vector<String> DICTIONARY = new DictionaryLoader(wordListFile).getDictionaryVector(); // Gets the dictionary words
	private static Vector<Character> letterPoolDupe = new Vector<Character>();
	private static int lengthLimit;
	
	static Vector<String> output = new Vector<String>();
	
	static Predicate<Vector<Character>> hasCorrectLetters;
	static Predicate<Character> isPresent;
	static Predicate<Vector<Character>> hasOneOfEveryLetter;
	
	/**
	 * The constructor
	 * @param letters The letterPool to be sorted.
	 * @param lengthLimit The length of the word
	 */
	public Test4(Vector<Character> letters, int lengthLimit) {
		
		LETTER_POOL.addAll(letters);
		this.lengthLimit = lengthLimit;
				
	}
	
	/**
	 * The constructor. Assumes the length of words to be the size of the letters parameter.
	 * @param letters The letters to unscramble.
	 */
	public Test4(Vector<Character> letters) {
		
		LETTER_POOL.addAll(letters);
		this.lengthLimit = LETTER_POOL.size();
		
	}
	
	/**
	 * Set a custom dictionary file. For UI.
	 * @param dictionaryFile
	 */
	public void setDictionary(File dictionaryFile) {
		
		DICTIONARY = new DictionaryLoader(dictionaryFile).getDictionaryVector();
		
	}
	
	/**
	 * Solves.
	 * @return Returns a vector of possible words.
	 */
	public Vector<String> solveAndGet() {	
		
		resetLetterPoolDupe();
		
		// This checks to see if the Vector that contains any characters that are in the letterPool Vector
		hasCorrectLetters = new Predicate<Vector<Character>>() {
			
			// The parameter is: the word to be checked.
			@Override
			public boolean test(Vector<Character> t) {
				int lettersMatched = 0;
				int counter = 0;
				
				for (Character c : t) {
					
					counter = 0;
					
					while (counter < LETTER_POOL.size()) {
						
						if (c.equals(LETTER_POOL.elementAt(counter))) {
							
							break;
							
						}
						
						else {
							
							counter++;
							
						}
						
					}
					
					if (counter == t.size()) {
						
						lettersMatched++;
						
					}
					
				}
				
				if (lettersMatched == t.size()) {
					
					return false;
					
				}
				
				else {
					
					return true;
					
				}
				
			}
			
		}; 
		
		// This takes in a character and tells if it is in the lettersPoolDupe Vector.
		isPresent = new Predicate<Character>() {

			@Override
			public boolean test(Character th) {
				
				for (Character ch : letterPoolDupe) {
					
					if (ch.toString().equalsIgnoreCase(th.toString())) {
						
						return true;
						
					}
					
				}
				
				return false;
				
			}
			
		};
		
		// Takes in a Vector<Character> that contains a separated word and checks if it has 1 of each required letter.
		hasOneOfEveryLetter = new Predicate<Vector<Character>>() { 
			
			@Override
			public boolean test(Vector<Character> t) {
				
				resetLetterPoolDupe();
				
				for (Character c : t) {
					
					if (isInLetterPool(c) == true) {
						
						letterPoolDupe.remove(c); // Remove the letter
						
					}
					
					else {
						
						return false;
						
					}
					
				}
				
				return true;
				
			}
			
			private boolean isInLetterPool(Character c) {
				
				for (Character ch : letterPoolDupe) {
					
					if (ch.toString().equalsIgnoreCase(c.toString())) {
						
						return true;
						
					}
					
				}
				
				return false;
				
			}
			
		};
		
		/* The magical place where all the steps come together. */
		Runnable solve = new Runnable() {

			@Override
			public void run() {
				
				// Step 1
				Vector<String> step1 = getWordsThatMeetLengthLimit(DICTIONARY);				
				
				// Step 2
				Vector<String> step2 = getAllWordsThatHaveLettersInLetterPool(step1);
				
				// Step 3
				ArrayList<Vector<Character>> step3 = separateLettersPlus(step2);
				
				// Step 4
				Vector<String> step4 = getWordsThatHaveAllTheLettersInLetterPoolOnce(step3);
				
				// Step 5
				output = step4;
				
			}
			
			
		}; // Magic ends here
		
		solve.run();
		
		// Return the possible words
		return output;
						
	}
	
	/**
	 * This gets all words that meet length limit, this is step 1
	 * @param words The words to sort through
	 * @return Returns a vector of all the words that meet the length limit.
	 */
	private static Vector<String> getWordsThatMeetLengthLimit(Vector<String> words) {
		
		Vector<String> output = new Vector<String>();
		
		for (String s : words) {
			
			if (s.length() == lengthLimit) {
					
				output.addElement(s);
					
			}
					
		}
		
		return output;
		
	}
	
	/**
	 * Gets all words that have atleast one letter that is in letterPool. This is step 2. 
	 * @param words Words to sort through.
	 * @return Returns a vector of all the words that meet the criteria.
	 */
	private static Vector<String> getAllWordsThatHaveLettersInLetterPool(Vector<String> words) {
		
		Vector<String> output = new Vector<String>();
		
		for (String s : words) {
			
			if (hasCorrectLetters.test(separateLetters(s))) {
				
				output.addElement(s);
				
			}
			
		}
		
		return output;
		
	}
	
	/**
	 * Gets all words that have all the letters in letterPool once. This is step 3 or 4. 
	 * @param separatedWordList A list of words who have their letters separated in a Vector<Character>.
	 * @return Returns a Vector of words that meet the criteria.
	 */
	private static Vector<String> getWordsThatHaveAllTheLettersInLetterPoolOnce(ArrayList<Vector<Character>> separatedWordList) {
		
		Vector<String> output = new Vector<String>();
		
		for (Vector<Character> vc : separatedWordList) {
			
			resetLetterPoolDupe();
			
			if (hasOneOfEveryLetter.test(vc) == true) {
				
				output.add(combineLetters(vc));
				
			}
			
		}
		
		return output;
		
	}
	
	/**
	 * Combines separated letters into a String.
	 * @param letters The letters to combine
	 * @return Returns a String.
	 */
	private static String combineLetters(Vector<Character> letters) {
		
		String output = "";
		
		for (Character c : letters) {
			
			output = output + c.toString();
			
		}
		
		return output;
		
	}
	
	/**
	 * Separates Letters
	 * @param s The word that the user wants to separate the letters to.
	 * @return Returns an array containing objects of type char.
	 */
	private static Vector<Character> separateLetters(String s) {
		
		int counter = 0;
		Vector<Character> output = new Vector<Character>();
		
		while (counter != s.length()) {
			
			output.add(s.charAt(counter));
			counter++;
			
		}		
		
		return output;
		
		
	}
	
	/**
	 * Does exactly what separateLetters() does except to Vectors of Strings.
	 * @param wordList The wordList to be separated.
	 * @return Returns a list of Vectors that contain the separated words.
	 */
	private static ArrayList<Vector<Character>> separateLettersPlus(Vector<String> wordList) {
		
		ArrayList<Vector<Character>> output = new ArrayList<Vector<Character>>();
		
		for (String s : wordList) {
			
			output.add(separateLetters(s));
			
		}
		
		return output;
		
	}
	
	/**
	 * Resets stuff.
	 */
	private static void resetLetterPoolDupe() {
		
		letterPoolDupe.removeAllElements();
		letterPoolDupe.addAll(LETTER_POOL);
		
	}

}
