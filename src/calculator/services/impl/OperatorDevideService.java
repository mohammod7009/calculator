package calculator.services.impl;

import calculator.services.IOperatorService;

public class OperatorDevideService extends IOperatorService {

	@Override
	public float applyOperation(float val1, float val2) throws Exception {
		
		if (val2 == 0) {
			throw new Exception ("Can't devide by 0!");
		}

		return val1 / val2;	
	}

}
