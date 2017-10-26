package HW7;

//----------------------------------------------------------------------------
//BSTNode.java               by Dale/Joyce/Weems                    Chapter 8
//
//Implements Comparable nodes for a binary search tree.
//----------------------------------------------------------------------------

public class TreeNode<T> implements Comparable<Object> {
	// Used to hold references to BST nodes for the linked implementation
	protected T info;                // The info in a BST node
	protected TreeNode<T> left;       // A link to the left child node
	protected TreeNode<T> right;      // A link to the right child node
	protected Double distance;

	public TreeNode(T info, Double distance) {
		this.info = info;
		left = null;
		right = null;
		this.distance = distance;
	}

	public void setInfo(T info) {
		// Sets info of this BSTNode.
		this.info = info;
	}

	public T getInfo() {
		// Returns info of this BSTNode.
		return info;
	}

	public void setLeft(TreeNode<T> link) {
		// Sets left link of this BSTNode.
		left = link;
	}

	public void setRight(TreeNode<T> link) {
		// Sets right link of this BSTNode.
		right = link;
	}

	public TreeNode<T> getLeft() {
		// Returns left link of this BSTNode.
		return left;
	}

	public TreeNode<T> getRight() {
		// Returns right link of this BSTNode.
		return right;
	}

	@Override
	public int compareTo(Object o) {
		return distance.compareTo(((TreeNode<?>) o).distance);
	}

}

