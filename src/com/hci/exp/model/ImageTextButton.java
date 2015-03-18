package com.hci.exp.model;

import java.util.HashMap;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ImageTextButton extends ImageButton {
	public enum TextPosition{above,inTop,inMiddile,inBottom,bellow,right,left};
	private TextPosition textPositon;
    private float textX;
    private float textY;
    private float textSize;
    private int textColor;
	public ImageTextButton(String name, Resources res, int bmpResId, float x,float y, float width, float height,float textSize,int textColor,TextPosition tp) {
		super(name, res, bmpResId, x, y, width, height);
		// TODO Auto-generated constructor stub
		textPositon = tp;
		Paint paint = new Paint();
		paint.setTextSize(textSize);
		float tw = paint.measureText(name);
		if(textPositon==TextPosition.bellow){
		this.textX = x+(width-tw)/2;
		this.textY = y+height+textSize;
		}
		else if(textPositon==TextPosition.left){
			this.textX = x-tw;
			this.textY = y+height-(height-textSize)/2.0f;
		}
		this.textSize = textSize;
		this.textColor = textColor;
		
	}
	@Override
	public void doDraw(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.doDraw(canvas, paint);
		if(paint==null)
			paint = new Paint();
		paint.reset();
		paint.setColor(this.textColor);
		paint.setTextSize(this.textSize);
		canvas.drawText(super.name, textX, textY, paint);
	}
	@Override
	public void loadProperties(HashMap<String, Object> properties) {
		// TODO Auto-generated method stub
		super.loadProperties(properties);
	}
    
}
