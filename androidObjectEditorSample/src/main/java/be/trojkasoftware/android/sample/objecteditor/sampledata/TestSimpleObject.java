package be.trojkasoftware.android.sample.objecteditor.sampledata;

public class TestSimpleObject {
	
	public static TestSimpleObject Create()
	{
		TestSimpleObject value = new TestSimpleObject();
		value.setSomeIntValue(10);
		value.setSomeBooleanValue(true);
		value.setSomeStringValue("Een tekst");
		value.setSomeEnumeration(SampleEnumeration.Value1);
		
		return value;
	}
	
	private int someIntValue;
	private double someDoubleValue;
	private boolean someBooleanValue;
	private String someStringValue;
	private SampleEnumeration someEnumeration;
	
	public int getSomeIntValue()
	{
		return someIntValue;
	}
	
	public void setSomeIntValue(int theValue)
	{
		someIntValue = theValue;
	}
	
	public double getSomeDoubleValue()
	{
		return someDoubleValue;
	}
	
	public void setSomeDoubleValue(double theValue)
	{
		someDoubleValue = theValue;
	}
	
	public boolean getSomeBooleanValue()
	{
		return someBooleanValue;
	}

	public void setSomeBooleanValue(boolean theValue)
	{
		someBooleanValue = theValue;
	}
	
	public String getSomeStringValue()
	{
		return someStringValue;
	}
	
	public void setSomeStringValue(String theValue)
	{
		someStringValue = theValue;
	}
	
	public SampleEnumeration getSomeEnumeration()
	{
		return someEnumeration;
	}
		
	public void setSomeEnumeration(SampleEnumeration theValue)
	{
		someEnumeration = theValue;
	}
}
