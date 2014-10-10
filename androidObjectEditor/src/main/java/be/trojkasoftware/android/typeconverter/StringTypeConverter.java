package be.trojkasoftware.android.typeconverter;

import android.os.Bundle;

public class StringTypeConverter implements TypeConverter {

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
		if(object.getClass() == Bundle.class)
		{
			Bundle data = (Bundle)object;
			return data.getString(BUNDLE_KEY);
		}
		if(object.getClass() == String.class)
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
			data.putString(BUNDLE_KEY, (String)object);
			return data;
		}
		if(type == String.class)
		{
			return object;
		}
		return null;
	}
	
	final String BUNDLE_KEY = "STRING_VALUE";

}
