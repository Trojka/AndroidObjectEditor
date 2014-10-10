package be.trojkasoftware.android.objecteditor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.text.InputType;
import be.trojkasoftware.android.componentmodel.INotifyPropertyChanged;
import be.trojkasoftware.android.componentmodel.OnPropertyChangedListener;
import be.trojkasoftware.android.data.annotations.AllowedValue;
import be.trojkasoftware.android.data.annotations.AllowedValues;
import be.trojkasoftware.android.data.validation.Validator;
import be.trojkasoftware.android.objecteditor.annotations.Category;
import be.trojkasoftware.android.objecteditor.annotations.DataTypeEditor;
import be.trojkasoftware.android.objecteditor.annotations.DataTypeViewer;
import be.trojkasoftware.android.objecteditor.annotations.DisplayName;
import be.trojkasoftware.android.objecteditor.annotations.DisplayValueMap;
import be.trojkasoftware.android.objecteditor.annotations.EnumDisplayValue;
import be.trojkasoftware.android.objecteditor.annotations.IntegerDisplayValue;
import be.trojkasoftware.android.objecteditor.annotations.StringDisplayValue;
import be.trojkasoftware.android.objecteditor.util.ObjectFactory;
import be.trojkasoftware.android.typeconverter.TypeConverter;


public class PropertyProxy implements INotifyPropertyChanged {
	private String categoryName;
	private String propertyName;
	private String propertyDisplayName;
	private boolean isReadOnly;
	private Method propertySetter;
	private Method propertyGetter;
	private Object theObject;
	private Object currentValue;
	private TypeConverter converter;
	private Map<Object,Object> toDisplayMap = null;
	private Map<Object,Object> fromDisplayMap = null;
	private List<OnValueChangedListener> onValueChangedListeners = null;
	private OnPropertyChangedListener onPropertyChangedListener = null;
	
	public interface OnValueChangedListener
	{
		void ValueChanged(PropertyProxy poperty, Object oldValue, Object newValue);
	}
	
