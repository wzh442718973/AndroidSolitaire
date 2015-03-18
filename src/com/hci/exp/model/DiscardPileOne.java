package com.hci.exp.model;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

public class DiscardPileOne extends DiscardPile {
    
	public DiscardPileOne(float x, float y, Resources res,float width,float height) {
		super(x, y, res,width,height);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doDraw(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		super.doDraw(canvas, paint);
		if(mSize>0){
			Card temp = mCards.get(mSize-1);
			temp.setmIsTurned(true);
			temp.setmIsVisible(true);
			temp.doDraw(canvas, paint);
		}
	}

	@Override
	public void addCard(Card newCard) {
		// TODO Auto-generated method stub
		if(newCard==null)
			return;
		newCard.setPosition(this.mX, this.mY);
		newCard.setmIsTurned(true);
		mSize++;
		mCards.add(newCard);
	}
    
	
	

}
