package be.trojkasoftware.android.sample.objecteditor.sampledata;

import android.graphics.Color;

import be.trojkasoftware.android.data.annotations.IntegerRangeValidation;
import be.trojkasoftware.android.data.annotations.IntegerStepValue;
import be.trojkasoftware.android.objecteditor.annotations.DataTypeEditor;
import be.trojkasoftware.android.objecteditor.annotations.DataTypeViewer;
import be.trojkasoftware.android.typeeditor.ColorEditorActivity;
import be.trojkasoftware.android.typeeditor.ColorViewer;
import be.trojkasoftware.android.typeeditor.IntegerSliderViewer;

public class TestCustomEditor {
	
	public static TestCustomEditor Create()
	{
		TestCustomEditor value = new TestCustomEditor();
		value.setSomeColorValue(Color.RED);
		value.setSomeIntegerValue(11);
		
		return value;
	}
	
	private int someColorValue;
	private int someIntegerValue;
	
	@DataTypeViewer(Type=ColorViewer.class)
	public int getSomeColorValue()
	{
		return someColorValue;
	}
	
	@DataTypeEditor(Type=ColorEditorActivity.class)
	public void setSomeColorValue(int theValue)
	{
		someColorValue = theValue;
	}	
	
	@DataTypeViewer(Type=IntegerSliderViewer.class)
	public int getSomeIntegerValue()
	{
		return someIntegerValue;
	}

	@IntegerRangeValidation(MinValue = 5, MaxValue = 15, ErrorMessage = "Value must be between 5 and 15")
	@IntegerStepValue(Step = 3)
	public void setSomeIntegerValue(int value)
	{
		someIntegerValue = value;
	}
	
}
