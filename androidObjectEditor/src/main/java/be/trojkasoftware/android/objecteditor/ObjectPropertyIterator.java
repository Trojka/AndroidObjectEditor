package be.trojkasoftware.android.objecteditor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;

import be.trojkasoftware.android.data.annotations.HideSetter;
import be.trojkasoftware.android.data.annotations.HideSetters;
import be.trojkasoftware.android.data.annotations.ShowGetters;

public class ObjectPropertyIterator implements Iterator<Method> {

	public ObjectPropertyIterator(Object obj)
	{
		this.obj = obj;
		
		Class<? extends Object> aClass = this.obj.getClass();
		
		hideSettersAnnotation = (HideSetters)aClass.getAnnotation(HideSetters.class);
		showGettersAnnotation = (ShowGetters)aClass.getAnnotation(ShowGetters.class);
		
		Iterable<Method> methodIterable = Arrays.asList(aClass.getMethods());
		methodIterator = methodIterable.iterator();
	}
	
	@Override
	public boolean hasNext() {
		while(methodIterator.hasNext())
		{
			Method setter = methodIterator.next();
			if(ValidateSetter(setter))
			{
				next = setter;
				return true;
			}
		}
		return false;
	}

	@Override
	public Method next() {
		return next;
	}

	@Override
	public void remove() {

		
	}
	
	private boolean ValidateSetter(Method setter)
	{
  		String methodName = setter.getName();
  		boolean isHidden = false;
  		if(hideSettersAnnotation != null)
  		{
	  		for(HideSetter hideSetterAnnotation : hideSettersAnnotation.value())
	  		{
	  			String hideSetterName = hideSetterAnnotation.Name();
	  			if(hideSetterName.equals(methodName))
	  			{
	  				isHidden = true;
	  				break;
	  			}
	  		}
  		}
  		
  		if(!methodName.startsWith("set") 
		|| (setter.getAnnotation(HideSetter.class) != null)
		|| isHidden)
		{
			return false;
		}
  		
  		return true;
	}

	private Object obj;
	private Iterator<Method> methodIterator;
	private Method next;
	
	HideSetters hideSettersAnnotation;
	ShowGetters showGettersAnnotation;
}
