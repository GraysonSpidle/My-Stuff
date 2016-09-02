package solver;

import java.awt.Point;

/**
 * I'm such a dumbass. I don't know what good creating this did. (But I can't turn back now)
 * @author Grayson Spidle
 *
 */
public class Letter {
	
	private String letter;
	private Point coords; // short for coordinates
	
	public Letter(String s, Point p) {
		
		letter = s;
		coords = p;
		
	}
	
	public Letter(String s) {
		
		letter = s;
		
	}

	public String get() {
		return letter;
	}

	public void set(String letter) {
		this.letter = letter;
	}

	public Point getCoords() {
		return coords;
	}

	public void setCoords(Point coordinate) {
		this.coords = coordinate;
	}

}
