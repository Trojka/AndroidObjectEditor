package be.trojkasoftware.android.data.validation;

import java.lang.annotation.Annotation;

import be.trojkasoftware.android.data.annotations.StringLengthValidation;

public class StringLengthValidator implements IValueValidator {

	@Override
	public boolean Validate(Object value, Annotation validationDefinition) {
		StringLengthValidation stringLengthDefinition = (StringLengthValidation)validationDefinition;
		String textValue = (String)value;
		
		return Validate(textValue, stringLengthDefinition.MinLength(), stringLengthDefinition.MaxLength());
	}

	
	private boolean Validate(String textValue, int minLength, int maxLength)
	{
		if((minLength <= textValue.length()) && (maxLength >= textValue.length()))
		{
			return true;
		}
		return false;
	}
}
