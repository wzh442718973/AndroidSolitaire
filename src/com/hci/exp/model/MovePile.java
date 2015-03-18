package com.hci.exp.model;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Paint;


public class MovePile extends CardPile {
    private int separation;
	public MovePile(float x,float y,int separation) {
		mX = x;
		mY = y;
		this.separation = separation;
		mSize = 0;
		mCards = new ArrayList<Card>();
	}
	
	@Override
	public void setPosition(float mX, float mY) {
		// TODO Auto-generated method stub
		this.mX = mX;
		this.mY = mY;
		for(int i=0;i<mSize;i++){
			mCards.get(i).setPosition(mX, mY+i*this.separation);
		}
	}
	@Override
	public void addCard(Card newCard) {
		// TODO Auto-generated method stub
		if(newCard==null)
			return;
		if(mSize>0){
		 newCard.setPosition(this.mX, this.mY+mSize*separation);
		 mSize++;
		 mCards.add(0,newCard);
		}
		else{
			newCard.setPosition(this.mX, this.mY);
			 mSize++;
			 mCards.add(0,newCard);
		}
	}
	@Override
	public void doDraw(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		for(Card card:mCards){
			card.doDraw(canvas, paint);
		}
	}
	
}
