package HW4;

import java.util.Scanner;

/**
 * Calculator Class that will evaluate the postfix expression (Assignment 4)
 * Checks for division by zero and prints a user-friendly response
 * @author Ashish Ramachandran (ar3986)
 */
public class Calculator {

	public static void main(String[] args) {

		Scanner conIn = new Scanner(System.in);
		Converter converter = null;

		String line = null;          // string to be evaluated
		String more = null;          // used to stop or continue processing
		double result;               // result of evaluation

		do {
			// Get next expression to be processed.
			System.out.println("Enter an infix expression to be evaluated: ");
			line = conIn.nextLine();

			//creates a Converter object that will convert the infix expression to postfix
			converter = new Converter(line);

			// Obtain and output result of expression evaluation.
			try {
				//Convert to postfix
				line = converter.toPostFix();
				System.out.println("Converted to postfix: " + line);

				//calculate the expression
				result = evaluate(line);

				// Output result.
				//System.out.println();
				//(10-2)*3+4/(1+1)
				System.out.println("Result = " + result);

				//Create expression tree
				ExpressionNode<String> tree = converter.getExpressionTree(line);
				System.out.print("PreOrder Expression: ");
				preOrder(tree);
				System.out.println();

				System.out.print("InOrder Expression: ");
				inOrder(tree);
				System.out.println();

				System.out.print("PostOrder Expression: ");
				postOrder(tree);
				System.out.println();


			} catch (PostFixException error) {        
				// Output error message.
				System.out.println();
				System.out.println("Error in expression - " + error.getMessage());
			}

			// Determine if there is another expression to process.
			System.out.println();
			System.out.print("Evaluate another expression? (Y=Yes): ");
			more = conIn.nextLine();
			System.out.println();
		} while (more.equalsIgnoreCase("y"));

		System.out.println("Program completed.");
		conIn.close();
	}

	public static double evaluate(String expression) {

		BoundedStackInterface<Double> stack = new ArrayStack<Double>(50);  
		double value;
		String operator;

		double operand1;
		double operand2;

		double result = 0;

		@SuppressWarnings("resource")
		Scanner tokenizer = new Scanner(expression);

		while (tokenizer.hasNext()) {
			if (tokenizer.hasNextInt()) {
				// Process operand.
				value = tokenizer.nextInt();
				if (stack.isFull())
					throw new PostFixException("Too many operands - stack overflow");
				stack.push(value);
			} else {
				// Process operator.
				operator = tokenizer.next();

				// Obtain second operand from stack.
				if (stack.isEmpty())
					throw new PostFixException("Not enough operands - stack underflow");
				operand2 = stack.top();
				stack.pop();

				// Obtain first operand from stack.
				if (stack.isEmpty())
					throw new PostFixException("Not enough operands - stack underflow");
				operand1 = stack.top();
				stack.pop();

				// Perform operation.
				if (operator.equals("/")) {
					//Checks if dividing by zero
					if(operand2 == 0) {
						System.out.println("You cannot divide by zero!");
						System.exit(0);
					}
					result = operand1 / operand2;
				} else if(operator.equals("*"))
					result = operand1 * operand2;
				else if(operator.equals("+"))
					result = operand1 + operand2;
				else if(operator.equals("-"))
					result = operand1 - operand2;
				else if(operator.equals("^"))
					result = Math.pow(operand1, operand2);
				else
					throw new PostFixException("Illegal symbol: " + operator);

				// Push result of operation onto stack.
				stack.push(result);
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

	public static void preOrder(ExpressionNode<String> tree) {
		if (tree != null) {
			System.out.print(tree.getInfo() + " ");
			preOrder(tree.getLeft());
			preOrder(tree.getRight());
		}
	}
	//9+6*2/(8-3)
	public static void inOrder(ExpressionNode<String> tree) {
		if (tree != null) {
			if(tree.getLeft() != null) {
				if (Operator.isOperator(tree.getLeft().getInfo())) {
					if(Operator.getOperator(tree.getInfo()).getPrecedence() > 
					Operator.getOperator(tree.getLeft().getInfo()).getPrecedence()) {
						System.out.print("(");
						inOrder(tree.getLeft());
						System.out.print(")");
					} else {
						inOrder(tree.getLeft());
					}
				} else {
					inOrder(tree.getLeft());
				}
			}

			System.out.print(tree.getInfo() + " ");
			
			if(tree.getRight() != null) {
				if (Operator.isOperator(tree.getRight().getInfo())) {
					if(Operator.getOperator(tree.getInfo()).getPrecedence() > 
					Operator.getOperator(tree.getRight().getInfo()).getPrecedence()) {
						System.out.print("(");
						inOrder(tree.getRight());
						System.out.print(")");
					} else {
						inOrder(tree.getRight());
					}
				} else {
					inOrder(tree.getRight());
				}
			}
		}
	}

	public static void postOrder(ExpressionNode<String> tree)	{
		if (tree != null) {
			postOrder(tree.getLeft());
			postOrder(tree.getRight());
			System.out.print(tree.getInfo() + " ");
		}
	}
}
