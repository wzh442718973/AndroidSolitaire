package com.hci.exp.view;
import android.content.Context;
import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
public abstract class GameView extends View
{
	public GameView(Context context)
	{
		super(context);
	}
	public abstract void saveGame();
	public abstract void pause();
	public abstract void resume();
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	}

	/**
	 * 回收资源
	 *
	 */
	public abstract void reCycle();	
	
	/**
	 * 刷新
	 *
	 */
	public abstract void refurbish();
}

