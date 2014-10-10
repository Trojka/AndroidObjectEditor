package be.trojkasoftware.android.typeconverter;

import android.os.Bundle;

public class DoubleTypeConverter implements TypeConverter {

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
			returnValue = data.getDouble(BUNDLE_KEY);
		}
		if(object.getClass() == String.class)
		{
			returnValue = Double.parseDouble((String)object);
		}
		if(object.getClass() == double.class || object.getClass() == Double.class)
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
			double objectAsDouble = (Double)object;
			data.putDouble(BUNDLE_KEY, objectAsDouble);
			returnValue = (Object)data;
		}
		if(type == String.class)
		{
			returnValue = object.toString();
		}
		if(type == double.class || type == Double.class)
		{
			returnValue = object;
		}
		return returnValue;
	}
	
	final String BUNDLE_KEY = "DOUBLE_VALUE";

}
