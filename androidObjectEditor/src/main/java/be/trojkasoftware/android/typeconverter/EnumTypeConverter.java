package be.trojkasoftware.android.typeconverter;

import android.os.Bundle;

public class EnumTypeConverter implements TypeConverter {

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
			returnValue = convertStringToEnum((String)object);
		}
		return returnValue;
	}

	@Override
	public Object ConvertTo(Object object, Class<?> type) {
		Object returnValue = null;
		if(type == Bundle.class)
		{
			Bundle data = new Bundle();
			String enumAsString = convertEnumToString(object);
			data.putString(BUNDLE_KEY, enumAsString);
			returnValue = (Object)data;
		}
		if(type == String.class)
		{
			returnValue = convertEnumToString(object);
		}
		return returnValue;
	}
	
	private String convertEnumToString(Object object)
	{
		Class<?> enumClass = object.getClass();
		return enumClass.getName() + "." + object.toString();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Object convertStringToEnum(String enumAsString)
	{
		String valuePart = enumAsString.substring(enumAsString.lastIndexOf(".")+1);
		String classPart = enumAsString.substring(enumAsString.indexOf(" ")+1, enumAsString.lastIndexOf("."));
		Class enumType = null;
		try {
			enumType = (Class)Class.forName(classPart);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return Enum.valueOf(enumType, valuePart);
	}
	
	
	final String BUNDLE_KEY = "ENUM_VALUE";

}
