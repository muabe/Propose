package com.markjmind.propose.sample.estory.common;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;

import com.markjmind.propose.GestureListener;
import com.markjmind.propose.Propose;
import com.markjmind.propose.ProposeListener;
import com.markjmind.propose.sample.estory.book.RatioFrameLayout;

public class FolioUnit extends MultiMotionAnimator{
	protected int[] pageWidth ={0,0};
	protected int[] pageHeight ={0,0};
	protected String tag;
	protected ObjectAnimator turnRight,turnLeft,turnRight2,turnLeft2;
	protected long personDuration = 3000;
	protected long turnDuration = 300;
	protected float startPoint = 0f;
	protected boolean firstStart = true;
	public boolean isFaceForwad = true;
	public boolean isMotionDown = true;
	public boolean enableFling = false;
	public boolean enableTabUp = false;
	protected ArrayList<AnimatorSet> waitAnimList = new ArrayList<AnimatorSet>();
	protected UnitAnimation waitAnim;
	protected ArrayList<AnimatorSet> moveAnimList = new ArrayList<AnimatorSet>();
	protected UnitAnimation moveAnim;
	protected ArrayList<AnimatorSet> touchAnimList = new ArrayList<AnimatorSet>();
	protected UnitAnimation touchAnim;
	public float distanceRatio = 1.0f;
	protected FolioListener folioListener;
	
	
	public FolioUnit(ViewGroup... parents){
		super(parents);
	}
	
	public void setDuration(long duration){
		personDuration = duration;
	}
	
