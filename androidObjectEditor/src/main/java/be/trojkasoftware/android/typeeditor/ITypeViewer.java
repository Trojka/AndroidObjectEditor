package be.trojkasoftware.android.typeeditor;

import be.trojkasoftware.android.objecteditor.PropertyProxy;


public interface ITypeViewer {

	void setPropertyProxy(PropertyProxy propertyProxy);
	PropertyProxy getPropertyProxy();

	void setError(Boolean error);
	
	TypeEditorType getEditorType();
	
	Class getViewClass();
	void setReadOnly(boolean readOnly);
}
