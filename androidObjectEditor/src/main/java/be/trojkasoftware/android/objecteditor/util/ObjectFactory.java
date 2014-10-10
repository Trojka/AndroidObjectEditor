package be.trojkasoftware.android.objecteditor.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import be.trojkasoftware.android.objecteditor.PropertyProxy;
import be.trojkasoftware.android.objecteditor.TypeConverterRegistry;
import be.trojkasoftware.android.objecteditor.TypeViewRegistry;
import be.trojkasoftware.android.typeconverter.EnumTypeConverter;
import be.trojkasoftware.android.typeconverter.TypeConverter;
import be.trojkasoftware.android.typeeditor.AllowedValueListViewEditor;
import be.trojkasoftware.android.typeeditor.DefaultEditor;
import be.trojkasoftware.android.typeeditor.DefaultViewer;


import android.content.Context;
import android.view.View;

public class ObjectFactory {
	
	public static TypeConverter getTypeConverterForProperty(PropertyProxy prop, TypeConverterRegistry converterRegistry)
	{
		if(prop.getPropertyType().isEnum())
			return new EnumTypeConverter();
		
		return converterRegistry.getConverterForType(prop.getPropertyType());
	}
	
	public static View getTypeViewerForPoperty(PropertyProxy prop, TypeViewRegistry viewerRegistry, Context context)
	{
		Class<?> propViewer = ObjectFactory.getTypeViewerClassForProperty(prop, viewerRegistry);
		if (propViewer == null)
		{
			return new DefaultViewer(context);
		}
		else
		{
			return ObjectFactory.instantiateViewFromClass(propViewer, context);
		}
	}
		
	public static View getTypeEditorForPoperty(PropertyProxy prop, TypeViewRegistry editorRegistry, Context context)
	{
		Class<?> propEditor = ObjectFactory.getTypeEditorClassForProperty(prop, editorRegistry);
		if (propEditor == null)
		{
			if(prop.getPropertyType().isEnum())
				return new AllowedValueListViewEditor(context);
			else
				return new DefaultEditor(context);
		}
		else
		{
			return ObjectFactory.instantiateViewFromClass(propEditor, context);
		}
	}
	
	public static Class<?> getTypeViewerClassForProperty(PropertyProxy prop, TypeViewRegistry viewerRegistry)
	{
		Class<?> propViewer = prop.getViewerType();
		if (propViewer == null)
		{
			if(!viewerRegistry.containsType(prop.getPropertyType()))
			{
				return null;
			}
			
			return viewerRegistry.getMappingForType(prop.getPropertyType());
		}
		
		return propViewer;
	}
	
	public static Class<?> getTypeEditorClassForProperty(PropertyProxy prop, TypeViewRegistry editorRegistry)
	{
		Class<?> propEditor = prop.getEditorType();
		if (propEditor == null)
		{
			if(prop.hasAllowedValues())
			{
				return AllowedValueListViewEditor.class;
			}
			if(!editorRegistry.containsType(prop.getPropertyType()))
			{
				return null;
			}
			
			return editorRegistry.getMappingForType(prop.getPropertyType());
		}
		
		return propEditor;
	}
	
	private static View instantiateViewFromClass(Class<?> itemClass, Context context)
	{
		Constructor<?> cons;
		View view = null;
		try {
			cons = itemClass.getConstructor(Context.class);
			view = (View)cons.newInstance(context);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return view;
	}

}
