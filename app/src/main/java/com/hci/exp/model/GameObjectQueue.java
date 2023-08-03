package com.hci.exp.model;

import java.util.HashMap;
import java.util.Iterator;

import com.hci.exp.device.Screen;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;


public class GameObjectQueue extends HashMap<String, GameObject> {
	private String id=null;
	public GameObjectQueue(String id){
		super();
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Object find(String gameObjectId){
		return this.get(gameObjectId);
	}
	public GameObject[] list(){
		GameObject[] result=new GameObject[this.size()];
		Iterator iterator = this.entrySet().iterator();
		int i=0;
		while(iterator.hasNext()){
			Entry  entry = (Entry)iterator.next();
			result[i++] = (GameObject)entry.getValue(); 
		}
		return result;
	}
	public void paint(Canvas canvas,Paint paint){
		Iterator iterator = this.entrySet().iterator();
		int i=0;
		while(iterator.hasNext()){
			Entry  entry = (Entry)iterator.next();
			((GameObject)entry.getValue()).doDraw(canvas,paint); 
		}
	}
	public void reCycle(){
		id = null;
	}
}
