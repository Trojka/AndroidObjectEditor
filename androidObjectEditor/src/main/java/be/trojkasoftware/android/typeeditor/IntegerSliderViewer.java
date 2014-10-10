package be.trojkasoftware.android.typeeditor;

import be.trojkasoftware.android.data.annotations.IntegerRangeValidation;
import be.trojkasoftware.android.data.annotations.IntegerStepValue;
import be.trojkasoftware.android.objecteditor.PropertyProxy;

import be.trojkasoftware.android.objecteditor.R;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class IntegerSliderViewer extends LinearLayout implements ITypeViewer, OnSeekBarChangeListener {

	private PropertyProxy propertyProxy;
	private IntegerRangeValidation validation;
	private int step = 1;
	
	private TextView textView;
	private SeekBar seekBar;

	public IntegerSliderViewer(Context context) {
		super(context);
        
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.typeviewer_integer_slider, this);
        
        textView = (TextView)this.findViewById(R.id.textViewValue);
        seekBar = (SeekBar)this.findViewById(R.id.seekBarValue);
        seekBar.setOnSeekBarChangeListener(this);

	}

	@Override
	public void setPropertyProxy(PropertyProxy propPrxy) {
		Class<?> validationType = propPrxy.getValidationType();
		if(validationType != IntegerRangeValidation.class)
		{
			return;
		}
		
		validation = (IntegerRangeValidation)propPrxy.GetValidation();
		
		IntegerStepValue stepValueAnnotation = propPrxy.getAnnotation(IntegerStepValue.class);
		if(stepValueAnnotation != null)
		{
			step = stepValueAnnotation.Step();
		}
		
		seekBar.setMax((validation.MaxValue() - validation.MinValue())/step);
		int numberOfChars = ((Integer)validation.MaxValue()).toString().length();
		String maxNumberLength = "0";
		for(int i=0; i < numberOfChars; i++)
		{
			maxNumberLength = maxNumberLength + "0";
		}
		Rect bounds = new Rect();
		Paint textPaint = textView.getPaint();
		textPaint.getTextBounds(maxNumberLength,0,maxNumberLength.length(),bounds);
		textView.setWidth(bounds.width());
		
		propertyProxy = propPrxy;
		
		showValue();

	}

	@Override
	public PropertyProxy getPropertyProxy() {
		return propertyProxy;

	}


	@Override
	public void setError(Boolean error) {
		
	}

	@Override
	public Class getViewClass() {
		return int.class;
	}

	@Override
	public TypeEditorType getEditorType() {
		return TypeEditorType.ViewIsEditor;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		seekBar.setEnabled(!readOnly);
		
	}

	private void showValue()
	{
		showValue((Integer)propertyProxy.getValue(getViewClass()));
	}
	
	private void showValue(int value) {
		seekBar.setProgress((value - validation.MinValue())/step);
		textView.setText(((Integer)value).toString());
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int value, boolean arg2) {
		if(propertyProxy == null || textView == null)
		{
			return;
		}
		
		propertyProxy.setValue((Integer)((value * step) + validation.MinValue()));
		textView.setText(((Integer)((value * step) + validation.MinValue())).toString());
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		
	}

}
