package com.hci.exp.model;

import java.util.HashMap;

import android.graphics.Canvas;
import android.graphics.Paint;


/**
 * 游戏对象类
 * @author yarin
 *
 */
public abstract class GameObject
{
	//对象的ID
	private String id=null;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public abstract void loadProperties(HashMap<String, Object> properties);
	public abstract void doDraw(Canvas canvas,Paint paint);
	
	public String toString(){
		return ("id="+id+"|"+getClass().hashCode());
	}
	public abstract void reCycle();	
}

