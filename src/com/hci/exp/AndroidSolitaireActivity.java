package com.hci.exp;

import com.hci.exp.device.Screen;
import com.hci.exp.view.ControlView;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class AndroidSolitaireActivity extends Activity {
    /** Called when the activity is first created. */
	private ControlView ct;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        /* 设置为无标题栏 */
//		requestWindowFeature(Window.FEATURE_NO_TITLE); 
//		/* 设置为全屏模式 */ 
//		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); 
//		/* 设置为横屏 */
//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//		
//		
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		Screen.width = dm.widthPixels;
		Screen.height = dm.heightPixels;
		ct = new ControlView(this);
		setContentView(ct);
    }
    
    @Override
	protected void onPause()
	{  
		super.onPause();
		ct.pause();
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ct.destroy();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		ct.resume();
		//mThreadCanvas.requestFocus();
		//mThreadCanvas.start();
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		//mThreadCanvas.onKeyDown(keyCode);
		 ct.onKeyDown(keyCode, event);
		return true;
	}


	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		//mThreadCanvas.onKeyUp(keyCode);
		ct.onKeyUp(keyCode, event);
		return false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		//mThreadCanvas.onTouchEvent(event);
		ct.onTouchEvent(event);
		return false;
	}
}