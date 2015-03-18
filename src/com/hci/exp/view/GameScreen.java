package com.hci.exp.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import com.hci.exp.R;
import com.hci.exp.control.CardPictureGenerator;
import com.hci.exp.control.CardPictureGenerator.CardStyle;
import com.hci.exp.control.CardPictureGenerator1;
import com.hci.exp.control.CardPictureGenerator2;
import com.hci.exp.control.CardPictureGeneratorFactory;
import com.hci.exp.control.MoveCards;
import com.hci.exp.device.GameFile;
import com.hci.exp.device.Screen;
import com.hci.exp.model.DiscardPileThere;
import com.hci.exp.model.GameObject;
import com.hci.exp.model.ImageButton;
import com.hci.exp.model.Card;
import com.hci.exp.model.CardPile;
import com.hci.exp.model.DeckPile;
import com.hci.exp.model.DiscardPileOne;
import com.hci.exp.model.GameObjectQueue;
import com.hci.exp.model.ImageTextButton;
import com.hci.exp.model.ImageTextButton.TextPosition;
import com.hci.exp.model.MovePile;
import com.hci.exp.model.SuitPile;
import com.hci.exp.model.TablePile;
import com.hci.exp.model.TipPile;
import com.hci.exp.model.Card.CardLand;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class GameScreen extends GameView{
	  private boolean mIsCreated = true;
	  private boolean mIsPause = false;
	  private boolean mIsWin = false;
	  private boolean mIsGameOver = false;
      private ControlView controlView;
      private CardStyle currentCardStyle;
      private GameObjectQueue cardPiles;
      /**各个牌堆*/
      private CardPile[] tablePile = new TablePile[7];
      private CardPile[] suitPile = new SuitPile[4];
      private CardPile deckPile;
      private CardPile discardPile;
      private CardPile movePile;
      private CardPile fromPile,toPile,lastToPile;
      private int[] matchIndex = new int[11];
      /**牌大小与牌堆位置*/
      private float cardWidth;
      private float cardHeight;
      private float deckPileX=0,deckPileY=0;
      private float discardPileX=0,discardPileY=0;
      private float tablePileX[] = new float[7],tablePileY[] = new float[7];
      private float suitPileX[] = new float[4],suitPileY[] = new float[4];
      /**游戏位置模式，是否为 右*/
      private boolean rightModel;
      /**游戏模式，是否为翻三张*/
      private boolean isDiscardPileShowThree;
      /**是否选中deckPile*/
      private boolean isHitDeckPile;
      /**触点的上一次位置*/
      private float oX,oY;
      /**计时器和操作数*/
      private boolean mIsShowTime;
      private int time;
      private int initTime;
      private Date beginTime;
      private boolean mIsShowOperation;
      private int operationCount = 0;
      /**背景*/
      private Bitmap mBackground;
      private int mBackgroundBitmapId;
      /**暂停*/
      private ImageButton pause;
      /**菜单*/
      private boolean mIsShowMenu;
      private GameObjectQueue menus;
      private ImageTextButton mainMenu;
      private ImageTextButton restart;
      private ImageTextButton resume;
      private ImageTextButton options;
      private ImageTextButton exit;
      private boolean mIsShowSetting;
      /**设置*/
      private SettingView settingView;
    public GameScreen(Context context,boolean isShowThree,ControlView cv) {
		super(context);
		controlView = cv;
		this.isDiscardPileShowThree = isShowThree;
		init();
		
	}
	public GameScreen(Context context,ControlView cv) {
		super(context);
		controlView = cv;
		loadGame();
	}
	
	private void init(){
		  
	    int backSeparation,frontSeparation;
	    float separation;
		for(int i =0;i<11;i++)
			matchIndex[i] = -1;
		//纸牌大小
		cardWidth = Screen.width / 11;
		cardHeight = (float)(cardWidth*1.5);
		backSeparation = (int)cardHeight/10;
		frontSeparation = (int)cardHeight/4;
		separation = cardWidth/3.0f;
		//读取配置文件
		loadSetting();
		//背景
		mBackground = ((BitmapDrawable)getResources().getDrawable(mBackgroundBitmapId)).getBitmap();
		Matrix mMatrix = new Matrix();
		float scaleW = (float)(Screen.width)/mBackground.getWidth();
		float scaleH = (float)(Screen.height)/mBackground.getHeight();
		mMatrix.postScale(scaleW, scaleH);
		mBackground = Bitmap.createBitmap(mBackground, 0, 0, mBackground.getWidth(),mBackground.getHeight(), mMatrix, true);
	    //牌堆位置
		initPosition();
	    //牌
          Resources res = getResources();
	      cardPiles = new GameObjectQueue("GameScreen");
	   	  List<Card> cards = new ArrayList<Card>();
	      CardPictureGenerator cf = CardPictureGeneratorFactory.getCardPictureGenerator(currentCardStyle);
	      for(int i=1;i<=13;i++){

	       	  cards.add(new Card(i, CardLand.Heart,cardWidth,cardHeight, res,cf));
	      }
	      for(int i=1;i<=13;i++){

	    	  cards.add(new Card(i, CardLand.Diamond, cardWidth,cardHeight,res,cf));
	      }
	      for(int i=1;i<=13;i++){

	       	  cards.add(new Card(i, CardLand.Spade,cardWidth,cardHeight, res,cf));
	      }
	      for(int i=1;i<=13;i++){

	       	  cards.add(new Card(i, CardLand.Club,cardWidth,cardHeight, res,cf));
	      }
	   	  
	   	  //发牌
           Random random = new Random();
	   	  //deckPile
	   	  deckPile = new DeckPile(deckPileX, deckPileY, res,cardWidth,cardHeight);
	   	  for(int i = 0;i < 24;i++){
	   		  Card c = cards.remove(random.nextInt(cards.size()));
	   		  deckPile.addCard(c);
	   	  }
	   	cardPiles.put("deckPile",deckPile );
	   	  //suitPile
	   	  for(int i=0;i<4;i++){
	   		suitPile[i] = new SuitPile(suitPileX[i], suitPileY[i],  res,cardWidth,cardHeight);
	   		cardPiles.put("suitPile"+(i+1), suitPile[i]);
	   	  }
	   	  //discardPile
	   	  if(!isDiscardPileShowThree)
	   	     discardPile = new DiscardPileOne(discardPileX, discardPileY,  res,cardWidth,cardHeight);
	   	  else
	   		discardPile = new DiscardPileThere(discardPileX, discardPileY,cardWidth,cardHeight,res,separation);
	   	cardPiles.put("discardPile", discardPile);
	   	   //talePile
	   	  for(int i = 0;i<7;i++){
	   		 tablePile[i] = new TablePile(tablePileX[i], tablePileY[i], res, backSeparation, frontSeparation,cardWidth,cardHeight);
	   		  for(int j =0;j<=i;j++){
	   			Card c = cards.remove(random.nextInt(cards.size()));
	   			c.setmIsTurned(false);
	   			tablePile[i].addCard(c);
	   		  }
	   		tablePile[i].turnTopCard();
	   		cardPiles.put("tablePile"+(i+1), tablePile[i]);
	   	  }
	   	  
	   	  //movePile
	      movePile = new MovePile(0, 0, frontSeparation);
	      //计时器
            this.time = 0;
			this.initTime = 0;
	        beginTime = new Date();
	   //暂停
	   pause = new ImageButton("暂停", res, R.raw.pause,0, 0, Screen.width/15.0f, Screen.width/15.0f) ;    
	   //菜单
	        createMenu();
	}
	  private void createMenu(){
		 //菜单
        mIsShowMenu = false;
		this.menus = new GameObjectQueue("menu");
		 mainMenu = new ImageTextButton("主菜单", getResources(), R.raw.m_mainmenu, Screen.width/9.0f, 3.0f*Screen.height/5.0f, Screen.width/9.0f, Screen.width/9.0f,Screen.height/18.0f, Color.GRAY,TextPosition.bellow);
		 restart = new ImageTextButton("重新开始", getResources(), R.raw.m_new, 5.0f*Screen.width/18.0f, 2.0f*Screen.height/5.0f, Screen.width/9.0f, Screen.width/9.0f,Screen.height/18.0f, Color.GRAY,TextPosition.bellow);
		 resume = new ImageTextButton("继续", getResources(), R.raw.m_resume, 8.0f*Screen.width/18.0f, 1.0f*Screen.height/5.0f, Screen.width/9.0f, Screen.width/9.0f,Screen.height/18.0f, Color.GRAY,TextPosition.bellow);
		 options = new ImageTextButton("设置", getResources(), R.raw.m_options, 11.0f*Screen.width/18.0f, 2.0f*Screen.height/5.0f, Screen.width/9.0f, Screen.width/9.0f,Screen.height/18.0f, Color.GRAY,TextPosition.bellow);
		 exit = new ImageTextButton("退出", getResources(), R.raw.m_exit, 14.0f*Screen.width/18.0f, 3.0f*Screen.height/5.0f, Screen.width/9.0f, Screen.width/9.0f,Screen.height/18.0f, Color.GRAY,TextPosition.bellow);
	    this.menus.put("mainMenu", mainMenu);
	    this.menus.put("restart", restart);
	    this.menus.put("resume", resume);
	    this.menus.put("about", options);
	    this.menus.put("exit", exit);
	  //设置
	    mIsShowSetting = false;

	}
    @Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
    	if(mIsShowSetting){
    		settingView.onDraw(canvas);
    		return;
    	}
    	else{
    	  Paint paint = new Paint();
       	  //背景
    	  canvas.drawBitmap(mBackground, 0, 0, paint);
    	  //纸牌
    	  paint.reset();
    	  cardPiles.paint(canvas,paint);
    	  paint.reset();
		  movePile.doDraw(canvas, paint);
		  //暂停
		  pause.doDraw(canvas, null);
		 //计时器和操作数
		  paint.reset();
		  drawTimeAndOpearation(canvas, paint);
		  if(mIsShowMenu){
			  drawMenuView(canvas, paint);
	      }
		  if(!mIsPause && mIsWin && !mIsGameOver)
			    completeNextCard();
		  if(mIsGameOver)
			  drawSuccessTip(canvas, paint);
      }
	}
    protected void drawSuccessTip(Canvas canvas,Paint paint){
    	String str = "游戏结束，您获胜了！";
    	if(paint==null)
    		paint = new Paint();
    	paint.setTextSize(Screen.height/13);
    	paint.setColor(0xFFEEB422);
    	float tw = paint.measureText(str);
    	canvas.drawText(str, (Screen.width-tw)/2.0f, 2.0f*Screen.height/3.0f, paint);
    }
    protected void drawTimeAndOpearation(Canvas canvas,Paint paint){
    	String str="";
    	if(mIsGameOver || mIsWin){
    	}
    	else if(mIsPause){
			beginTime = new Date();
			initTime = time;
    	}
    	else{
			Date currentTime = new Date();
			time = (int)((currentTime .getTime()- beginTime.getTime())/1000) + initTime;
			currentTime = null;
		}
    	if(mIsShowTime){
 		   String hour,minute,second;
    		int h = time/3600;
    		if(h<10)
    			hour ="0"+h;
    		else
    			hour = ""+h;
    		int m = time/60%60;
    		if(m<10)
    			minute ="0"+m;
    		else
    			minute = ""+m;
    		int s = time%60;
    		if(s<10)
    			second ="0"+s;
    		else
    			second = ""+s;
          str += "时间:"+hour+":"+minute+":"+second;
    	}
    	if(mIsShowOperation)
    	  str +=" 操作数:"+operationCount;
    	if(str.length()>0){
        	paint.reset();
            paint.setTextSize(Screen.height/20);
            paint.setAntiAlias(true);
            paint.setTypeface(Typeface.SANS_SERIF);
            paint.setColor(Color.GRAY);
            canvas.drawText(str, 10,Screen.height-Screen.height/40, paint);
    	}
    }
	
    protected void drawMenuView(Canvas canvas,Paint paint){
    	if(paint ==null)
    		paint = new Paint();
    	paint.reset();
	    paint.setColor(Color.BLACK);
	    paint.setAlpha(200);
	    paint.setAntiAlias(true);
	    paint.setStyle(Style.FILL);
	    canvas.drawRect(0, 0, Screen.width, Screen.height, paint);
	    paint.reset();

	    menus.paint(canvas, paint);
    }
	

	
   public String toString(){
	   return "";
   }
   
   
   @Override
   public boolean onKeyDown(int keyCode, KeyEvent event) {
	// TODO Auto-generated method stub
	return super.onKeyDown(keyCode, event);
   }
   

