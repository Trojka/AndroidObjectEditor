package be.trojkasoftware.android.objecteditor;

import java.util.Comparator;

public class CategoryOrderingComparator implements Comparator<PropertyCategory> {

	@Override
	public int compare(PropertyCategory object1, PropertyCategory object2) {
		int result = object1.getName().compareTo(object2.getName());
		
		if(result == 0)
			return 0;
		
		if(object1.getName().equalsIgnoreCase(PropertyCategory.DefaultCategoryName))
			return -1;
		
		if(object2.getName().equalsIgnoreCase(PropertyCategory.DefaultCategoryName))
			return 1;
		
		return result;
	}

}
