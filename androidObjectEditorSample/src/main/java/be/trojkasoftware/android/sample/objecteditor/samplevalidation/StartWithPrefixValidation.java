package be.trojkasoftware.android.sample.objecteditor.samplevalidation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


import be.trojkasoftware.android.data.validation.IsValidationAnnotation;
import be.trojkasoftware.android.data.validation.ValidatorType;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@IsValidationAnnotation()
@ValidatorType(Type = StartWithPrefixValidator.class)
public @interface StartWithPrefixValidation {
	String Prefix();
}
