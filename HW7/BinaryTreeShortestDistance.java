package HW7;

import java.util.Scanner;
/**
 * BinaryTreeShortestDistance class which contains the main method; CS102 Data Structures; Evan Korth
 * Assignment 7
 * Calculates the shortest distance to "*"
 * @author Ashish Ramachandran (ar3986)
 */
public class BinaryTreeShortestDistance {
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter a binary tree input:");
		String input = scan.nextLine();
		TreeNode<String> tree = inputToTree(input);
		System.out.println("Found \"*\" at distance: " + findClosest(tree, "*"));
		scan.close();
	}
	
	public static TreeNode<String> inputToTree(String input) {
		ArrayStack<TreeNode<String>> stack = new ArrayStack<TreeNode<String>>();
		Scanner tokenizer = new Scanner(input);
		while(tokenizer.hasNext()){
			if(tokenizer.hasNext("[^()]")){ //accounts for if need to create node
				stack.push(new TreeNode<String>(tokenizer.next(), tokenizer.nextDouble()));
			} else if (tokenizer.hasNext("[(]")){
				tokenizer.next();
			} else { //accounts for adding children to a parent node
				tokenizer.next();
				TreeNode<String> right = stack.top();
				stack.pop();
				TreeNode<String> left = stack.top();
				stack.pop();
				stack.top().setRight(right);
				stack.top().setLeft(left);
			}
		}
		tokenizer.close();
		return stack.top();
	}
	
	public static double findClosest(TreeNode<String> root, Object target) {
		BinaryHeap<TreeNode<String>> minHeap = new BinaryHeap<TreeNode<String>>();
		minHeap.insert(root);
		TreeNode<String> q = null;
		while(!minHeap.isEmpty()) {
			q = minHeap.deleteMin();
			if(q != null && !q.info.equals(target)) {
				if(q.left != null) {
					q.left.distance += q.distance;
					minHeap.insert(q.left);
				}
				if(q.right != null) {
					q.right.distance += q.distance;
					minHeap.insert(q.right);
				}
			} else if (q.info.equals(target)) {
				return q.distance;
			}	
		}
		return -1;
	}

}
