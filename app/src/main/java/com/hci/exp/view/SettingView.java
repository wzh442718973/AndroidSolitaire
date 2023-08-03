package com.hci.exp.view;

import com.hci.exp.R;
import com.hci.exp.control.CardPictureGenerator;
import com.hci.exp.control.CardPictureGenerator.CardStyle;
import com.hci.exp.control.CardPictureGenerator1;
import com.hci.exp.control.CardPictureGenerator2;
import com.hci.exp.device.Screen;
import com.hci.exp.model.Card.CardLand;
import com.hci.exp.model.ImageButton;
import com.hci.exp.model.ImageTextButton;
import com.hci.exp.model.ImageTextButton.TextPosition;

import android.content.Context;
import android.content.res.Resources;
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

public class SettingView extends GameView {
	GameScreen gs;
	private ImageButton back;
	private String[] options = {"背景","纸牌","高级"};
	private RectF[] opionsRects = new RectF[options.length];
	private float partition = Screen.height/5.0f;
	private String currentOption = "背景";
	//高级
	private ImageTextButton timeShow;
	private ImageTextButton operationShow;
	private ImageTextButton modelSelect;
	//纸牌
	private ImageButton cardStyle1;
	private ImageButton cardStyle2;
	public SettingView(Context context,GameScreen gs) {
		super(context);
		this.gs = gs;
		back =  new ImageButton("后退",getResources(),R.raw.back,0,0,Screen.height/5.0f,Screen.height/5.00f);
		float count = 1 + opionsRects.length+(opionsRects.length-1)/3.0f;
		for(int i=0;i<opionsRects.length;i++){
			opionsRects[i] = new RectF(Screen.height/10.0f+(i+0.5f+i/3.0f)*Screen.width/count,partition/3.0f,Screen.height/10.0f+(i+1.5f+i/3.0f)*Screen.width/count, partition+20);
		}
		float size = Screen.height/5.0f;
		float beginP = (Screen.width/3.0f - size)/2.0f;
		if(gs.ismIsShowTime())
			timeShow = new ImageTextButton("是否显示时间", getResources(), R.raw.check_enable,beginP,Screen.height/2 , size, size, size/3.0f, Color.GRAY, TextPosition.bellow);
		else
			timeShow = new ImageTextButton("是否显示时间", getResources(), R.raw.check_disable,beginP,Screen.height/2 , size, size, size/3.0f, Color.GRAY, TextPosition.bellow);
		if(gs.ismIsShowOperation())
			operationShow = new ImageTextButton("是否显示操作数", getResources(), R.raw.check_enable,Screen.width/3.0f+beginP,Screen.height/2 , size, size, size/3.0f, Color.GRAY, TextPosition.bellow);
		else
			operationShow = new ImageTextButton("是否显示操作数", getResources(), R.raw.check_disable,Screen.width/3.0f+beginP,Screen.height/2 , size, size, size/3.0f, Color.GRAY, TextPosition.bellow);
		if(gs.isRightModel())
			modelSelect = new ImageTextButton("左手操作模式", getResources(), R.raw.check_disable,2.0f*Screen.width/3.0f+beginP,Screen.height/2 , size, size, size/3.0f, Color.GRAY, TextPosition.bellow);
		else
			modelSelect = new ImageTextButton("左手操作模式", getResources(), R.raw.check_enable,2.0f*Screen.width/3.0f+beginP,Screen.height/2 , size, size, size/3.0f, Color.GRAY, TextPosition.bellow);

		if(gs.getCurrentCardStyle()==CardStyle.style1){
			cardStyle1 = new ImageButton("style1", getResources(), R.raw.check_enable, Screen.width-1.5f*size, 2.0f*Screen.height/5.0f-size/2.0f, size, size);
			cardStyle2 = new ImageButton("style2", getResources(), R.raw.check_disable, Screen.width-1.5f*size, 4.0f*Screen.height/5.0f-size/2.0f, size, size);
		}
		else{
			cardStyle1 = new ImageButton("style1", getResources(), R.raw.check_disable, Screen.width-1.5f*size, 2.0f*Screen.height/5.0f-size/2.0f, size, size);
			cardStyle2 = new ImageButton("style2", getResources(), R.raw.check_enable, Screen.width-1.5f*size, 4.0f*Screen.height/5.0f-size/2.0f, size, size);
		}

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
		return super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		int action = event.getAction();
		float x =  event.getX();
		float y =  event.getY();
		if (action == MotionEvent.ACTION_UP) {
			for(int i=0;i<opionsRects.length;i++){
				if(opionsRects[i].contains(x, y)){
					currentOption = options[i];
					return true;
				}
			}
			if(back.isInclude(x, y)){
				gs.setmIsShowMenu(true);
				gs.setmIsPause(true);
				gs.setmIsShowSetting(false);
				reCycle();
				return true;
			}
			if(currentOption.equals("背景")){
				handleBackgroundSelect(x,y);
			}
			else if(currentOption.equals("纸牌")){
				handleCardStyle(x, y);
			}
			else if(currentOption.equals("高级")){
				handleAdvanceSetting(x, y);
			}

		}
		return true;
	}
	private void handleBackgroundSelect(float x,float y){
		float h1 = Screen.height/5.0f;
		float h2 = 3*Screen.height/5.0f;
		float h3 = Screen.height;
		float w1 = 0;
		float w2 = Screen.width/3.0f;
		float w3 = 2.0f*Screen.width/3.0f;
		float w4 = Screen.width;
		if(y > h1 && y < h2){
			if(x>w1&&x<w2){
				if(R.raw.backgroundgreen!=gs.getmBackgroundBitmapId())
					gs.setmBackgroundBitmapId(R.raw.backgroundgreen);
				return;
			}
			else if(x>w2&&x<w3){
				if(R.raw.backgroundtree!=gs.getmBackgroundBitmapId())
					gs.setmBackgroundBitmapId(R.raw.backgroundtree);
				return;
			}
			else if(x>w3&&x<w4){
				if(R.raw.backgroundxuancai!=gs.getmBackgroundBitmapId())
					gs.setmBackgroundBitmapId(R.raw.backgroundxuancai);
				return;
			}
			return;
		}
		else if(y>h2&&y<h3){
			if(x>w1&&x<w2){
				if(R.raw.backgroundyingmu!=gs.getmBackgroundBitmapId())
					gs.setmBackgroundBitmapId(R.raw.backgroundyingmu);
				return;
			}
			else if(x>w2&&x<w3){
				if(R.raw.backgroundclouds!=gs.getmBackgroundBitmapId())
					gs.setmBackgroundBitmapId(R.raw.backgroundclouds);
				return;
			}
			else if(x>w3&&x<w4){
				if(R.raw.backgroundwoodpanel!=gs.getmBackgroundBitmapId())
					gs.setmBackgroundBitmapId(R.raw.backgroundwoodpanel);
				return;
			}
			return;
		}
	}
	private void handleCardStyle(float x,float y){
		if(cardStyle1.isInclude(x, y) || (y > 3.0f*Screen.height/10.0f && y < 5.0f*Screen.height/10.0f)){
			if(gs.getCurrentCardStyle()==CardStyle.style1)
				return;
			gs.setCurrentCardStyle(CardStyle.style1);
			Bitmap bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.check_enable)).getBitmap();
			cardStyle1.setmBackground(bmp);
			bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.check_disable)).getBitmap();
			cardStyle2.setmBackground(bmp);

		}
		else if(cardStyle2.isInclude(x, y) || (y > 7.0f*Screen.height/10.0f && y < 9.0f*Screen.height/10.0f)){
			if(gs.getCurrentCardStyle()==CardStyle.style2)
				return;
			gs.setCurrentCardStyle(CardStyle.style2);
			Bitmap bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.check_enable)).getBitmap();
			cardStyle2.setmBackground(bmp);
			bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.check_disable)).getBitmap();
			cardStyle1.setmBackground(bmp);

		}
	}
	private void handleAdvanceSetting(float x,float y){
		if(timeShow.isInclude(x, y)){
			gs.setmIsShowTime(!gs.ismIsShowTime());
			Bitmap bmp;
			if(gs.ismIsShowTime())
				bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.check_enable)).getBitmap();
			else
				bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.check_disable)).getBitmap();
			timeShow.setmBackground(bmp);
		}
		else if(operationShow.isInclude(x, y)){

			gs.setmIsShowOperation(!gs.ismIsShowOperation());
			Bitmap bmp;
			if(gs.ismIsShowOperation())
				bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.check_enable)).getBitmap();
			else
				bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.check_disable)).getBitmap();
			operationShow.setmBackground(bmp);

		}
		else if(modelSelect.isInclude(x, y)){
			gs.setRightModel(!gs.isRightModel());
			Bitmap bmp;
			if(gs.isRightModel())
				bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.check_disable)).getBitmap();
			else
				bmp = ((BitmapDrawable)getResources().getDrawable(R.raw.check_enable)).getBitmap();
			modelSelect.setmBackground(bmp);


		}

	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(0XFF4F94CD);
		canvas.drawRect(0, 0, Screen.width, Screen.height/5, paint);
		back.doDraw(canvas, null);
		for(int i=0;i<opionsRects.length;i++){
			if(options[i].equals(currentOption)){
				paint.setColor(Color.WHITE);
				canvas.drawRoundRect(opionsRects[i],15, 15, paint);
				paint.setColor(Color.BLACK);
				paint.setTypeface(Typeface.SANS_SERIF);
				paint.setTextSize(partition/3.0f);
				float tw = paint.measureText(options[i]);
				canvas.drawText(options[i], opionsRects[i].left+((opionsRects[i].right-opionsRects[i].left)-tw)/2.0f,partition-((partition-opionsRects[i].top)-partition/3.0f)/2.0f, paint);
			}
			else{
				paint.setColor(0XFF436EEE);
				canvas.drawRoundRect(opionsRects[i],15, 15, paint);
				paint.setColor(Color.WHITE);
				paint.setTypeface(Typeface.SANS_SERIF);
				paint.setTextSize(partition/3.0f);
				float tw = paint.measureText(options[i]);
				canvas.drawText(options[i], opionsRects[i].left+((opionsRects[i].right-opionsRects[i].left)-tw)/2.0f,partition-((partition-opionsRects[i].top)-partition/3.0f)/2.0f, paint);
			}
		}
		Bitmap mBackBitmap = ((BitmapDrawable)getResources().getDrawable(R.raw.settingadvancebackground)).getBitmap();
		Matrix m = new Matrix();
		m.postScale((float)Screen.width/mBackBitmap.getWidth(), 4.0f*Screen.height/5.0f/mBackBitmap.getHeight());
		mBackBitmap =  Bitmap.createBitmap(mBackBitmap, 0, 0, mBackBitmap.getWidth(),mBackBitmap.getHeight(), m, true);
		canvas.drawBitmap(mBackBitmap, 0, Screen.height/5.0f, null);
		if(currentOption.equals("背景")){
			drawBackgroundOption(canvas);
		}
		else if(currentOption.equals("纸牌")){
			drawCardOption(canvas);
		}
		else if(currentOption.equals("高级")){
			drawAdvanceOption(canvas);
		}
	}
	private void drawBackgroundOption(Canvas canvas){
		Paint paint = new Paint();
		float bw = Screen.width/3.0f;
		float bh = 2.0f*Screen.height/5.0f;
		Matrix mMatrix = new Matrix();
		Resources res = getResources();
		int bimaapId = gs.getmBackgroundBitmapId();
		Bitmap bitMap = ((BitmapDrawable)res.getDrawable(R.raw.backgroundgreen_small)).getBitmap();
		mMatrix.postScale(bw/bitMap.getWidth(),bh/bitMap.getHeight());
		bitMap = Bitmap.createBitmap(bitMap, 0, 0, bitMap.getWidth(),bitMap.getHeight(), mMatrix, true);
		canvas.drawBitmap(bitMap,0, Screen.height/5.0f,null);
		if(bimaapId == R.raw.backgroundgreen){
			paint.reset();
			paint.setColor(Color.WHITE);
			paint.setTextSize(Screen.height/15.0f);
			float tw = paint.measureText("当前背景");
			canvas.drawText("当前背景", (bitMap.getWidth()-tw)/2.0f,3.0f/5.0f*Screen.height-Screen.height/15.0f,paint);

		}
		bitMap = ((BitmapDrawable)res.getDrawable(R.raw.backgroundtree_small)).getBitmap();
		mMatrix.reset();
		mMatrix.postScale(bw/bitMap.getWidth(),bh/bitMap.getHeight());
		bitMap = Bitmap.createBitmap(bitMap, 0, 0, bitMap.getWidth(),bitMap.getHeight(), mMatrix, true);
		canvas.drawBitmap(bitMap,Screen.width/3.0f, Screen.height/5.0f,null);
		if(bimaapId == R.raw.backgroundtree){
			paint.reset();
			paint.setColor(Color.WHITE);
			paint.setTextSize(Screen.height/15.0f);
			float tw = paint.measureText("当前背景");
			canvas.drawText("当前背景", Screen.width/3.0f+(bitMap.getWidth()-tw)/2.0f,3.0f/5.0f*Screen.height-Screen.height/15.0f,paint);

		}
		bitMap = ((BitmapDrawable)res.getDrawable(R.raw.backgroundxuancai_small)).getBitmap();
		mMatrix.reset();
		mMatrix.postScale(bw/bitMap.getWidth(),bh/bitMap.getHeight());
		bitMap = Bitmap.createBitmap(bitMap, 0, 0, bitMap.getWidth(),bitMap.getHeight(), mMatrix, true);
		canvas.drawBitmap(bitMap,2.0f*Screen.width/3.0f, Screen.height/5.0f,null);
		if(bimaapId == R.raw.backgroundxuancai){
			paint.reset();
			paint.setColor(Color.WHITE);
			paint.setTextSize(Screen.height/15.0f);
			float tw = paint.measureText("当前背景");
			canvas.drawText("当前背景", 2*Screen.width/3.0f+(bitMap.getWidth()-tw)/2.0f,3.0f/5.0f*Screen.height-Screen.height/15.0f,paint);

		}
		bitMap = ((BitmapDrawable)res.getDrawable(R.raw.backgroundyingmu_small)).getBitmap();
		mMatrix.reset();
		mMatrix.postScale(bw/bitMap.getWidth(),bh/bitMap.getHeight());
		bitMap = Bitmap.createBitmap(bitMap, 0, 0, bitMap.getWidth(),bitMap.getHeight(), mMatrix, true);
		canvas.drawBitmap(bitMap,0, 3.0f*Screen.height/5.0f,null);
		if(bimaapId == R.raw.backgroundyingmu){
			paint.reset();
			paint.setColor(Color.WHITE);
			paint.setTextSize(Screen.height/15.0f);
			float tw = paint.measureText("当前背景");
			canvas.drawText("当前背景", (bitMap.getWidth()-tw)/2.0f,Screen.height-Screen.height/15.0f,paint);

		}
		bitMap = ((BitmapDrawable)res.getDrawable(R.raw.backgroundclouds_small)).getBitmap();
		mMatrix.reset();
		mMatrix.postScale(bw/bitMap.getWidth(),bh/bitMap.getHeight());
		bitMap = Bitmap.createBitmap(bitMap, 0, 0, bitMap.getWidth(),bitMap.getHeight(), mMatrix, true);
		canvas.drawBitmap(bitMap,Screen.width/3.0f, 3.0f*Screen.height/5.0f,null);
		if(bimaapId == R.raw.backgroundclouds){
			paint.reset();
			paint.setColor(Color.WHITE);
			paint.setTextSize(Screen.height/15.0f);
			float tw = paint.measureText("当前背景");
			canvas.drawText("当前背景", Screen.width/3.0f+(bitMap.getWidth()-tw)/2.0f,Screen.height-Screen.height/15.0f,paint);

		}
		bitMap = ((BitmapDrawable)res.getDrawable(R.raw.backgroundwoodpanel_small)).getBitmap();
		mMatrix.reset();
		mMatrix.postScale(bw/bitMap.getWidth(),bh/bitMap.getHeight());
		bitMap = Bitmap.createBitmap(bitMap, 0, 0, bitMap.getWidth(),bitMap.getHeight(), mMatrix, true);
		canvas.drawBitmap(bitMap,2.0f*Screen.width/3.0f, 3.0f*Screen.height/5.0f,null);
		if(bimaapId == R.raw.backgroundwoodpanel){
			paint.reset();
			paint.setColor(Color.WHITE);
			paint.setTextSize(Screen.height/15.0f);
			float tw = paint.measureText("当前背景");
			canvas.drawText("当前背景", 2*Screen.width/3.0f+(bitMap.getWidth()-tw)/2.0f,Screen.height-Screen.height/15.0f,paint);

		}
	}

	public void drawCardOption(Canvas canvas){
		Resources res = getResources();
		Matrix matrix = new Matrix();
		//Paint paint = new Paint();
		float width = Screen.height/5.0f/1.5f;
		float height = Screen.height/5.0f;
		float sepration  = (Screen.width-2*height)/13;
		float y1 = 2.0f*Screen.height/5.0f-height/2;
		CardPictureGenerator cf = new CardPictureGenerator1();
		for(int i = 1;i<=13;i++){
			Bitmap bmp = cf.getCardPiture(i, CardLand.Heart, res);
			matrix.reset();
			matrix.postScale(width/bmp.getWidth(), height/bmp.getHeight());
			bmp =  Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),bmp.getHeight(), matrix, true);
			canvas.drawBitmap(bmp, (i-1)*sepration, y1, null);
		}
		cardStyle1.doDraw(canvas, null);
		float y2 = 4.0f*Screen.height/5.0f-height/2;
		cf = new CardPictureGenerator2();
		for(int i = 1;i<=13;i++){
			Bitmap bmp = cf.getCardPiture(i, CardLand.Spade, res);
			matrix.reset();
			matrix.postScale(width/bmp.getWidth(), height/bmp.getHeight());
			bmp =  Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),bmp.getHeight(), matrix, true);
			canvas.drawBitmap(bmp, (i-1)*sepration, y2, null);
		}
		cardStyle2.doDraw(canvas, null);
	}
	public void drawAdvanceOption(Canvas canvas){

		timeShow.doDraw(canvas, null);
		operationShow.doDraw(canvas, null);
		modelSelect.doDraw(canvas, null);
	}
	@Override
	public void reCycle() {
		// TODO Auto-generated method stub
		for(int i=0;i<options.length;i++)
			options[i] = null;
		options = null;
		for(int i=0;i<opionsRects.length;i++)
			opionsRects[i] = null;
		opionsRects = null;
		currentOption = null;
		timeShow.reCycle();
		timeShow = null;
		operationShow.reCycle();
		operationShow= null;
		modelSelect.reCycle();
		modelSelect = null;
		cardStyle1.reCycle();
		cardStyle1 = null;
		cardStyle2.reCycle();
		cardStyle2 = null;
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
