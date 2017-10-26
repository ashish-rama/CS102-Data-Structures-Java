package HW4;

/**
 * Operator Enum that holds all data for operators including priorities and comparisons (Assignment 4)
 * @author Ashish Ramachandran (ar3986) 
 */
public enum Operator {
	
	OPEN_PARENTHESIS('(', 0), CLOSE_PARENTHESIS(')', 0),
	PLUS('+', 1), MINUS('-', 1), 
	MULTIPLY('*', 2), DIVIDE('/', 2),
	POWER('^', 3);

	public final char symbol;
	final int precedence;

	Operator(char symbol, int precedence) {
		this.symbol = symbol;
		this.precedence = precedence;
	}

	public static Operator getOperator(char symbol) {
		for(Operator o: Operator.values()) {
			if (o.symbol == symbol)
				return o;
		}
		return null;
	}
	
	public static Operator getOperator(String symbol) {
		for(Operator o: Operator.values()) {
			if (("" + o.symbol).equals(symbol))
				return o;
		}
		return null;
	}

	public static boolean isOperator(char symbol) {
		return getOperator(symbol) != null;
	}
	
	public static boolean isOperator(String symbol) {
		return getOperator(symbol) != null;
	}

	public static boolean isHigherPrecedence(Operator op1, Operator op2) {
		return op1.precedence > op2.precedence;
	}
	
	public static boolean isGreaterOrEqualPrecedence(Operator op1, Operator op2) {
		return op1.precedence >= op2.precedence;
	}
	
	public static boolean isOpenParenthesis(Operator o) {
		return o.equals(OPEN_PARENTHESIS);
	}
	
	public int getPrecedence() {
		return precedence;
	}
}