	@Override
	public void touchDown(int index, ViewGroup[] parents, Propose motion, ObjectAnimator[] anims) {
		if(pageWidth[index]!=parents[index].getWidth() || pageHeight[index]!=parents[index].getHeight()){
			pageWidth[index] = parents[index].getWidth();
			pageHeight[index] = parents[index].getHeight();
			
			View person = getViews(tag)[index];
			float fraction = ((RatioFrameLayout)parents[index]).getFractionY();
			float heightMargin =0;
			
			heightMargin = startPoint*fraction;
			//상하 움직임
			float startY = RatioFrameLayout.getTagChildXY(person)[1]*fraction-heightMargin;
			anims[0].setFloatValues(startY,pageHeight[index]-person.getHeight());
			if(isMotionDown){
				motion.motionDown.setMotionDistance((pageHeight[index]-startY-person.getHeight())*distanceRatio);
			}
			
//			//좌우 움직임
			fraction = ((RatioFrameLayout)parents[0]).getFractionX();
			float startX,endX;
			if(index==0){
				if(isFaceForwad){
					startX = 0;
					endX = parents[0].getWidth()+parents[1].getWidth()-person.getWidth();
				}else{
					startX = parents[0].getWidth()+parents[1].getWidth()-person.getWidth();
					endX = 0;
				}
			}else{
				if(isFaceForwad){
					startX = parents[0].getWidth()*-1;
					endX = parents[1].getWidth()-person.getWidth();
				}else{
					startX = parents[1].getWidth()-person.getWidth();
					endX = -parents[0].getWidth();
				}
			}
			if(isFaceForwad){
				motion.motionRight.setMotionDistance((Math.abs(endX-startX))*distanceRatio);
			}else{
				motion.motionLeft.setMotionDistance((Math.abs(startX-endX))*distanceRatio);
			}
			
			anims[1].setFloatValues(startX,endX);
			if(firstStart){
				if(moveAnim!=null){
					moveAnimList.add(moveAnim.getAnimation(index,person));
				}
				//초기 위치 잡아주기
				if(isFaceForwad){
					motion.motionRight.move(RatioFrameLayout.getTagChildXY(getViews(tag)[0])[0]*fraction*distanceRatio, false);
				}else{
					motion.motionLeft.move((1024-RatioFrameLayout.getTagChildXY(getViews(tag)[1])[0])*fraction*distanceRatio-person.getWidth()*distanceRatio, false);
				}
				
				if(startPoint>0){
					if(isMotionDown){
						motion.motionDown.move(heightMargin, false);
					}
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
		if(isMotionDown){
			motion.motionDown.play(anims[0]);
			motion.motionDown.enableFling(false).enableTabUp(false).enableSingleTabUp(false);
		}
		if(isFaceForwad){
			motion.motionRight.play(anims[1]);
			motion.motionRight.enableFling(enableFling).enableTabUp(enableTabUp);
		}else{
			motion.motionLeft.play(anims[1]);
			motion.motionLeft.enableFling(enableFling).enableTabUp(enableTabUp);
		}
		
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
			long tempDuration=0;
			long checkDuration=0;
			boolean isFirst = true;
			boolean forward = true;
			boolean tempForward = true;
			@Override
			public void onStart() {
				if(mIndex==0){
					if(folioListener!=null){
						folioListener.onStart();
					}
				}
				isFirst = true;
				checkDuration=0;
				tempDuration=0;
				startMoveAnimation();
				stopTouchAnimation();
			}
			@Override
			public void onScroll(int Direction, long currDuration, long totalDuration) {
				if(Direction==Propose.DIRECTION_RIGHT || Direction==Propose.DIRECTION_LEFT){
					if(!isFirst){
						checkDuration = checkDuration+currDuration-tempDuration;
						if(!forward && checkDuration>=5){
							backTurn();
							forward = true;
							tempForward = forward;
						}else if(forward && checkDuration<=-5){
							frontTurn();
							forward = false;
							tempForward = forward;
						}else{
							if(tempForward == forward){
								checkDuration=0;
							}
						}
					}else{
						tempForward = !forward;
					}
					isFirst = false;
					tempDuration = currDuration;
				}
			}
			@Override
			public void onEnd() {
				if(mIndex==0){
					if(folioListener!=null){
						folioListener.onEnd();
					}
				}
				stopMoveAnimation();
				startWaitAnimation();
			}
		});
	}
	
	public void frontTurn(){
			turnRight.cancel();
			turnLeft.start();
			turnRight2.cancel();
			turnLeft2.start();
	}
	public void backTurn(){
			turnLeft.cancel();
			turnRight.start();
			turnLeft2.cancel();
			turnRight2.start();
	}
	
	
	public void setAnimation(String tag,int id, Property<View, Float>... property){
		this.tag = tag;
		addView(tag,id);
		View[] views = getViews(tag);
		turnRight = ObjectAnimator.ofFloat(views[0], View.ROTATION_Y, 180,0);
		turnLeft  = ObjectAnimator.ofFloat(views[0], View.ROTATION_Y, 0,180);
		turnRight.setDuration(turnDuration);
		turnLeft.setDuration(turnDuration);
		turnRight.setInterpolator(null);
		turnLeft.setInterpolator(null);
		turnRight2 = ObjectAnimator.ofFloat(views[1], View.ROTATION_Y, 180,0);
		turnLeft2  = ObjectAnimator.ofFloat(views[1], View.ROTATION_Y, 0,180);
		turnRight2.setDuration(turnDuration);
		turnLeft2.setDuration(turnDuration);
		turnRight2.setInterpolator(null);
		turnLeft2.setInterpolator(null);
		loadOfFloat(tag, property);
		setMultyOnTouch(tag);
		
	}
	
	public void setStartHeight(float point){
		this.startPoint = point;
	}
	
	
	
	public void setMoveAnimation(UnitAnimation moveAnim){
		this.moveAnim = moveAnim;
	}
	public void startMoveAnimation(){
		if(moveAnimList.size()>0){
			Log.e("move","move 애니메이션");
			for(int i=0;i<moveAnimList.size();i++){
				moveAnimList.get(i).start();
			}
		}
	}
	public void stopMoveAnimation(){
		if(moveAnimList.size()>0){
			for(int i=0;i<moveAnimList.size();i++){
				for(Animator am : moveAnimList.get(i).getChildAnimations()){
					if(am!=null){
						am.end();	
					}
				}
			}
		}
	}
	
	public void setWaitAnimation(UnitAnimation waitAnim){
		this.waitAnim = waitAnim;
		
	}
	
	public void startWaitAnimation(){
		if(waitAnim!=null){
			stopWaitAnimation();
			waitAnimList.clear();
			View[] persons = getViews(tag);
			for(int i=0;i<persons.length;i++){
				waitAnimList.add(waitAnim.getAnimation(i,persons[i]));
				waitAnimList.get(i).start();
			}
		}
	}
	
	public void stopWaitAnimation(){
		if(waitAnimList.size()>0){
			for(int i=0;i<waitAnimList.size();i++){
				for(Animator am : waitAnimList.get(i).getChildAnimations()){
					am.end();	
				}
			}
		}
	}

	
	public void setTouchAnimation(UnitAnimation touchAnim){
		this.touchAnim = touchAnim;
		
	}
	
	public void startTouchAnimation(){
		if(touchAnim!=null){
			stopTouchAnimation();
			touchAnimList.clear();
			View[] persons = getViews(tag);
			for(int i=0;i<persons.length;i++){
				touchAnimList.add(touchAnim.getAnimation(i,persons[i]));
				touchAnimList.get(i).start();
			}
		}
	}
	
	public void stopTouchAnimation(){
		if(touchAnimList.size()>0){
			for(int i=0;i<touchAnimList.size();i++){
				for(Animator am : touchAnimList.get(i).getChildAnimations()){
					am.end();	
				}
			}
		}
	}
	
	public void setFolioListener(FolioListener listener){
		this.folioListener = listener;
	}
}