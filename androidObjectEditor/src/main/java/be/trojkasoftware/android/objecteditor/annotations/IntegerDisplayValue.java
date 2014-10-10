package be.trojkasoftware.android.objecteditor.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

//@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IntegerDisplayValue {
	int	   Value();
	String ShowAs();
}
