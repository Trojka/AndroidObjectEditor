package be.trojkasoftware.android.objecteditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import be.trojkasoftware.android.objecteditor.ObjectEditorView.ItemCreationCallback;
import be.trojkasoftware.android.objecteditor.util.ObjectFactory;
import be.trojkasoftware.android.typeeditor.ITypeViewer;
import be.trojkasoftware.android.typeeditor.TypeEditorType;

import be.trojkasoftware.android.objecteditor.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


public class ObjectProxyAdapter 
		extends BaseExpandableListAdapter {
	private Context context;
	private ArrayList<PropertyCategory> categories;
	private TypeViewRegistry viewerRegistry;
	private TypeViewRegistry editorRegistry;
	private ObjectEditorView objectEditor;
	private ObjectEditorViewConfig objectEditorConfig;
	private ItemCreationCallback itemCreationCallback;
	
	private Map<PropertyProxy,View> propertyViewerCache = new HashMap<PropertyProxy,View>();
	
	public static String getChildTag(int groupPosition, int childPosition)
	{
		return "G" + groupPosition + "_C" + childPosition;
	}
	
	public ObjectProxyAdapter(ObjectEditorView editor, Context context, ObjectProxy proxy, 
			TypeViewRegistry viewers, TypeViewRegistry editors, 
			ObjectEditorViewConfig config,
			ItemCreationCallback itemCreationCallback) {
		this.context = context;
		this.objectEditor = editor;
		this.objectEditorConfig = config;
		this.itemCreationCallback = itemCreationCallback;
		this.viewerRegistry = viewers;
		this.editorRegistry = editors;
		this.categories = proxy.getCategories();
	}
	

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		ArrayList<PropertyProxy> propList = categories.get(groupPosition).getProperties();
		return propList.get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		PropertyProxy child = (PropertyProxy) getChild(groupPosition, childPosition);
		View propertyView = null;
		propertyView = getViewer(child);
		if (convertView == null) {
			PropertyEditorFrame frameView = null;
			
			if(((ITypeViewer)propertyView).getEditorType() == TypeEditorType.ExternalDialog)
			{
				frameView = new PropertyEditorFrameExternalDialog(context, objectEditorConfig, editorRegistry);
			}
			else if (((ITypeViewer)propertyView).getEditorType() == TypeEditorType.ExternalIntent)
			{
				frameView = new PropertyEditorFrameExternalIntent(context, objectEditorConfig, editorRegistry);
			}
			else
			{
				frameView = new PropertyEditorFrame(context, objectEditorConfig);
			}
			
			if(itemCreationCallback != null)
			{
				itemCreationCallback.OnFrameCreated(frameView);
			}
			
			frameView.setObjectEditor(objectEditor);
			convertView = frameView;
			
		}
		else
		{			
			if(itemCreationCallback != null)
			{
				itemCreationCallback.OnFrameUnInitialized(convertView);
			}
			
			((PropertyEditorFrame)convertView).Clear();
		}

		convertView.setTag(getChildTag(groupPosition, childPosition));
		((PropertyEditorFrame)convertView).setProperty(child);
		if(((ITypeViewer)propertyView).getEditorType() == TypeEditorType.ExternalDialog)
		{
			((PropertyEditorFrameExternalDialog)convertView).setPropertyView(propertyView);
			((PropertyEditorFrameExternalDialog)convertView).addEventWatcher(objectEditor);
		}
		else if (((ITypeViewer)propertyView).getEditorType() == TypeEditorType.ExternalIntent)
		{
			((PropertyEditorFrameExternalIntent)convertView).setPropertyView(propertyView);
		}
		else
		{
			((PropertyEditorFrame)convertView).setPropertyView(propertyView);
		}
		
		if(itemCreationCallback != null)
		{
			itemCreationCallback.OnFrameInitialized(convertView, child);
		}

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		ArrayList<PropertyProxy> propList = categories.get(groupPosition).getProperties();

		int groupSize = propList.size();
		return groupSize;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return categories.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return categories.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		PropertyCategory category = (PropertyCategory) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inf.inflate(R.layout.expandlist_group_item, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.tvGroup);
		tv.setText(category.getName());

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
		
	@Override
	public int getChildTypeCount() {
	    return 3;
	}
	
	@Override
	public int getChildType(int groupPosition, int childPosition)
	{
		PropertyProxy child = (PropertyProxy) getChild(groupPosition, childPosition);
		View propertyView = getViewer(child);
		if(((ITypeViewer)propertyView).getEditorType() == TypeEditorType.ExternalDialog)
			return 1;
		if(((ITypeViewer)propertyView).getEditorType() == TypeEditorType.ExternalIntent)
			return 2;
		return 0;
	}
	
	private View getViewer(PropertyProxy prop)
	{
		View propertyView = null;
		if(!propertyViewerCache.containsKey(prop))
		{
			propertyView = ObjectFactory.getTypeViewerForPoperty(prop, viewerRegistry, context);
		}
		else
		{
			propertyView = propertyViewerCache.get(prop);
		}
		return propertyView;
	}

}
