package solver;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;
import java.util.function.Predicate;
import java.io.File;

/**
 * This class uses the Test4 class (so don't delete it!). It solves for more than two words. THIS ONE WORKS!
 * @author Grayson Spidle
 *
 */
public class MultiWordTest3 {
	
	
	/* Testing
	 * bsarnloom
	 * 6
	 * 3
	 * 
	 * answers:
	 * salmon
	 * orb
	 */
	
	private static final Vector<Character> LETTER_POOL = new Vector<Character>(); // The letters that we will be dealing with.
	private static final Vector<Integer> LENGTH_LIMITS = new Vector<Integer>();
	
	// Duplicates of the modified letterPools
	private static Vector<Character> letterPoolDupe = new Vector<Character>();
	private static Vector<Character> letterPoolDupeDupe = new Vector<Character>();
	
	private static ArrayList<Vector<String>> words = new ArrayList<Vector<String>>();
	
	private static Scanner scan = new Scanner(System.in);
	
	private static Predicate<String> isCorrect;
	
	/**
	 * Main method. This is where the magic happens.
	 * @param args Don't worry about this. It doesn't mean much.
	 */
	public static void main(String[] args) {
		
		 
		System.out.println("Input Letters:"); // Asks for user to input letters available.
		String letters = scan.nextLine(); // Puts letters into a String called "letters".
		
		LETTER_POOL.addAll(separateLetters(letters)); /* Takes the String "letters" and separates them
		 												to have their own respective spot in the Vector. */
		
		System.out.println("Enter the length of a word then press enter then the next word's length.");
		System.out.println("Enter something that isn't an int to continue.");
		while (scan.hasNextInt()) {
			
			int length = scan.nextInt();
			LENGTH_LIMITS.add(length);
			
		}
		
		resetLetterPoolDupe();
		
		// Adds appropriate slots to the amount of words specified.
		words = new ArrayList<Vector<String>>(); 
		for (Integer i : LENGTH_LIMITS) {
			
			words.add(new Test4(letterPoolDupe, i).solveAndGet());
			
		}
		
		letterPoolDupeDupe.removeAllElements();
		letterPoolDupeDupe.addAll(letterPoolDupe);
		
		Predicate<Vector<Character>> hasOneOfEveryLetter = new Predicate<Vector<Character>>() { 
			
			Vector<Character> letterPoolTripDupe = new Vector<Character>();
			
			@Override
			public boolean test(Vector<Character> t) {
				
				letterPoolTripDupe.removeAllElements();
				letterPoolTripDupe.addAll(letterPoolDupeDupe);
//				System.out.println("TripDupe: " + letterPoolTripDupe);
				
				for (Character c : t) {
					
					if (isInLetterPool(c) == true) {
						
						letterPoolTripDupe.remove(c); // Remove the letter
						
					}
					
					else {
						
						return false;
						
					}
					
				}
				
				return true;
				
			}
			
			private boolean isInLetterPool(Character c) {
				
				for (Character ch : letterPoolTripDupe) {
					
					if (ch.toString().equalsIgnoreCase(c.toString())) {
						
						return true;
						
					}
					
				}
				
				return false;
				
			}
			
		};
				
		int counter = 0;
		int lengthCounter = 0;
		
		// Deciphers the words by asking the user if each word is correct.
		for (Vector<String> vs : words) {
			
			Vector<String> vsDupe = new Vector<String>();
			vsDupe.addAll(vs);
			
			for (String s : vsDupe) {
				
				words.get(counter).removeAllElements();
				words.get(counter).addAll(new Test4(letterPoolDupeDupe, LENGTH_LIMITS.get(lengthCounter)).solveAndGet());
				
				if (hasOneOfEveryLetter.test(separateLetters(s)) == true) {
					
					System.out.println("[" + vsDupe.indexOf(s) + "/" + vsDupe.size() + "] " + s);
					System.out.println("Is this a correct word? Y/N");
					String input = new Scanner(System.in).nextLine();
					
					if (input.equalsIgnoreCase("Y")) {
						
						eliminateLettersFromPool(separateLetters(s));
						letterPoolDupeDupe.removeAllElements();
						letterPoolDupeDupe.addAll(letterPoolDupe);
						lengthCounter++;
						break;
						
					}
					
				}
				
			}
			
			counter++;
			
		}
		
		scan.close();
		System.out.println("Done.");
		
		/* Magic Ends Here. */
		
		System.exit(0);
		
	}
	
	/* *** METHODS *** */
	
	/**
	 * Takes the letters provided and elminates them from the letter pool.
	 * @param letters The letters to remove.
	 */
	private static void eliminateLettersFromPool(Vector<Character> letters) {
		
		for (Character c : letters) {
			
			letterPoolDupe.remove(c);
			
		}
		
	}
	
	/**
	 * Separates letters from a String and returns them as a Vector<Character>.
	 * @param s The String whose letters to separate.
	 * @return Returns the separated letters.
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
	 * Resets the first word's letter pool duplicate.
	 */
	private static void resetLetterPoolDupe() {
		
		letterPoolDupe.removeAllElements();
		letterPoolDupe.addAll(LETTER_POOL);
		
	}
	
	/**
	 * Returns all possible words from the specified list of letters.
	 * @param letters The specified list of letters.
	 * @return Returns all possible words in a Vector.
	 */
	private static Vector<String> unscramble(Vector<Character> letters) {
		
		Vector<String> output = new Vector<String>();
		output.addAll(new Test4(letters).solveAndGet());
		return output;
		
	}
	
	/* *** END OF METHODS *** */
	
}
