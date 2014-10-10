package be.trojkasoftware.android.sample.objecteditor.sampledata;

import be.trojkasoftware.android.data.annotations.AllowedValues;
import be.trojkasoftware.android.data.annotations.AllowedValue;

public class TestAllowedValues {
	
	public static TestAllowedValues Create()
	{
		TestAllowedValues value = new TestAllowedValues();
		value.setSomeLimitedIntValue(10);
		value.setSomeLimitedStringValue("AllowedValue10");
		
		return value;
	}

	private int someLimitedIntValue;
	private String someLimitedStringValue;
	
	public int getSomeLimitedIntValue()
	{
		return someLimitedIntValue;
	}
	
	@AllowedValues( 
			{@AllowedValue(IntegerValue=10),
			@AllowedValue(IntegerValue=20)} 
			)
	public void setSomeLimitedIntValue(int theValue)
	{
		someLimitedIntValue = theValue;
	}
	
	public String getSomeLimitedStringValue()
	{
		return someLimitedStringValue;
	}
	
	@AllowedValues( 
			{@AllowedValue(StringValue="AllowedValue10"),
			@AllowedValue(StringValue="AllowedValue20")} 
			)
	public void setSomeLimitedStringValue(String theValue)
	{
		someLimitedStringValue = theValue;
	}
}
