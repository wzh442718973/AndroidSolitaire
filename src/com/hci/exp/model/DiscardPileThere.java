package com.hci.exp.model;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

public class DiscardPileThere extends DiscardPile {
    private float separation;
    private int showCount;
	public DiscardPileThere(float x, float y, float width, float height, Resources res,float sep) {
		super(x, y, res,width,height);
		separation = sep;
		showCount = 0;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void doDraw(Canvas canvas, Paint paint) {
		// TODO Auto-generated method stub
		canvas.drawBitmap(baseBitmap,mX,mY,paint);
		switch (showCount) {
		  case 0:
			  break;
		  case 1:
			 Card card= top();
			 if(card==null)
				 break;
			 card.doDraw(canvas, paint);
			 break;
		  case 2:
			  for(int i = 2;i >=1 ;i--){
					Card temp = mCards.get(mSize-i);
					if(temp!=null)
						temp.doDraw(canvas, paint);
				}
				 break;
		  default:
			  for(int i = 3;i >= 1;i--){
					Card temp = mCards.get(mSize-i);
					if(temp!=null)
						temp.doDraw(canvas, paint);
				}
			break;
		}
	}
	@Override
	public void addCard(Card newCard) {
		// TODO Auto-generated method stub
		if(newCard==null)
			return;
		newCard.setmIsVisible(true);
		newCard.setmIsTurned(true);
		mSize++;
		mCards.add(newCard);
		for(Card card:mCards)
			card.setPosition(mX, mY);
		switch(mSize){
		 case 0:
			  showCount = 0;
			  mRect.right =mX+mRect.right-mRect.left;
			  mRect.left = mX;
			  break;
		 case 1:
			  showCount = 1;
			  mRect.right =mX+mRect.right-mRect.left;
			  mRect.left = mX;
					Card temps = mCards.get(0);
					temps.setmX(mX);
			  break;
		  case 2:
			  showCount = 2;
              mRect.right =mX+separation+mRect.right-mRect.left;
			  mRect.left = mX+separation;
			//  mWidth = mRect.right-mX;
			  for(int i = 1;i <=2 ;i++){
					Card temp = mCards.get(mSize-i);
					temp.setmX(mX+separation*(2-i));
				}
			  break;
		  default:
			  showCount = 3;
			  mRect.right =mX+2*separation+mRect.right-mRect.left;
			  mRect.left = mX+2*separation;
			 // mWidth = mRect.right-mX;
			  for(int i = 1;i <= 3;i++){
					Card temp = mCards.get(mSize-i);
					temp.setmX(mX+separation*(3-i));
				}
			  break;
		}
	}
	@Override
	public Card removeCard() {
		// TODO Auto-generated method stub
		Card card = super.removeCard();
		if(showCount>1)
          showCount--;
		if(mSize==0)
			showCount = 0;
		switch(showCount){
		  case 0:
			  mRect.right =mX+mRect.right-mRect.left;
			  mRect.left = mX;
			  break;
		  case 1:
			   mRect.right =mX+mRect.right-mRect.left;
			   mRect.left = mX;
			  break;
		  case 2:
            mRect.right =mX+separation+mRect.right-mRect.left;
			  mRect.left =mX+separation;
			  break;
		  case 3:
			  mRect.right =mX+2*separation+mRect.right-mRect.left;
			  mRect.left = mX+2*separation;
			  break;
		}
		return card;
	}
   
}
