package solver;

import java.awt.Point;
import java.util.Vector;

/**
 * Use this to create different layouts for different puzzles. This will facilitate the discovery of possible words.
 * @author Grayson Spidle
 *
 */
public class PuzzleFormatManager {
	
	/* P X E
	 * O T R
	 * * A P
	 * 
	 * Trap
	 * Expo
	 */
	
	Vector<Vector<Character>> rows = new Vector<Vector<Character>>();
	
	int numberOfRows = rows.size();
	
	/**
	 * The constructor. Use "*" marks to indicate empty spaces.
	 * @param rows The rows.
	 * @param columns The columns.
	 */
	 public PuzzleFormatManager(Vector<Vector<Character>> rows) {
		 
		 this.rows = rows;
		 numberOfRows = this.rows.size();
		 
		 
	 }
	 
	 /**
	  * This is the test constructor
	  */
	 public PuzzleFormatManager() {
		 
		 initializeTestFormat();
		 numberOfRows = this.rows.size();
		 
	 }
	 
	 private void initializeTestFormat() {
		 
		 // TODO Possible issue may arise here
		 
		 /* Setting it a 3x3 */
		 rows.add(new Vector<Character>());
		 	rows.get(0).add(new Character('*'));
		 	rows.get(0).add(new Character('*'));
		 	rows.get(0).add(new Character('*'));
		 rows.add(new Vector<Character>());
		 	rows.get(0).add(new Character('*'));
		 	rows.get(0).add(new Character('*'));
		 	rows.get(0).add(new Character('*'));
		 rows.add(new Vector<Character>());
		 	rows.get(0).add(new Character('*'));
		 	rows.get(0).add(new Character('*'));
		 	rows.get(0).add(new Character('*'));
		 
		 
		 /* ****** */
		 
		 /* Setting the values for each row */
		 rows.get(0).set(0,'P');
		 rows.get(0).set(1,'X');
		 rows.get(0).set(2,'E');
		 rows.get(1).set(0,'O');
		 rows.get(1).set(1,'T');
		 rows.get(1).set(2,'R');
		 rows.get(2).set(0,'*');
		 rows.get(2).set(1,'A');
		 rows.get(2).set(2,'P');
		 /* ******** */
		 
	 }
	 
	 public Vector<Vector<Character>> getNeighboringLetters(Character arg0) {
		 
		 Vector<Vector<Character>> output = new Vector<Vector<Character>>();
		 Vector<Point> coords = getCoords(new Character(arg0));
		 Vector<Vector<Point>> neighboringCoords = new Vector<Vector<Point>>();
		 		 
		 // Get ALL neighboring coordinates & add stuff to the output
		 for (int counter = 0; counter > coords.size(); counter++) {
			 
			 neighboringCoords.add(getNeighboringCoords(coords.get(counter)));
			 output.add(new Vector<Character>());
			 
		 }
		 
		 // Convert the coords into Letters
		 int counter = 0;
		 for (Vector<Point> vp : neighboringCoords) {
			 
			 Vector<Point> dupe = new Vector<Point>();
			 dupe.addAll(vp);
			 
			 for (Point p : dupe) {
				 
				 output.get(counter).add(getLetterAt(p));
				 
			 }
			 
			 counter++;
			 
		 }
		 
		 return output;
		 
	 }
	 
	 private Vector<Point> getNeighboringCoords(Point arg0) {
		 
		 Vector<Point> output = new Vector<Point>();
		 int adjustY = 0;
		 
		 output.add(new Point(arg0.x + 1, arg0.y + 0));
		 output.add(new Point(arg0.x + 0, arg0.y + 1));
		 output.add(new Point(arg0.x + 1, arg0.y + 1));
		 output.add(new Point(arg0.x - 1, arg0.y + 0));
		 output.add(new Point(arg0.x + 0, arg0.y - 1));
		 output.add(new Point(arg0.x - 0, arg0.y - 1));
		 output.add(new Point(arg0.x + 1, arg0.y - 1));
		 output.add(new Point(arg0.x - 1, arg0.y + 1));
		 
		 // Checks if the points are actually on the format
		 for (Point p : output) {
			 
			 if (p.y > rows.size() || p.y < rows.size()) {
				 
				 output.remove(p);
				 
			 }
			 
			 else if (p.x > rows.get(p.y).size()) {
				 
				 output.remove(p);
				 
			 }
			 
		 }
		 
		 // Filter out the ones that contain asterisks
		 for (Point p : output) {
			 
			 if (getLetterAt(p) == '*') {
				 
				 output.remove(p);
				 
			 }
			 
		 }
		 
		 return output;
		 
	 }
	 
	 private Vector<Point> getCoords(Character arg0) {
		 
		 Vector<Point> output = new Vector<Point>();
		 int x = 0;
		 int y = 0;
		 
		 while (true) {
			 
			if (rows.get(y).get(x) == arg0) {
			 
				output.add(new Point(x,y));
			 
			}
			
			x++;
			
			if (x > rows.get(y).size()) {
			 
				x = 0;
				y++;
				 
			}
			
			if (y > rows.size()) {
				
				return output; 
				 
			}
			 
		 }
		 
	 }
	 
	 private Character getLetterAt(Point arg0) {
		 
		Character output;
		output = rows.get(arg0.y).get(arg0.x);
		return output;
		
	 }
	 

}
