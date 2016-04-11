package com.markjmind.propose.animation;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressLint("NewApi")
public abstract class TimeValue implements AnimatorUpdateListener,AnimatorListener{
	long start=0,end=0;
	boolean forward = true;
	HashMap<String,Object> params= new HashMap<>();
	public int index = 0;
	private AnimatorListener animationListener;
	private int direction= 1;
	
	public abstract void onAnimationUpdate(long timeValue,HashMap<String,Object> params);
	
	public TimeValue addParam(String key,Object value){
		params.put(key, value);
		return this;
	}
	public TimeValue setValues(long start, long end){
		this.start = start;
		this.end = end;
		return this;
	}
	public TimeValue setForward(boolean forward){
		this.forward = forward;
		return this;
	}

	@Override
	public void onAnimationUpdate(ValueAnimator animation) {
		ArrayList<Long> timeValue = (ArrayList<Long>)animation.getAnimatedValue();
		onAnimationUpdate(timeValue.get(index), params);
	}
	
	public TimeValue setAnimatorListener(AnimatorListener animationListener, int direction){
		this.animationListener = animationListener;
		this.direction = direction;
		return this;
	}
	
	
	@Override
	public void onAnimationStart(Animator animation) {
		if(animationListener!=null){
			animationListener.onAnimationStart(animation);
		}
	}

	@Override
	public void onAnimationEnd(Animator animation) {
		if(animationListener!=null){
			animationListener.onAnimationEnd(animation);
		}
	}

	@Override
	public void onAnimationCancel(Animator animation) {
		if(animationListener!=null){
			animationListener.onAnimationCancel(animation);
		}
	}

	@Override
	public void onAnimationRepeat(Animator animation) {
		if(animationListener!=null){
			animationListener.onAnimationRepeat(animation);
		}
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	
}
