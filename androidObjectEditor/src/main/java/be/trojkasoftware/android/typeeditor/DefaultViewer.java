package be.trojkasoftware.android.typeeditor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import be.trojkasoftware.android.objecteditor.PropertyProxy;
import be.trojkasoftware.android.objecteditor.PropertyProxy.OnValueChangedListener;

import be.trojkasoftware.android.objecteditor.R;

public class DefaultViewer extends LinearLayout implements ITypeViewer, OnValueChangedListener {

	private PropertyProxy propertyProxy;
	private TextView editText;
	
	public DefaultViewer(Context context) {
        super(context);
                
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.typeviewer_default, this);
        
        editText = (TextView)this.findViewById(R.id.tvText);
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

	private void showValue()
	{
		showValue((String)propertyProxy.getValue(getViewClass()));
	}
	
	private void showValue(String value)
	{
		if(value == null)
		{
			editText.setText("");
			return;
		}
		editText.setText(value);
	}

	@Override
	public void setError(Boolean error) {
		editText.setBackgroundColor(Color.RED);
	}

	@Override
	public TypeEditorType getEditorType() {
		return TypeEditorType.ExternalDialog;
	}

	@Override
	public Class getViewClass() {
		return String.class;
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
