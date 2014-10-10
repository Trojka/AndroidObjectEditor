package be.trojkasoftware.android.objecteditor;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.trojkasoftware.android.objecteditor.PropertyProxy.OnValueChangedListener;
import be.trojkasoftware.android.objecteditor.annotations.DependentOnPropertyValue;


public class PropertyDependencyManager implements OnValueChangedListener {

	private Map<String,ArrayList<PropertyProxy> > todo = new HashMap<String,ArrayList<PropertyProxy>>();
	
	private Map<String,PropertyProxy> propertyMap = new HashMap<String,PropertyProxy>();
	private Map<String,ArrayList<DependencyDefinition>> dependencyMap = new HashMap<String,ArrayList<DependencyDefinition>>();
	
	public void RegisterProperty(PropertyProxy property)
	{
		propertyMap.put(property.getName(), property);
		
		Method propertySetter = property.getSetter();
		if(!propertySetter.isAnnotationPresent(DependentOnPropertyValue.class))
		{
			return;
		}
		
		Handle(property);
	}


	@Override
	public void ValueChanged(PropertyProxy property, Object oldValue,
			Object newValue) {

		if(property == null)
		{
			return;
		}
		
		if(!dependencyMap.containsKey(property.getName()))
		{
			return;
		}
		
		for(DependencyDefinition dependency : dependencyMap.get(property.getName()))
		{
			if(dependency.value.equals(newValue))
			{
				dependency.targetProperty.setIsReadonly(true);
			}
			else
			{
				dependency.targetProperty.setIsReadonly(false);
			}
		}
	}
	
	private class DependencyDefinition
	{
		PropertyProxy targetProperty;
		Object value;
	}
	
	private void Handle(PropertyProxy property)
	{	
		Method propertySetter = property.getSetter();
		DependentOnPropertyValue dependentOnProp = propertySetter.getAnnotation(DependentOnPropertyValue.class);
		if(!propertyMap.containsKey(dependentOnProp.ValueProviderName()))
		{
			// the map does not already have the valueprovider property: we will handle it later
			if(!todo.containsKey(dependentOnProp.ValueProviderName()))
			{
				todo.put(dependentOnProp.ValueProviderName(), new ArrayList<PropertyProxy>());
			}
			
			todo.get(dependentOnProp.ValueProviderName()).add(property);
			
			CheckToDoList(property.getName());
			
			return;
		}		
		
		CheckToDoList(property.getName());
		
		WireUp(propertyMap.get(dependentOnProp.ValueProviderName()), property);
		
	}
	
	private void CheckToDoList(String propertyName)
	{
		if(!todo.containsKey(propertyName))
		{
			return;
		}
		
		ArrayList<PropertyProxy> dependeeList = todo.get(propertyName);
		for(PropertyProxy dependee:dependeeList)
		{
			WireUp(propertyMap.get(propertyName), dependee);
		}
		
		todo.remove(propertyName);
	}
	
	private void WireUp(PropertyProxy valueProvider, PropertyProxy dependee)
	{
		DependentOnPropertyValue dependentOn = dependee.getSetter().getAnnotation(DependentOnPropertyValue.class);
		DependencyDefinition dependencyDefinition = new DependencyDefinition();
		dependencyDefinition.targetProperty = dependee;
		if(valueProvider.getPropertyType() == int.class)
		{
			dependencyDefinition.value = dependentOn.IntValue();
		}
		else if (valueProvider.getPropertyType() == String.class)
		{
			dependencyDefinition.value = dependentOn.StringValue();
		}
		else
		{
			return;
		}

		if(!dependencyMap.containsKey(valueProvider.getName()))
		{
			dependencyMap.put(valueProvider.getName(), new ArrayList<DependencyDefinition>());
		}
		dependencyMap.get(valueProvider.getName()).add(dependencyDefinition);
		
		valueProvider.addOnValueChangedListener(this);
	}
	
	
}
