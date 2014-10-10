package be.trojkasoftware.android.typeeditor;

import be.trojkasoftware.android.objecteditor.PropertyProxy;
import be.trojkasoftware.android.objecteditor.PropertyProxy.OnValueChangedListener;

import be.trojkasoftware.android.objecteditor.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class ColorViewer extends LinearLayout implements ITypeViewer, OnValueChangedListener {

	private PropertyProxy propertyProxy;
	
	public ColorViewer(Context context) {
        super(context);
        
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.typeviewer_color, this);

	}
	
	@Override
	public void setPropertyProxy(PropertyProxy propPrxy) {
		if(propertyProxy != null)
		{
			propertyProxy.removeOnValueChangedListener(this);
		}
		
		propertyProxy = propPrxy;

		showValue();
		propertyProxy.addOnValueChangedListener(this);
	}
	
	@Override
	public PropertyProxy getPropertyProxy() {
		return propertyProxy;
	}

	@Override
	public void setError(Boolean error) {
		
	}

	private void showValue()
	{
		showValue((Integer)propertyProxy.getValue(getViewClass()));
	}
	
	private void showValue(int value)
	{
		this.setBackgroundColor(value);
	}

	@Override
	public TypeEditorType getEditorType() {
		return TypeEditorType.ExternalIntent;
	}

	@Override
	public Class getViewClass() {
		return int.class;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		
	}

	@Override
	public void ValueChanged(PropertyProxy poperty, Object oldValue,
			Object newValue) {
		showValue();
	}

}
