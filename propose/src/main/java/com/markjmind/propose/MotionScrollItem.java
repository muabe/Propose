package com.markjmind.propose;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;

import com.markjmind.propose.listener.AnimatorAdapter;

/**
 * <br>捲土重來<br>
 *
 *
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2013-09-14
 */
@SuppressLint("NewApi") 
public class MotionScrollItem {
	protected long joinDuration = 0;
	protected long delayDuration = 0;
	protected long startDuration = 0;
	protected long endDuration = 0;
	protected long playDuration = 0;

	private ValueAnimator anim;
	
	
	private long currDuration = 0;

	private ScrollState currScrollState;
	private ScrollState forwardReady, forwardStart, reverseReady, reverseStart;

	public MotionScrollItem(ValueAnimator animator, long joinDuration){
		this.anim = animator;

		this.joinDuration = joinDuration;
		this.delayDuration = anim.getStartDelay();
		this.playDuration = anim.getDuration();
		this.startDuration = joinDuration+delayDuration;
		this.endDuration = startDuration+playDuration;

		forwardReady = new ScrollState(false) {
			@Override
			public boolean between(int index) {
				currScrollState = forwardStart;
				anim.setCurrentPlayTime(currDuration);
				scrollEvent();
				startEvent(index, true);
				return true;
			}

			@Override
			public boolean over(int index) {
				currScrollState = reverseReady;
				currDuration = playDuration;
				anim.setCurrentPlayTime(currDuration);
				scrollEvent();
				startEvent(index, true);
				endEvent(index, true);
				return true;
			}

			@Override
			public boolean lesser(int index) {
				currDuration = 0;
				return false;
			}
		};

		forwardStart = new ScrollState(false) {
			@Override
			public boolean between(int index) {
				anim.setCurrentPlayTime(currDuration);
				scrollEvent();
				return true;
			}

			@Override
			public boolean over(int index) {
				currScrollState = reverseReady;
				currDuration = playDuration;
				anim.setCurrentPlayTime(currDuration);
				scrollEvent();
				endEvent(index, true);
				return true;
			}

			@Override
			public boolean lesser(int index) {
				currScrollState = forwardReady;
				currDuration = 0;
				anim.setCurrentPlayTime(currDuration);
				scrollEvent();
				endEvent(index, false);
				return true;
			}
		};
		reverseStart = new ScrollState(true) {
			@Override
			public boolean between(int index) {
				anim.setCurrentPlayTime(currDuration);
				scrollEvent();
				return true;
			}

			@Override
			public boolean over(int index) {
				currScrollState = reverseReady;
				currDuration = playDuration;
				anim.setCurrentPlayTime(currDuration);
				scrollEvent();
				endEvent(index, true);
				return true;
			}

			@Override
			public boolean lesser(int index) {
				currScrollState = forwardReady;
				currDuration = 0;
				anim.setCurrentPlayTime(currDuration);
				scrollEvent();
				endEvent(index, false);
				return true;
			}
		};
		reverseReady = new ScrollState(true) {
			@Override
			public boolean between(int index) {
				currScrollState = reverseStart;
				anim.setCurrentPlayTime(currDuration);
				scrollEvent();
				startEvent(index, false);
				return true;
			}

			@Override
			public boolean over(int index) {
				currDuration = playDuration;
				return false;
			}

			@Override
			public boolean lesser(int index) {
				currScrollState = forwardReady;
				currDuration = 0;
				anim.setCurrentPlayTime(currDuration);
				scrollEvent();
				startEvent(index, false);
				endEvent(index, false);
				return true;
			}
		};
		this.currScrollState = forwardReady;
	}
	
	protected void reset(){
		currDuration = 0;
		this.currScrollState = forwardReady;
		anim.setCurrentPlayTime(currDuration);
	}
	
	public boolean scroll(int index, long duration){
		currDuration = duration-startDuration;

		if(currScrollState.scroll(index)){

			return true;
		}
		return false;

	}

	private void onEvent(int index, boolean start, boolean isForward){
		if(anim.getListeners()!=null){
			for(int i=0;i<anim.getListeners().size();i++){
				Animator.AnimatorListener al = anim.getListeners().get(i);
				//애니메이션 이벤트
				if(al instanceof AnimatorAdapter){
					((AnimatorAdapter)al).setReverse(!isForward);
				}
				if(start){
					al.onAnimationStart(anim);
				}else{
					al.onAnimationEnd(anim);
				}
			}
		}
	}

	private void startEvent(int index, boolean isForward){
		this.onEvent(index, true, isForward);
	}
	private void endEvent(int index, boolean isForward){
		this.onEvent(index, false, isForward);
	}

	private void scrollEvent() {
		if(anim.getListeners()!=null) {
			for (int i = 0; i < anim.getListeners().size(); i++) {
				Animator.AnimatorListener al = anim.getListeners().get(i);
				//애니메이션 이벤트
				if (al instanceof AnimatorAdapter) {
					((AnimatorAdapter) al).onScroll(anim, currScrollState.isReverse, currDuration, anim.getDuration());
				}
			}
		}
	}
	
	protected long getCurrDuration(){
		return this.currDuration;
	}
	
	protected long getEndDuration(){
		return this.endDuration;
	}

	abstract class ScrollState{
		public boolean isReverse;
		public ScrollState(boolean isReverse){
			this.isReverse = isReverse;
		}
		public boolean scroll(int index){
			boolean result = false;
			if(0<currDuration && currDuration<playDuration){
				result =  between(index);
			}else if(currDuration>=playDuration){
				result =  over(index);
			}else{
				result =  lesser(index);
			}
			return result;
		}
		public abstract boolean between(int index);
		public abstract boolean over(int index);
		public abstract boolean lesser(int index);
	}
}
