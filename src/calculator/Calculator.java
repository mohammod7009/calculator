package calculator;

import java.util.*;

public class Calculator {

	private static Stack<String> currentTokens = new Stack<String>();
	
	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		
		String line = s.nextLine();		

		// while more input..
		while (!isEndOfInput(line)) {
			processInput(line);
			line = s.nextLine();
		}
		
		System.out.println("Exited.");		
	}

	private static boolean isEndOfInput(String line) {
		return (line==null || line.trim().equals("q")); //TODO: Or, Ctrl + D
	}

	private static void processInput(String line) {
		if (line!=null) {
			
			System.out.println("input: " + line);

			String[] tokens = line.trim().split("\\s+");
			
			for (String t : tokens) {
				if (t!=null) {
					
					try {
						processToken(t.trim());
					} catch (Exception e) {
						// display error
						System.err.println(e.getMessage());;
					}
				}
			}
		}
	}

	private static void processToken(String token) throws Exception {
		
		/* algo:
		 * if token is a number, push it to stack
		 * if token is an operator, pop the last 2 numbers and apply the operation on them, and push result to stack
		 */

		//	if !valid token
		if (!isValidToken(token)) {
			// abort with error
			throw new Exception("Invalid input. Please enter number or valid operator only.");
		}

		//	if token is an operand
		if (isNum(token)) {
			// push to stack
			currentTokens.push(token);
			// and display
			System.out.println(token);
		}
		else { // token is an operator			
			
			// get the operands..
			String opnd1 = null, opnd2 = null;
			// opnd1 = pop stack
			if (!currentTokens.isEmpty())
				opnd1 = currentTokens.pop();
			// opnd2 = pop stack
			if (!currentTokens.isEmpty())
				opnd2 = currentTokens.pop();

			//	if opnd1 == null || opnd2 == null -> error: not enough opnd's
			if (opnd1 == null || opnd2 == null || !isNum(opnd1) || !isNum(opnd2)) {				
				// restore opnd's
				restoreOperands(opnd1, opnd2);
				// abort with error
				throw new Exception("Please enter at least two operands before entering an operator!");
			}

			// apply the operation on the operands ..
			try {
				float res = applyOperation(token, opnd2, opnd1);
				// push  result to stack
				currentTokens.push(Float.toString(res));
				// display result
				System.out.println(Float.toString(res));
			}
			catch (Exception e) {
				// restore opnd's
				restoreOperands(opnd1, opnd2);
				// display error
				System.err.println(e.getMessage());;
			}

			displayCurrentTokens(currentTokens);
		}
	}

	private static void restoreOperands(String opnd1, String opnd2) {
		// restore tokens stack. maintain order!
		if (opnd2!=null) currentTokens.push(opnd2);
		if (opnd1!=null) currentTokens.push(opnd1);
		displayCurrentTokens(currentTokens);
	}

	private static boolean isValidToken(String t) {
		return (t != null && (isOp(t) || isNum(t)));
	}


	private static boolean isOp (String op) {
		// TODO: make valid operators-list configurable / extendable
		return (op!=null && (op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/")));
	}

	private static boolean isNum (String n) {
		try {
			Float.parseFloat(n);
		}
		catch (Exception e) {
			return  false;
		}
		return true;
	}

	private static float applyOperation(String operator, String opnd1, String opnd2) throws Exception {

		System.out.println("Calculating...: " + opnd1 + " " + operator + " " + opnd2);
		
		if (operator.equals("+"))
			return toFloat(opnd1) + toFloat(opnd2);
		
		if (operator.equals("-"))
			return toFloat(opnd1) - toFloat(opnd2);
		
		if (operator.equals("*"))
			return toFloat(opnd1) * toFloat(opnd2);
		
		if (operator.equals("/")) {			

			if (opnd2.trim().equals("0")) {
				throw new Exception ("Can't devide by 0!");
			}
			
			return toFloat(opnd1) / toFloat(opnd2);
		}

		throw new Exception ("Invalid operation " + operator + ". Only + , - , *, and / are allowed at this time.");
	}

	private static float toFloat(String opnd) {
		return Float.parseFloat(opnd);
	}

	private static void displayCurrentTokens(Stack<String> tokens2) {
		System.out.print("Current Stack of Tokens : ");
		for (String t: currentTokens) 
			System.out.print(t + "  ");
		System.out.println();
	}

}
