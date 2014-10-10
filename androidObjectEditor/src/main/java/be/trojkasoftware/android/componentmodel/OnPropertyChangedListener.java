package be.trojkasoftware.android.componentmodel;

public interface OnPropertyChangedListener {
	void PropertyChanged(Object source, String propertyName, Object oldValue, Object newValue);
}
