package be.trojkasoftware.android.sample.objecteditor.sampledata;

import be.trojkasoftware.android.objecteditor.annotations.Category;
import be.trojkasoftware.android.objecteditor.annotations.DisplayName;

public class TestDisplayProperties {
	
	public static TestDisplayProperties Create()
	{
		TestDisplayProperties value = new TestDisplayProperties();
		value.setIntegerValue(10);
		value.setAnIntegerWithDisplayValue(20);
		value.setAnIntegerWithCategory(30);
		
		return value;
	}
	
	private int anIntegerValue;
	private int anIntegerWithDisplayValue;
	private int anIntegerWithCategory;	
	private int anIntegerWithDisplayNameInNewCat;
	private int anIntegerWithDisplayNameInExistCat;
	
	public int getIntegerValue()
	{
		return anIntegerValue;
	}
	
	public void setIntegerValue(int value)
	{
		anIntegerValue = value;
	}
	
	@DisplayName(Name="DefaultCatDisplayName")
	public int getAnIntegerWithDisplayValue()
	{
		return anIntegerWithDisplayValue;
	}
	
	public void setAnIntegerWithDisplayValue(int value)
	{
		anIntegerWithDisplayValue = value;
	}
	
	@Category(Name = "CustomCategory")
	public int getAnIntegerWithCategory()
	{
		return anIntegerWithCategory;
	}
	
	public void setAnIntegerWithCategory(int value)
	{
		anIntegerWithCategory = value;
	}
	
	@DisplayName(Name="NewCatDisplayName", CategoryName="NewCategory")
	public int getAnIntegerWithDisplayNameInNewCat()
	{
		return anIntegerWithDisplayNameInNewCat;
	}
	
	public void setAnIntegerWithDisplayNameInNewCat(int value)
	{
		anIntegerWithDisplayNameInNewCat = value;
	}
	
	@DisplayName(Name="ExistCatDisplayName", CategoryName="CustomCategory")
	public int getAnIntegerWithDisplayNameInExistCat()
	{
		return anIntegerWithDisplayNameInExistCat;
	}
	
	public void setAnIntegerWithDisplayNameInExistCat(int value)
	{
		anIntegerWithDisplayNameInExistCat = value;
	}
}
