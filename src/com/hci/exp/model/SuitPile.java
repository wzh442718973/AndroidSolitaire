package com.hci.exp.model;

import com.hci.exp.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

public class SuitPile extends CardPile {
	 private Bitmap baseBitmap;
	public SuitPile(float x, float y,  Resources res,float width,float height) {
		super(x, y, res,width,height);
		// TODO Auto-generated constructor stub
		baseBitmap  = ((BitmapDrawable)res.getDrawable(R.raw.empty)).getBitmap();
		float scaleW = width/baseBitmap.getWidth();
		float scaleH = height/baseBitmap.getHeight();
		Matrix mMatrix = new Matrix();
		mMatrix.postScale(scaleW, scaleH);
		baseBitmap =  Bitmap.createBitmap(baseBitmap, 0, 0, baseBitmap.getWidth(),baseBitmap.getHeight(), mMatrix, true);
		
	}

	@Override
	public void doDraw(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(baseBitmap,mX,mY, null);
		if(mSize>0){
			Card temp = mCards.get(mSize-1);
			temp.setmIsTurned(true);
			temp.setmIsVisible(true);
			temp.doDraw(canvas, paint);
		}
		
	}

}
