package be.trojkasoftware.android.typeeditor;


public interface IExternalEditorEvents {
	void EditingStarted(String propertyName);
	void EditingFinished(String propertyName, Object newValue);
}
