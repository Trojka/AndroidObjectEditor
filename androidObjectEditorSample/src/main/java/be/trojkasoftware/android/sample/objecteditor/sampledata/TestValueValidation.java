package be.trojkasoftware.android.sample.objecteditor.sampledata;

import be.trojkasoftware.android.data.annotations.IntegerRangeValidation;
import be.trojkasoftware.android.data.annotations.StringLengthValidation;

public class TestValueValidation {
	
	public static TestValueValidation Create()
	{
		TestValueValidation value = new TestValueValidation();
		value.setSomeLimitedIntValue(10);
		value.setSomeLimitedStringValue("ABC");
		
		return value;
	}

	private int someLimitedIntValue;
	private String someLimitedStringValue;
	
	public int getSomeLimitedIntValue()
	{
		return someLimitedIntValue;
	}
	
	@IntegerRangeValidation(MinValue = 5, MaxValue = 15, ErrorMessage = "Value must be between 5 and 15")
	public void setSomeLimitedIntValue(int theValue)
	{
		someLimitedIntValue = theValue;
	}
	
	public String getSomeLimitedStringValue()
	{
		return someLimitedStringValue;
	}
	
	@StringLengthValidation( MinLength = 2, MaxLength = 6, ErrorMessage = "Length must be between 2 and 6")
	public void setSomeLimitedStringValue(String theValue)
	{
		someLimitedStringValue = theValue;
	}
}
