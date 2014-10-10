package be.trojkasoftware.android.typeeditor;

import java.util.ArrayList;

import be.trojkasoftware.android.objecteditor.PropertyProxy;

import be.trojkasoftware.android.objecteditor.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class AllowedValueListViewEditor extends LinearLayout implements ITypeEditor, OnItemClickListener {
	private PropertyProxy propertyProxy = null;
	private ListView allowedValueListView;
	private ArrayList<String> allowedValueList;
	private ArrayAdapter<String> adapter;
	private String selectedValue;

	public AllowedValueListViewEditor(Context context) {
		super(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.typeeditor_enum, this);

        allowedValueListView = (ListView)this.findViewById(R.id.lvEnumList);       
        allowedValueListView.setOnItemClickListener(this);
	}

	@Override
	public void setPropertyProxy(PropertyProxy propPrxy) {
		propertyProxy = propPrxy;
		
		allowedValueList = new ArrayList<String>();
		for(Object allowedValue : propertyProxy.getAllowedValues())
		{
			allowedValueList.add(allowedValue.toString());
		}
	    
	    adapter = new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_list_item_1, allowedValueList);
	    allowedValueListView.setAdapter(adapter);
	      
		showValue();
	}

	@Override
	public PropertyProxy getPropertyProxy() {
		return propertyProxy;
	}

	@Override
	public Object getEditorValue() {
		return selectedValue;
	}

	@Override
	public void setError(Boolean error) {
		
	}

	private void showValue() {
		showValue((String)propertyProxy.getValue(getEditorClass()));
	}
	
	private void showValue(String enumValue) {

	}

	@Override
	public Class getEditorClass() {
		return String.class;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		selectedValue = adapter.getItem(position);
	}

}
