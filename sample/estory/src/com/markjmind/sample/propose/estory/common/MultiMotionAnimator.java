package com.markjmind.sample.propose.estory.common;

import java.util.ArrayList;
import java.util.HashMap;

import android.animation.ObjectAnimator;
import android.util.Property;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;

import com.markjmind.propose.Propose;
import com.markjmind.propose.MotionInitor;

public abstract class MultiMotionAnimator extends MotionInitor implements OnTouchListener{
	private Propose[] motion;
	private ViewGroup[] layouts;
	private HashMap<String, View[]> views; 
	private HashMap<String, View> singleViews;
	private ArrayList<ObjectAnimator[]> animList = new ArrayList<ObjectAnimator[]>();
	
	public MultiMotionAnimator(ViewGroup... parents){
		this.layouts = parents;
		views = new HashMap<String, View[]>();
		singleViews = new HashMap<String, View>(); 
		motion = new Propose[layouts.length];
		for(int i=0;i<layouts.length;i++){
			motion[i] = new Propose(layouts[i].getContext());
		}
	}
	
	public void addView(String key, int id){
		View[] vs = new View[layouts.length];
		for(int i=0;i<vs.length;i++){
			vs[i] = layouts[i].findViewById(id);
		}
		views.put(key, vs);
	}
	
	public void addSingleView(String key, View view){
		singleViews.put(key, view);
	}
	
	public View[] getViews(String key){
		return views.get(key);
	}
	
	public View getSingleView(String key){
		return singleViews.get(key);
	}
	
	public void loadOfFloat(String key, Property<View, Float>... property){
		View[] vs = getViews(key);
		if(vs!=null){
			for(int i=0;i<motion.length;i++){
				ObjectAnimator[] obs = new ObjectAnimator[property.length];
				for(int j=0;j<property.length;j++){
					obs[j] = ObjectAnimator.ofFloat(vs[i], property[j], 0,0);
				}
				animList.add(obs);
			}
			
		}else{
			View v = getSingleView(key);
			if(v!=null){
				for(int i=0;i<motion.length;i++){
    				ObjectAnimator[] obs = new ObjectAnimator[property.length];
    				for(int j=0;j<property.length;j++){
    					obs[j] = ObjectAnimator.ofFloat(v, property[j], 0,0);
    				}
    				animList.add(obs);
    			}
			}
		}
		playMotion();
	}
	
	protected void playMotion(){
		for(int i=0;i<motion.length;i++){
			motion[i].setOnMotionInitor(this);
			play(i, motion[i],animList.get(i));
		}
	}
	public abstract void play(int index, Propose motion, ObjectAnimator[] anims);
	public abstract void touchDown(int index, ViewGroup[] parents, Propose motion, ObjectAnimator[] anims);
	public abstract void touchUp(int index, ViewGroup[] parents, Propose motion, ObjectAnimator[] anims);
	
	
	@Override
	public void touchDown(Propose jwm) {
		for(int i=0;i<motion.length;i++){
			touchDown(i, layouts, motion[i], animList.get(i));
		}
	}
	@Override
	public void touchUp(Propose jwm) {
		for(int i=0;i<motion.length;i++){
			touchUp(i, layouts, motion[i], animList.get(i));
		}
	}
	
	public void setMultyOnTouch(String key){
		View[] vs = getViews(key);
		if(vs!=null){
			for(int i=0;i<vs.length;i++){
				vs[i].setOnTouchListener(this);
			}
		}
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		for(int i=0;i<motion.length;i++){
			motion[i].onTouch(v, event);
		}
		return true;
	}
	
	public Propose[] getMotions(){
		return motion;
	}
}