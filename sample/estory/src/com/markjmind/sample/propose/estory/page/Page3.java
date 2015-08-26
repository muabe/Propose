package com.markjmind.sample.propose.estory.page;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.markjmind.propose.MotionInitor;
import com.markjmind.propose.Propose;
import com.markjmind.propose.ProposeListener;
import com.markjmind.sample.propose.estory.R;
import com.markjmind.sample.propose.estory.book.Page;
import com.markjmind.sample.propose.estory.book.RatioFrameLayout;
import com.markjmind.sample.propose.estory.common.FolioListener;
import com.markjmind.sample.propose.estory.common.FolioUnit;
import com.markjmind.sample.propose.estory.common.UnitAnimation;

public class Page3 extends Page{
	FolioUnit girl;
	FolioUnit car;
	FolioUnit mouse;
	
	public Page3(Context context, int layout_id) {
		super(context, layout_id);
	}

	@Override
	public void initAnimation(int index, ViewGroup pageView, Page page1, Page page2) {
		final RatioFrameLayout scale_layout1 = (RatioFrameLayout)pageView.findViewById(R.id.scale_layout);
		final RatioFrameLayout scale_layout2 = (RatioFrameLayout)page1.getView().findViewById(R.id.scale_layout);
	
		//문 애니메이션
		final ImageView door = (ImageView)scale_layout1.findViewById(R.id.door2);
		Propose motion_door = new Propose(scale_layout1.getContext());
		ObjectAnimator door_anim = ObjectAnimator.ofFloat(door, View.ROTATION_Y, 0,-180);
		door.setPivotX(0f);
		door.setCameraDistance(3000 * Propose.getDensity(getContext()));
		door_anim.setDuration(700);
		motion_door.setOnMotionInitor(new MotionInitor() {
			@Override
			public void touchDown(Propose jwm) {
				int distance = door.getWidth()*2;
				jwm.motionLeft.setMotionDistance(distance);
			}
			@Override
			public void touchUp(Propose jwm) {
			}
		});
		motion_door.motionLeft.play(door_anim);
		motion_door.setOnProposeListener(new ProposeListener() {
			@Override
			public void onStart() {
				playSound(R.raw.door, false);
			}
			
			@Override
			public void onScroll(int Direction, long currDuration, long totalDuration) {
			}
			@Override
			public void onEnd() {
			}
		});
		putPageMotion(motion_door,"door2");
		door.setOnTouchListener(motion_door);
		
		//mouse 애니메이션
		mouse = new FolioUnit(scale_layout2, scale_layout1);
		mouse.isFaceForwad = false;
		mouse.distanceRatio = 0.5f;
		putPageMotion(mouse.getMotions(),"mouse");
		mouse.setAnimation("mouse",R.id.mouse, View.TRANSLATION_Y, View.TRANSLATION_X);
		mouse.setMoveAnimation(new UnitAnimation() {
			@Override
			public AnimatorSet getAnimation(int index, View person) {
		  		ObjectAnimator shake = ObjectAnimator.ofFloat(person.findViewById(R.id.anim_img), 
		  				View.ROTATION, 0,20,0,-10,0);
		  		ObjectAnimator jump = ObjectAnimator.ofFloat(person.findViewById(R.id.anim_img), View.TRANSLATION_Y, 0,10,0);
		  		jump.setDuration(100);
		  		shake.setDuration(100);
		  		AnimatorSet walk = new AnimatorSet();
		  		jump.setRepeatCount(ObjectAnimator.INFINITE);
		  		shake.setRepeatCount(ObjectAnimator.INFINITE);
		  		walk.play(shake).with(jump);
		  		return walk;
			}
		});
		mouse.setTouchAnimation(new UnitAnimation() {
			@Override
			public AnimatorSet getAnimation(int index, View person) {
				View unit = person.findViewById(R.id.anim_img);
		  		ObjectAnimator jump = ObjectAnimator.ofFloat(unit,
		  				View.TRANSLATION_Y, 0,-20,0);
		  		jump.setDuration(200);
		  		AnimatorSet walk = new AnimatorSet();
		  		walk.play(jump);
		  		return walk;
			}
		});
		mouse.setFolioListener(new FolioListener() {
			@Override
			public void onTouch(boolean isMotionStart) {
				playSound(R.raw.squeaky, false);
			}
			@Override
			public void onTouchUp(boolean isMotionStart) {
			}
			@Override
			public void onStart() {
				playSound(R.raw.mouse, false);
			}
			@Override
			public void onEnd() {
			}
		});
		
		
		
		//car 애니메이션
		car = new FolioUnit(scale_layout2, scale_layout1);
		car.isFaceForwad = false;
		car.isMotionDown = false;
		car.enableFling = true;
		car.setDuration(2000);
		putPageMotion(car.getMotions(),"car");
		car.setAnimation("car",R.id.car, View.TRANSLATION_Y, View.TRANSLATION_X);
		car.setMoveAnimation(new UnitAnimation() {
			@Override
			public AnimatorSet getAnimation(int index, View person) {
				View unit = person.findViewById(R.id.anim_img);
		  		ObjectAnimator shake = ObjectAnimator.ofFloat(unit, 
		  				View.ROTATION, 0,-1.5f,0.5f,0);
		  		shake.setDuration(100);
		  		AnimatorSet walk = new AnimatorSet();
		  		shake.setRepeatCount(ObjectAnimator.INFINITE);
		  		walk.play(shake);
		  		return walk;
			}
		});
		car.setWaitAnimation(new UnitAnimation() {
			@Override
			public AnimatorSet getAnimation(int index, View person) {
				View unit = person.findViewById(R.id.anim_img);
		  		ObjectAnimator shake = ObjectAnimator.ofFloat(unit, 
		  				View.ROTATION, 0,-1.5f,0.5f,0);
		  		shake.setDuration(150);
		  		AnimatorSet walk = new AnimatorSet();
		  		shake.setRepeatCount(ObjectAnimator.INFINITE);
		  		walk.play(shake);
		  		return walk;
			}
		});
		car.setTouchAnimation(new UnitAnimation() {
			@Override
			public AnimatorSet getAnimation(int index, View person) {
				View unit = person.findViewById(R.id.anim_img);
		  		ObjectAnimator jump = ObjectAnimator.ofFloat(unit,
		  				View.TRANSLATION_Y, 0,-20,0);
		  		jump.setDuration(200);
		  		AnimatorSet walk = new AnimatorSet();
		  		walk.play(jump);
		  		return walk;
			}
		});
		car.setFolioListener(new FolioListener() {
			@Override
			public void onTouch(boolean isMotionStart) {
				playSound(R.raw.car, false);
			}
			@Override
			public void onTouchUp(boolean isMotionStart) {
			}
			@Override
			public void onStart() {
				playSound(R.raw.car_start, false);
			}
			@Override
			public void onEnd() {
				playSound(R.raw.car_beep, false);
				stopSound(R.raw.car_start);
			}
		});
		car.startWaitAnimation();
		
		//girl 애니메이션
		girl = new FolioUnit(scale_layout2, scale_layout1);
		girl.isFaceForwad = false;
		girl.setDuration(5000);
		girl.setStartHeight(100);
		putPageMotion(girl.getMotions(),"girl");
		girl.setAnimation("girl",R.id.girl, View.TRANSLATION_Y, View.TRANSLATION_X);
		girl.setMoveAnimation(new UnitAnimation() {
			@Override
			public AnimatorSet getAnimation(int index, View person) {
				View unit = person.findViewById(R.id.anim_img);
				View unit2 = person.findViewById(R.id.anim_img2);
		  		ObjectAnimator shake = ObjectAnimator.ofFloat(unit, 
		  				View.ROTATION, 0,-10,0,10,0);
		  		ObjectAnimator jump = ObjectAnimator.ofFloat(unit,
		  				View.TRANSLATION_Y, 0,10,0);
		  		jump.setDuration(500);
		  		shake.setDuration(500);
		  		AnimatorSet walk = new AnimatorSet();
		  		jump.setRepeatCount(ObjectAnimator.INFINITE);
		  		shake.setRepeatCount(ObjectAnimator.INFINITE);
		  		
		  		ObjectAnimator shake2 = ObjectAnimator.ofFloat(unit2, 
		  				View.ROTATION, 0,-10,0,10,0);
		  		ObjectAnimator jump2 = ObjectAnimator.ofFloat(unit2,
		  				View.TRANSLATION_Y, 0,-10,0);
		  		jump2.setDuration(250);
		  		shake2.setDuration(250);
		  		jump2.setRepeatCount(ObjectAnimator.INFINITE);
		  		shake2.setRepeatCount(ObjectAnimator.INFINITE);
		  		walk.play(shake).with(jump).with(shake2).with(jump2);
		  		return walk;
			}
		});
		girl.setWaitAnimation(new UnitAnimation() {
			@Override
			public AnimatorSet getAnimation(int index, View person) {
				View unit = person.findViewById(R.id.anim_img);
				View unit2 = person.findViewById(R.id.anim_img2);
				ObjectAnimator rotation = ObjectAnimator.ofFloat(unit, View.ROTATION_Y, 0,30,0,-30,0);
	            rotation.setDuration(2000);
	            
		  		ObjectAnimator jump2 = ObjectAnimator.ofFloat(unit2,
		  				View.TRANSLATION_Y, 0,-10,0);
		  		jump2.setDuration(250);
		  		jump2.setRepeatCount(ObjectAnimator.INFINITE);
	            
	            AnimatorSet set = new AnimatorSet();
	            set.play(rotation).with(jump2);
	            rotation.setRepeatCount(ObjectAnimator.INFINITE);
				return set;
			}
		});
		girl.setTouchAnimation(new UnitAnimation() {
			@Override
			public AnimatorSet getAnimation(int index, View person) {
				View unit = person.findViewById(R.id.anim_img);
				View unit2 = person.findViewById(R.id.anim_img2);
		  		ObjectAnimator jump = ObjectAnimator.ofFloat(unit,
		  				View.TRANSLATION_Y, 0,-20,0);
		  		jump.setDuration(200);
		  		AnimatorSet walk = new AnimatorSet();
		  		
		  		ObjectAnimator shake2 = ObjectAnimator.ofFloat(unit2, 
		  				View.ROTATION, 0,360);
		  		ObjectAnimator jump2 = ObjectAnimator.ofFloat(unit2,
		  				View.TRANSLATION_Y, 0,-50,0);
		  		jump2.setDuration(250);
		  		shake2.setDuration(250);
		  		
		  		walk.play(jump).with(shake2).with(jump2);
		  		return walk;
			}
		});
		girl.setFolioListener(new FolioListener() {
			@Override
			public void onTouch(boolean isMotionStart) {
				playSound(R.raw.girl_hello, false);
			}
			@Override
			public void onTouchUp(boolean isMotionStart) {
			}
			@Override
			public void onStart() {
				playSound(R.raw.dog, true);
			}
			@Override
			public void onEnd() {
				playSound(R.raw.gril_oopsi, false);
				stopSound(R.raw.dog);
			}
		});
		girl.startWaitAnimation();
		
		
	}

	@Override
	public void dispose() {
		if(girl!=null){
			girl.stopWaitAnimation();
			car.stopWaitAnimation();
			mouse.stopWaitAnimation();
		}
	}
}
