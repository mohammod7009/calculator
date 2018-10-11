package calculator;

import java.util.*;

import calculator.services.ICalculatorService;
import calculator.services.impl.RPNCalculatorService;

public class Calculator {

	public static void main(String[] args) {
		
		ICalculatorService calculator = new RPNCalculatorService();

		Scanner s = new Scanner(System.in);
		
		String line = s.nextLine();		

		// while more input..
		while (!isEndOfInput(line)) {
			String response = calculator.calculateExpression(line);
			System.out.println("r=" + response);
			line = s.nextLine();
		}
		
		System.out.println("Exited.");		
	}

	private static boolean isEndOfInput(String line) {
		return (line==null || line.trim().equals("q"));
	}

}
