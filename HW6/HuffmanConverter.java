package HW6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * HuffmanConverter class; Professor Evan Korth; CS102 Data Structures Assignment 6
 * @author Ashish Ramachandran (ar3986)
 */
public class HuffmanConverter { 
	// The # of chars in the ASCII table dictates
	// the size of the count[] & code[] arrays.
	public static final int NUMBER_OF_CHARACTERS = 256;

	// the contents of our message...
	private String contents;

	// the tree created from the msg
	private HuffmanTree huffmanTree;

	// tracks how often each character occurs
	private int count[];

	// the huffman code for each character
	private String code[];

	// stores the # of unique chars in contents
	//private int uniqueChars = 0; //(optional)
	
	//used to print the codes nicely
	private int numberPerLine = 0;

	/** Constructor taking input String to be converted */
	public HuffmanConverter(String input) {
		this.contents = input;
		this.count = new int[NUMBER_OF_CHARACTERS];
		this.code = new String[NUMBER_OF_CHARACTERS];
	}

	/**
	 * Records the frequencies that each character of our
	 * message occurs...
	 * I.e., we use 'contents' to fill up the count[] list...
	 */
	public void recordFrequencies() {
		for(int i = 0; i < contents.length(); i++)
			count[(int) contents.charAt(i)]++;
	}

	/**
	 * Converts our frequency list into a Huffman Tree. We do this by
	 * taking our count[] list of frequencies, and creating a binary
	 * heap in a manner similar to how a heap was made in HuffmanTree's
	 * fileToHeap method. Then, we print the heap, and make a call to
	 * HuffmanTree.heapToTree() method to get our much desired
	 * HuffmanTree object, which we store as huffmanTree.
	 */
	public void frequenciesToTree() {
		ArrayList<HuffmanNode> list = new ArrayList<HuffmanNode>();
		//Adds correct number of nodes to an ArrayList to be converted into an array to create a binary heap
		for(int i = 0; i < count.length; i++) {
			if(count[i] != 0)
				list.add(new HuffmanNode("" + (char) i, (double) count[i]));
		}
		HuffmanNode[] huffmanArray = new HuffmanNode[list.size()];
		for(int i = 0; i < list.size(); i++) {
			huffmanArray[i] = list.get(i);
			System.out.println((i+1) + ": " + "[" + huffmanArray[i] + "]");
		}
		
		BinaryHeap<HuffmanNode> bheap = new BinaryHeap<HuffmanNode>(huffmanArray);
		huffmanTree = HuffmanTree.createFromHeap(bheap);
	}

	/**
	 * Iterates over the huffmanTree to get the code for each letter.
	 * The code for letter i gets stored as code[i]... This method
	 * behaves similarly to HuffmanTree's printLegend() method...
	 * Warning: Don't forget to initialize each code[i] to ""
	 * BEFORE calling the recursive version of treeToCode...
	 */
	public void treeToCode() {
		for(int i = 0; i < code.length; i++)
			code[i] = "";
		treeToCode(huffmanTree.root, "");
	}

	/*
	 * A private method to iterate over a HuffmanNode t using s, which
	 * contains what we know of the HuffmanCode up to node t. This is
	 * called by treeToCode(), and resembles the recursive printLegend
	 * method in the HuffmanTree class. Note that when t is a leaf node,
	 * t's letter tells us which index i to access in code[], and tells
	 * us what to set code[i] to...
	 */
	private void treeToCode(HuffmanNode t, String s) {
		if(t.letter.length() > 1) {
			treeToCode(t.left, s + "0");
			treeToCode(t.right, s + "1");
		} else if (t.letter.length() == 1) {
			code[(int) t.letter.charAt(0)] = s;
			System.out.print("'" + (t.letter.equalsIgnoreCase("\n") ? "\\n" : t.letter) + "'" + "=" + s + " ");
			//Prints with nice format
			if(numberPerLine++ % 5 == 0)
				System.out.println();
		}
	}

	/**
	 * Using the message stored in contents, and the huffman conversions
	 * stored in code[], we create the Huffman encoding for our message
	 * (a String of 0's and 1's), and return it...
	 */
	public String encodeMessage() {
		String encoded = "";
		for(int i = 0; i < contents.length(); i++)
			encoded += code[(int) contents.charAt(i)];
		return encoded;
	}

	/**
	 * Reads in the contents of the file named filename and returns
	 * it as a String. The main method calls this method on args[0]...
	 */
	public static String readContents(String filename) {
		String fileContents = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			while(true) {
				String line = in.readLine();
				if(line == null)
					break;
				fileContents += line;
				fileContents += '\n';
			}
			in.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return fileContents;
	}

	/**
	 * Using the encoded String argument, and the huffman codings,
	 * re-create the original message from our
	 * huffman encoding and return it...
	 */
	public String decodeMessage(String encodedStr) {
		String decoded = "";
		HuffmanNode node = huffmanTree.root;
		
		//Special case of only one node
		if(node.left == null && node.right == null)
			return node.letter;
		
		for(int i = 0; i < encodedStr.length(); i++) {
			if(encodedStr.charAt(i) == '0')
				node = node.left;
			else if (encodedStr.charAt(i) == '1')
				node = node.right;
			if(node.left == null && node.right == null) {
				decoded += node.letter;
				node = huffmanTree.root;
			}
		}
		
		return decoded;
	}

	/**
	 * Uses args[0] as the filename, and reads in its contents. Then
	 * instantiates a HuffmanConverter object, using its methods to
	 * obtain our results and print the necessary output. Finally,
	 * decode the message and compare it to the input file.<p>
	 * NOTE: Example method provided below...
	 */
	public static void main(String args[]) {
		//call all your methods from here
		String con = readContents(args[0]);
		HuffmanConverter huffConverter = new HuffmanConverter(con);
		huffConverter.recordFrequencies();
		
		huffConverter.frequenciesToTree();
		System.out.println();
		
		huffConverter.treeToCode();
		
		System.out.println("\n\nHuffman Encoding:");
		String encodedMessage = huffConverter.encodeMessage();
		System.out.println(encodedMessage);
		System.out.println();
		
		System.out.println("Message size in ASCII encoding: " + huffConverter.contents.length() * 8);
		System.out.println("Message size in Huffman coding: " + encodedMessage.length());
		System.out.println();
		
		String decodedMessage = huffConverter.decodeMessage(encodedMessage);
		System.out.println(decodedMessage);
	}
}