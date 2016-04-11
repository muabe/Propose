package com.markjmind.propose.actor;

import android.animation.ValueAnimator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2014-08-13
 */
public class MotionBuilder implements Comparator<MotionScrollItem>{
	private Motion motion;
	private MotionScrollItem currAdapter;
	public ArrayList<MotionScrollItem> scrollItemList = new ArrayList<>();
	
	public MotionBuilder(Motion motion, MotionScrollItem playAdapter){
		this.motion = motion;
		scrollItemList.clear();
		currAdapter = playAdapter;
		this.motion.totalDuration = playAdapter.delayDuration+playAdapter.playDuration;
		scrollItemList.add(playAdapter);
		Collections.sort(scrollItemList, this);
	}
	
	public MotionBuilder with(ValueAnimator valueAnimator){
		MotionScrollItem adapter = new MotionScrollItem(valueAnimator,currAdapter.joinDuration);
		currAdapter = adapter;
		if(motion.totalDuration<adapter.joinDuration+adapter.delayDuration+adapter.playDuration){
			motion.totalDuration = adapter.joinDuration+adapter.delayDuration+adapter.playDuration;
		}
		scrollItemList.add(currAdapter);
		Collections.sort(scrollItemList, this);
		return this;
	}
	
	public MotionBuilder next(ValueAnimator valueAnimator){
		MotionScrollItem adapter = new MotionScrollItem(valueAnimator, motion.totalDuration);
		currAdapter = adapter;
		motion.totalDuration += adapter.delayDuration+adapter.playDuration;
		scrollItemList.add(currAdapter);
		Collections.sort(scrollItemList, this);
		return this;
	}
	
	@Override
	public int compare(MotionScrollItem lhs, MotionScrollItem rhs) {
		return  lhs.getEndDuration()< rhs.getEndDuration() ? -1 : lhs.getEndDuration() > rhs.getEndDuration() ? 1:0;
	}

	public void clear(){
		scrollItemList.clear();
	}
}
