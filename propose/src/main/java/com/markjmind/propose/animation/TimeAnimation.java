package com.markjmind.propose.animation;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;

import java.util.ArrayList;

@SuppressLint("NewApi")
public class TimeAnimation implements AnimatorUpdateListener,AnimatorListener{
	private ArrayList<TimeValue> valueList = new ArrayList<>();
	private boolean isInitListener = false;
	private ValueAnimator anim;
	private AnimatorListener animationListener;
	
	public TimeAnimation(){
		anim = new ValueAnimator();
	}
	
	private void initListener(){
		TypeEvaluator<ArrayList<Long>> evaluator = new TypeEvaluator<ArrayList<Long>>() {
			@Override
			public ArrayList<Long> evaluate(float fraction, ArrayList<Long> startValue, ArrayList<Long> endValue) {
				ArrayList<Long> list = new ArrayList<>();
				for(int i=0;i<startValue.size();i++){
					list.add(startValue.get(i)+(long)((endValue.get(i)-startValue.get(i))*fraction));
				}
				return list;
			}
		};
		anim.setEvaluator(evaluator);
		anim.addUpdateListener(this);
//		for(int i=0;i<valueList.size();i++){
//			anim.addUpdateListener(valueList.get(i));
//		}
		
	}
	
	public void addTimerValue(TimeValue listener){
		isInitListener = false;
		listener.index = valueList.size();
		valueList.add(listener);
	}
	
	@Override
	public void onAnimationUpdate(ValueAnimator animation) {
		ArrayList<Long> timeValue = (ArrayList<Long>)animation.getAnimatedValue();
		for(int i=0;i<valueList.size();i++){
			valueList.get(i).onAnimationUpdate(timeValue.get(i), valueList.get(i).params);
		}
	}
	
	public void start() {
		if(valueList.size()>0){
			if(!isInitListener){
				ArrayList<Long> startValue = new ArrayList<Long>();
				ArrayList<Long> endValue = new ArrayList<Long>();
				for(int i=0;i<valueList.size();i++){
					startValue.add(valueList.get(i).start);
					endValue.add(valueList.get(i).end);
				}
				anim.setObjectValues(startValue, endValue);
				initListener();
			}
			if(animationListener!=null){
				anim.addListener(animationListener);
			}
			anim.start();
		}
	}
	
	public void setDuration(long duration){
		anim.setDuration(duration);
	}
	
	public void setAnimatorListener(AnimatorListener animationListener){
		this.animationListener = animationListener;
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
}
