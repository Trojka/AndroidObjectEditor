package be.trojkasoftware.android.objecteditor;

import java.lang.reflect.Method;

public class ObjectPropertyList implements Iterable<Method> {

	public ObjectPropertyList(Object obj)
	{
		this.obj = obj;
	}
	
	@Override
	public ObjectPropertyIterator iterator() {
		return new ObjectPropertyIterator(this.obj);
	}

	private Object obj;

}
