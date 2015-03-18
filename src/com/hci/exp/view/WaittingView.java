package com.hci.exp.view;

import com.hci.exp.device.Screen;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

public class WaittingView extends GameView {
    private String showText;
	public WaittingView(Context context,String text) {
		super(context);
		showText = text;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		Paint paint = new Paint();
		paint.setColor(Color.GRAY);
		paint.setTextSize(Screen.height/15.0f);
		paint.setTypeface(Typeface.SANS_SERIF);
		float tw = paint.measureText(showText);
		if(tw<Screen.width)
		   canvas.drawText(showText, (Screen.width-tw)/2.0f, Screen.height/2.0f, paint);
		//需加强处理
//		int lineCount = (int)(tw /Screen.width);
//		for(int i=0;i<lineCount;i++){
//			canvas.drawText("", x, y, paint)
//		}
	}

	@Override
	public void reCycle() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refurbish() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveGame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
