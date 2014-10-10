package be.trojkasoftware.android.sample.objecteditor.sampledata;

import be.trojkasoftware.android.objecteditor.annotations.DependentOnPropertyValue;

public class TestDependencies {
	
	public static TestDependencies Create()
	{
		TestDependencies value = new TestDependencies();
		value.setDependentOnSource1(0);
		//value.setDependentOnSrc1Trgt1("SomeValue");
		value.setDependentOnSrc1Trgt2(10);
		value.setDependentOnSrc1Trgt3(true);
		
		return value;
	}

	private int dependentOnSource1;
	private String dependentOnSrc1Trgt1;
	private int dependentOnSrc1Trgt2;
	private boolean dependentOnSrc1Trgt3;
	private String dependentOnSource2;
	private String dependentOnSrc2Trgt1;
	private int dependentOnSrc2Trgt2;
	private int dependentOnSrc2Trgt3;
	
	public int getDependentOnSource1()
	{
		return dependentOnSource1;
	}
	
	public void setDependentOnSource1(int value)
	{
		dependentOnSource1 = value;
	}
	
//	public String getDependentOnSrc1Trgt1()
//	{
//		return dependentOnSrc1Trgt1;
//	}
//	
//	@DependentOnPropertyValue(ValueProviderName="DependentOnSource1", IntValue=1)
//	public void setDependentOnSrc1Trgt1(String value)
//	{
//		dependentOnSrc1Trgt1 = value;
//	}
	
	public int getDependentOnSrc1Trgt2()
	{
		return dependentOnSrc1Trgt2;
	}
	
	@DependentOnPropertyValue(ValueProviderName="DependentOnSource1", IntValue=1)
	public void setDependentOnSrc1Trgt2(int value)
	{
		dependentOnSrc1Trgt2 = value;
	}
	
	public boolean getDependentOnSrc1Trgt3()
	{
		return dependentOnSrc1Trgt3;
	}
	
	@DependentOnPropertyValue(ValueProviderName="DependentOnSource1", IntValue=2)
	public void setDependentOnSrc1Trgt3(boolean value)
	{
		dependentOnSrc1Trgt3 = value;
	}

}
