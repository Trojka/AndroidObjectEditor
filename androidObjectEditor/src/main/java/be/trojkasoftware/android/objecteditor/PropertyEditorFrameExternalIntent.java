package be.trojkasoftware.android.objecteditor;


import be.trojkasoftware.android.objecteditor.util.ObjectFactory;
import be.trojkasoftware.android.typeeditor.ITypeViewer;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class PropertyEditorFrameExternalIntent 
	extends PropertyEditorFrame
	implements OnClickListener {

	private TypeViewRegistry editorRegistry;

	public static int PROCESS_RESULT;

	public PropertyEditorFrameExternalIntent(Context context, ObjectEditorViewConfig configuration, TypeViewRegistry editorRegistry) {
		super(context, configuration);
        
		this.editorRegistry = editorRegistry;
		
		Initialize(context);
	}
	
	public void Initialize(Context context)
	{
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(config.FrameIntentId, this, true);
		
        propNameText = (TextView)findViewById(config.FrameIntentNameId);
	}
	
	public void Clear()
	{
		if(propertyViewer != null)
			propertyViewer.setOnClickListener(null);
		
		propertyViewer = null;
		propertyProxy = null;
	}
	
	public int getContainerViewId()
	{
		return config.FrameIntentPlaceholderId;
	}
	
	public void setPropertyView(View propertyView)
	{
		super.setPropertyView(propertyView);
		
		propertyView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		
		if(propertyProxy.getIsReadonly())
		{
			return;
		}
		
		// if our frame is recycled while editing, the object pointed to by propertyEditorType will
		//	change and we will not get the correct value in the onClick handler.
		final Class<?> propertyEditorType = ObjectFactory.getTypeEditorClassForProperty(propertyProxy, editorRegistry);
		
		Bundle valueData = (Bundle)((ITypeViewer)propertyViewer).getPropertyProxy().getValue(Bundle.class);

		Bundle arg = new Bundle();
		arg.putString(PropertyEditorFrame.PROPERTYNAME, propertyProxy.getName());
		arg.putAll(valueData);
		Activity activity = (Activity)PropertyEditorFrameExternalIntent.this.getContext();
		Intent myIntent = new Intent(activity, propertyEditorType);
		myIntent.putExtras(arg);
		activity.startActivityForResult(myIntent, PropertyEditorFrameExternalIntent.PROCESS_RESULT);
		
	}

}
