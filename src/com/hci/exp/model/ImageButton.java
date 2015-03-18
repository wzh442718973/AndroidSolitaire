package com.hci.exp.model;

import java.util.HashMap;

import com.hci.exp.R;
import com.hci.exp.device.Screen;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;

public class ImageButton extends GameObject {
	protected String name;
	protected float mX,mY;
	protected float mWidth,mHeight;
	protected Bitmap mBackground;
	protected boolean isHighlight;
    public ImageButton(String name,Resources res, int bmpResId,float x,float y,float width,float height){
    	this.name = name;
    	this.mBackground = ((BitmapDrawable)res.getDrawable(bmpResId)).getBitmap();
    	mX = x;
    	mY = y;
    	mWidth =  width;     
        mHeight = height;
    	float scaleW = (float)mWidth/mBackground.getWidth();
        float scaleH = (float)mHeight/mBackground.getHeight();
        Matrix mMatrix = new Matrix();
        mMatrix.reset();
   	    mMatrix.postScale(scaleW, scaleH);
        mBackground = Bitmap.createBitmap(mBackground, 0, 0, mBackground.getWidth(),mBackground.getHeight(), mMatrix, true);
        isHighlight = false;	 
    		
    }
    public boolean isInclude(float x,float y){
    	if(mX<x && x<mX+mWidth && mY<y && y<mY+mHeight)
    		return true;
    	else
    		return false;
    }
	@Override
	public void loadProperties(HashMap<String, Object> properties) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doDraw(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
      canvas.drawBitmap(mBackground,mX,mY,null);
      
	}
	@Override
	public void reCycle() {
		// TODO Auto-generated method stub
		name = null;
		mBackground = null;
	}
	
	public void setmBackground(Bitmap newBmp) {
		this.mBackground = newBmp;
		float scaleW = (float)mWidth/mBackground.getWidth();
        float scaleH = (float)mHeight/mBackground.getHeight();
        Matrix mMatrix = new Matrix();
   	    mMatrix.postScale(scaleW, scaleH);
        mBackground = Bitmap.createBitmap(mBackground, 0, 0, mBackground.getWidth(),mBackground.getHeight(), mMatrix, true);
	}
	public boolean isHighlight() {
		return isHighlight;
	}
	public void setHighlight(boolean isHighlight) {
		this.isHighlight = isHighlight;
	}
	public Bitmap getmBackground() {
		return mBackground;
	}
    
}
