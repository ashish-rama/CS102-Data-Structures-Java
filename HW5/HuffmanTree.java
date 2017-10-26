package HW5;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * HuffmanTree class; Professor Evan Korth; Assignment 5
 * @author Ashish Ramachandran (ar3986)
 */
public class HuffmanTree {

	HuffmanNode root;
	
	public HuffmanTree(HuffmanNode huff) {
		this.root = huff;
	}
	
	public void printLegend() {
		printLegend(root, "");
	}
	
	private void printLegend(HuffmanNode t, String s) {
		if(t.letter.length() > 1) {
			printLegend(t.left, s + "0");
			printLegend(t.right, s + "1");
		} else if (t.letter.length() == 1) {
			System.out.println(t.letter + "=" + s);
		}
	}
	
	public static BinaryHeap<HuffmanNode> fileToHeap(String filename) {
		HuffmanNode[] huffmanArray = null;
		int huffmanIndex = 0;
		try {
			BufferedReader in = new BufferedReader(new FileReader(filename));
			while(true) {
				String line = in.readLine();
				if(line == null)
					break;
				String[] splittedLine = line.split(" ");
				huffmanArray = new HuffmanNode[splittedLine.length / 2];
				for(int i = 0; i < splittedLine.length; i += 2) {
					huffmanArray[huffmanIndex++] = new HuffmanNode(splittedLine[i], Double.parseDouble(splittedLine[i+1]));
				}
			}
			in.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return new BinaryHeap<HuffmanNode>(huffmanArray);
	}
	
	public static HuffmanTree createFromHeap(BinaryHeap<HuffmanNode> b) {
		HuffmanNode newRoot = null;
		while(!b.isEmpty()) {
			newRoot = b.deleteMin();
			if(!b.isEmpty()) {
				HuffmanNode secondMin = b.deleteMin();
				HuffmanNode merged = new HuffmanNode(newRoot, secondMin);
				merged.left = secondMin;
				merged.right = newRoot;
				b.insert(merged);
			}
		}
		return new HuffmanTree(newRoot);
	}
	
	public static void main(String[] args) {
		BinaryHeap<HuffmanNode> bheap = fileToHeap(args[0]);
		HuffmanTree htree = createFromHeap(bheap);
		htree.printLegend();
	}

	// - add a constructor to init the Tree from a HuffmanNode
	// - the main method will go here, as well as code to take
	//   a command-line argument for the input file name
}
