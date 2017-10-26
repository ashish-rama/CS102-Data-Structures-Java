package HW4;

public class ExpressionNode<T extends Comparable<T>> {
	// Used to hold references to Exp nodes for the linked implementation
	protected T info;                // The info in a Exp node
	protected ExpressionNode<T> left;       // A link to the left child node
	protected ExpressionNode<T> right;      // A link to the right child node

	public ExpressionNode(T info) {
		this.info = info;
		left = null;
		right = null;
	}

	public void setInfo(T info)
	// Sets info of this ExpressionNode.
	{
		this.info = info;
	}

	public T getInfo()
	// Returns info of this ExpressionNode.
	{
		return info;
	}

	public void setLeft(ExpressionNode<T> link)
	// Sets left link of this ExpressionNode.
	{
		left = link;
	}

	public void setRight(ExpressionNode<T> link)
	// Sets right link of this ExpressionNode.
	{
		right = link;
	}

	public ExpressionNode<T> getLeft()
	// Returns left link of this ExpressionNode.
	{
		return left;
	}

	public ExpressionNode<T> getRight()
	// Returns right link of this ExpressionNode.
	{
		return right;
	}
}