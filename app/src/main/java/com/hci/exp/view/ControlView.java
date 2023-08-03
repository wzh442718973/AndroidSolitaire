package com.hci.exp.view;

import android.content.Context;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class ControlView extends View implements Runnable{
    private GameView currentView;
	public ControlView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		currentView = new MainMenuView(context,this);
		new Thread(this).start();
	}
    @Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		if(currentView!=null)
			currentView.onDraw(canvas);
	}
    public GameView getCurrentView() {
		return currentView;
	}

	public void setCurrentView(GameView currentView) {
		if(currentView!=null){
		  this.currentView = currentView;
		  System.gc();
		  postInvalidate();
		}
	}
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
    	if(currentView!=null)
			currentView.onKeyDown(keyCode,  event);
		return true;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(currentView!=null)
			currentView.onKeyUp(keyCode,  event);
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if(currentView!=null)
			currentView.onTouchEvent(event);
		return true;
	}
	public void run() {
		// TODO Auto-generated method stub

		while (true)
		{
			try
			{
				Thread.sleep(100);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

			postInvalidate(); // Ë¢ÐÂÆÁÄ»
		}
	}
	public void pause(){
		currentView.pause();
		
	}
	public void destroy(){
		currentView.saveGame();
		currentView.reCycle();
	}
	public void resume(){
		currentView.resume();
	}
}
