package calculator;

import java.util.*;

import calculator.services.ICalculatorService;
import calculator.services.impl.RPNCalculatorService;

public class Calculator {

	public static void main(String[] args) {
		
		ICalculatorService calculator = new RPNCalculatorService();

		Scanner input = new Scanner(System.in);
		
		// while more input..
		while (input.hasNext()) {

			String line = input.nextLine();		

			if (isEndOfInput(line))
				break;
			
			String response = calculator.calculateExpression(line);
			System.out.println(response);
		}
		
		System.out.println("Exited.");		
	}

	private static boolean isEndOfInput(String line) {
		return (line==null || line.trim().equals("q"));
	}

}
