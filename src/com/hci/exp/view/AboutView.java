package com.hci.exp.view;

import com.hci.exp.R;
import com.hci.exp.device.Screen;
import com.hci.exp.model.ImageButton;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class AboutView extends GameView {
	private ControlView controlView;
	private Bitmap background;
	private ImageButton logo;
	private ImageButton back;
	private Paint paint;
	private RectF textRectF;
	public AboutView(Context context,ControlView ct) {
		super(context);
		controlView = ct;
		Matrix mMatrix = new Matrix();
		float size;
		float x,y;
		//background
		background = ((BitmapDrawable)getResources().getDrawable(R.raw.aboutbackground)).getBitmap();
		mMatrix.postScale((float)Screen.width/background.getWidth(),(float)Screen.height/background.getHeight());
		background =  Bitmap.createBitmap(background, 0, 0, background.getWidth(),background.getHeight(), mMatrix, true);
		//logo
		size = Screen.height/3.0f;
		x = (Screen.width/2.0f-size)/2.0f;
		if(x < 0)
			x =0;
		y = Screen.height/2.0f - size/2.0f;
		logo = new ImageButton("logo", getResources(), R.raw.logo,x, y,size, size);
		back =  new ImageButton("����",getResources(),R.raw.back,10,Screen.height-Screen.height/5.0f,Screen.height/5.0f,Screen.height/5.0f);
	    paint = new Paint();
	    
	    textRectF = new RectF(Screen.width/2.0f, Screen.height/6.0f, Screen.width-1, 5.0f*Screen.height/6.0f);
		// TODO Auto-generated constructor stub
	}
    
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}



	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			 MainMenuView gs = new MainMenuView(getContext(),controlView);
			 controlView.setCurrentView(gs);
				 reCycle();
				 return true;
		}
	 return true;
	}



	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int action = event.getAction();
		float x =  event.getX();
		float y =  event.getY();
		if (action == MotionEvent.ACTION_UP) {
			if(back.isInclude(x, y)){
				 MainMenuView gs = new MainMenuView(getContext(),controlView);
    			 controlView.setCurrentView(gs);
 				 reCycle();
 				 return true;
			}
		}
		return true;
	}



	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.drawBitmap(background, 0, 0, null);
		logo.doDraw(canvas, null);
		back.doDraw(canvas, null);
		paint.reset();
		paint.setColor(Color.BLACK);
		paint.setAlpha(100);
		canvas.drawRoundRect(textRectF, 10, 10, paint);
		paint.reset();
		paint.setTextSize(Screen.height/15.0f);
		paint.setColor(Color.GRAY);
		paint.setTypeface(Typeface.SANS_SERIF);
		float tw = paint.measureText("ֽ����Ϸ");
		float x = 3.0f*Screen.width/4.0f-(Screen.width/2.0f-tw)/2.0f;
		canvas.drawText("ֽ����Ϸ",x, Screen.height*2.0f/6.0f, paint);
		 tw = paint.measureText("�汾1.0.0");
		 x = 3.0f*Screen.width/4.0f-(Screen.width/2.0f-tw)/2.0f;
		canvas.drawText("�汾1.0.0",x, Screen.height*3.0f/6.0f, paint);
		 tw = paint.measureText("��ϵ�ң�");
		 x = 3.0f*Screen.width/4.0f-(Screen.width/2.0f-tw)/2.0f;
		canvas.drawText("��ϵ�ң�",x, Screen.height*4.0f/6.0f, paint);
		canvas.drawText("zhbhun@gmail.com",x, Screen.height*4.0f/6.0f+Screen.height/14.0f, paint);
		
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

	@Override
	public void reCycle() {
		// TODO Auto-generated method stub
		back.reCycle();
		back = null;
		logo.reCycle();
		logo = null;
		textRectF = null;
		paint = null;
		background =  null;
		controlView = null;
		
	}

	@Override
	public void refurbish() {
		// TODO Auto-generated method stub
		
	}

	

}
