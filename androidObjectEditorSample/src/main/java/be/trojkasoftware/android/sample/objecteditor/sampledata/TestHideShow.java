package be.trojkasoftware.android.sample.objecteditor.sampledata;

import be.trojkasoftware.android.data.annotations.HideSetter;
import be.trojkasoftware.android.data.annotations.HideSetters;

@HideSetters( 
		{@HideSetter(Name="setHiddenFromClassLvlValue"),
		@HideSetter(Name="setHiddenUnknownMethod")} 
		)
public class TestHideShow {
	
	public static TestHideShow Create()
	{
		TestHideShow value = new TestHideShow();
		value.setRegularMethod(10);
		value.setHiddenFromClassLvlValue(20);
		value.setHiddenFromMethodLvlValue(30);
		
		return value;
	}
	
	private int regularMethod;
	private int hiddenFromClassLvlValue;
	private int hiddenFromMethodLvlValue;
	
	public int getRegularMethod()
	{
		return regularMethod;
	}
	
	public void setRegularMethod(int value)
	{
		regularMethod = value;
	}
	
	public int getHiddenFromClassLvlValue()
	{
		return hiddenFromClassLvlValue;
	}
	
	public void setHiddenFromClassLvlValue(int value)
	{
		hiddenFromClassLvlValue = value;
	}
	
	public int getHiddenFromMethodLvlValue()
	{
		return hiddenFromMethodLvlValue;
	}
	
	@HideSetter
	public void setHiddenFromMethodLvlValue(int value)
	{
		hiddenFromMethodLvlValue = value;
	}

}
