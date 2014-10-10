package be.trojkasoftware.android.objecteditor;

import java.util.HashMap;
import java.util.Map;

import be.trojkasoftware.android.typeconverter.BooleanTypeConverter;
import be.trojkasoftware.android.typeconverter.DoubleTypeConverter;
import be.trojkasoftware.android.typeconverter.IntTypeConverter;
import be.trojkasoftware.android.typeconverter.StringTypeConverter;
import be.trojkasoftware.android.typeconverter.TypeConverter;

public class TypeConverterRegistry {

	Map<Class<?>,TypeConverter> registry = new HashMap<Class<?>,TypeConverter>();
	
	public static TypeConverterRegistry create()
	{
        TypeConverterRegistry converterRegistry = new TypeConverterRegistry();
        converterRegistry.registerType(int.class, new IntTypeConverter());
        converterRegistry.registerType(Integer.class, new IntTypeConverter());
        converterRegistry.registerType(double.class, new DoubleTypeConverter());
        converterRegistry.registerType(Double.class, new DoubleTypeConverter());
        converterRegistry.registerType(String.class, new StringTypeConverter());        
        converterRegistry.registerType(boolean.class, new BooleanTypeConverter()); 
        converterRegistry.registerType(Boolean.class, new BooleanTypeConverter());        
        
        return converterRegistry;
	}
	
	public void registerType(Class<?> propertyType, TypeConverter converter)
	{
		registry.put(propertyType, converter);
	}
	
	public boolean containsType(Class<?> forType)
	{
		return registry.containsKey(forType);
	}
	
	public TypeConverter getConverterForType(Class<?> forType)
	{
		if(!registry.containsKey(forType))
		{
			return null;
		}
		
		return registry.get(forType);
	}

}
