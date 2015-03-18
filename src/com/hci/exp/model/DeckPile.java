package com.hci.exp.model;

import com.hci.exp.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

public class DeckPile extends CardPile {
	 private Bitmap baseBitmap;
	public DeckPile(float x, float y, Resources res,float width,float height) {
		super(x, y, res,width,height);
		// TODO Auto-generated constructor stub
		baseBitmap  = ((BitmapDrawable)res.getDrawable(R.raw.deckbackground)).getBitmap();
		float scaleW = width/baseBitmap.getWidth();
		float scaleH = height/baseBitmap.getHeight();
		Matrix mMatrix = new Matrix();
		mMatrix.postScale(scaleW, scaleH);
		baseBitmap =  Bitmap.createBitmap(baseBitmap, 0, 0, baseBitmap.getWidth(),baseBitmap.getHeight(), mMatrix, true);
		
	}

	@Override
	public void addCard(Card newCard) {
		// TODO Auto-generated method stub
		
		newCard.setPosition(this.mX, this.mY);
		newCard.setmIsTurned(false);
		mSize++;
		mCards.add(newCard);
	}

	@Override
	public void doDraw(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(baseBitmap,mX,mY, null);
		Card top =top();
		if(top!=null)
			top.doDraw(canvas, paint);
	}
    
	

}
