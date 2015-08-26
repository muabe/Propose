package com.markjmind.sample.propose.estory.book;

import java.util.Enumeration;
import java.util.Hashtable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.markjmind.propose.MotionInitor;
import com.markjmind.propose.Propose;
import com.markjmind.sample.propose.estory.sound.Sound;

/**
 * 
 * @author 오재웅
 * @phone 010-2898-7850
 * @email markjmind@gmail.com
 * @date 2015. 4. 29.
 */
public abstract class Page {
	private Sound sound;
	private Context context;
	private int layout_id;
	private ViewGroup page_view;
	protected int index;
	private Hashtable<String,Propose> motionPool = new Hashtable<String,Propose>();
	
	public abstract void initAnimation(int index, ViewGroup pageView, Page page1, Page page2);
	
	public Page(Context context, int layout_id){
		this.context = context;
		this.layout_id = layout_id;
	}
	
	protected void setSound(Sound sound){
		this.sound = sound;
	}
	
	public Context getContext(){
		return context;
	}
	
	public ViewGroup makeView(){
		page_view = (ViewGroup)((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(layout_id, null);
		motionPool.clear();
		return page_view;
	}
	
	public ViewGroup getView(){
		return page_view;
	}
	
	public void remove(){
		dispose();
		motionPool.clear();
		page_view = null;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void addInitorMotion(String key,Propose motion){
		motionPool.put(key, motion);
	}
	
	public Propose getInitorMotion(String key){
		return motionPool.get(key);
	}
	
	public void resetInitor(){
		Enumeration<String> em =  motionPool.keys();
		while(em.hasMoreElements()){
			String key = em.nextElement();
			MotionInitor mi = motionPool.get(key).getMotionInitor();
			if(mi!=null){
				mi.touchDown(motionPool.get(key));
			}
		}
	}
	
	public void playSound(int raw_id,boolean loop){
		if(sound!=null){
			sound.play(raw_id, loop);
		}
	}
	
	public void stopSound(int raw_id){
		if(sound!=null){
			sound.stop(raw_id);
		}
	}
	
   protected void putPageMotion(Propose motion, String tag){
   	 addInitorMotion(tag, motion);
   }
   
   protected void putPageMotion(Propose motion[], String tag){
   	for(int i=0;i<motion.length;i++){
   		addInitorMotion(tag+i, motion[i]);
   	}
   }
   
   public void dispose(){
	   
   }
}
