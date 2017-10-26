package HW1;

/**
 * Converter Class that will convert an input String to postfix expression (Assignment 1)
 * Also takes into account for negative inputs
 * @author Ashish Ramachandran (ar3986)
 */
public class Converter {

	private String infixExpression;
	private static final String HIGHEST_PRECEDENCE = "*/^";
	private static final String LOWER_PRECEDENCE = "+-";
	private static final String OPEN_PARENTHESIS = "(";
	private static final String CLOSE_PARENTHESIS = ")";

	/**
	 * Converter constructor
	 * @param infixExpression an inputed string
	 */
	public Converter(String infixExpression)  {
		this.infixExpression = infixExpression;
	}

	/**
	 * Converts an inputed String (infix) to a postfix expression
	 * @return the postfix expression of an inputed (infix) String
	 */
	public String toPostFix() {
		String output = "";
		BoundedStackInterface<Character> stack = new ArrayStack<Character>(50);  

		//Evaluate each character of the infix expression
		for (int i = 0; i < infixExpression.length(); i++) {
			//Gets the next character to evaluate
			char characterOfInfix = infixExpression.charAt(i);
			
			//If there is a negative number (if its the first number or there is an operator right before the negative sign)
			if(infixExpression.substring(i, i + 1).equals("-") &&
					(i == 0 || (i > 0 && !Character.isDigit(infixExpression.charAt(i - 1))))) {
				String negativeNumber = "-";
				//Look at the next digit to see if it is part of a number with more than one digit
				int j = i + 1;
				while(j < infixExpression.length() && Character.isDigit(infixExpression.charAt(j))) {
					negativeNumber += "" + infixExpression.charAt(j);
					j++;
				}
				//set the iterator to the location after the number with more than one digit
				i = j - 1;
				//has to get the next character to avoid double input/errors
				characterOfInfix = infixExpression.charAt(i);
				output += negativeNumber + " ";
			}

			if (Character.isDigit(characterOfInfix)) {
				//Process operand
				String completeValue = "" + characterOfInfix;
				
				//Look at the next digit to see if it is part of a number with more than one digit
				int j = i + 1;
				while(j < infixExpression.length() && Character.isDigit(infixExpression.charAt(j))) {
					completeValue += "" + infixExpression.charAt(j);
					j++;
				}
				//set the iterator to the location after the number with more than one digit
				i = j - 1;
				
				output += completeValue + " ";
			} else {
				// Process operator.
				if(HIGHEST_PRECEDENCE.contains("" + characterOfInfix)) {
					//If both are the same precedence, pop from stack and replace with next operator
					if(!stack.isEmpty() && HIGHEST_PRECEDENCE.contains("" + stack.top())) {
						output += stack.top() + " ";
						stack.pop();
					}
					stack.push(characterOfInfix);
				} else if(LOWER_PRECEDENCE.contains("" + characterOfInfix)) {
					//If operator is of lower precedence, pop everything from stack that has higher precedence
					if(!stack.isEmpty() && HIGHEST_PRECEDENCE.contains("" + stack.top())) {
						while(!stack.isEmpty() && HIGHEST_PRECEDENCE.contains("" + stack.top())) {
							output += stack.top() + " ";
							stack.pop();
						}
					} 
					//If both are the same precedence, pop from stack and replace with next operator
					else if(!stack.isEmpty() && LOWER_PRECEDENCE.contains("" + stack.top())) {
						output += stack.top() + " ";
						stack.pop();
					}
					stack.push(characterOfInfix);
				} else if(OPEN_PARENTHESIS.contains("" + characterOfInfix)) {
					//If operator is open parenthesis, push to stack
					stack.push(characterOfInfix);
				} else if(CLOSE_PARENTHESIS.contains("" + characterOfInfix)) {
					//If operator is close parenthesis, pop everything from the stack until it reaches the open parenthesis
					while(!stack.isEmpty() && !OPEN_PARENTHESIS.contains("" + stack.top())) {
						output += stack.top() + " ";
						stack.pop();
					}
					//pops the open parenthesis from the stack
					stack.pop();
				}
			}
		}
		//output the remaining operators
		while(!stack.isEmpty()) {
			output += stack.top() + " ";
			stack.pop();
		}
		return output;
	}

}
