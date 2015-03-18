package com.hci.exp.model;

import android.graphics.Canvas;
import android.graphics.Paint;

public class TipPile extends CardPile {

	public TipPile() {
		
	}
	public void setSize(int mWidth,int mHeight){
		for(Card card:mCards){
			card.setSize(mWidth, mHeight);
		}
	}
	@Override
	public void doDraw(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		
	}
	
}
