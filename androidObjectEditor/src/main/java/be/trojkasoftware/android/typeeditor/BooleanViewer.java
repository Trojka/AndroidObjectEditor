package be.trojkasoftware.android.typeeditor;

import be.trojkasoftware.android.objecteditor.PropertyProxy;

import be.trojkasoftware.android.objecteditor.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

public class BooleanViewer extends LinearLayout implements ITypeViewer, OnCheckedChangeListener {

	private PropertyProxy propertyProxy;
	private CheckBox booleanValue;
	
	public BooleanViewer(Context context) {
		super(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.typeviewer_boolean, this);
        		
        View booleanValueAsView = this.findViewById(R.id.cbTypeViewerBoolean);
		booleanValue = (CheckBox)booleanValueAsView;
		booleanValue.setOnCheckedChangeListener(this);
	}

	@Override
	public void setPropertyProxy(PropertyProxy propPrxy) {
		propertyProxy = propPrxy;
		
		showValue();
	}

	@Override
	public PropertyProxy getPropertyProxy() {
		return propertyProxy;
	}

	@Override
	public TypeEditorType getEditorType() {
		return TypeEditorType.ViewIsEditor;
	}

	@Override
	public void setError(Boolean error) {
		
	}
	
	private void showValue()
	{
		showValue((Boolean)propertyProxy.getValue(getViewClass()));
	}
	
	private void showValue(boolean value)
	{
		booleanValue.setChecked(value);
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(propertyProxy != null)
		{
			propertyProxy.setValue(isChecked);
		}
	}

	@Override
	public Class getViewClass() {
		return boolean.class;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		booleanValue.setEnabled(!readOnly);
		
	}

}
