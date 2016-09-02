package solver;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Loads the dictionary. Must be in 1 word 1 line format.
 * @author Grayson Spidle
 *
 */
public class DictionaryLoader {
	
	File dictionary;
	ArrayList<String> words;
	
	/**
	 * This is the constructor which takes a file. 
	 * @param dictionaryFile A txt file that contains word list.
	 */
	public DictionaryLoader(File dictionaryFile) {
		
		words = new ArrayList<String>();
		dictionary = dictionaryFile;
		
	}
	
	public ArrayList<String> getDictionaryList() {
		
		ArrayList<String> output = new ArrayList<String>();
		
		try {
			
			for (String s : Files.readAllLines(dictionary.toPath())) {
				
				output.add(s);
				
			}
			
		} catch (IOException e) {
			System.err.println("File not found.");
			e.printStackTrace();
		}
		
		return output;
		
	}
	
	public Vector<String> getDictionaryVector() {
		
		Vector<String> output = new Vector<String>();
		
		try {
			
			for (String s : Files.readAllLines(dictionary.toPath())) {
				
				output.add(s);
				
			}
			
		} catch (IOException e) {
			System.err.println("File not found.");
			e.printStackTrace();
		}
		
		return output;
		
	}
	
	
}
