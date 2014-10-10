package be.trojkasoftware.android.typeconverter;

import android.os.Bundle;

public class IntTypeConverter implements TypeConverter {

	@Override
	public boolean CanConvertFrom(Class<?> from) {
		if((from == Bundle.class)
			|| (from == String.class))
			return true;
		return false;
	}

	@Override
	public boolean CanConvertTo(Class<?> to) {
		if((to == Bundle.class)
			|| (to == String.class))
			return true;
		return false;
	}

	@Override
	public Object ConvertFrom(Object object) {
		Object returnValue = null;
		if(object.getClass() == Bundle.class)
		{
			Bundle data = (Bundle)object;
			returnValue = data.getInt(BUNDLE_KEY);
		}
		if(object.getClass() == String.class)
		{
			returnValue = Integer.parseInt((String)object);
		}
		if(object.getClass() == int.class || object.getClass() == Integer.class)
		{
			returnValue = object;
		}
		return returnValue;
	}

	@Override
	public Object ConvertTo(Object object, Class<?> type) {
		Object returnValue = null;
		if(type == Bundle.class)
		{
			Bundle data = new Bundle();
			int objectAsInt = (Integer)object;
			data.putInt(BUNDLE_KEY, objectAsInt);
			returnValue = (Object)data;
		}
		if(type == String.class)
		{
			returnValue = object.toString();
		}
		if(type == int.class || type == Integer.class)
		{
			returnValue = object;
		}
		return returnValue;
	}
	
	final String BUNDLE_KEY = "INTEGER_VALUE";

}
