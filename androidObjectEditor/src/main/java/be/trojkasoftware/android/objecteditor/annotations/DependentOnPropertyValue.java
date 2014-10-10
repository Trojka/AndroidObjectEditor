package be.trojkasoftware.android.objecteditor.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DependentOnPropertyValue {
	String ValueProviderName();
	int IntValue() default 0;
	String StringValue() default "";
}
