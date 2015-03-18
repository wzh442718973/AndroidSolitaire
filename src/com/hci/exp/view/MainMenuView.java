package com.hci.exp.view;

import com.hci.exp.R;
import com.hci.exp.device.Screen;
import com.hci.exp.model.ImageButton;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

public class MainMenuView extends GameView {
	private ControlView controlView;
	private Bitmap mBackground;
	private Bitmap money;//背景
	private ImageButton newGame;
	private ImageButton continues;
	//private ImageButton help;
	private ImageButton about;
	private ImageButton exit;
	public MainMenuView(Context context,ControlView control) {
		super(context);
		// TODO Auto-generated constructor stub
		controlView = control;
		init();
		
	}
    private void init(){
    	initBackground();
    	initButton();
    	
    }
	private void initBackground(){
		mBackground = ((BitmapDrawable)getResources().getDrawable(R.raw.backgroundmainmenu)).getBitmap();
		Matrix mMatrix = new Matrix();
		float scaleW = (float)(Screen.width)/mBackground.getWidth();
		float scaleH = (float)(Screen.height)/mBackground.getHeight();
		mMatrix.reset();
		mMatrix.postScale(scaleW, scaleH);
		mBackground = Bitmap.createBitmap(mBackground, 0, 0, mBackground.getWidth(),mBackground.getHeight(), mMatrix, true);
		//
		money = ((BitmapDrawable)getResources().getDrawable(R.raw.money)).getBitmap();
		scaleH = (2*(float)(Screen.height)/3)/money.getHeight();
		mMatrix.reset();
		mMatrix.postScale(scaleH, scaleH);
		money = Bitmap.createBitmap(money, 0, 0, money.getWidth(),money.getHeight(), mMatrix, true);
	}
	private void initButton(){
		newGame = new ImageButton("新游戏",getResources(),R.raw.mainview_new,(float)Screen.width/16.0f, 9.0f*(float)Screen.height/12.0f, (float)Screen.width/7.0f,(float)Screen.height/6.0f);
     	continues = new ImageButton("继续",getResources(),R.raw.mainview_continue,(float)Screen.width/4.0f, 9.0f*(float)Screen.height/12.0f, (float)Screen.width/7.0f,(float)Screen.height/6.0f);
     	//help = new ImageButton("帮助",getResources(),R.raw.mainview_help,7.0f*(float)Screen.width/16.0f, 5.0f*(float)Screen.height/6.0f-10, (float)Screen.width/7.0f,(float)Screen.height/6.0f);
     	about = new ImageButton("关于",getResources(),R.raw.mainview_about,10.0f*(float)Screen.width/16.0f, 9.0f*(float)Screen.height/12.0f, (float)Screen.width/7.0f,(float)Screen.height/6.0f);
     	exit = new ImageButton("退出",getResources(),R.raw.mainview_exit,13.0f*(float)Screen.width/16.0f,9.0f*(float)Screen.height/12.0f, (float)Screen.width/7.0f,(float)Screen.height/6.0f);
	    
	
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
				Dialog dialog = new AlertDialog.Builder(getContext())
				.setTitle("提示")//设置标题
				.setMessage("是否退出游戏！")//设置内容
				.setPositiveButton("确定",//设置确定按钮
				new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int whichButton){
						System.exit(0);
					}
					
				}).setNeutralButton("取消", 
				new DialogInterface.OnClickListener() 
				{
				public void onClick(DialogInterface dialog, int whichButton)
				{
					
				}
			     }).create();//创建按钮
			     dialog.show();
		      }
		 return true;
	}





	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int action = event.getAction();
		float x =  event.getX();
		float y =  event.getY();
		if(action == MotionEvent.ACTION_DOWN){
 
            if(newGame.isInclude(x, y)){
            	newGame.setHighlight(true);
            	Bitmap bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.new_pressed)).getBitmap();
            	newGame.setmBackground(bmp);
            	return true;
            }
			else if(continues.isInclude(x, y)){
				continues.setHighlight(true);
				Bitmap bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.continue_pre1ssed)).getBitmap();
				continues.setmBackground(bmp);
				return true;
			}
