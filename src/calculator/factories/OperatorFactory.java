package calculator.factories;

import calculator.services.IOperatorService;
import calculator.services.impl.OperatorAddService;
import calculator.services.impl.OperatorDivideService;
import calculator.services.impl.OperatorMultiplyService;
import calculator.services.impl.OperatorSubtractService;

public class OperatorFactory {

	/**
	 * Validates whether given operatorSign is a valid operator
	 * @param operatorSign
	 * @return True if operatorSign is valid (and has been implemented)
	 */
	public boolean isValidOperator(String operatorSign) {
		return (operatorSign!=null && (operatorSign.equals("+") || operatorSign.equals("-") || operatorSign.equals("*") || operatorSign.equals("/")));
	}

	/**
	 * Returns an instance of the operator for the given operatorSign
	 * @param operatorSign
	 * @return Returns null, if given operatorSign has not been implemented yet.
	 * Otherwise, returns an instance of the requested operator
	 */
	public IOperatorService getOperatorService(String operatorSign) {
		
		if (operatorSign.equals("+"))
			return new OperatorAddService();
		
		if (operatorSign.equals("-"))
			return new OperatorSubtractService();
		
		if (operatorSign.equals("*"))
			return new OperatorMultiplyService();
		
		if (operatorSign.equals("/"))
			return new OperatorDivideService();

		return null; // invalid sign
	}

}
