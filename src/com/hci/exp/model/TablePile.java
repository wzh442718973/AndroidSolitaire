package com.hci.exp.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;

import com.hci.exp.R;
import com.hci.exp.device.Screen;
import com.hci.exp.model.Card;
public class TablePile extends CardPile {
	private int backCardCount = 0;
    private int backSeparation = 0;
    private int frontSeparation = 0;
    private Bitmap baseBitmap;
	public TablePile(float x, float y,  Resources res,int backSeparation,int frontSeparation,float width,float height) {
		super(x, y,res,width,height);
		backCardCount = 0;
		this.backSeparation = backSeparation;
		this.frontSeparation = frontSeparation;
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
		for(Card card:mCards){
			card.doDraw(canvas, paint);
		}
	}
	
	@Override
	public void turnTopCard() {
		// TODO Auto-generated method stub
		
			Card temp = top();
			if(temp!=null)
			if(!temp.ismIsTurned()){
				top().setmIsTurned(true);
				backCardCount--;
				this.mRect.set(this.mRect.left, this.mY+(this.backCardCount*this.backSeparation), temp.getmWidth()+this.mX, temp.getmY()+temp.getmHeight());
			}
		
	}
	
	
	@Override
	public int getViwCount() {
		// TODO Auto-generated method stub
		return mSize - backCardCount;
	}
	@Override
	public void addCard(Card newCard) {
		// TODO Auto-generated method stub
		if(newCard == null)
			return;
		if(newCard.ismIsTurned()){
		    if(mSize!=0){
			     newCard.setPosition(this.mX, this.mY+this.frontSeparation*(this.mSize-this.backCardCount)+this.backCardCount*this.backSeparation);
			    // this.setmHeight(this.mHeight + this.frontSeparation);
			     this.mRect.set(this.mRect.left, this.mY+(this.backCardCount*this.backSeparation), newCard.getmWidth()+this.mX, newCard.getmY()+newCard.getmHeight());
	         }
		    else{
		    	newCard.setPosition(this.mX,this.mY);
		    	//this.setmHeight(newCard.getmHeight());
		    	this.mRect.set(this.mX, this.mY, newCard.getmWidth()+this.mX, this.mY+newCard.getmHeight());
		    }
			newCard.setmIsVisible(true);
		    mCards.add(newCard);
            mSize++;
		}
		else{
			if(mSize!=0){
			 newCard.setPosition(this.mX, this.mY+(this.backCardCount)*this.backSeparation);
			// this.setmHeight(this.mHeight + this.backSeparation);
			 float y = this.mY+(this.backCardCount)*this.backSeparation;
			 this.mRect.set(this.mRect.left, y, newCard.getmWidth()+this.mX, y);
			}
			else {
             newCard.setPosition(this.mX,this.mY);
             this.mRect.set(this.mX, this.mY, newCard.getmWidth()+this.mX, this.mY+newCard.getmHeight());
			}
			newCard.setmIsVisible(true);
			mCards.add(newCard);
		    mSize++;
		    backCardCount++;
		}
		Card top = top();
		if(top!=null){
			float bottom = top.getmY()+top.getmHeight();
			if(bottom>Screen.height){
				int turnCount = mSize-backCardCount;
				int changeCount = turnCount-2;
				float beginPos = this.mY + this.backCardCount*this.backSeparation+this.frontSeparation;
				float sepration = (Screen.height - beginPos-top.getmHeight()-10.0f)/(mSize-backCardCount-2);
				for(int i = 1;i<=changeCount;i++){
					mCards.get(backCardCount+1+i).setPosition(mX,beginPos+i*sepration);
				}
				mRect.set(mX,beginPos-frontSeparation,mX+top.getmWidth(),top.getmY()+top.getmHeight());
			}
		}
		
	}
	@Override
	public Card removeCard() {
		// TODO Auto-generated method stub
		if(mSize > 0){
		  mSize--;
		  Card temp = mCards.remove(mSize);
		  if(!temp.ismIsTurned())
			    backCardCount--;
           if(mSize==0)
				backCardCount = 0;
		  //this.setmHeight(this.mHeight - this.backSeparation);
		  this.mRect.set(this.mX, this.mY, temp.getmWidth()+this.mX, this.mY+temp.getmHeight());
		  return temp;
		}
		else
			return null;
		
	}
	
	
	
	
	@Override
	public void setmY(float mY) {
		// TODO Auto-generated method stub
		super.setmY(mY);
	}
	
	@Override
	public void setmX(float mX) {
		// TODO Auto-generated method stub
		super.setmX(mX);
	}
	@Override
	public void setPosition(float mX, float mY) {
		// TODO Auto-generated method stub
		super.setPosition(mX, mY);
	}
	public int getBackCardCount() {
		return backCardCount;
	}
	
	
	
    
}
