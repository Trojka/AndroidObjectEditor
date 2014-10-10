package be.trojkasoftware.android.objecteditor;

import java.util.HashMap;
import java.util.Map;

public class TypeViewRegistry {

	Map< Class<?>,Class<?> > registry = new HashMap< Class<?>,Class<?> >();
	
	public void registerType(Class<?> propertyType, Class<?> editorType)
	{
		registry.put(propertyType, editorType);
	}
	
	public boolean containsType(Class<?> forType)
	{
		return registry.containsKey(forType);
	}
	
	public Class<?> getMappingForType(Class<?> forType)
	{
		if(!registry.containsKey(forType))
		{
			return null;
		}
		
		return registry.get(forType);
	}

}
