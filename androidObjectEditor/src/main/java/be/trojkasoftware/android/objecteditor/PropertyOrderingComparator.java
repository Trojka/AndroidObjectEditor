package be.trojkasoftware.android.objecteditor;

import java.util.Comparator;

public class PropertyOrderingComparator implements Comparator<PropertyProxy> {

	@Override
	public int compare(PropertyProxy object1, PropertyProxy object2) {
		return object1.getDisplayName().compareTo(object2.getDisplayName());
	}


}
