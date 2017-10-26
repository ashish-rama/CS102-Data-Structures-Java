package llHomework;

/**
 * FastMaxStack class; CS102 Data Structures (Evan Korth) Assignment 3 (Part 3)
 * @author Ashish Ramachandran (ar3986)
 */
public class FastMaxStack<T> implements MaxStack<T> {

	private final Maximizer<T> maximizer;
	private LLNode<T> top;
	private LLNode<T> topOfMaxStack;

	/**
	 * FastMaxStack constructor
	 * @param maximizer a class that implements Maximizer<T> to help with evaluations of elements in the stack
	 */
	public FastMaxStack(Maximizer<T> maximizer) {
		this.maximizer = maximizer;
	}

	/**
	 * Checks if the stack is empty by checking if there is a top value
	 * @return true if top is null (there are no elements)
	 */
	@Override
	public boolean isEmpty() {
		return top == null;
	}

	/**
	 * Pushes a new LLNode<T> onto the stack; Since both the current stack and the maxStack are always of same size,
	 * when the current stack is empty, need to create a new node for both the current stack and the maxStack (pushing the same
	 * value as the first value pushed on the stack is the maximum of the stack). When the current stack pushes a new value,
	 * we push it and then we evaluate if the incoming value is larger than the top of the maxStack and push the larger to the 
	 * maxStack (ensuring always of same size)
	 * @param info the new node to push
	 */
	@Override
	public void push(T info) {
		if (top == null) {
			top =  new LLNode<T>(info, null);
			topOfMaxStack = new LLNode<T>(info, null);
		} else {
			top = top.pushValue(info);
			if(maximizer.getMax(info, topOfMaxStack.info).equals(info)) {
				topOfMaxStack = topOfMaxStack.pushValue(info);
			} else {
				topOfMaxStack = topOfMaxStack.pushValue(topOfMaxStack.info);
			}
		}
	}

	/**
	 * Pops a LLNode<T> from the stack; Because the current stack and maxStack always should have the same size, we also
	 * pop the top value of maxStack to also ensure that the maximum of the current stack is the top of the maxStack. This
	 * logic is properly implemented in the push(T info) method
	 */
	@Override
	public T pop() {
		T info = top.info;
		top = top.link;
		topOfMaxStack = topOfMaxStack.link;
		return info;
	}

	/**
	 * Gets the maximum of the stack so far which (assuming not empty stack) is the top of the maxStack stack
	 */
	@Override
	public T getMaxSoFar() {
		if(topOfMaxStack != null)
			return topOfMaxStack.info;
		return null;
	}

}
