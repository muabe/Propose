package com.markjmind.propose;

import android.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * <br>捲土重來<br>
 * 애니메이션 조합 기능을 제공하는 클래스이다.<br>
 * MotionBuilder는 모든 모션과 애니메이션들을 총괄 관리하는 컨트롤러 역할을하며<br>
 * 개발자에겐 모션조합에 대한 개발 인터페이스를 제공한다.<br>
 * 내부적으로 애니메이션은 MotionScrollItem으로 각각 나누어 엔진에서 사용되는 재료로 만들어준다.<br>
 * Motion 클래스에서 play() 함수 체이닝시 사용된다.
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2014-08-13
 */
public class MotionBuilder implements Comparator<MotionScrollItem>{
	private Motion motion;
	private MotionScrollItem currAdapter;
	public ArrayList<MotionScrollItem> scrollItemList = new ArrayList<>();

	/**
	 * 기본 생성자
	 * @param motion Motion 객체
	 * @param playAdapter MotionScrollItem 객체
	 */
	public MotionBuilder(Motion motion, MotionScrollItem playAdapter){
		this.motion = motion;
		scrollItemList.clear();
		currAdapter = playAdapter;
		this.motion.totalDuration = playAdapter.delayDuration+playAdapter.playDuration;
		scrollItemList.add(playAdapter);
		Collections.sort(scrollItemList, this);
	}

	/**
	 * 등록할 애니메이션과 이전 애니메이션이 함께 play이 필요 할때 사용된다.
	 * @param valueAnimator 등록 애니메이션
	 * @return MotionBuilder 체이닝 객체
	 */
	public MotionBuilder with(ValueAnimator valueAnimator){
		MotionScrollItem adapter = new MotionScrollItem(valueAnimator,currAdapter.joinDuration);
		currAdapter = adapter;
		if(motion.getTotalDuration()<adapter.joinDuration+adapter.delayDuration+adapter.playDuration){
			motion.totalDuration = adapter.joinDuration+adapter.delayDuration+adapter.playDuration;
		}
		scrollItemList.add(currAdapter);
		Collections.sort(scrollItemList, this);
		return this;
	}

	/**
	 * 이전 애니메이션이 끝난후 등록할 애니메이션 play가 필요 할때 사용된다.
	 * @param valueAnimator 등록 애니메이션
	 * @return MotionBuilder 체이닝 객체
	 */
	public MotionBuilder next(ValueAnimator valueAnimator){
		MotionScrollItem adapter = new MotionScrollItem(valueAnimator, motion.totalDuration);
		currAdapter = adapter;
		motion.totalDuration += adapter.delayDuration+adapter.playDuration;
		scrollItemList.add(currAdapter);
		Collections.sort(scrollItemList, this);
		return this;
	}

	/**
	 * MotionEngine에서 진행방에 따른 MotionScrollItem 객체 정렬에 사용된다.
	 * @param lhs  정방향 MotionScrollItem 객체
	 * @param rhs 반대 방향 MotionScrollItem 객체
	 * @return 우선순위
	 */
	@Override
	public int compare(MotionScrollItem lhs, MotionScrollItem rhs) {
		return  lhs.getEndDuration()< rhs.getEndDuration() ? -1 : lhs.getEndDuration() > rhs.getEndDuration() ? 1:0;
	}

	/**
	 * 등록한 모든 애니메이션을 clear 한다.
	 */
	public void clear(){
		scrollItemList.clear();
	}
}