//			else if(help.isInclude(x, y)){
//				help.setHighlight(true);
//				Bitmap bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.help_pressed)).getBitmap();
//				help.setmBackground(bmp);
//					return true;
//			}
			else if(about.isInclude(x, y)){
                 about.setHighlight(true);
				Bitmap bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.about_pressed)).getBitmap();
				about.setmBackground(bmp);
				return true;
			}
			else if(exit.isInclude(x, y)){
                exit.setHighlight(true);
				Bitmap bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.exit_pressed)).getBitmap();
				exit.setmBackground(bmp);
				return true;
			}
		}
		else if (action == MotionEvent.ACTION_UP) {
			if(newGame.isHighlight()){
			   newGame.setHighlight(false);
			   Bitmap bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.mainview_new)).getBitmap();
               newGame.setmBackground(bmp);
			}
			else if(continues.isHighlight()){
			   continues.setHighlight(false);
			   Bitmap bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.mainview_continue)).getBitmap();
			   continues.setmBackground(bmp);
			}
//			else if(help.isHighlight()){
//			   help.setHighlight(false);
//			   Bitmap bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.mainview_help)).getBitmap();
//			   help.setmBackground(bmp);
//			}
			else if(about.isHighlight()){
			   about.setHighlight(false);
			   Bitmap bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.mainview_about)).getBitmap();
			   about.setmBackground(bmp);
			}
			else if(exit.isHighlight()){
			   exit.setHighlight(false);
			   Bitmap bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.mainview_exit)).getBitmap();
			   exit.setmBackground(bmp);
			}
			if(newGame.isInclude(x, y)){
				ModelSelectView gs = new ModelSelectView(getContext(),controlView);
				controlView.setCurrentView(gs);
				reCycle();
				return true;
			}
			else if(continues.isInclude(x, y)){
				GameScreen gs = new GameScreen(getContext(),controlView);
				if(gs.ismIsCreated()){
				 controlView.setCurrentView(gs);
				 reCycle();
			     return true;
				}
				else{
					Dialog dialog = new AlertDialog.Builder(getContext())
					.setTitle("提示")//设置标题
					.setMessage("没有存档！")//设置内容
					.setNeutralButton("确定", 
					new DialogInterface.OnClickListener() 
					{
					public void onClick(DialogInterface dialog, int whichButton)
					{
						
					}
				     }).create();//创建按钮
				     dialog.show();
			      
			        return true;

			    }
            
			}
//			else if(help.isInclude(x, y)){
//				
//				return true;
//			}
			else if(about.isInclude(x, y)){
				AboutView aboutView = new AboutView(getContext(),controlView);
				controlView.setCurrentView(aboutView);
				 reCycle();
			     return true;
			}
			else if(exit.isInclude(x, y)){
				System.exit(0);
				return true;
			}
		
		}
		return false;
	}





	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint paint = new Paint();
		drawBackground(canvas,paint);
		drawText(canvas,paint);
		drawButton(canvas,paint);
	}
    protected void drawBackground(Canvas canvas,Paint paint){
    	paint.reset();
    	canvas.drawBitmap(mBackground, 0,0, paint);
		canvas.drawBitmap(money,Screen.width-money.getWidth()-10,Screen.height/6,paint);
    }
    protected void drawText(Canvas canvas,Paint paint) {
    	if(paint == null)
    		paint = new Paint();
    	paint.reset();
    	paint.setColor(0xFFEEB422);
    	paint.setTextSize(Screen.height/5.0f);
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.SANS_SERIF);
        canvas.drawText("纸牌游戏",Screen.width/8.0f, Screen.height/2.0f, paint);
	}
    protected void drawButton(Canvas canvas,Paint paint){
    	if(paint == null)
    		paint = new Paint();
    	paint.reset();
    	 newGame.doDraw(canvas, paint);
    	 continues.doDraw(canvas, paint);
    	// help.doDraw(canvas, paint);
    	 about.doDraw(canvas, paint);
    	 exit.doDraw(canvas, paint);
     }



	@Override
	public void reCycle() {
		// TODO Auto-generated method stub
		
		controlView = null;
		newGame.reCycle();
		newGame = null;
		continues.reCycle();
		continues = null;
	//	help.reCycle();
		//help = null;
		about.reCycle();
		about = null;
		exit.reCycle();
		exit = null;
		mBackground = null;
		money = null;
		System.gc();
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
