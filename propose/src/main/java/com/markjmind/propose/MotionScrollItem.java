package com.markjmind.propose;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;

import com.markjmind.propose.listener.AnimatorAdapter;

/**
 * <br>捲土重來<br>
 *
 * 스크롤(드래그)에 따른 애니메이션의 진행상태 감지하고 이벤트를 발생히주는 구현체 클래스이다.<br>
 * 애니메이션 진행상태는 애니메이션의 준비상태와 시작상태이며<br>
 * 준비상태는 아직 애니메이션이 play되지 않은 상태를 말한다.<br>
 * 애니메이션의 상태를 구분함과 동시에 애니메이션의 Duration을 싱크해준다.<br>
 * 애니메이션의 상태는 스테이트 패턴으로 되어있어 ScrollState로 상태를 추가 확장할수 있다.
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2013-09-14
 */
@SuppressLint("NewApi") 
public class MotionScrollItem {
	/** 추가되는 Duration*/
	protected long joinDuration = 0;
	/** 애니메이션 시작전 delay Duration*/
	protected long delayDuration = 0;
	/** 실제 애니메이션이 play시작되는 시점*/
	protected long startDuration = 0;
	/** 총 애니메이션 Duration(애니메이션의 끝과 같다) */
	protected long endDuration = 0;
	/** delay 등을 제외한 순수 애니메이션 Duration*/
	protected long playDuration = 0;

	/** 애니메이션 */
	private ValueAnimator anim;
	
	/** 현재 Duration 수치*/
	private long currDuration = 0;

	/** 현재 진행상태 */
	private ScrollState currScrollState;
	/** 진행상태 */
	private ScrollState forwardReady, forwardStart, reverseReady, reverseStart;


	/**
	 * 애니메이션 진행상태 스테이트 패턴 인터페이스<br>
	 * between : 드래그가 현재 아이템의 duration 범위 안에서 진행중 일때 처리<br>
	 * over : 드래그가 현재 아이템의 duration 범위를 벗어 났을때 처리<br>
	 * lesser :드래그가 현재 아이템의 duration 범위에 못들어 올때 처리 <br>
	 */
	abstract class ScrollState{
		public boolean isReverse;
		public ScrollState(boolean isReverse){
			this.isReverse = isReverse;
		}
		/**
		 * 상태를 분기해준다.
		 * @param index 모션의 인덱스
		 * @return 이벤트 발생 여부
		 */
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

	/**
	 * 기본 생성자
	 * @param animator 애니메이션
	 * @param joinDuration 애니메이션의 Duration
	 */
	public MotionScrollItem(ValueAnimator animator, long joinDuration){
		this.anim = animator;

		this.joinDuration = joinDuration;
		this.delayDuration = anim.getStartDelay();
		this.playDuration = anim.getDuration();
		this.startDuration = joinDuration+delayDuration;
		this.endDuration = startDuration+playDuration;

		/** 애니메이션의 준비상태 정의 */
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

		/** 애니메이션의 시작상태 정의*/
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

		/** 반대방향 애니메이션의 준비상태 정의*/
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

		/** 반대방향 애니메이션의 시작상태 정의*/
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


	/**
	 * 진행 상태와 애니메이션의 duration을 초기화한다.
	 */
	protected void reset(){
		currDuration = 0;
		this.currScrollState = forwardReady;
		anim.setCurrentPlayTime(currDuration);
	}


	/**
	 * 외부에서 드래그시 진행상태를 변경할때 사용한다.
	 * @param index 모션의 인덱스
	 * @param duration 진행할 duration
	 * @return 이벤트 발생 여부
	 */
	public boolean scroll(int index, long duration){
		currDuration = duration-startDuration;

		if(currScrollState.scroll(index)){

			return true;
		}
		return false;

	}


	/**
	 * 애니메이션의 시작과 끝의 이벤트를 발생시켜주는 구현체
	 * @param index 모션의 인덱스
	 * @param start 애니메이션의 시작여부(false일 경우 애니메이션 종료)
	 * @param isForward 애니메이션의 방향(false일 경우 반대방향)
	 */
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


	/**
	 * 외부에서 애니메이션의 시작 이벤트를 받기 위한 인터페이스
	 * @param index 모션의 인덱스
	 * @param isForward 애니메이션의 방향(false일 경우 반대방향)
	 */
	private void startEvent(int index, boolean isForward){
		this.onEvent(index, true, isForward);
	}

	/**
	 * 외부에서 애니메이션의 종료 이벤트를 받기 위한 인터페이스
	 * @param index
	 * @param isForward
	 */
	private void endEvent(int index, boolean isForward){
		this.onEvent(index, false, isForward);
	}


	/**
	 *
	 * 드래그에 따라 이벤트를 발생시켜주는 구현체<br>
	 * anim 객체에 onScroll 리스너를 등록했을 경우 이벤트를 발생시켜 준다.
	 */
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

	/**
	 * 현지 진행되고 있는 duration을 가져온다.
	 * @return 현재 duration 값
	 */
	protected long getCurrDuration(){
		return this.currDuration;
	}

	/**
	 * 애니메이션이 끝나는 시점의 duration을 가져온다.
	 * @return 종료 duration 값
	 */
	protected long getEndDuration(){
		return this.endDuration;
	}

}
