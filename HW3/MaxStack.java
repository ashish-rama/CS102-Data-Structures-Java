package llHomework;

public interface MaxStack<T> {
	boolean isEmpty();
	
	void push(T info);
	
	T pop();
	
	T getMaxSoFar();
}