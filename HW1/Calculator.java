package HW1;

import java.util.Scanner;

/**
 * Calculator Class that will evaluate the postfix expression (Assignment 1)
 * Checks for division by zero and prints a user-friendly response
 * @author Ashish Ramachandran (ar3986)
 */
public class Calculator {

	public static void main(String[] args) {

		Scanner conIn = new Scanner(System.in);
		Converter converter = null;

		String line = null;          // string to be evaluated
		String more = null;          // used to stop or continue processing
		int result;                  // result of evaluation

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
				System.out.println();
				System.out.println("Result = " + result);
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

	public static int evaluate(String expression) {

		BoundedStackInterface<Integer> stack = new ArrayStack<Integer>(50);  
		int value;
		String operator;

		int operand1;
		int operand2;

		int result = 0;

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
					result = (int) Math.pow(operand1, operand2);
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
}
