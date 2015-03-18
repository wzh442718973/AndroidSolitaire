package com.hci.exp.control;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.hci.exp.R;
import com.hci.exp.control.CardPictureGenerator.CardStyle;
import com.hci.exp.model.Card.CardLand;

public class CardPictureGenerator2 extends CardPictureGenerator {
	
	public CardPictureGenerator2(){
		cardStyle = CardStyle.style1;
	}
	@Override
	public Bitmap getCardPiture(int value, CardLand land, Resources res) {
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
				  return R.raw.h1;
			  else if(land==CardLand.Club)
				  return R.raw.c1;
			  else if(land==CardLand.Spade)
				  return R.raw.s1;
			  else if(land==CardLand.Diamond)
				  return R.raw.d1;
			break;
		  case 2:
			  if(land==CardLand.Heart)
				  return R.raw.h2;
			  else if(land==CardLand.Club)
				  return R.raw.c2;
			  else if(land==CardLand.Spade)
				  return R.raw.s2;
			  else if(land==CardLand.Diamond)
				  return R.raw.d2;
			  break;
		  case 3:
			  if(land==CardLand.Heart)
				  return R.raw.h3;
			  else if(land==CardLand.Club)
				  return R.raw.c3;
			  else if(land==CardLand.Spade)
				  return R.raw.s3;
			  else if(land==CardLand.Diamond)
				  return R.raw.d3;
			break;
		  case 4:
			  if(land==CardLand.Heart)
				  return R.raw.h4;
			  else if(land==CardLand.Club)
				  return R.raw.c4;
			  else if(land==CardLand.Spade)
				  return R.raw.s4;
			  else if(land==CardLand.Diamond)
				  return R.raw.d4;
				break;
		  case 5:
			  if(land==CardLand.Heart)
				  return R.raw.h5;
			  else if(land==CardLand.Club)
				  return R.raw.c5;
			  else if(land==CardLand.Spade)
				  return R.raw.s5;
			  else if(land==CardLand.Diamond)
				  return R.raw.d5;
				break;
		  case 6:
			  if(land==CardLand.Heart)
				  return R.raw.h6;
			  else if(land==CardLand.Club)
				  return R.raw.c6;
			  else if(land==CardLand.Spade)
				  return R.raw.s6;
			  else if(land==CardLand.Diamond)
				  return R.raw.d6;
				break;
		  case 7:
			  if(land==CardLand.Heart)
				  return R.raw.h7;
			  else if(land==CardLand.Club)
				  return R.raw.c7;
			  else if(land==CardLand.Spade)
				  return R.raw.s7;
			  else if(land==CardLand.Diamond)
				  return R.raw.d7;
				break;
		  case 8:
			  if(land==CardLand.Heart)
				  return R.raw.h8;
			  else if(land==CardLand.Club)
				  return R.raw.c8;
			  else if(land==CardLand.Spade)
				  return R.raw.s8;
			  else if(land==CardLand.Diamond)
				  return R.raw.d8;
				break;
		  case 9:
			  if(land==CardLand.Heart)
				  return R.raw.h9;
			  else if(land==CardLand.Club)
				  return R.raw.c9;
			  else if(land==CardLand.Spade)
				  return R.raw.s9;
			  else if(land==CardLand.Diamond)
				  return R.raw.d9;
				break;
		  case 10:
			  if(land==CardLand.Heart)
				  return R.raw.h10;
			  else if(land==CardLand.Club)
				  return R.raw.c10;
			  else if(land==CardLand.Spade)
				  return R.raw.s10;
			  else if(land==CardLand.Diamond)
				  return R.raw.d10;
				break;
		  case 11:
			  if(land==CardLand.Heart)
				  return R.raw.h11;
			  else if(land==CardLand.Club)
				  return R.raw.c11;
			  else if(land==CardLand.Spade)
				  return R.raw.s11;
			  else if(land==CardLand.Diamond)
				  return R.raw.d11;
				break;
		  case 12:
			  if(land==CardLand.Heart)
				  return R.raw.h12;
			  else if(land==CardLand.Club)
				  return R.raw.c12;
			  else if(land==CardLand.Spade)
				  return R.raw.s12;
			  else if(land==CardLand.Diamond)
				  return R.raw.d12;
				break;
		  case 13:
			  if(land==CardLand.Heart)
				  return R.raw.h13;
			  else if(land==CardLand.Club)
				  return R.raw.c13;
			  else if(land==CardLand.Spade)
				  return R.raw.s13;
			  else if(land==CardLand.Diamond)
				  return R.raw.d13;
				break;
		  default:
			  break;
		}
		return 0;
	}

}
