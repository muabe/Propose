package com.markjmind.propose.sample.estory.common;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.markjmind.propose.GestureListener;
import com.markjmind.propose.Propose;
import com.markjmind.propose.ProposeListener;
import com.markjmind.propose.sample.estory.book.RatioFrameLayout;

public class FolioUnitRun extends FolioUnit{
	
	public FolioUnitRun(ViewGroup... parents){
		super(parents);
	}
	
	@Override
	public void touchDown(int index, ViewGroup[] parents, Propose motion, ObjectAnimator[] anims) {
		if(pageWidth[index]!=parents[index].getWidth() || pageHeight[index]!=parents[index].getHeight()){
			pageWidth[index] = parents[index].getWidth();
			pageHeight[index] = parents[index].getHeight();
			
			View person = getViews(tag)[index];
			float fraction = ((RatioFrameLayout)parents[index]).getFractionY();
			
//			//좌우 움직임
			fraction = ((RatioFrameLayout)parents[0]).getFractionX();
			float startX,endX;
			if(index==0){
				startX = parents[0].getWidth()+person.getWidth()
						-(1024-RatioFrameLayout.getTagChildXY(getViews(tag)[0])[0])*fraction*distanceRatio-person.getWidth()*distanceRatio
						;
				endX = 0;
			}else{
				startX = parents[1].getWidth()+person.getWidth()-(1024-RatioFrameLayout.getTagChildXY(getViews(tag)[1])[0])*fraction*distanceRatio-person.getWidth()*distanceRatio;
				endX = -parents[0].getWidth();
			}
			motion.motionLeft.setMotionDistance((Math.abs(startX-endX)*2)*distanceRatio);
			
			anims[0].setFloatValues(startX,endX);
			anims[1].setFloatValues(endX,startX);
			if(firstStart){
				if(moveAnim!=null){
					moveAnimList.add(moveAnim.getAnimation(index,person));
				}
				if(index==parents.length-1){
					firstStart = false;
				}
			}
			
		}
	}
	
	@Override
	public void touchUp(int index, ViewGroup[] parents, Propose motion, ObjectAnimator[] anims) {
	}
	
	@Override
	public void play(int index, Propose motion, ObjectAnimator[] anims) {
		anims[0].setDuration((long)(personDuration*distanceRatio));
		anims[0].setInterpolator(null);
		anims[1].setDuration((long)(personDuration*distanceRatio));
		anims[1].setInterpolator(null);
		motion.motionLeft.play(anims[0]).next(turnLeft2).with(turnLeft).next(anims[1]).next(turnRight2).with(turnRight);
		motion.motionLeft.enableMove(false);
		final Propose endMotion = motion;
		final int mIndex = index;
		motion.setOnGestureListener(new GestureListener() {
			@Override
			public void actionDown(boolean isMotionStart) {
				if(mIndex==0){
					if(folioListener!=null){
						folioListener.onTouch(isMotionStart);
					}
				}
				stopWaitAnimation();
				startTouchAnimation();
			}
			@Override
			public void actionUp(boolean isMotionStart) {
				if(mIndex==0){
					if(folioListener!=null){
						folioListener.onTouchUp(isMotionStart);
					}
				}
				if(!isMotionStart){
					startWaitAnimation();
				}
				stopTouchAnimation();
			}
			@Override
			public void actionMove(boolean isMotionStart) {
			}
		});
		motion.setOnProposeListener(new ProposeListener() {
			@Override
			public void onStart() {
				if(mIndex==0){
					if(folioListener!=null){
						folioListener.onStart();
					}
				}
				startMoveAnimation();
				stopWaitAnimation();
			}
			
			@Override
			public void onScroll(int Direction, long currDuration, long totalDuration) {
			}
			@Override
			public void onEnd() {
				Log.e("test","온엔드");
				stopMoveAnimation();
				startWaitAnimation();
				endMotion.motionLeft.reset();
			}
		});
		
		
	}
	
}