package be.trojkasoftware.android.typeeditor;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import be.trojkasoftware.android.objecteditor.PropertyProxy;

import be.trojkasoftware.android.objecteditor.R;

public class DefaultEditor extends LinearLayout implements ITypeEditor, IKeyBoardInput {

	private PropertyProxy propertyProxy;
	private TextView editText;
	
	public DefaultEditor(Context context) {
        super(context);
        
        final Context contextForToast = context;
                
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.typeeditor_default, this);
        
        editText = (TextView)this.findViewById(R.id.etDefaultEdit);

	}
	
	@Override
	public void setPropertyProxy(PropertyProxy propPrxy) {
		propertyProxy = propPrxy;
		showValue();
	}
	
	@Override
	public PropertyProxy getPropertyProxy() {
		return propertyProxy;
	}

	@Override
	public Object getEditorValue() {
		return editText.getText().toString();
	}

	@Override
	public void setError(Boolean error) {
		editText.setBackgroundColor(Color.RED);
	}

	private void showValue()
	{
		showValue((String)propertyProxy.getValue(getEditorClass()));
	}

	private void showValue(String value) {
		if(value == null)
		{
			editText.setText("");
			return;
		}
		editText.setText(value);
	}

	@Override
	public Class getEditorClass() {
		return String.class;
	}

	@Override
	public void setInputType(int inputType) {
		editText.setInputType(inputType);
	}

}
