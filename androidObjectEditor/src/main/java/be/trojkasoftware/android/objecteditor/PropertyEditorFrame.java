package be.trojkasoftware.android.objecteditor;

import junit.framework.Assert;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import be.trojkasoftware.android.componentmodel.OnPropertyChangedListener;
import be.trojkasoftware.android.typeeditor.ITypeViewer;


public class PropertyEditorFrame extends LinearLayout
	implements OnPropertyChangedListener{

	protected ObjectEditorView objectEditor;
	protected View propertyViewer;
	protected TextView propNameText;
	protected PropertyProxy propertyProxy;
	protected ObjectEditorViewConfig config;
	
	public static String PROPERTYNAME = "PROPERTY_NAME";
	
	public PropertyEditorFrame(Context context, ObjectEditorViewConfig configuration) {
		super(context);
		
		config = configuration;
	    
		Initialize(context);
	}
	
	public void Initialize(Context context)
	{
    	LayoutInflater inflater = LayoutInflater.from(context);
	    inflater.inflate(config.FrameId, this, true);
		
	    propNameText = (TextView)findViewById(config.FrameNameId);
	}
	
	public void Clear()
	{
		propertyProxy.setPropertyChangedListener(null);
		propertyViewer = null;
		propertyProxy = null;
		objectEditor = null;
	}
	
	public void setObjectEditor(ObjectEditorView objectEditor)
	{
		this.objectEditor = objectEditor;
	}
	
	public void setProperty(PropertyProxy propertyProxy)
	{
		this.propertyProxy = propertyProxy;
		this.propertyProxy.setPropertyChangedListener(this);
		InitView();
	}
	
	public PropertyProxy getProperty()
	{
		return this.propertyProxy;
	}
	
	private void InitView()
	{
		if(propertyProxy != null)
		{
			propNameText.setText(propertyProxy.getDisplayName());		
		}
		if((propertyViewer != null) && (propertyProxy != null))
		{
			setReadOnly(propertyProxy.getIsReadonly());
			((ITypeViewer)this.propertyViewer).setPropertyProxy(propertyProxy);
		}
	}
	
	public int getContainerViewId()
	{
		return config.FramePlaceholderId;
	}
	
	public void setPropertyView(View propertyView)
	{
		this.propertyViewer = propertyView;
		View containerViewAsView = findViewById(getContainerViewId());
		LinearLayout containerView = (LinearLayout)containerViewAsView;
		Assert.assertNotNull(containerView);
		containerView.removeAllViews();
		containerView.addView(this.propertyViewer, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	
		InitView();
	}
	
	public ITypeViewer getPropertyViewer()
	{
		return (ITypeViewer)propertyViewer;
	}

	@Override
	public void PropertyChanged(Object source, String propertyName,
			Object oldValue, Object newValue) {
		if(propertyName.equals("IsReadonly"))
		{
			setReadOnly(((Boolean)newValue).booleanValue());
		}
	}
	
	public void setReadOnly(boolean readOnly)
	{
		 ((ITypeViewer)this.propertyViewer).setReadOnly(readOnly);		
	}

}
