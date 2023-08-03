package com.hci.exp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hci.exp.R;
import com.hci.exp.control.CardPictureGenerator;
import com.hci.exp.device.Screen;
import com.hci.exp.model.Card.ShowType;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;

public abstract class CardPile extends GameObject {
	protected float mX;
	protected float mY;
	protected RectF mRect;//牌可视范围，即可点位置
	protected int mSize;
	protected List<Card> mCards;
	private float mWidth,mHeight;
	public CardPile (){
		
	}
	@Override
	public void reCycle() {
		// TODO Auto-generated method stub
		if(mCards!=null){
		for(Card card:mCards){
			if(card!=null){
			  card.reCycle();
			}
		}
		mCards.clear();
		mCards = null;
		}
		mRect = null;
	}
	public void clear(){
		mCards.clear();
	}
	public CardPile (float x, float y,Resources res,float width,float height) {
		mX = x;
		mY = y;
        mWidth = width;
        mHeight = height;
        mRect = new RectF(mX,mY,mX+mWidth,mY+mHeight);
        mSize = 0;
		mCards = new ArrayList<Card>();
	}
	public boolean isEmpty(){
		return mSize == 0;
	}
	public abstract void doDraw(Canvas canvas,Paint paint);
	public boolean includes (float x, float y) {
		if (mRect.contains(x, y))
			return true;
		else
			return false;
    }
	public void showNormal(){
		for(Card card:mCards)
			card.setShowType(ShowType.normal);
	}
	public void showTopCardNormal(){
		if(mSize>0)
			mCards.get(mSize-1).setShowType(ShowType.normal);
	}
	public void showTopCardHighlight(){
		if(mSize>0)
			mCards.get(mSize-1).setShowType(ShowType.Highlight);
	}
	public void showHightlightTansparent(int index){
		if(index>=0 && index < mSize)
		showHighlight(index,mSize-1);
		showTrasparent(0, index-1);
	}
	public void showHighlight(int index,int end){
		if(index>=0 && index <= end && end < mSize)
		for(int i = index;i <=end;i++){
			mCards.get(i).setShowType(ShowType.Highlight);
		}
	}
	public void showTrasparent(int index,int end){
		if( index >=0 && index <= end && end <mSize)
		for(int i = index;i <=end;i++ ){
			mCards.get(i).setShowType(ShowType.transparent);
		}
	}
	public void addCard(Card newCard){
		if(newCard==null)
			return;
		newCard.setPosition(this.mX, this.mY);
		mSize++;
		mCards.add(newCard);
	}
	public Card top(){
		if(mSize>0)
		 return mCards.get(mSize-1);
		else
			return null;
	}
	public Card get(int index){
		if(index<mSize && index >=0)
		     return mCards.get(index);
		else
			 return null;
	}
	public Card removeCard() {
		if(mSize > 0){
		  mSize--;
		  return mCards.remove(mSize);
		}
		else
			return null;
	    
	}
	public List<Card> removeCard(int index) {
		List<Card> list = new ArrayList<Card>();
		for(int i=mSize-index;i>0;i--){
			list.add(0,removeCard());
		}
	    return list;
	}
	public void turnTopCard(){
		if(mSize>0)
		if(!top().ismIsTurned()){
			top().setmIsTurned(true);
		}
	}
	public int getViwCount(){
		return 0;
	}
	public void setPosition(float mX,float mY){
		this.mX = mX;
		this.mY = mY;
		mRect.set(mX, mY,mRect.right-mRect.left+mX,mRect.bottom-mRect.top+mY);
		for (Card card : mCards) {
			card.setPosition(mX, mY);
		}
	}
	
	
	public float getmX() {
		return mX;
	}
	public void setmX(float mX) {
		this.mX = mX;
		mRect.set(mX, mRect.top, mRect.right-mRect.left+mX, mRect.bottom);
		for (Card card : mCards) {
			card.setmX(mX);
		}
	}
	public float getmY() {
		return mY;
	}
	public void setmY(float mY) {
		this.mY = mY;
        mRect.set(mRect.left,mY , mRect.right, mRect.bottom-mRect.top+mY);
        for (Card card : mCards) {
			card.setmY(mY);
		}
	}
	
	
	public List<Card> getmCards() {
		return mCards;
	}
	public void setmCards(List<Card> mCards) {
		this.mCards = mCards;
	}
	
	public int getmSize() {
		return mSize;
	}
	
	@Override
	public void loadProperties(HashMap<String, Object> properties) {
		// TODO Auto-generated method stub

	}
	public void setCardStyle(CardPictureGenerator cf,Resources res){
		for(Card card:mCards){
			card.setCardStyle(cf,res);
		}
	}
}
