package be.trojkasoftware.android.typeeditor;

import be.trojkasoftware.android.objecteditor.PropertyEditorFrame;
import be.trojkasoftware.android.typeconverter.IntTypeConverter;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class ColorEditorActivity extends Activity {
	
	private String propertyName;
	private ColorEditor colorEditor;
    private IntTypeConverter converter = new IntTypeConverter();
    private int value;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		colorEditor = new ColorEditor(this);
		this.setContentView(colorEditor);
		
		Bundle args = getIntent().getExtras();
		
		propertyName = args.getString(PropertyEditorFrame.PROPERTYNAME);
		value = (Integer)converter.ConvertFrom(args);

		colorEditor.setValue(value);
	}

	@Override
	public void onBackPressed() {
		
	    Intent result = new Intent();

	    Bundle ret = new Bundle();
	    ret.putString(PropertyEditorFrame.PROPERTYNAME, propertyName);
	    
	    Bundle colorValue = (Bundle) converter.ConvertTo(colorEditor.getValue(), Bundle.class);
	    ret.putAll(colorValue);

	    result.putExtras(ret);
	    setResult(Activity.RESULT_OK, result);
	    
		super.onBackPressed();
	}

}
