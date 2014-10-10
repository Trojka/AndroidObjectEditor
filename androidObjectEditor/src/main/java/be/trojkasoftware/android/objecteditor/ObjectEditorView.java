package be.trojkasoftware.android.objecteditor;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import be.trojkasoftware.android.typeeditor.BooleanViewer;
import be.trojkasoftware.android.typeeditor.IExternalEditorEvents;

import be.trojkasoftware.android.objecteditor.R;


public class ObjectEditorView extends LinearLayout 
	implements IExternalEditorEvents  {
	private ExpandableListView propertyListView;
	private TypeViewRegistry editorRegistry;
	private TypeViewRegistry viewerRegistry;
	private ObjectEditorViewConfig config;
	private ObjectProxyAdapter adapter;
	private ObjectProxy proxy;
	
	private ItemCreationCallback itemCreationCallback;
	
	public interface ItemCreationCallback
	{
		public void OnFrameCreated(View frame);
		public void OnFrameInitialized(View frame, PropertyProxy property);
		public void OnFrameUnInitialized(View frame);
	}
	
    public ObjectEditorView(Context context) {
        super(context);
        
        config = new ObjectEditorViewConfig();
        config.FrameId = R.layout.typeeditor_frame;
        config.FrameNameId = R.id.propName;
        config.FramePlaceholderId = R.id.editorPlaceHolder;
    	
        config.FrameDlgId = R.layout.typeeditor_external_frame;
        config.FrameDlgNameId = R.id.extPropName;
        config.FrameDlgPlaceholderId = R.id.extEditorPlaceHolder;

        config.FrameIntentId = R.layout.typeeditor_external_frame;
        config.FrameIntentNameId = R.id.extPropName;
        config.FrameIntentPlaceholderId = R.id.extEditorPlaceHolder;

        Initialize(context, config);
       
    }

	
    public ObjectEditorView(Context context, ObjectEditorViewConfig configuration) {
        super(context);
        
        Initialize(context, configuration);
        
    }
    
    private void Initialize(Context context, ObjectEditorViewConfig configuration)
    {
        editorRegistry = new TypeViewRegistry();
        
        viewerRegistry = new TypeViewRegistry();
        viewerRegistry.registerType(boolean.class, BooleanViewer.class);
        
        config = configuration;

        this.setOrientation(VERTICAL);
        
        propertyListView = new ExpandableListView(context);
        propertyListView.setGroupIndicator(null);
        addView(propertyListView, new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
    }
    
    public ObjectProxy getObjectToEdit()
    {
    	return proxy;
    }
    
    public void setItemCreationCallback(ItemCreationCallback itemCreationCallback)
    {
    	this.itemCreationCallback = itemCreationCallback;
    }
    
    public void setObjectToEdit(ObjectProxy objectToEdit)
    {
    	this.proxy = objectToEdit;
        
        adapter = new ObjectProxyAdapter(this, this.getContext(), proxy, 
        		viewerRegistry, editorRegistry,
        		config, itemCreationCallback);
        
        propertyListView.setAdapter(adapter);
        int numberOfGroups = adapter.getGroupCount();
        for (int i = 0; i < numberOfGroups; i++)
        {
        	propertyListView.expandGroup(i);
        }
    }

	public void HandleRequestResult(int requestCode, int resultCode,
			Intent intent) {

		Bundle resultValue = intent.getExtras();
		String propertyName = resultValue.getString(PropertyEditorFrame.PROPERTYNAME);
		
		EditingFinished(propertyName, resultValue);
		
	}

	@Override
	public void EditingStarted(String propertyName) {
		
	}

	@Override
	public void EditingFinished(String propertyName, Object newValue) {
		if(proxy == null)
			return;
		
		PropertyProxy property = proxy.getProperty(propertyName);
		if(property == null)
			return;
		
		property.setValue(newValue);
	}

    
}
