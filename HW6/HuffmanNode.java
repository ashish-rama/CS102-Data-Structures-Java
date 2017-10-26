package HW6;

/**
 * HuffmanNode class; Professor Evan Korth; Assignment 5
 * @author Ashish Ramachandran (ar3986)
 */
public class HuffmanNode implements Comparable<Object> {

	public String letter; 
	public Double frequency;
	public HuffmanNode left, right;

	public HuffmanNode(String letter, Double frequency) {
		this.letter = letter;
		this.frequency = frequency;
		this.left = null;
		this.right = null;
	}

	public HuffmanNode(HuffmanNode left, HuffmanNode right) {
		this.letter = left.letter + right.letter;
		this.frequency = left.frequency + right.frequency;
		this.left = left;
		this.right = right;
	}

	public String toString() {
		return "<" + (letter.equalsIgnoreCase("\n") ? "\\n" : letter) + ", " + frequency + ">";
	}

	public int compareTo(Object o) {
		HuffmanNode huff = (HuffmanNode) o;
		return frequency.compareTo(huff.frequency);
	}

	// - add constructors to initialize letter and frequency, etc.
	// - add int compareTo(Object o) method so we can compare 
	//   two HuffmanNodes based on their frequency attributes. 
	// - we're going to build a .heap. of these HuffmanNodes...
}