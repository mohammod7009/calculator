package calculator.services.impl;

import java.util.Stack;

import calculator.factories.OperatorFactory;
import calculator.services.ICalculatorService;
import calculator.services.IOperatorService;

public class RPNCalculatorService implements ICalculatorService {

	private static final boolean DEBUG = false;

	private Stack<String> currentTokens = new Stack<String>();
	private OperatorFactory operatorFactory = new OperatorFactory();
	
	@Override
	public String calculateExpression(String input) {
		
		String response = null;
		
		if (input!=null) {
			
			// System.out.println("input: " + input);

			String[] tokens = input.trim().split("\\s+");
			
			for (String t : tokens) {
				if (t!=null) {
					
					try {
						response = processToken(t.trim());
					} catch (Exception e) {
						// display error
						response = e.getMessage();
					}
				}
			}
		}
		
		return response;
	}

	private String processToken(String token) throws Exception {
		
		/* algo:
		 * if token is a number, push it to stack
		 * if token is an operator, pop the last 2 numbers and apply the operation on them, and push result to stack
		 */

		String response = null;

		//	if !valid token
		if (!isValidToken(token)) {
			// abort with error
			throw new Exception("Invalid input. Please enter number or valid operator only.");
		}

		//	if token is an operand
		if (isNum(token)) {
			// push to stack
			currentTokens.push(token);
			// return result
			response = token;
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
				// return result
				response = Float.toString(res);
			}
			catch (Exception e) {
				// restore opnd's
				restoreOperands(opnd1, opnd2);
				// return error
				throw e;
			}

			displayCurrentTokens();
		}
		
		return response;

	}

	private void restoreOperands(String opnd1, String opnd2) {
		// restore tokens stack. maintain order!
		if (opnd2!=null) currentTokens.push(opnd2);
		if (opnd1!=null) currentTokens.push(opnd1);
		displayCurrentTokens();
	}

	private boolean isValidToken(String t) {
		return (t != null && (operatorFactory.isValidOperator(t) || isNum(t)));
	}

	private boolean isNum (String n) {
		try {
			Float.parseFloat(n);
		}
		catch (Exception e) {
			return  false;
		}
		return true;
	}

	private float applyOperation(String operatorSign, String opnd1, String opnd2) throws Exception {

		System.out.println("Calculating...: " + opnd1 + " " + operatorSign + " " + opnd2);
		
		IOperatorService op = operatorFactory.getOperatorService(operatorSign);
		if (op == null) {
			throw new Exception ("Invalid operator sign: " + operatorSign);
		}
		
		float val1 = toFloat(opnd1);
		float val2 = toFloat(opnd2);
		
		return op.applyOperation(val1, val2);

	}

	private float toFloat(String opnd) {
		return Float.parseFloat(opnd);
	}

	private void displayCurrentTokens() {
		if (DEBUG) {
			System.out.print("Current Stack of Tokens : ");
			for (String t: currentTokens) 
				System.out.print(t + "  ");
			System.out.println();
		}
	}
	
}
