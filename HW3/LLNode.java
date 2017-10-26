package llHomework;

public class LLNode<T> {
	public T info;
	public LLNode<T> link;

	public LLNode(T info, LLNode<T> link) {
		this.link = link;
		this.info = info;
	}
	
	public LLNode<T> pushValue(T info) {
		return new LLNode<T>(info, this);
	}
}