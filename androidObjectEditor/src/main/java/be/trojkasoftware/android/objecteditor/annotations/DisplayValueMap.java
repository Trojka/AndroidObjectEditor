package be.trojkasoftware.android.objecteditor.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DisplayValueMap {
	IntegerDisplayValue[] IntegerMap() default {};
	StringDisplayValue[] StringMap() default {};
}
