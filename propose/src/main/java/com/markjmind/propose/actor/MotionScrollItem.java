package com.markjmind.propose.actor;

import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;

/**
 * <br>捲土重來<br>
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
	// TODO TOUCH와 똑같이 VIEW가 움직이려면 화면 크게에 맞게 duration이 변경되어야해서
	// 실제 play 되는 Duration과 사용자가 지정한 Duration이 다를수 있는데 이부분에 대한 해결책을 생각해야함
	protected long playDuration = 0;

	private ValueAnimator anim;
	
	
	private long currDuration = 0;

	private ScrollState currScrollState;
	private ScrollState forwardReady, forwardStart, reverseReady, reverseStart;

	protected MotionScrollItem(ValueAnimator animator, long joinDuration){
		this.anim = animator;

		this.joinDuration = joinDuration;
		this.delayDuration = anim.getStartDelay();
		this.playDuration = anim.getDuration();
		this.startDuration = joinDuration+delayDuration;
		this.endDuration = startDuration+playDuration;

		forwardReady = new ScrollState() {
			@Override
			public boolean between(int index) {
				currScrollState = forwardStart;
				anim.setCurrentPlayTime(currDuration);
				startEvent(index, true);
				return true;
			}

			@Override
			public boolean over(int index) {
				currScrollState = reverseReady;
				currDuration = playDuration;
				anim.setCurrentPlayTime(currDuration);
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

		forwardStart = new ScrollState() {
			@Override
			public boolean between(int index) {
				anim.setCurrentPlayTime(currDuration);
				return true;
			}

			@Override
			public boolean over(int index) {
				currScrollState = reverseReady;
				currDuration = playDuration;
				anim.setCurrentPlayTime(currDuration);
				endEvent(index, true);
				return true;
			}

			@Override
			public boolean lesser(int index) {
				currScrollState = forwardReady;
				currDuration = 0;
				anim.setCurrentPlayTime(currDuration);
				endEvent(index, false);
				return true;
			}
		};
		reverseStart = new ScrollState() {
			@Override
			public boolean between(int index) {
				anim.setCurrentPlayTime(currDuration);
				return true;
			}

			@Override
			public boolean over(int index) {
				currScrollState = reverseReady;
				currDuration = playDuration;
				anim.setCurrentPlayTime(currDuration);
				endEvent(index, true);
				return true;
			}

			@Override
			public boolean lesser(int index) {
				currScrollState = forwardReady;
				currDuration = 0;
				anim.setCurrentPlayTime(currDuration);
				endEvent(index, false);
				return true;
			}
		};
		reverseReady = new ScrollState() {
			@Override
			public boolean between(int index) {
				currScrollState = reverseStart;
				anim.setCurrentPlayTime(currDuration);
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
	
	protected boolean scroll(int index, Motion motion){
		currDuration = (long)((motion.getCurrDuration()-startDuration));
		return currScrollState.scroll(index);
	}

	private void onEvent(int index, boolean start, boolean isForward){
		String msg =" : End ";
		if(start){
			msg = " : Start ";
		}
		if(isForward){
			msg = msg+"Forward";
		}else{
			msg = msg+"reverse";
		}
		if(anim.getListeners()!=null){
			for(int i=0;i<anim.getListeners().size();i++){
				AnimatorListener al = anim.getListeners().get(i);
				//애니메이션 이벤트
//				if(al instanceof JwAnimatorListener){
//					((JwAnimatorListener)al).setForward(isForward);
//					if(start){
//						((JwAnimatorListener)al).onAnimationStart(anim);
//					}else{
//						((JwAnimatorListener)al).onAnimationEnd(anim);
//					}
//				}else{
//					al.onAnimationStart(anim);
//				}
			}
		}
	}
	
	private void startEvent(int index, boolean isForward){
		this.onEvent(index, true, isForward);
	}
	private void endEvent(int index, boolean isForward){
		this.onEvent(index, false, isForward);
	}
	
	protected long getCurrDuration(){
		return this.currDuration;
	}
	
	protected long getEndDuration(){
		return this.endDuration;
	}

	abstract class ScrollState{
		public boolean scroll(int index){
			if(0<currDuration && currDuration<playDuration){
				return between(index);
			}else if(currDuration>=playDuration){
				return over(index);
			}else{
				return lesser(index);
			}
		}
		public abstract boolean between(int index);
		public abstract boolean over(int index);
		public abstract boolean lesser(int index);
	}
}
