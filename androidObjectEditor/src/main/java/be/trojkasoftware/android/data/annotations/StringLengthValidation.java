package be.trojkasoftware.android.data.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import be.trojkasoftware.android.data.validation.IsValidationAnnotation;
import be.trojkasoftware.android.data.validation.StringLengthValidator;
import be.trojkasoftware.android.data.validation.ValidatorType;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@IsValidationAnnotation()
@ValidatorType(Type = StringLengthValidator.class)
public @interface StringLengthValidation {
	int MinLength() default 0;
	int MaxLength() default Integer.MAX_VALUE;
	String ErrorMessage() default "";
}
