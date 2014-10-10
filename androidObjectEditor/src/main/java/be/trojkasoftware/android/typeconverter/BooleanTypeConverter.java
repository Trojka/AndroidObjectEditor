package be.trojkasoftware.android.typeconverter;

import android.os.Bundle;

public class BooleanTypeConverter implements TypeConverter {

	@Override
	public boolean CanConvertFrom(Class<?> from) {
		if((from == Bundle.class)
			|| (from == boolean.class))
			return true;
		return false;
	}

	@Override
	public boolean CanConvertTo(Class<?> to) {
		if((to == Bundle.class)
			|| (to == boolean.class))
			return true;
		return false;
	}

	@Override
	public Object ConvertFrom(Object object) {
		if(object.getClass() == Bundle.class)
		{
			Bundle data = (Bundle)object;
			return data.getBoolean(BUNDLE_KEY);
		}
		if(object.getClass() == boolean.class || object.getClass() == Boolean.class)
		{
			return object;
		}
		return null;
	}

	@Override
	public Object ConvertTo(Object object, Class<?> type) {
		if(type == Bundle.class)
		{
			Bundle data = new Bundle();
			data.putBoolean(BUNDLE_KEY, (Boolean)object);
			return data;
		}
		if(type == boolean.class)
		{
			return object;
		}
		return null;
	}
	
	final String BUNDLE_KEY = "BOOLEAN_VALUE";

}
