package be.trojkasoftware.android.sample.objecteditor.sampledata;

import be.trojkasoftware.android.data.annotations.AllowedValue;
import be.trojkasoftware.android.data.annotations.AllowedValues;
import be.trojkasoftware.android.objecteditor.annotations.DisplayValueMap;
import be.trojkasoftware.android.objecteditor.annotations.IntegerDisplayValue;
import be.trojkasoftware.android.objecteditor.annotations.StringDisplayValue;

public class TestDisplayValue {
	
	public static TestDisplayValue Create()
	{
		TestDisplayValue value = new TestDisplayValue();
		value.setSomeDisplayLimitedIntValue(11);
		value.setSomeDisplayLimitedStringValue("AllowedValue11");
		value.setSomeDisplayEnumeration(EnumWithDisplayValues.Value1DisplayWAARDE1);
		
		return value;
	}
	
	private int someDisplayLimitedIntValue;
	private String someLimitedStringValue;
	private EnumWithDisplayValues someDisplayEnumeration;
	
	public int getSomeDisplayLimitedIntValue()
	{
		return someDisplayLimitedIntValue;
	}
	
	@AllowedValues( 
			{@AllowedValue(IntegerValue=11),
			@AllowedValue(IntegerValue=21)} 
			)
	@DisplayValueMap( IntegerMap = 
			{@IntegerDisplayValue(Value=11, ShowAs="IntValue11"),
			@IntegerDisplayValue(Value=21, ShowAs="IntValue21")} 
			)
	public void setSomeDisplayLimitedIntValue(int theValue)
	{
		someDisplayLimitedIntValue = theValue;
	}
	
	public String getSomeDisplayLimitedStringValue()
	{
		return someLimitedStringValue;
	}
	
	@AllowedValues( 
			{@AllowedValue(StringValue="AllowedValue11"),
			@AllowedValue(StringValue="AllowedValue21")} 
			)
	@DisplayValueMap( StringMap = 
		{@StringDisplayValue(Value="AllowedValue11", ShowAs="StringValue11"),
		@StringDisplayValue(Value="AllowedValue21", ShowAs="StringValue21")} 
		)
	public void setSomeDisplayLimitedStringValue(String theValue)
	{
		someLimitedStringValue = theValue;
	}
	
	public EnumWithDisplayValues getSomeDisplayEnumeration()
	{
		return someDisplayEnumeration;
	}
		
	public void setSomeDisplayEnumeration(EnumWithDisplayValues theValue)
	{
		someDisplayEnumeration = theValue;
	}
}
