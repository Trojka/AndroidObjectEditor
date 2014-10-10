package be.trojkasoftware.android.objecteditor;

import java.util.ArrayList;
import java.util.List;

import be.trojkasoftware.android.objecteditor.util.ObjectFactory;
import be.trojkasoftware.android.typeeditor.IExternalEditorEvents;
import be.trojkasoftware.android.typeeditor.IKeyBoardInput;
import be.trojkasoftware.android.typeeditor.ITypeEditor;
import be.trojkasoftware.android.typeeditor.ITypeViewer;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class PropertyEditorFrameExternalDialog 
	extends PropertyEditorFrame 
	implements OnClickListener {

	private List<IExternalEditorEvents> editorEvents = new ArrayList<IExternalEditorEvents>();
	private TypeViewRegistry editorRegistry;
	
	public PropertyEditorFrameExternalDialog(Context context, ObjectEditorViewConfig configuration, TypeViewRegistry editorRegistry) {
		super(context, configuration);
        
		this.editorRegistry = editorRegistry;
        
		Initialize(context);
	}
	
	public void Initialize(Context context)
	{
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(config.FrameDlgId, this, true);
		
        propNameText = (TextView)findViewById(config.FrameDlgNameId);
	}
	
	public void InitView()
	{
		if(propertyProxy != null)
		{
			propNameText.setText(propertyProxy.getDisplayName());		
		}
		if((propertyViewer != null) && (propertyProxy != null))
		{
			((ITypeViewer)propertyViewer).setPropertyProxy(propertyProxy);
		}
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
		return config.FrameDlgPlaceholderId;
	}
	
	public void setPropertyView(View propertyView)
	{
		super.setPropertyView(propertyView);

		propertyView.setOnClickListener(this);
	}	
	
	public void addEventWatcher(IExternalEditorEvents editorEvents)
	{
		this.editorEvents.add(editorEvents);
	}
	
	public void removeEventWatcher(IExternalEditorEvents editorEvents)
	{
		this.editorEvents.remove(editorEvents);
	}

	@Override
	public void onClick(View propView) {
		
		if(propertyProxy.getIsReadonly())
		{
			return;
		}
		
		// if our frame is recycled while editing, the object pointed to by propertyEditor will
		//	change and we will not get the correct value in the onClick handler. Same goes for
		//	the property
		final PropertyProxy property = propertyProxy;
		final View editorView =  ObjectFactory.getTypeEditorForPoperty(property, editorRegistry, this.getContext()); //propertyEditor;
		if((editorView != null) && (propertyProxy != null))
		{
			((ITypeEditor)editorView).setPropertyProxy(propertyProxy);
		}

		final List<IExternalEditorEvents> events = new ArrayList<IExternalEditorEvents>(editorEvents);
		
		if(IKeyBoardInput.class.isAssignableFrom(editorView.getClass()))
		{
			((IKeyBoardInput)editorView).setInputType(propertyProxy.getInputType());
		}

		if (editorView.getParent() != null && editorView.getParent() instanceof ViewGroup)
		{
			((ViewGroup)editorView.getParent()).removeAllViews();
		}
		
	    if(events != null && events.size() != 0)
	    {
	    	for (IExternalEditorEvents editorEvent : events)
	    	{
	    		editorEvent.EditingStarted(property.getName());
	    	}
	    }
	    
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PropertyEditorFrameExternalDialog.this.getContext());
		alertDialogBuilder.setView(editorView);
		alertDialogBuilder.setPositiveButton("Apply", null);
		
		AlertDialog dialog = alertDialogBuilder.create();
	
		dialog.setOnShowListener(new DialogInterface.OnShowListener() {

		    @Override
		    public void onShow(final DialogInterface dialog) {

		        Button b = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
		        b.setOnClickListener(new View.OnClickListener() {

		            @Override
		            public void onClick(View view) {

		            	Object value = ((ITypeEditor)editorView).getEditorValue();
		            	if(property.validateValue(value))
		            	{
			            	dialog.dismiss();
			        	    if(events != null && events.size() != 0)
			        	    {
			        	    	for (IExternalEditorEvents editorEvent : events)
			        	    	{
			        	    		editorEvent.EditingFinished(property.getName(), value);
			        	    	}
			        	    }
		            	}
		            	else
		            	{
		            		((ITypeEditor)editorView).setError(true);
		            	}
		            }
		        });
		    }
		});		
		
	    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	    lp.copyFrom(dialog.getWindow().getAttributes());
	    lp.width = WindowManager.LayoutParams.FILL_PARENT;
	    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

	    dialog.show();
		dialog.getWindow().setAttributes(lp);
	}


}
