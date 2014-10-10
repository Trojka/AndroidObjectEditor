package be.trojkasoftware.android.sample;

import be.trojkasoftware.android.sample.objecteditor.sampledata.TestCustomEditor;
import be.trojkasoftware.android.sample.objecteditor.sampledata.TestCustomValidation;
import be.trojkasoftware.android.sample.objecteditor.sampledata.TestHideShow;
import be.trojkasoftware.android.sample.objecteditor.sampledata.TestSimpleObject;
import be.trojkasoftware.android.sample.objecteditor.sampledata.TestValueValidation;

import be.trojkasoftware.android.objecteditor.ObjectEditorView;
import be.trojkasoftware.android.objecteditor.ObjectEditorViewConfig;
import be.trojkasoftware.android.objecteditor.ObjectProxy;
import be.trojkasoftware.android.objecteditor.PropertyProxy;
import be.trojkasoftware.android.objecteditor.TypeConverterRegistry;
import be.trojkasoftware.android.sample.objecteditor.R;
import be.trojkasoftware.android.typeconverter.BooleanTypeConverter;
import be.trojkasoftware.android.typeconverter.DoubleTypeConverter;
import be.trojkasoftware.android.typeconverter.IntTypeConverter;
import be.trojkasoftware.android.typeconverter.StringTypeConverter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AndroidObjectEditorSampleActivity 
	extends Activity
	implements ObjectEditorView.ItemCreationCallback{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ObjectEditorViewConfig config = new ObjectEditorViewConfig();
        config.FrameId = R.layout.custom_frame;
        config.FrameNameId = R.id.tvcustframe_name;
        config.FramePlaceholderId = R.id.llcustframe_holder;
    	
        config.FrameDlgId = R.layout.custom_external_frame;
        config.FrameDlgNameId = R.id.tvcustextframe_name;
        config.FrameDlgPlaceholderId = R.id.llcustextframe_holder;

        config.FrameIntentId = R.layout.custom_external_frame;
        config.FrameIntentNameId = R.id.tvcustextframe_name;
        config.FrameIntentPlaceholderId = R.id.llcustextframe_holder;
        
        //view = new ObjectEditorView(this, config);
        view = new ObjectEditorView(this);
        
        view.setItemCreationCallback(this);
        
        Object objectToEdit = null;
        
        objectToEdit = TestSimpleObject.Create();
        //objectToEdit = TestDisplayProperties.Create();
        //objectToEdit = TestCustomEditor.Create();
        //objectToEdit = TestAllowedValues.Create();
        //objectToEdit = TestDisplayValue.Create();
        //objectToEdit = TestValueValidation.Create();
        //objectToEdit = TestCustomValidation.Create();
        //objectToEdit = TestHideShow.Create();
        //objectToEdit = TestDependencies.Create();

        TypeConverterRegistry converterRegistry = TypeConverterRegistry.create(); 
        
        view.setObjectToEdit(new ObjectProxy(objectToEdit, converterRegistry));
        
        setContentView(view);
        
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
    	view.HandleRequestResult(requestCode, resultCode, intent);
    }
    
    ObjectEditorView view;

	@Override
	public void OnFrameCreated(View frame) {
		int duration = 1;
		//Toast.makeText(this, "View created", duration).show();
	}

	@Override
	public void OnFrameInitialized(View frame, PropertyProxy property) {
		int duration = 1;
		//Toast.makeText(this, "View init for " + property.getDisplayName(), duration).show();
	}

	@Override
	public void OnFrameUnInitialized(View frame) {
		int duration = 1;
		//Toast.makeText(this, "View uninit for " + property.getDisplayName(), duration).show();
	}
}