package com.hci.exp.view;

import com.hci.exp.R;
import com.hci.exp.device.Screen;
import com.hci.exp.model.ImageButton;
import com.hci.exp.model.ImageTextButton;
import com.hci.exp.model.ImageTextButton.TextPosition;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class ModelSelectView extends GameView {
	private ImageTextButton flip1;
	private ImageTextButton flip3;
	private ImageButton back;
	private ControlView controlView;
	private Bitmap background;
	public ModelSelectView(Context context,ControlView controlView) {
		super(context);
		// TODO Auto-generated constructor stub
		this.controlView = controlView;

		flip1 = new ImageTextButton("翻一张牌", getResources(), R.raw.flip1, Screen.width/6.0f, Screen.height/4.0f, Screen.width/6.0f, Screen.height/2.0f, Screen.height/15.0f, Color.WHITE,TextPosition.bellow);
		flip3 = new ImageTextButton("翻三张牌", getResources(), R.raw.flip3, 5.0f*Screen.width/8.0f, Screen.height/4.0f, Screen.width/4.0f, Screen.height/2.0f, Screen.height/15.0f, Color.WHITE,TextPosition.bellow);
		back =  new ImageButton("后退",getResources(),R.raw.back,10,Screen.height-Screen.height/5.0f,Screen.height/5.0f,Screen.height/5.0f);
		background = ((BitmapDrawable)getResources().getDrawable(R.raw.modelselectbackgroundwoodpanel)).getBitmap();
		Matrix m =new Matrix();
		m.postScale((float)Screen.width/background.getWidth(), (float)Screen.height/background.getHeight());
		background = Bitmap.createBitmap(background, 0, 0, background.getWidth(),background.getHeight(), m, true);


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
			if(flip1.isInclude(x, y)){
				GameScreen gs = new GameScreen(getContext(), false,controlView);
				controlView.setCurrentView(gs);
				reCycle();
				return true;
			}
			else if(flip3.isInclude(x, y)){
				GameScreen gs = new GameScreen(getContext(), true,controlView);
				controlView.setCurrentView(gs);
				reCycle();
				return true;
			}
			else if(back.isInclude(x, y)){
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
		canvas.drawBitmap(background, 0, 0, null);
		flip1.doDraw(canvas, null);
		flip3.doDraw(canvas, null);
		back.doDraw(canvas, null);
	}

	@Override
	public void reCycle() {
		// TODO Auto-generated method stub
		flip1.reCycle();
		flip1 = null;
		flip3.reCycle();
		flip3 = null;
		back.reCycle();
		back = null;
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
