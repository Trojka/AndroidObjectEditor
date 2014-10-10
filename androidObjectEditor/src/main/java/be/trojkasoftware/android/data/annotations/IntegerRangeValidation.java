package be.trojkasoftware.android.data.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import be.trojkasoftware.android.data.validation.IntegerRangeValidator;
import be.trojkasoftware.android.data.validation.IsValidationAnnotation;
import be.trojkasoftware.android.data.validation.ValidatorType;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@IsValidationAnnotation()
@ValidatorType(Type = IntegerRangeValidator.class)
public @interface IntegerRangeValidation {
	int MinValue();
	int MaxValue();
	boolean MinIncluded() default true;
	boolean MaxIncluded() default true;
	String ErrorMessage() default "";
}
