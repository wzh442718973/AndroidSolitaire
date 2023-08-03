package com.hci.exp.control;

import java.util.List;

import android.util.Log;

import com.hci.exp.model.Card;
import com.hci.exp.model.CardPile;
import com.hci.exp.model.DiscardPile;
import com.hci.exp.model.SuitPile;
import com.hci.exp.model.TablePile;

public class MoveCards {
   public static void deckToDiscard(CardPile from,CardPile to,boolean isShowThree){
	   Card temp;
	   if(from.isEmpty()){
		   for(int i = to.getmSize();i>0;i--){
			   temp = to.removeCard();
			   temp.setmIsTurned(false);
			   from.addCard(temp);
		   }
		   return;
	   }
	   if(!isShowThree){
		   temp = from.removeCard();
		   temp.setmIsTurned(true);
	       to.addCard(temp);
      }
	   else{
		   if(from.getmSize()>=3)
		   for(int i = 0;i<3;i++){
			   temp = from.removeCard();
			   temp.setmIsTurned(true);
			   to.addCard(temp);
		   }
		   else  
			 for(int i = from.getmSize();i>0;i--){
			   temp = from.removeCard();
			   temp.setmIsTurned(true);
			   to.addCard(temp);
		   }
			   
	   }
   }
//   private static void test(CardPile move,CardPile to,CardPile from){
//
//	   Card temp1 = move.top();
//	   Card temp2 = to.top();
//	  // Log.v("top card",""+temp1+temp2);
//	   if(temp2 == null){
//		  List<Card> list = move.removeCard(0);
//		  for(Card card:list)
//			  to.addCard(card);
//		  list = null;
//	   }
//	   else{
//		 if(to instanceof TablePile){
//		   //Log.v("remove index to tablePile",""+(move.getmSize()-(temp2.getmCardValue()-temp1.getmCardValue())));
//		   List<Card> list = move.removeCard(move.getmSize()-(temp2.getmCardValue()-temp1.getmCardValue()));
//		   for(Card card:list)
//			  to.addCard(card);
//		   list = move.removeCard(0);
//		   for(Card card:list)
//				  from.addCard(card);
//		   if(from instanceof TablePile){
//			   from.turnTopCard();
//		   }
//		   list =null;
//		   }
//	    
//	     else 
//		   if(to instanceof SuitPile){
//             //  Log.v("remove card to suitPile ",""+temp1.getmCardValue()+" "+temp2.getmCardValue());
//			   Card temp = move.removeCard();
//			   to.addCard(temp);
//			   List<Card> list = move.removeCard(0);
//			   for(Card card:list)
//					  from.addCard(card);
//			   if(from instanceof TablePile){
//				   from.turnTopCard();
//			   }
//			   list =null;
//			   
//	     }
//	   }
//   }
   public static void pileToPile(CardPile move,CardPile to,CardPile from){
	   if(from instanceof TablePile){
		   if(to instanceof SuitPile){
			   to.addCard(move.removeCard());
			   List<Card> list = move.removeCard(0);
			   for(Card card:list)
			      from.addCard(card);
			   from.turnTopCard();
			}
		   else if(to instanceof TablePile){
             Card temp1 = move.top();
		     Card temp2 = to.top();
		     // Log.v("top card",""+temp1+temp2);
		     if(temp2 == null){
		       Log.v("test card k",""+123);
			   List<Card> list = move.removeCard(0);
			   for(Card card:list)
				  to.addCard(card);
			   from.turnTopCard();
			   list = null;
		    }
		   else{
			   List<Card> list = move.removeCard(move.getmSize()-(temp2.getmCardValue()-temp1.getmCardValue()));
			   for(Card card:list)
				  to.addCard(card);
			   list = move.removeCard(0);
			   for(Card card:list)
					  from.addCard(card);
			   from.turnTopCard();
			   list =null;
			  
		   }
          }
	   }
	   else if(from instanceof DiscardPile){
		   to.addCard(move.removeCard());
	   }
	   
   }
   public  static void cardsBackToFrom(CardPile move,CardPile from){
	   //Log.v("card come back","begin");
	   List<Card> list = move.removeCard(0);
	   for(Card card:list){
			  from.addCard(card);
	   }
	   list =null;
   }
}
