package be.trojkasoftware.android.typeeditor;

import be.trojkasoftware.android.objecteditor.PropertyProxy;

public interface ITypeEditor {

	void setPropertyProxy(PropertyProxy propertyProxy);
	PropertyProxy getPropertyProxy();

	Object getEditorValue();
	void setError(Boolean error);
	
	Class<?> getEditorClass();
}
