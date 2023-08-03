package com.hci.exp.control;

import com.hci.exp.model.Card.CardLand;

import android.content.res.Resources;
import android.graphics.Bitmap;

public abstract class CardPictureGenerator {
  public enum CardStyle{style1,style2}
  protected CardStyle cardStyle;
  public abstract Bitmap getCardPiture(int value,CardLand land,Resources res);
  public abstract int getCardPitureId(int value,CardLand land,Resources res);
  
}
