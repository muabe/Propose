package com.markjmind.propose.animation;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;

import java.util.ArrayList;

/**
 * 실제 애니메이션을 구동 및 제어를 담당하는 구현체 클래스이다.<br>
 * 여러개의 애니메이션을 조합하여 전체를 하나의 애니메이션으로 통합하여 구동시켜 준다<br>
 * 애니메이션의 구동과 함께 이에 따른 상태 이벤트를 감지하며<br>
 * 전체 애니메이션의 시작, 취소 등 애니메이션을 제어할수 있다.<br>
 * 애니메이션은 Android의 기본 애니메이션 처럼 구동되는 시간을 기준으로 한다.<br>
 * 여러개의 애니메이션을 시간을 기준으로하여 각각 TimeValue 단위로 조합하여 구동시켜준다.
 */
public class TimeAnimation implements AnimatorUpdateListener,AnimatorListener{
	/** 조합 애니메이션 리스트*/
	private ArrayList<TimeValue> valueList = new ArrayList<>();
	/** 애니메이션의 초기화 되었는지 여부*/
	private boolean isInitListener = false;
	/** 조합된 애니메이션 전체를 하나의 애니메이션으로 구동하는 객체*/
	private ValueAnimator anim;
	/** 애니메이션 변화에 따른 이벤트 감지 리스너*/
	private AnimatorListener animationListener;

	/**
	 * 초기화
	 */
	public TimeAnimation(){
		anim = new ValueAnimator();
	}

	/**
	 * 애니메이션을 초기화 한다.
	 */
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
	}
	
	public void addTimerValue(TimeValue listener){
		isInitListener = false;
		listener.index = valueList.size();
		valueList.add(listener);
	}

	/**
	 * 애니메이션이 구동되는 동안의 상태변경에 따른 이벤트를 감지하고 리스너로 전달한다.
	 * @param animation 전체 구동 애니메이션 객체
	 */
	@Override
	public void onAnimationUpdate(ValueAnimator animation) {
		ArrayList<Long> timeList = (ArrayList<Long>)animation.getAnimatedValue();
		for(int i=0;i<valueList.size();i++){
			valueList.get(i).onAnimationUpdate(timeList.get(i), valueList.get(i).params);
		}
	}

	/**
	 * 전체 애니메이션을 구동 시킨다.
	 */
	public void start() {
		if(valueList.size()>0){
			if(!isInitListener){
				ArrayList<Long> startValue = new ArrayList<>();
				ArrayList<Long> endValue = new ArrayList<>();
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


	/**
	 * 전체 애니메이션을 취소 시킨다.
	 */
	public void cancel(){
		anim.cancel();
	}


	/**
	 * 전체 애니메이션의 구동 시간을 설정한다.
	 * @param duration 구동시간
	 */
	public void setDuration(long duration){
		anim.setDuration(duration);
	}

	/**
	 * 애니메이션 상태에 따른 이벤트 리스너를 등록한다.
	 * @param animationListener AnimatorListener 객체
	 */
	public void setAnimatorListener(AnimatorListener animationListener){
		this.animationListener = animationListener;
	}

	/**
	 * 애니메이션이 시작 될때 이벤트를  받고 등록된 리스너로 이벤트를 전달한다..
	 * @param animation 해당 애니메이션 객체
	 */
	@Override
	public void onAnimationStart(Animator animation) {
		if(animationListener!=null){
			animationListener.onAnimationStart(animation);
		}
	}

	/**
	 * 애니메이션이 종료 될때 이벤트를 받고 등록된 리스너로 이벤트를 전달한다.
	 * @param animation 해당 애니메이션 객체
	 */
	@Override
	public void onAnimationEnd(Animator animation) {
		if(animationListener!=null){
			animationListener.onAnimationEnd(animation);
		}
	}

	/**
	 * 애니메이션이 취소 될때 이벤트를 받고 등록된 리스너로 이벤트를 전달한다.
	 * @param animation 해당 애니메이션 객체
	 */
	@Override
	public void onAnimationCancel(Animator animation) {
		if(animationListener!=null){
			animationListener.onAnimationCancel(animation);
		}
	}


	/**
	 * 애니메이션이 반복 될때 이벤트를 받고 등록된 리스너로 이벤트를 전달한다.
	 * @param animation 해당 애니메이션 객체
	 */
	@Override
	public void onAnimationRepeat(Animator animation) {
		if(animationListener!=null){
			animationListener.onAnimationRepeat(animation);
		}
	}
}
