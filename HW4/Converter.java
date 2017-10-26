package HW4;

import java.util.Scanner;

/**
 * Converter Class that will convert an input String to postfix expression (Assignment 4)
 * Also takes into account for negative inputs
 * @author Ashish Ramachandran (ar3986)
 */
public class Converter {

	private String infixExpression;

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
				switch(Operator.getOperator(characterOfInfix)) {
				case POWER:
				case OPEN_PARENTHESIS:
					//If operator is open parenthesis, push to stack
					//If operator is power, will always have a higher precedence than top of stack
					stack.push(characterOfInfix);
					break;

				case DIVIDE:
				case MULTIPLY:
				case PLUS:
				case MINUS:
					//If operator is of lower precedence, pop everything from stack that has higher precedence
					//If both are the same precedence, pop from stack and replace with next operator
					while(!stack.isEmpty() && Operator.isGreaterOrEqualPrecedence(Operator.getOperator(stack.top()), Operator.getOperator(characterOfInfix))) {
						output += stack.top() + " ";
						stack.pop();
					}
					stack.push(characterOfInfix);
					break;

				case CLOSE_PARENTHESIS:
					//If operator is close parenthesis, pop everything from the stack until it reaches the open parenthesis
					while(!stack.isEmpty() && !Operator.isOpenParenthesis(Operator.getOperator(stack.top()))) {
						output += stack.top() + " ";
						stack.pop();
					}
					//pops the open parenthesis from the stack
					stack.pop();
					break;
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

	public ExpressionNode<String> getExpressionTree(String postfix) {

		BoundedStackInterface<ExpressionNode<String>> stack = new ArrayStack<ExpressionNode<String>>(50);
		int value;
		String operator;
		ExpressionNode<String> node1;
		ExpressionNode<String> node2;

		ExpressionNode<String> result = null;

		@SuppressWarnings("resource")
		Scanner tokenizer = new Scanner(postfix);

		while (tokenizer.hasNext()) {
			if (tokenizer.hasNextInt()) {
				// Process operand.
				value = tokenizer.nextInt();
				ExpressionNode<String> node = new ExpressionNode<String>(""+value);

				if (stack.isFull())
					throw new PostFixException("Too many operands - stack overflow");
				stack.push(node);

			} else {

				// Process operator.
				operator = tokenizer.next();
				ExpressionNode<String> node = new ExpressionNode<String>(operator);
				if (stack.isEmpty())
					throw new PostFixException("Not enough operands - stack underflow");
				node2 = stack.top();
				stack.pop();

				// Obtain first operand from stack.
				if (stack.isEmpty())
					throw new PostFixException("Not enough operands - stack underflow");
				node1 = stack.top();
				stack.pop();

				// Assign nodes to right and left
				node.setRight(node2);
				node.setLeft(node1);

				stack.push(node);
			}
		}

		// Obtain final result from stack. 
		if (stack.isEmpty())
			throw new PostFixException("Not enough operands - stack underflow");
		result = stack.top();
		stack.pop();

		// Stack should now be empty.
		if (!stack.isEmpty())
			throw new PostFixException("Too many operands - operands left over");

		// Return the final.
		return result;		
	}

}
