package HW4;


public class ExpressionTree<T extends Comparable<T>> {
	
	protected ExpressionNode<T> root;      // reference to the root of this ExpressionTree

	boolean found;   // used by remove
	
	// Creates an empty ExpressionTree object.
	public ExpressionTree()	{
		root = null;
	}
	// Returns true if this ExpressionTree is empty; otherwise, returns false.
	public boolean isEmpty() {
		return (root == null);
	}
	
	// Returns the number of elements in tree.
	private int recSize(ExpressionNode<T> tree)	{
		if (tree == null)    
			return 0;
		else
			return recSize(tree.getLeft()) + recSize(tree.getRight()) + 1;
	}

	public int size()
	// Returns the number of elements in this ExpressionTree.
	{
		return recSize(root);
	}

	private boolean recContains(T element, ExpressionNode<T> tree)
	// Returns true if tree contains an element e such that 
	// e.compareTo(element) == 0; otherwise, returns false.
	{
		if (tree == null)
			return false;       // element is not found
		else if (element.compareTo(tree.getInfo()) < 0)
			return recContains(element, tree.getLeft());   // Search left subtree
		else if (element.compareTo(tree.getInfo()) > 0)
			return recContains(element, tree.getRight());  // Search right subtree
		else
			return true;        // element is found
	}

	public boolean contains (T element)
	// Returns true if this ExpressionTree contains an element e such that 
	// e.compareTo(element) == 0; otherwise, returns false.
	{
		return recContains(element, root);
	}

	private T recGet(T element, ExpressionNode<T> tree)
	// Returns an element e from tree such that e.compareTo(element) == 0;
	// if no such element exists, returns null.
	{
		if (tree == null)
			return null;             // element is not found
		else if (element.compareTo(tree.getInfo()) < 0)
			return recGet(element, tree.getLeft());          // get from left subtree
		else
			if (element.compareTo(tree.getInfo()) > 0)
				return recGet(element, tree.getRight());         // get from right subtree
			else
				return tree.getInfo();  // element is found
	}

	public T get(T element)
	// Returns an element e from this ExpressionTree such that e.compareTo(element) == 0;
	// if no such element exists, returns null.
	{
		return recGet(element, root);
	}

	private ExpressionNode<T> recAdd(T element, ExpressionNode<T> tree)
	// Adds element to tree; tree retains its ExpressionTree property.
	{
		if (tree == null)
			// Addition place found
			tree = new ExpressionNode<T>(element);
		else if (element.compareTo(tree.getInfo()) <= 0)
			tree.setLeft(recAdd(element, tree.getLeft()));    // Add in left subtree
		else
			tree.setRight(recAdd(element, tree.getRight()));   // Add in right subtree
		return tree;
	}

	public void add (T element)
	// Adds element to this ExpressionTree. The tree retains its ExpressionTree property.
	{
		root = recAdd(element, root);
	}

	private T getPredecessor(ExpressionNode<T> tree)
	// Returns the information held in the rightmost node in tree
	{
		while (tree.getRight() != null)
			tree = tree.getRight();
		return tree.getInfo();
	}

	private ExpressionNode<T> removeNode(ExpressionNode<T> tree)
	// Removes the information at the node referenced by tree.
	// The user's data in the node referenced by tree is no
	// longer in the tree.  If tree is a leaf node or has only
	// a non-null child pointer, the node pointed to by tree is
	// removed; otherwise, the user's data is replaced by its
	// logical predecessor and the predecessor's node is removed.
	{
		T data;

		if (tree.getLeft() == null)
			return tree.getRight();
		else if (tree.getRight() == null) 
			return tree.getLeft();
		else
		{
			data = getPredecessor(tree.getLeft());
			tree.setInfo(data);
			tree.setLeft(recRemove(data, tree.getLeft()));  
			return tree;
		}
	}

	private ExpressionNode<T> recRemove(T element, ExpressionNode<T> tree)
	// Removes an element e from tree such that e.compareTo(element) == 0
	// and returns true; if no such element exists, returns false. 
	{
		if (tree == null)
			found = false;
		else if (element.compareTo(tree.getInfo()) < 0)
			tree.setLeft(recRemove(element, tree.getLeft()));
		else if (element.compareTo(tree.getInfo()) > 0)
			tree.setRight(recRemove(element, tree.getRight()));
		else  
		{
			tree = removeNode(tree);
			found = true;
		}
		return tree;
	}

	public boolean remove (T element)
	// Removes an element e from this ExpressionTree such that e.compareTo(element) == 0
	// and returns true; if no such element exists, returns false. 
	{
		root = recRemove(element, root);
		return found;
	}

}