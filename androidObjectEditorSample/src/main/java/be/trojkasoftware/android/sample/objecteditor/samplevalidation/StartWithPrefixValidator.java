package be.trojkasoftware.android.sample.objecteditor.samplevalidation;

import java.lang.annotation.Annotation;

import be.trojkasoftware.android.data.validation.IValueValidator;

public class StartWithPrefixValidator implements IValueValidator {

	@Override
	public boolean Validate(Object value, Annotation validationDefinition) {
		StartWithPrefixValidation startWithLetterDefinition = (StartWithPrefixValidation)validationDefinition;
		String textValue = (String)value;
		
		return Validate(textValue, startWithLetterDefinition.Prefix());
	}
	
	private boolean Validate(String textValue, String letter)
	{
		if(textValue.startsWith(letter))
		{
			return true;
		}
		return false;
	}
}