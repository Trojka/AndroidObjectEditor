package be.trojkasoftware.android.data.validation;

import java.lang.annotation.Annotation;

public interface IValueValidator {
	public boolean Validate(Object value, Annotation validationDefinition);

}
