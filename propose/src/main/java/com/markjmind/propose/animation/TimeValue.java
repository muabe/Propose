package com.markjmind.propose.animation;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.HashMap;

/**
  * TimeValue는 하나의 애니메이션 구동하기 위한 속성 및 제어 대한 클래스이다.<br>
 *  TimeValue는 TimeAnimation 클래스에서 여러개의 애니메이션을 조합 구동시키는데 필요한 하나의 아이템이다.<br>
 *  애니메이션의 구동과 함께 이에 따른 상태 이벤트를 감지하며<br>
 *  시작, 취소 등 애니메이션을 제어할수 있다.<br>
 *  애니메이션은 Android의 기본 애니메이션 처럼 구동되는 시간을 기준으로 한다.<br>
 */
@SuppressLint("NewApi")
public abstract class TimeValue implements AnimatorUpdateListener,AnimatorListener{
	/** 애니메이션 시점*/
	long start=0,end=0;
	/** 필요한 파라미터를 map형태로 전달할수 있는 객체*/
	HashMap<String,Object> params= new HashMap<>();
	/** 애니메이션 순서*/
	public int index = 0;
	/** 애니메이션 이벤트 리스너*/
	private AnimatorListener animationListener;

	/**
	 * 애니메이션이 구동되는 동안의 상태변경에 따른 구현
	 * @param timeValue 시간
	 * @param params 파라미터
	 */
	public abstract void onAnimationUpdate(long timeValue,HashMap<String,Object> params);

	/**
	 * 파라미터를 추가한다
	 * @param key 파라미터 키
	 * @param value 파라미터 값
	 * @return 체이닝 TimeValue 객체
	 */
	public TimeValue addParam(String key,Object value){
		params.put(key, value);
		return this;
	}

	/**
	 * 애니메이션 구동에 따른 시작과 종료시간을 설정한다.
	 * @param start 시작시간
	 * @param end 종료시간
	 * @return 체이닝 TimeValue 객체
	 */
	public TimeValue setValues(long start, long end){
		this.start = start;
		this.end = end;
		return this;
	}

	/**
	 * 애니메이션이 구동되는 동안의 상태변경에 따른 이벤트를 감지하고 리스너로 전달한다.
	 * @param animation 구동 애니메이션 객체
	 */
	@Override
	public void onAnimationUpdate(ValueAnimator animation) {
		ArrayList<Long> timeValue = (ArrayList<Long>)animation.getAnimatedValue();
		onAnimationUpdate(timeValue.get(index), params);
	}


	/**
	 * 애니메이션 이벤트 리스너를 등록한다.
	 * @param animationListener 애니메이션 리스너
	 * @param direction 애니메이션 진행방향
	 * @return 체이닝 TimeValue 객체
	 */
	public TimeValue setAnimatorListener(AnimatorListener animationListener, int direction){
		this.animationListener = animationListener;
		return this;
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
		if (animationListener != null) {
			animationListener.onAnimationRepeat(animation);
		}

	}
	
}
