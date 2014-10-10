package be.trojkasoftware.android.data.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Validator {
	
	public static boolean isValidationAnnotation(Annotation annotation)
	{
		Class<? extends Annotation> annotationType = annotation.annotationType();
		Annotation isValidationAnnotation = annotationType.getAnnotation(IsValidationAnnotation.class);
		if(isValidationAnnotation == null)
			return false;
		
		return true;
	}
	
	public static boolean Validate (Object value, Annotation validationDefinition)
	{
		IValueValidator validator = getValidator(validationDefinition);
		if(validator == null)
			return false;
		
		return validator.Validate(value, validationDefinition);
	}
	
	private static IValueValidator getValidator(Annotation validationDefinition)
	{
		Class<? extends Annotation> annotationType = validationDefinition.annotationType();
		ValidatorType validatorTypeAnnotation = (ValidatorType)annotationType.getAnnotation(ValidatorType.class);
		if(validatorTypeAnnotation == null)
			return null;
		
		return instantiateValueValidatorFromClass(validatorTypeAnnotation.Type());
	}
	
	private static IValueValidator instantiateValueValidatorFromClass(Class<?> valueValidatorType)
	{
		Constructor<?> cons;
		IValueValidator valueValidator = null;
		try {
			cons = valueValidatorType.getConstructor();
			valueValidator = (IValueValidator)cons.newInstance();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return valueValidator;
	}
}