@Override
   public boolean onKeyUp(int keyCode, KeyEvent event) {
	// TODO Auto-generated method stub
	   
	  switch(keyCode) {
	      case KeyEvent.KEYCODE_MENU:
	    	  mIsShowMenu = true;
	    	  mIsPause = true;
		      break;
	      case KeyEvent.KEYCODE_BACK:
	    	  if(mIsShowSetting){
	    		  mIsShowSetting = false;
	    		  mIsShowMenu = true;
	    		  mIsPause = true;
	    		  settingView.reCycle();
	    		  settingView = null;
	    	  }
	    	  else if(mIsShowMenu){
	    		  mIsShowMenu = false;
	    		  mIsPause = false;
	    	  }
	    	  else{
	    	  Dialog dialog = new AlertDialog.Builder(getContext())
				.setTitle("提示：")//设置标题
				.setMessage("是否退出游戏！")//设置内容
				.setPositiveButton("退出",//设置确定按钮
				new DialogInterface.OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int whichButton){
						saveGame();
						System.exit(0);
					}
				}).setNeutralButton("取消", 
				  new DialogInterface.OnClickListener(){
				  public void onClick(DialogInterface dialog, int whichButton){
					  
				   }
			     }).create();//创建按钮
			    dialog.show();
	    	  }
		      break;
	      default:
		     break;
	}
	return super.onKeyUp(keyCode, event);
   }
   
   @Override
   public boolean onTouchEvent(MotionEvent event) {
	// TODO Auto-generated method stub
	if(mIsShowSetting){
		if(settingView==null)
			settingView = new SettingView(getContext(), this);
		settingView.onTouchEvent(event);
		return true;
	}
	int action = event.getAction();
	float x =  event.getX();
	float y =  event.getY();
	if (action == MotionEvent.ACTION_DOWN) {
		if(mIsShowMenu){
			return true;
		}
		oX = x;
		oY = y;
		fromPile = null;
		toPile = null;
		isHitDeckPile = deckPile.includes(x, y);
		if(isHitDeckPile){
			fromPile = deckPile;
			toPile = discardPile;
			return true;
		}
		if(discardPile.includes(x, y)){
			if(!(discardPile.isEmpty())){
				Card temp = discardPile.top();
				movePile.setPosition(temp.getmX(), temp.getmY());
				temp = null;
			    movePile.addCard(discardPile.removeCard());
			    fromPile = discardPile;
			    matchindexCal();
			}
			return true;
		}
		for(int i = 0;i<7;i++){
			if(tablePile[i].includes(x, y)){
				if(tablePile[i].getViwCount()>0){
				Card temp = tablePile[i].get(tablePile[i].getmSize()-tablePile[i].getViwCount());
				movePile.setPosition(temp.getmX(), temp.getmY());
				temp = null;
				for(int j = tablePile[i].getViwCount();j>0;j--)
				   movePile.addCard(tablePile[i].removeCard());
				fromPile = tablePile[i];
				matchindexCal();
				}
				return true;
			}
		}
		
		 return true;
	}
    else if (action == MotionEvent.ACTION_MOVE) {
         if(mIsShowMenu){
        	 return true;
		}
    	if(isHitDeckPile)
    		return true;
    	if(movePile.isEmpty())
    		return true;
    	lastToPile = toPile;
    	toPile = null;
    	movePile.setPosition(movePile.getmX()+x-oX, movePile.getmY()+y-oY);
    	oX = x;
    	oY = y;
    	for(int i = 0;i<7;i++){
			if(tablePile[i].includes(x, y)){
				if(matchIndex[i]>=0){
					toPile = tablePile[i];
					if(toPile!=lastToPile){
						if(lastToPile!=null){
							lastToPile.showTopCardNormal();
						}
					  toPile.showTopCardHighlight();
					  movePile.showHightlightTansparent(matchIndex[i]);
					}
						
				}
				else {
					if(lastToPile!=null){
				       lastToPile.showTopCardNormal();
					   movePile.showNormal();
					}
				}
				return true;
			}
		}
    	for(int i = 0;i<4;i++){
			if(suitPile[i].includes(x, y)){
               if(matchIndex[7+i]>=0){
					toPile = suitPile[i];
					if(toPile!=lastToPile){
						if(lastToPile!=null){
							lastToPile.showTopCardNormal();
						}
					  toPile.showTopCardHighlight();
					  movePile.showHightlightTansparent(matchIndex[7+i]);
					}
						
				}
				else {
					if(lastToPile!=null){
				       lastToPile.showTopCardNormal();
					   movePile.showNormal();
					}
				}
			  return true;
			}
		}
    	if(lastToPile!=null){
		       lastToPile.showTopCardNormal();
	           movePile.showNormal();
    	}
		return true;
    }
    else if (action == MotionEvent.ACTION_UP) {
    	 if(mIsShowMenu){
    		 if(mainMenu.isInclude(x, y)){
    			 gotoMainmenuView();
 				return true;
    		 }
    		 else if(restart.isInclude(x, y)){
    			 restart();
    			 return true;
    		}
    		 else if(resume.isInclude(x, y)){
    			 mIsShowMenu = false;
    			 mIsPause = false;
    			 
    			 return true;
    		 }
    		 else if(options.isInclude(x, y)){
    			 mIsShowSetting = true;
    			 mIsPause = true;
    			 settingView = new SettingView(getContext(), this);
    			 return true;
    		 }
    		 else if(exit.isInclude(x, y)){
    			 saveGame();
    			 System.exit(0);
    			 return true;
    		 }
			 return true;
		}
    	 else if(isHitDeckPile){
    		
    		MoveCards.deckToDiscard(fromPile,toPile,isDiscardPileShowThree);
            isHitDeckPile = false;
            fromPile = null;
        	toPile = null;
        	this.operationCount++;
        	return true;
    	}
    	 else if(!(movePile.isEmpty())){
    		if(toPile == null){
    			MoveCards.cardsBackToFrom(movePile, fromPile);
    		}
    		else{
    			toPile.showTopCardNormal();
    			movePile.showNormal();
    			MoveCards.pileToPile(movePile, toPile,fromPile);
    			this.operationCount++;
    		}
    		fromPile = null;
        	toPile = null;
        	mIsWin = isWin();
            return true;
    	}
    	 else if(pause.isInclude(x, y)){
    		  mIsShowMenu = true;
	    	  mIsPause = true;
    	 }
    	fromPile = null;
    	toPile = null;
    	return true;
    }
	return false;
}

   @Override
 	public void refurbish() {
	   
   }
     @Override
 	public void reCycle() {
 		// TODO Auto-generated method stub
   	  controlView = null;
   	  cardPiles = null;
   	  for(CardPile pile:tablePile){
   		  pile.reCycle();
   	  }
   	  tablePile = null;
   	  for(CardPile pile:suitPile){
   		  pile.reCycle();
   	  }
   	  suitPile = null;
   	  deckPile.reCycle();
   	  deckPile = null;
   	  discardPile.reCycle();
   	  discardPile = null;
   	  movePile.reCycle();
   	  movePile = null;
   	  if(fromPile!=null){
   		  fromPile.reCycle();
   	     fromPile = null;
   	  }
   	  if(toPile!=null){
   	      toPile.reCycle();
   		  toPile = null;
   	  }
   	  mBackground = null;
   	  menus.reCycle();
   	  menus = null;
   	  mainMenu.reCycle();
   	  mainMenu = null;
   	  restart.reCycle();
   	  restart = null;
   	  resume.reCycle();
   	  resume = null;
   	  options.reCycle();
   	  options = null;
   	  exit.reCycle();
   	  exit = null;
      matchIndex = null;
      if(settingView!=null){
      settingView.reCycle();
      settingView = null;
      }
   	  System.gc();
 	}
    private void matchindexCal(){
    	if(movePile.isEmpty())
    		return;
    	for(int i = 0;i<4;i++){
    		Card temp1 = movePile.top();
			Card temp2 = suitPile[i].top();
			if(temp2==null){
				if(temp1.getmCardValue() == 1)
				   matchIndex[7+i] = movePile.getmSize()-1;
				else
					matchIndex[7+i] = -1;
			}
			else{
				int sub = temp1.getmCardValue()-temp2.getmCardValue();
				if(temp1.getmCardLand()==temp2.getmCardLand() && sub==1)
					matchIndex[7+i] = movePile.getmSize()-1;
				else
					matchIndex[7+i] = -1;
			}
			Log.v("suit"+i, ""+matchIndex[7+i]);
		}
    	
    	for(int i = 0;i<7;i++){
            if(fromPile!=tablePile[i]){
			Card temp1 = movePile.top();
			Card temp2 = tablePile[i].top();
			if(temp2==null ){
                if((temp1.getmCardValue()+ movePile.getmSize()-1 )== 13)
                	  matchIndex[i] = 0;
                else
                	 matchIndex[i] = -1;
			}
			else{
				int sub = temp2.getmCardValue()-temp1.getmCardValue();
				if((sub <= movePile.getmSize() && sub > 0) && ((temp1.mIsBlack==temp2.mIsBlack && sub%2==0) || (temp1.mIsBlack!=temp2.mIsBlack && sub%2==1)))
					 matchIndex[i] = movePile.getmSize() - sub;
				else
					matchIndex[i] = -1;
			   }
			 
    	    }
            else
            	matchIndex[i] = -1;
            Log.v("table"+i, ""+matchIndex[i]);
       }
    }
   
    private void restart(){
    	mIsShowMenu = false;
		mIsPause = false;
		mIsWin = false;
		mIsGameOver = false;
		mIsShowSetting = false;
		isHitDeckPile = false;
	   	for(int i =0;i<11;i++)
			matchIndex[i] = -1;
	   	time = 0;
	   	initTime =0;
	   	beginTime = new Date();
	   	operationCount = 0;
	   	fromPile = null;
	   	toPile = null;
	   	lastToPile = null;
    	List<Card> cards= new ArrayList<Card>();
        List<Card> cl = deckPile.removeCard(0);
        for(Card card:cl)
        	cards.add(card);
        cl.clear();
        cl = discardPile.removeCard(0);
        for(Card card:cl)
        	cards.add(card);
        cl.clear();
        for(int c=0;c<4;c++){
          cl = suitPile[c].removeCard(0);
          for(Card card:cl)
        	cards.add(card);
          cl.clear();
        }
        for(int c=0;c<7;c++){
            cl = tablePile[c].removeCard(0);
            for(Card card:cl)
          	  cards.add(card);
            cl.clear();
        }
        cl = movePile.removeCard(0);
        for(Card card:cl)
        	cards.add(card);
        cl.clear();
        Random random = new Random();
	   	  //deckPile
	   	  for(int i = 0;i < 24;i++){
	   		  Card c = cards.remove(random.nextInt(cards.size()));
	   		  deckPile.addCard(c);
	   	  }
	   	  for(int i = 0;i<7;i++){
	   		  for(int j =0;j<=i;j++){
	   			Card c = cards.remove(random.nextInt(cards.size()));
	   			c.setmIsTurned(false);
	   			tablePile[i].addCard(c);
	   		  }
	   		tablePile[i].turnTopCard();
	   		}
    }
	
    private void loadGame(){
    	mIsCreated = true;
    	//游戏存档
    	Properties properties = new Properties();
		try
		{
			FileInputStream inputstream = getContext().openFileInput(GameFile.saveFile);
			properties.load(inputstream);
		}
		catch (FileNotFoundException e)
		{
			
			mIsCreated = false;
			return;
		}
		catch (IOException e)
		{   
			mIsCreated = false;
			return;
		}
		//检测是否有存档
		if(properties.get("havesave")==null){
			mIsCreated = false;
			return;
		}
		// 游戏设置
		loadSetting();
		//初始化
        int backSeparation,frontSeparation;
	    float separation;
		for(int i =0;i<11;i++)
			matchIndex[i] = -1;
		cardWidth = Screen.width / 11;
		cardHeight = (float)(cardWidth*1.5);
		backSeparation = (int)cardHeight/10;
		frontSeparation = (int)cardHeight/4;
		separation = cardWidth/3.0f;
		initPosition();
		//背景
		mBackground = ((BitmapDrawable)getResources().getDrawable(mBackgroundBitmapId)).getBitmap();
		Matrix mMatrix = new Matrix();
		float scaleW = (float)(Screen.width)/mBackground.getWidth();
		float scaleH = (float)(Screen.height)/mBackground.getHeight();
		mMatrix.postScale(scaleW, scaleH);
		mBackground = Bitmap.createBitmap(mBackground, 0, 0, mBackground.getWidth(),mBackground.getHeight(), mMatrix, true);
	    //牌与牌堆
          Resources res = getResources();
          cardPiles = new GameObjectQueue("GameScreen");
  	   	  CardPictureGenerator cf = CardPictureGeneratorFactory.getCardPictureGenerator(currentCardStyle);
  	   	  int size ;
	   	  //deckPile
	   	  deckPile = new DeckPile(deckPileX, deckPileY, res,cardWidth,cardHeight);
	   	  size = Integer.valueOf((String)(properties.get("deckPileSize")));
	   	  for(int i = 0;i<size;i++){
	   		  String str = (String)(properties.get("deckPileCard"+i));
	   		  Card temp = new Card(getCardValue(str), getCardLand(str), cardWidth, cardHeight, res, cf);
	   		  temp.setmIsTurned(false);
	   		  deckPile.addCard(temp);
	   	  }
	   		  
	   	  cardPiles.put("deckPile",deckPile );
	   	  //suitPile
	   	  for(int i=0;i<4;i++){
	   		suitPile[i] = new SuitPile(suitPileX[i], suitPileY[i],  res,cardWidth,cardHeight);
	   		 size = Integer.valueOf((String)(properties.get("suitPile"+i+"Size")));
		   	  for(int j = 0;j<size;j++){
		   		  String str = (String)(properties.get("suitPile"+i+"Card"+j));
		   		  Card temp = new Card(getCardValue(str), getCardLand(str), cardWidth, cardHeight, res, cf);
		   		  temp.setmIsTurned(true);
		   		  suitPile[i].addCard(temp);
		   	  }
	   		cardPiles.put("suitPile"+(i+1), suitPile[i]);
	   	  }
	   	  //deckPile
	   	isDiscardPileShowThree = Boolean.valueOf((String)(properties.get("gameModel")));
	   	size = Integer.valueOf((String)(properties.get("discardPileSize")));
	   	  if(!isDiscardPileShowThree)
	   	     discardPile = new DiscardPileOne(discardPileX, discardPileY,  res,cardWidth,cardHeight);
	   	  else
	   		discardPile = new DiscardPileThere(discardPileX, discardPileY,cardWidth,cardHeight,res,separation);
	   	 for(int i = 0;i<size;i++){
	   		  String str = (String)(properties.get("discardPileCard"+i));
	   		  Card temp = new Card(getCardValue(str), getCardLand(str), cardWidth, cardHeight, res, cf);
	   		  temp.setmIsTurned(true);
	   		discardPile.addCard(temp);
	   	  }
	   	
	   	 cardPiles.put("discardPile", discardPile);
	   	   //talePile
	   	  for(int i = 0;i<7;i++){
	   		 tablePile[i] = new TablePile(tablePileX[i], tablePileY[i], res, backSeparation, frontSeparation,cardWidth,cardHeight);
	   		size = Integer.valueOf((String)(properties.get("tablePile"+i+"Size")));
		   	  for(int j = 0;j<size;j++){
		   		  String str = (String)(properties.get("tablePile"+i+"Card"+j));
		   		  Card temp = new Card(getCardValue(str), getCardLand(str), cardWidth, cardHeight, res, cf);
		   		  temp.setmIsTurned(getCardmIsTurned(str));
		   		 tablePile[i].addCard(temp);
		   	  }

				tablePile[i].turnTopCard();
	   		cardPiles.put("tablePile"+(i+1), tablePile[i]);
	   	  }
	   	  //movePile
	      movePile = new MovePile(0, 0, frontSeparation);
	    
	    //计时器
	        
            this.initTime = Integer.valueOf((String)(properties.get("usetime")));
            operationCount = Integer.valueOf((String)(properties.get("operationCount")));
			this.time = 0;
	        beginTime = new Date();
	        //暂停
	         pause = new ImageButton("暂停", res, R.raw.pause,0, 0, Screen.width/15.0f, Screen.width/15.0f) ;
	      //菜单
	        createMenu();
	}
    private CardLand getCardLand(String str){
    	return CardLand.valueOf(str.substring(0, str.indexOf(":", 0)));
    }
    private int getCardValue(String str){
    	return Integer.valueOf(str.substring(str.indexOf(":", 0)+1,str.lastIndexOf(":")));
    }
    private boolean getCardmIsTurned(String str){
    	return Boolean.valueOf(str.substring(str.lastIndexOf(":")+1,str.length()));
    }

	public void saveGame(){
		if(!isWin()){
		if(movePile.getmSize()>0){
			if(fromPile!=null)
			  MoveCards.cardsBackToFrom(movePile, fromPile);
			else
			  MoveCards.cardsBackToFrom(movePile, discardPile);
		}
		Properties properties = new Properties();
		properties.put("havesave","yes");
		properties.put("operationCount",String.valueOf(operationCount));
		if(mIsPause)
		  properties.put("usetime",String.valueOf(time));
		else
		  properties.put("usetime",String.valueOf((time+initTime)));
		properties.put("gameModel",String.valueOf(isDiscardPileShowThree));
		int size = deckPile.getmSize();
		properties.put("deckPileSize", ""+size);
		for(int i=0;i<size;i++){
			Card temp = deckPile.get(i);
			properties.put("deckPileCard"+i,temp.getmCardLand().toString()+":"+temp.getmCardValue()+":"+String.valueOf(temp.ismIsTurned()));
		}
		size = discardPile.getmSize();
		properties.put("discardPileSize", ""+size);
		for(int i=0;i<size;i++){
			Card temp = discardPile.get(i);
			properties.put("discardPileCard"+i,temp.getmCardLand().toString()+":"+temp.getmCardValue()+":"+String.valueOf(temp.ismIsTurned()));
		}
		for(int c = 0;c<4;c++){
			size = suitPile[c].getmSize();
			properties.put("suitPile"+c+"Size", ""+size);
			for(int i=0;i<size;i++){
				Card temp = suitPile[c].get(i);
				properties.put("suitPile"+c+"Card"+i,temp.getmCardLand().toString()+":"+temp.getmCardValue()+":"+String.valueOf(temp.ismIsTurned()));
			}
		}
		for(int c = 0;c<7;c++){
			size = tablePile[c].getmSize();
			properties.put("tablePile"+c+"Size", ""+size);
			for(int i=0;i<size;i++){
				Card temp = tablePile[c].get(i);
				properties.put("tablePile"+c+"Card"+i,temp.getmCardLand().toString()+":"+temp.getmCardValue()+":"+String.valueOf(temp.ismIsTurned()));
			}
		}
		try
		{
			FileOutputStream stream = getContext().openFileOutput(GameFile.saveFile, Context.MODE_WORLD_WRITEABLE);
			/* 将打包好的数据写入文件中 */
			properties.store(stream, "");
		}
		catch (FileNotFoundException e)
		{   
			//String test = (String)properties.get("deckPileSize");
			try{
				FileOutputStream outputstream = getContext().openFileOutput(GameFile.saveFile, Context.MODE_WORLD_WRITEABLE);
				properties.store(outputstream, "");
			}
			catch (FileNotFoundException e1){
				showWarning("存档文件出错了！");
				System.exit(0);
			}
			catch (IOException e1){
				showWarning("存档文件出错了！");
				System.exit(0);
			}
		}
		catch (IOException e)
		{
			showWarning("存档文件出错了！");
			System.exit(0);
		}
		}	
	}
	public int getmBackgroundBitmapId() {
		return mBackgroundBitmapId;
	}
	public void setmBackgroundBitmapId(int mBackgroundBitmapId) {
		
		this.mBackgroundBitmapId = mBackgroundBitmapId;
		saveSetting();
		mBackground = ((BitmapDrawable)getResources().getDrawable(mBackgroundBitmapId)).getBitmap();
		Matrix mMatrix = new Matrix();
		float scaleW = (float)(Screen.width)/mBackground.getWidth();
		float scaleH = (float)(Screen.height)/mBackground.getHeight();
		mMatrix.postScale(scaleW, scaleH);
		mBackground = Bitmap.createBitmap(mBackground, 0, 0, mBackground.getWidth(),mBackground.getHeight(), mMatrix, true);
	    
	}
	public boolean ismIsShowTime() {
		return mIsShowTime;
	}
	public void setmIsShowTime(boolean mIsShowTime) {
		this.mIsShowTime = mIsShowTime;
		saveSetting();
		
	}
	public boolean ismIsShowOperation() {
		return mIsShowOperation;
	}
	public void setmIsShowOperation(boolean mIsShowOperation) {
		this.mIsShowOperation = mIsShowOperation;
		saveSetting();
	}
	public boolean isRightModel() {
		return rightModel;
	}
	public void setRightModel(boolean rightModel) {
		this.rightModel = rightModel;
		saveSetting();
		updatePosition();
	}
   public void initPosition(){

		if(!this.rightModel){
			   deckPileX = Screen.width  / 11.0f;
		       deckPileY = Screen.height / 18.0f;
			   discardPileX = (float)(deckPileX + 1.5*cardWidth);
		       discardPileY = deckPileY;
			   for(int i=0;i<4;i++){
				  suitPileX[i] = 5*Screen.width/11+i*(cardWidth+cardWidth/3);
				  suitPileY[i] = deckPileY;
			   }
			   
	        }
	        else{

	 		   deckPileX = 7*Screen.width  / 11.0f;;
	 	       deckPileY = Screen.height / 18.0f;
	 		   discardPileX = (float)(deckPileX + 1.5*cardWidth);
	 		   discardPileY = deckPileY;
	 		   for(int i=0;i<4;i++){
	 			  suitPileX[i] = Screen.width/11+i*(cardWidth+cardWidth/3);
	 			  suitPileY[i] = deckPileY;
	 		   }
	 		 
	        }
		 for(int i = 0;i<7;i++){
			   tablePileX[i] = cardWidth*i+(1+i)*cardWidth/2.0f;
			   tablePileY[i] = deckPileY+cardHeight+cardHeight/5.0f;
		 }
	
	}
	public void updatePosition(){
		if(!this.rightModel){
			   deckPileX = Screen.width  / 11.0f;;
		       deckPileY = Screen.height / 18.0f;
			   deckPile.setPosition(deckPileX, deckPileY);
			   discardPileX = (float)(deckPileX + 1.5*cardWidth);
		       discardPileY = deckPileY;
		       discardPile.setPosition(discardPileX, discardPileY);
			   for(int i=0;i<4;i++){
				  suitPileX[i] = 5*Screen.width/11+i*(cardWidth+cardWidth/3);
				  suitPileY[i] = deckPileY;
				  suitPile[i].setPosition(suitPileX[i], suitPileY[i]);
			   }
	        }
	        else{

	 		   deckPileX = 7*Screen.width  / 11.0f;;
	 	       deckPileY = Screen.height / 18.0f;
	 		   deckPile.setPosition(deckPileX, deckPileY);
	 		   discardPileX = (float)(deckPileX + 1.5*cardWidth);
	 	       discardPile.setPosition(discardPileX, deckPileY);
	 		   for(int i=0;i<4;i++){
	 			  suitPileX[i] = Screen.width/11+i*(cardWidth+cardWidth/3);
	 			  suitPile[i].setPosition(suitPileX[i], deckPileY);
	 		   }
	         
	        }
	}
	public CardStyle getCurrentCardStyle() {
		return currentCardStyle;
	}
	public void setCurrentCardStyle(CardStyle currentCardStyle) {
		this.currentCardStyle = currentCardStyle;
		saveSetting();
		CardPictureGenerator cfg = CardPictureGeneratorFactory.getCardPictureGenerator(currentCardStyle);
		deckPile.setCardStyle(cfg, getResources());
		discardPile.setCardStyle(cfg, getResources());
		for(int i=0;i<4;i++){
			suitPile[i].setCardStyle(cfg, getResources());
		}
		for(int i =0;i<7;i++){
			tablePile[i].setCardStyle(cfg, getResources());
		}
		movePile.setCardStyle(cfg, getResources());
	}
    
    public void showWarning(String str){

  	  Dialog dialog = new AlertDialog.Builder(getContext())
			.setTitle("提示：")//设置标题
			.setMessage(str)//设置内容
			.setPositiveButton("确定",//设置确定按钮
			new DialogInterface.OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int whichButton){
					System.exit(0);
				}
			}).setNeutralButton("取消", 
			  new DialogInterface.OnClickListener(){
			  public void onClick(DialogInterface dialog, int whichButton){
				  System.exit(0);
			   }
		     }).create();//创建按钮
		    dialog.show();
  	  
    }
    
    public void saveSetting(){
    	Properties properties = new Properties();
		properties.put("backgroundId",String.valueOf(mBackgroundBitmapId));
		properties.put("cardStyle",currentCardStyle.toString());
		properties.put("showTime",String.valueOf(mIsShowTime));
		properties.put("showOperation",String.valueOf(mIsShowOperation));
		properties.put("rightModel",String.valueOf(rightModel));
		try
		{
			FileOutputStream stream = getContext().openFileOutput(GameFile.settingFile, Context.MODE_WORLD_WRITEABLE);
			/* 将打包好的数据写入文件中 */
			properties.store(stream, "");
		}
		catch (FileNotFoundException e)
		{   
			showWarning("配置文件出错了！");
			System.exit(0);
		}
		catch (IOException e)
		{
			showWarning("配置文件出错了！");
			System.exit(0);
		}
		
    }
    private void loadSetting(){
    	//读取配置文件
		Properties properties = new Properties();
		try
		{
			FileInputStream inputstream = getContext().openFileInput(GameFile.settingFile);
			properties.load(inputstream);
		}
		catch (FileNotFoundException e)
		{
			Properties newPro = new Properties();
			mBackgroundBitmapId = R.raw.backgroundtree;
			newPro.put("backgroundId",String.valueOf(mBackgroundBitmapId));
			currentCardStyle = CardStyle.style1;
			newPro.put("cardStyle",currentCardStyle.toString());
			mIsShowTime = true;
			newPro.put("showTime",String.valueOf(mIsShowTime));
			mIsShowOperation = true;
			newPro.put("showOperation",String.valueOf(mIsShowOperation));
			rightModel = false;
			newPro.put("rightModel",String.valueOf(rightModel));
			try{
				FileOutputStream outputstream = getContext().openFileOutput(GameFile.settingFile, Context.MODE_WORLD_WRITEABLE);
		        newPro.store(outputstream, "");
		    	FileInputStream inputstream = getContext().openFileInput(GameFile.settingFile);
				properties.load(inputstream);
			
			}
			catch (FileNotFoundException e1){
				showWarning("配置文件出错了！");
				System.exit(0);
			}
			catch (IOException e1){
				showWarning("配置文件出错了！");
				System.exit(0);
			}
		}
		catch (IOException e)
		{   
			showWarning("配置文件出错了！");
			System.exit(0);
		}
        if(properties.get("backgroundId")!=null)
		   mBackgroundBitmapId = Integer.parseInt((String)(properties.get("backgroundId")));
        else
        	mBackgroundBitmapId = R.raw.backgroundtree;
        if(properties.get("cardStyle")!=null)
		   currentCardStyle = CardStyle.valueOf((String)(properties.get("cardStyle")));
        else
        	currentCardStyle = CardStyle.style1;
        if(properties.get("showTime")!=null)
		   mIsShowTime = Boolean.valueOf((String)(properties.get("showTime")));
        else
        	mIsShowTime = true;
        if(properties.get("showOperation")!=null)
		    mIsShowOperation = Boolean.valueOf((String)(properties.get("showOperation")));
        else
        	mIsShowOperation = true;
        if(properties.get("rightModel")!=null)
		    rightModel = Boolean.valueOf((String)(properties.get("rightModel")));
		else
		    rightModel = false;    	
		
    }
	public boolean ismIsCreated() {
		return mIsCreated;
	}
	public void setmIsCreated(boolean mIsCreated) {
		this.mIsCreated = mIsCreated;
	}
    public boolean isWin(){
    	if(deckPile.isEmpty() && discardPile.isEmpty()){
    		for(int i=0;i<7;i++){
    			if(tablePile[i].getViwCount()!=tablePile[i].getmSize())
    				return false;
    		}
    		return true;
    	}
    	else
    	   return false;
    }
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		initTime = time;
		mIsPause = true;
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		if(mIsShowMenu || mIsShowSetting){
			beginTime = new Date();
			mIsPause = true;
		}
		else{
		    mIsPause = false;
		    beginTime = new Date();
		}
	}
    private void gotoMainmenuView(){

		 MainMenuView gs = new MainMenuView(getContext(),controlView);
		 controlView.setCurrentView(gs);
		 saveGame();
		 reCycle();
    }
   
    public void completeNextCard(){
    	if(mIsWin && !mIsGameOver){
    	boolean gameOver = true;
    	for(int i = 0;i < 4;i++){
    		Card temp = suitPile[i].top();
    		if(temp==null || temp.getmCardValue()!=13)
    			gameOver = false;
    		for(int j = 0;j < 7;j++){
    		  Card top = tablePile[j].top();
    		  if(top == null)
    			  continue;
    		  if(temp==null){
    			  if(top.getmCardValue()==1)
    				  suitPile[i].addCard(tablePile[j].removeCard());
    		  }
    		  else{
    			  if((top.getmCardValue()==(temp.getmCardValue()+1))&&top.getmCardLand()==temp.getmCardLand())
    				 suitPile[i].addCard(tablePile[j].removeCard());
    		  }
    		}
    		
    	}
    	mIsGameOver = gameOver;
      }
    }
    public boolean ismIsPause() {
    	return mIsPause;
    }
    public void setmIsPause(boolean mIsPause) {
    	this.mIsPause = mIsPause;
    }
    public boolean ismIsShowMenu() {
    	return mIsShowMenu;
    }
    public void setmIsShowMenu(boolean mIsShowMenu) {
    	this.mIsShowMenu = mIsShowMenu;
    }
    public boolean ismIsShowSetting() {
    	return mIsShowSetting;
    }
    public void setmIsShowSetting(boolean mIsShowSetting) {
    	this.mIsShowSetting = mIsShowSetting;
    }
}
