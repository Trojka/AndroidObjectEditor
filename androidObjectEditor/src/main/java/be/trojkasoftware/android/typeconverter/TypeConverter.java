package be.trojkasoftware.android.typeconverter;

public interface TypeConverter {
	boolean CanConvertFrom(Class<?> from);
	
	boolean CanConvertTo(Class<?> to);
	
	Object ConvertFrom(Object object);

	Object ConvertTo(Object object, Class<?> type);
}