	public static PropertyProxy CreateFomPopertyName(String propertyName, Object obj, TypeConverterRegistry converterRegistry)
	{
		Class<? extends Object> aClass = obj.getClass();
		Method setter = null;
		try {
			for (Method someMethod : aClass.getMethods())
			{
				if(someMethod.getName().equals("set" + propertyName))
				{
					setter = someMethod;
					break;
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}	
		
		return CreateFromSetter(setter, obj, converterRegistry);
	}
	
	public static PropertyProxy CreateFromSetter(Method setter, Object obj, TypeConverterRegistry converterRegistry)
	{
  		String methodName = setter.getName();
		String stdMethodName = methodName.substring(3);
		
		Class<? extends Object> aClass = obj.getClass();
		Method getter = null;
		try {
			getter = aClass.getMethod("get" + stdMethodName, null);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}			
		
		return new PropertyProxy(
				setter, 
				getter, 
				obj,
				converterRegistry);
	}
	
	private PropertyProxy(Method setter, Method getter, Object obj, TypeConverterRegistry converterRegistry)
	{
		onValueChangedListeners = new ArrayList<OnValueChangedListener>();
		
		propertySetter = setter;
		propertyGetter = getter;
		theObject = obj;
		
  		String methodName = propertySetter.getName();
		propertyName = methodName.substring(3);
		propertyDisplayName = propertyName;
		
		if(propertyGetter.isAnnotationPresent(DisplayName.class))
		{
			DisplayName displayName = propertyGetter.getAnnotation(DisplayName.class);
			propertyDisplayName = displayName.Name();
			categoryName = displayName.CategoryName();
		}
		else
		{			
			Category category = propertyGetter.getAnnotation(Category.class);
			if(category == null)
				categoryName = null;
			else
				categoryName = category.Name();
		}
		
		converter = ObjectFactory.getTypeConverterForProperty(this, converterRegistry);
		
		if(getPropertyType().isEnum())
		{
			toDisplayMap = new HashMap<Object,Object>();
			fromDisplayMap = new HashMap<Object,Object>();
			

			for(Object enumConstant : getPropertyType().getEnumConstants())
			{
				Field field=  null;
				try {
					field = getPropertyType().getField(enumConstant.toString());
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				}
				String displayValue = null;
				if(field != null && field.isAnnotationPresent(EnumDisplayValue.class))
				{
					EnumDisplayValue enumDisplayValue = field.getAnnotation(EnumDisplayValue.class);
					displayValue = enumDisplayValue.DisplayAs();
				}
				else
				{
					displayValue = enumConstant.toString();
				}
				toDisplayMap.put(enumConstant, displayValue);
				fromDisplayMap.put(displayValue, enumConstant);
			}
		}
		else if(propertySetter.isAnnotationPresent(AllowedValues.class))
		{
			toDisplayMap = new HashMap<Object,Object>();
			fromDisplayMap = new HashMap<Object,Object>();
			
			AllowedValues allowedValues = propertySetter.getAnnotation(AllowedValues.class);
			DisplayValueMap displayValueMap = propertySetter.getAnnotation(DisplayValueMap.class);
			
			for(AllowedValue allowedValue : allowedValues.value())
			{
				if(getPropertyType() == String.class)
				{
					String value = allowedValue.StringValue();
					String displayName = value;
					if(displayValueMap != null)
					{
						for (StringDisplayValue dsplVal : displayValueMap.StringMap())
						{
							if(dsplVal.Value().equals(value))
								displayName = dsplVal.ShowAs();
						}
					}
					toDisplayMap.put(value, displayName);
					fromDisplayMap.put(displayName, value);
				}
				if(getPropertyType() == int.class || getPropertyType() == Integer.class)
				{
					Integer value = allowedValue.IntegerValue();
					String displayName = value.toString();
					if(displayValueMap != null)
					{
						for (IntegerDisplayValue dsplVal : displayValueMap.IntegerMap())
						{
							if(dsplVal.Value() == value)
								displayName = dsplVal.ShowAs();
						}
					}
					toDisplayMap.put(value, displayName);
					fromDisplayMap.put(displayName, value);
				}
			}
		}
	}
	
	public <T extends Annotation> T getAnnotation(Class<T> annotationClass)
	{
		if(propertySetter.isAnnotationPresent(annotationClass))
		{
			return propertySetter.getAnnotation(annotationClass);
		}
		if(propertyGetter.isAnnotationPresent(annotationClass))
		{
			return propertyGetter.getAnnotation(annotationClass);			
		}
		
		return null;
	}
	
	Method getSetter()
	{
		return propertySetter;
	}
	
	Method getGetter()
	{
		return propertyGetter;
	}
	
	public String getName()
	{
		return propertyName;
	}
	
	public String getDisplayName()
	{
		return propertyDisplayName;
	}
	
	public String getCategory()
	{
		return categoryName;
	}
	
	public Class<?> getPropertyType()
	{
		return propertyGetter.getReturnType();
	}
	
	public boolean getIsReadonly()
	{
		return isReadOnly;
	}
	
	public void setIsReadonly(boolean value)
	{
		if (isReadOnly == value)
		{
			return;
		}
		boolean oldValue = isReadOnly;
		isReadOnly = value;
		if(onPropertyChangedListener != null)
		{
			onPropertyChangedListener.PropertyChanged(this, "IsReadonly", oldValue, isReadOnly);
		}
	}
	
	public int getInputType()
	{
		if(Integer.class == this.getPropertyType() || int.class == this.getPropertyType())
		{
			return InputType.TYPE_CLASS_NUMBER;
		}
		if(Double.class ==  this.getPropertyType() || double.class == this.getPropertyType())
		{
			return (InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
		}
		
		return InputType.TYPE_CLASS_TEXT;
	}
	
	public boolean hasAllowedValues()
	{
		return ((fromDisplayMap != null) && !fromDisplayMap.isEmpty());
	}
	
	public Object[] getAllowedValues()
	{
		return fromDisplayMap.keySet().toArray();
	}
	
	public void addOnValueChangedListener(OnValueChangedListener valueChangedListener)
	{
		onValueChangedListeners.add(valueChangedListener);
	}
	
	public void removeOnValueChangedListener(OnValueChangedListener valueChangedListener)
	{
		if(!onValueChangedListeners.contains(valueChangedListener))
			return;
		
		onValueChangedListeners.remove(valueChangedListener);
	}
	
	private Object getRawValue()
	{
		Object value = null;
		try {
			value = propertyGetter.invoke(theObject);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return value;
	}
	
	public Object getValue(Class<?> toType)
	{
		Object displayValue = null;
		if(toDisplayMap != null)
		{
			displayValue = toDisplayMap.get(getRawValue());
			return displayValue;
		}
		displayValue = converter.ConvertTo(getRawValue(), toType);
		return displayValue;
	}
	
	private void setRawValue(Object value)
	{
		try {
			Object previousValue = currentValue;
			propertySetter.invoke(theObject, value);
			currentValue = value;
			if(onValueChangedListeners != null && onValueChangedListeners.size() != 0)
			{
				for(OnValueChangedListener onValueChangedListener : onValueChangedListeners)
				{
					onValueChangedListener.ValueChanged(this, previousValue, currentValue);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public void setValue(Object value)
	{
		Object convertedValue = convertToRawValue(value);
		setRawValue(convertedValue);
	}
	
	public Object convertToRawValue(Object value)
	{
		Object convertedValue = null;
		if(fromDisplayMap != null)
		{
			convertedValue = fromDisplayMap.get(value);
			return convertedValue;
		}
		convertedValue = converter.ConvertFrom(value);

		return convertedValue;
	}
	
	public boolean validateValue(Object value)
	{
		Object convertedValue = convertToRawValue(value);
		Annotation validation = GetValidation();
		if(validation != null)
		{
			return Validator.Validate(convertedValue, validation);
		}
		
		return true;
	}
	
	public Annotation GetValidation()
	{
		for(Annotation annotation : propertySetter.getAnnotations())
		{
			if(Validator.isValidationAnnotation(annotation))
			{
				 return annotation;
			}
		}
		
		return null;
	}
	
	public Class<?> getValidationType()
	{
		Annotation validation = GetValidation();
		if(validation != null)
		{
			return validation.annotationType();
		}
		
		return null;
	}
	
	public Class<?> getViewerType()
	{
		DataTypeViewer viewerType = propertyGetter.getAnnotation(DataTypeViewer.class);
		if(viewerType == null)
			return null;
		return viewerType.Type();
	}
	
	public Class<?> getEditorType()
	{
		DataTypeEditor editorType = propertySetter.getAnnotation(DataTypeEditor.class);
		if(editorType == null)
			return null;
		return editorType.Type();
	}

	@Override
	public void setPropertyChangedListener(OnPropertyChangedListener listener) {
		onPropertyChangedListener = listener;
	}
}
