package be.trojkasoftware.android.typeeditor;

import be.trojkasoftware.android.objecteditor.PropertyProxy;

import be.trojkasoftware.android.objecteditor.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ColorEditor extends LinearLayout implements OnSeekBarChangeListener {

	private SeekBar editRedChannel;
	private SeekBar editGreenChannel;
	private SeekBar editBlueChannel;
	private LinearLayout layColorPreview;

	public ColorEditor(Context context) {
		super(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.typeeditor_color, this);
        
        editRedChannel = (SeekBar)this.findViewById(R.id.seekBarRed);
        editGreenChannel = (SeekBar)this.findViewById(R.id.seekBarGreen);
        editBlueChannel = (SeekBar)this.findViewById(R.id.seekBarBlue);
        
        editRedChannel.setMax(255);
        editGreenChannel.setMax(255);
        editBlueChannel.setMax(255);
        
        editRedChannel.setOnSeekBarChangeListener(this);
        editGreenChannel.setOnSeekBarChangeListener(this);
        editBlueChannel.setOnSeekBarChangeListener(this);
        
        layColorPreview = (LinearLayout)this.findViewById(R.id.llColorPreview);
        
	}

	public void setValue(int colorValue) {
		layColorPreview.setBackgroundColor(colorValue);
		editRedChannel.setProgress(Color.red(colorValue));
		editGreenChannel.setProgress(Color.green(colorValue));
		editBlueChannel.setProgress(Color.blue(colorValue));
	}
	
	public int getValue()
	{
		return Color.rgb(
				ColorEditor.this.getRedChannel(), 
				ColorEditor.this.getGreenChannel(), 
				ColorEditor.this.getBlueChannel()
				);
	}
	
	private int getRedChannel()
	{
		return editRedChannel.getProgress();
	}
	
	private int getGreenChannel()
	{
		return editGreenChannel.getProgress();
	}
	
	private int getBlueChannel()
	{
		return editBlueChannel.getProgress();
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		layColorPreview.setBackgroundColor(getValue());
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		
	}

}
