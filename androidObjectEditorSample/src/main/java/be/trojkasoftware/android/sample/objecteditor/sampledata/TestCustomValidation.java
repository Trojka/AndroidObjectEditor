package be.trojkasoftware.android.sample.objecteditor.sampledata;

import be.trojkasoftware.android.sample.objecteditor.samplevalidation.StartWithPrefixValidation;

public class TestCustomValidation {
	
	public static TestCustomValidation Create()
	{
		TestCustomValidation value = new TestCustomValidation();
		value.setSomeLimitedStringValue("ABC");
		
		return value;
	}

	private String someLimitedStringValue;
	
	public String getSomeLimitedStringValue()
	{
		return someLimitedStringValue;
	}
	
	@StartWithPrefixValidation(Prefix = "AB")
	public void setSomeLimitedStringValue(String theValue)
	{
		someLimitedStringValue = theValue;
	}
}
