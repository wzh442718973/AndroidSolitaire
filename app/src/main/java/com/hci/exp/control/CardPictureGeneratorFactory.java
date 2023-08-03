package com.hci.exp.control;

import com.hci.exp.control.CardPictureGenerator.CardStyle;

public class CardPictureGeneratorFactory {
   public static CardPictureGenerator getCardPictureGenerator(CardStyle cs){
	   if(cs==CardStyle.style1)
		   return new CardPictureGenerator1();
	   else if(cs==CardStyle.style2)
		   return new CardPictureGenerator2();
	   return null;
   }
}
