package be.trojkasoftware.android.objecteditor;

import java.util.ArrayList;
import java.util.Collections;

public class PropertyCategory {
	private String name;
	private ArrayList<PropertyProxy> properties;
	
	public static final String DefaultCategoryName = "General";
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String n)
	{
		name = n;
	}
	
	public ArrayList<PropertyProxy> getProperties()
	{
		if(!isSorted)
		{
			Collections.sort(properties, new PropertyOrderingComparator());
			isSorted = true;
		}
		return properties;
	}
	
	public void setProperties(ArrayList<PropertyProxy> props)
	{
		properties = props;
		isSorted = false;
	}
	
	public void addProperty(PropertyProxy prop)
	{
		properties.add(prop);
		isSorted = false;
	}
	
	private boolean isSorted = false;
}
