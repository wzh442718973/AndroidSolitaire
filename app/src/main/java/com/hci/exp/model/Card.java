package com.hci.exp.model;

import java.util.HashMap;

import com.hci.exp.R;
import com.hci.exp.control.CardPictureGenerator;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

public class Card extends GameObject {
	public enum CardLand {
		Club, Diamond, Spade, Heart
	}
	public enum ShowType{normal,Highlight,transparent}
	public static Bitmap mBackBitmap;
    private int mCardValue;
    private CardLand mCardLand;
    public boolean mIsBlack;
	public float mWidth;
	public float mHeight;
	private float mX;
	private float mY;
	private boolean mIsVisible;
	private boolean mIsTurned;
    private Bitmap mBitmap;
    private ShowType showType;
    public Card(int cardValue, CardLand cardLand,float width,float height,Resources res,CardPictureGenerator cf) {
    	Matrix mMatrix = new Matrix();
    	if(mBackBitmap == null){
    		mBackBitmap = ((BitmapDrawable)res.getDrawable(R.raw.cardback)).getBitmap();
    		float scaleW = width/mBackBitmap.getWidth();
    		float scaleH = height/mBackBitmap.getHeight();
    		mMatrix.reset();
    		mMatrix.postScale(scaleW, scaleH);
    		mBackBitmap =  Bitmap.createBitmap(mBackBitmap, 0, 0, mBackBitmap.getWidth(),mBackBitmap.getHeight(), mMatrix, true);
    		
    	}
		mCardValue = cardValue;
		mCardLand = cardLand;
        if (mCardLand == Card.CardLand.Spade
				|| mCardLand == Card.CardLand.Club)
        	mIsBlack = true;
		else
			mIsBlack = false;
        mWidth = width;
        mHeight = height;
		mIsVisible = true;
		mIsTurned = false;
		mBitmap = cf.getCardPiture(cardValue, cardLand, res);
		float scaleW = width/mBitmap.getWidth();
		float scaleH = height/mBitmap.getHeight();
		mMatrix.reset();
		mMatrix.postScale(scaleW, scaleH);
		mBitmap =  Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),mBitmap.getHeight(), mMatrix, true);
		showType = ShowType.normal;
	}
     
     public void doDraw(Canvas canvas,Paint paint){
    	 if (mIsVisible) {
 			if (mIsTurned){
 				if(showType==ShowType.normal){
 				  canvas.drawBitmap(mBitmap, mX, mY, null);
 				}
 				else if(showType==ShowType.transparent){
 					if(paint == null)
 						paint = new Paint();
 					paint.reset();
 					paint.setAlpha(70);
 					canvas.drawBitmap(mBitmap, mX, mY, paint);
 				}
 				else if(showType==ShowType.Highlight){
 					if(paint == null)
 						paint = new Paint();
 					paint.reset();
 					canvas.drawBitmap(mBitmap, mX, mY, paint);
 					paint.reset();
 					paint.setColor(Color.YELLOW);//和下一句不能颠倒
 					paint.setAlpha(80);
 					canvas.drawRect(mX, mY, mX+mBitmap.getWidth(), mY+mBitmap.getHeight(), paint);
 				}
 			}
 			else{
 				
 				canvas.drawBitmap(mBackBitmap,  mX, mY, null);
 			}
          }
     }
     public void setSize(int width,int height){
    	 mWidth = width;
    	 mHeight = height;
     }
    public void setPosition(float x, float y) {
		mX = x;
		mY = y;
		
	}
    
    

    @Override
	public void loadProperties(HashMap<String, Object> properties) {
		// TODO Auto-generated method stub

	}

	public static Bitmap getmBackBitmap() {
		return mBackBitmap;
	}

	public static void setmBackBitmap(Bitmap mBackBitmap) {
		Card.mBackBitmap = mBackBitmap;
	}

	

	public int getmCardValue() {
		return mCardValue;
	}

	

	public CardLand getmCardLand() {
		return mCardLand;
	}

	

	public float getmWidth() {
		return mWidth;
	}

	

	public float getmHeight() {
		return mHeight;
	}

	

	public float getmX() {
		return mX;
	}

	

	public void setmWidth(int mWidth) {
		this.mWidth = mWidth;
	}

	public void setmHeight(int mHeight) {
		this.mHeight = mHeight;
	}

	public void setmX(float mX) {
		this.mX = mX;
	}

	public void setmY(float mY) {
		this.mY = mY;
	}

	public float getmY() {
		return mY;
	}

	

	

	public Bitmap getmBitmap() {
		return mBitmap;
	}

	public void setmBitmap(Bitmap mBitmap) {
		this.mBitmap = mBitmap;
	}

	public boolean ismIsVisible() {
		return mIsVisible;
	}

	public void setmIsVisible(boolean mIsVisible) {
		this.mIsVisible = mIsVisible;
	}

	public boolean ismIsTurned() {
		return mIsTurned;
	}

	public void setmIsTurned(boolean mIsTurned) {
		this.mIsTurned = mIsTurned;
	}

	

	public ShowType getShowType() {
		return showType;
	}

	public void setShowType(ShowType showType) {
		this.showType = showType;
	}

	@Override
	public void reCycle() {
		// TODO Auto-generated method stub
    	mBackBitmap = null;
    	mBitmap = null;
	}
    
	public void setCardStyle(CardPictureGenerator cf,Resources res){
		mBitmap = cf.getCardPiture(mCardValue, mCardLand, res);
		float scaleW = mWidth/mBitmap.getWidth();
		float scaleH = mHeight/mBitmap.getHeight();
		Matrix mMatrix = new Matrix();
		mMatrix.postScale(scaleW, scaleH);
		mBitmap =  Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(),mBitmap.getHeight(), mMatrix, true);
	}
}
