package be.trojkasoftware.android.data.validation;

import java.lang.annotation.Annotation;

import be.trojkasoftware.android.data.annotations.IntegerRangeValidation;

public class IntegerRangeValidator implements IValueValidator {

	@Override
	public boolean Validate(Object value, Annotation validationDefinition) {
		IntegerRangeValidation integerRangeDefinition = (IntegerRangeValidation)validationDefinition;
		int integerValue = (Integer)value;
		
		return Validate(integerValue, 
				integerRangeDefinition.MinIncluded(), integerRangeDefinition.MinValue(), integerRangeDefinition.MaxIncluded(), integerRangeDefinition.MaxValue());
	}
	
	private boolean Validate(int integerValue, boolean minIncluded, int minValue, boolean maxIncluded, int maxValue)
	{
		if(((minIncluded && (minValue <= integerValue))
				|| (!minIncluded && (minValue < integerValue)))
			&& ((maxIncluded && (maxValue >= integerValue))
					|| (!maxIncluded && (maxValue > integerValue))))
		{
			return true;
		}
		return false;
	}
}
