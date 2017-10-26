package llHomework;

public class SlowMaxStack<T> implements MaxStack<T> {

	private final Maximizer<T> maximizer;
	private LLNode<T> top;

	public SlowMaxStack(Maximizer<T> maximizer) {
		this.maximizer = maximizer;
	}

	@Override
	public boolean isEmpty() {
		return top == null;
	}

	@Override
	public void push(T info) {
		if (top == null)
			top = new LLNode<T>(info, null);
		else
			top = top.pushValue(info);
	}

	@Override
	public T pop() {
		T info = top.info;
		top = top.link;
		return info;
	}

	@Override
	public T getMaxSoFar() {
		T currentMax = maximizer.getGlobalMin();

		for(LLNode<T> node = top; node != null; node = node.link) {
			currentMax = maximizer.getMax(currentMax, node.info);
		}

		return currentMax;
	}

}