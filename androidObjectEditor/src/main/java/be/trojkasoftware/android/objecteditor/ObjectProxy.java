package be.trojkasoftware.android.objecteditor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;


public class ObjectProxy {

	private PropertyDependencyManager dependencyManager = new PropertyDependencyManager();
	private Object anObject;
	private TypeConverterRegistry converters;
	private ArrayList<PropertyCategory> categories = new ArrayList<PropertyCategory>();
	private ArrayList<PropertyProxy> properties = new ArrayList<PropertyProxy>();
	
	public ObjectProxy(Object obj, TypeConverterRegistry converters)
	{
		this.converters = converters;
		this.anObject = obj;
		
		PropertyCategory defaultCategory = new PropertyCategory();
		defaultCategory.setName(PropertyCategory.DefaultCategoryName);
		defaultCategory.setProperties(new ArrayList<PropertyProxy>());
		categories.add(defaultCategory);
		
		try {
			ObjectPropertyList propertyList = new ObjectPropertyList(obj);
			for(Method aMethod : propertyList)
		  	{
				
				PropertyProxy propProxy = PropertyProxy.CreateFromSetter(aMethod, anObject, converters);		  		
				properties.add(propProxy);
				
				dependencyManager.RegisterProperty(propProxy);
				
				String propertyCategory = propProxy.getCategory();
				if(propertyCategory == null || propertyCategory.isEmpty())
				{
					defaultCategory.addProperty(propProxy);
				}
				else
				{
					PropertyCategory existingCategory = null;
					for(PropertyCategory cat : categories)
					{
						if(cat.getName().equalsIgnoreCase(propertyCategory))
						{
							existingCategory = cat;
							break;
						}
					}
					
					if(existingCategory == null)
					{
						existingCategory = new PropertyCategory();
						existingCategory.setName(propertyCategory);
						existingCategory.setProperties(new ArrayList<PropertyProxy>());
						
						categories.add(existingCategory);
					}
					
					existingCategory.addProperty(propProxy);
				}
		  	}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
	}
	
	public void Destroy()
	{
		for(PropertyCategory category : categories)
		{
			for(PropertyProxy property : category.getProperties())
			{
				property.removeOnValueChangedListener(dependencyManager);
			}
		}
	}
	
	public TypeConverterRegistry getConverters()
	{
		return this.converters;
	}
	
	public ArrayList<PropertyCategory> getCategories()
	{
		if(!isSorted)
		{
			Collections.sort(categories, new CategoryOrderingComparator());
			isSorted = true;
		}
		return categories;
	}
	
	public PropertyProxy getProperty(String propertyName)
	{
		PropertyProxy result = null;
		for(PropertyProxy property : properties)
		{
			if(property.getName().equals(propertyName))
			{
				return property;
			}
		}
		
		return result;
	}
	
	private boolean isSorted = false;
	
}
