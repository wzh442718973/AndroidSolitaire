package com.hci.exp.control;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.hci.exp.R;
import com.hci.exp.control.CardPictureGenerator.CardStyle;
import com.hci.exp.model.Card.CardLand;

public class CardPictureGenerator1 extends CardPictureGenerator {
	public CardPictureGenerator1(){
		cardStyle = CardStyle.style1;
	}
	@Override
	public Bitmap getCardPiture(int value, CardLand land,Resources res) {
		// TODO Auto-generated method stub
		Bitmap bmp =((BitmapDrawable)res.getDrawable(getCardPitureId(value, land, res))).getBitmap();
		return bmp;
	}

	@Override
	public int getCardPitureId(int value, CardLand land, Resources res) {
		// TODO Auto-generated method stub
		switch(value){
		  case 1:
			  if(land==CardLand.Heart)
				  return R.raw.heart1;
			  else if(land==CardLand.Club)
				  return R.raw.club1;
			  else if(land==CardLand.Spade)
				  return R.raw.spade1;
			  else if(land==CardLand.Diamond)
				  return R.raw.diamond1;
			break;
		  case 2:
			  if(land==CardLand.Heart)
				  return R.raw.heart2;
			  else if(land==CardLand.Club)
				  return R.raw.club2;
			  else if(land==CardLand.Spade)
				  return R.raw.spade2;
			  else if(land==CardLand.Diamond)
				  return R.raw.diamond2;
			  break;
		  case 3:
			  if(land==CardLand.Heart)
				  return R.raw.heart3;
			  else if(land==CardLand.Club)
				  return R.raw.club3;
			  else if(land==CardLand.Spade)
				  return R.raw.spade3;
			  else if(land==CardLand.Diamond)
				  return R.raw.diamond3;
			break;
		  case 4:
			  if(land==CardLand.Heart)
				  return R.raw.heart4;
			  else if(land==CardLand.Club)
				  return R.raw.club4;
			  else if(land==CardLand.Spade)
				  return R.raw.spade4;
			  else if(land==CardLand.Diamond)
				  return R.raw.diamond4;
				break;
		  case 5:
			  if(land==CardLand.Heart)
				  return R.raw.heart5;
			  else if(land==CardLand.Club)
				  return R.raw.club5;
			  else if(land==CardLand.Spade)
				  return R.raw.spade5;
			  else if(land==CardLand.Diamond)
				  return R.raw.diamond5;
				break;
		  case 6:
			  if(land==CardLand.Heart)
				  return R.raw.heart6;
			  else if(land==CardLand.Club)
				  return R.raw.club6;
			  else if(land==CardLand.Spade)
				  return R.raw.spade6;
			  else if(land==CardLand.Diamond)
				  return R.raw.diamond6;
				break;
		  case 7:
			  if(land==CardLand.Heart)
				  return R.raw.heart7;
			  else if(land==CardLand.Club)
				  return R.raw.club7;
			  else if(land==CardLand.Spade)
				  return R.raw.spade7;
			  else if(land==CardLand.Diamond)
				  return R.raw.diamond7;
				break;
		  case 8:
			  if(land==CardLand.Heart)
				  return R.raw.heart8;
			  else if(land==CardLand.Club)
				  return R.raw.club8;
			  else if(land==CardLand.Spade)
				  return R.raw.spade8;
			  else if(land==CardLand.Diamond)
				  return R.raw.diamond8;
				break;
		  case 9:
			  if(land==CardLand.Heart)
				  return R.raw.heart9;
			  else if(land==CardLand.Club)
				  return R.raw.club9;
			  else if(land==CardLand.Spade)
				  return R.raw.spade9;
			  else if(land==CardLand.Diamond)
				  return R.raw.diamond9;
				break;
		  case 10:
			  if(land==CardLand.Heart)
				  return R.raw.heart10;
			  else if(land==CardLand.Club)
				  return R.raw.club10;
			  else if(land==CardLand.Spade)
				  return R.raw.spade10;
			  else if(land==CardLand.Diamond)
				  return R.raw.diamond10;
				break;
		  case 11:
			  if(land==CardLand.Heart)
				  return R.raw.heart11;
			  else if(land==CardLand.Club)
				  return R.raw.club11;
			  else if(land==CardLand.Spade)
				  return R.raw.spade11;
			  else if(land==CardLand.Diamond)
				  return R.raw.diamond11;
				break;
		  case 12:
			  if(land==CardLand.Heart)
				  return R.raw.heart12;
			  else if(land==CardLand.Club)
				  return R.raw.club12;
			  else if(land==CardLand.Spade)
				  return R.raw.spade12;
			  else if(land==CardLand.Diamond)
				  return R.raw.diamond12;
				break;
		  case 13:
			  if(land==CardLand.Heart)
				  return R.raw.heart13;
			  else if(land==CardLand.Club)
				  return R.raw.club13;
			  else if(land==CardLand.Spade)
				  return R.raw.spade13;
			  else if(land==CardLand.Diamond)
				  return R.raw.diamond13;
				break;
		  default:
			  break;
		}
		return 0;
	}

}
