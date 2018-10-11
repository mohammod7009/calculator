package calculator.services.impl;

import calculator.services.IOperatorService;

public class OperatorSubtractService extends IOperatorService {

	@Override
	public float applyOperation(float val1, float val2) {
		return val1 - val2;	
	}

}
