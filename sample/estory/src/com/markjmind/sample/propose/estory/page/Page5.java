package com.markjmind.sample.propose.estory.page;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.markjmind.sample.propose.estory.R;
import com.markjmind.sample.propose.estory.book.Page;
import com.markjmind.sample.propose.estory.book.RatioFrameLayout;
import com.markjmind.sample.propose.estory.common.FolioListener;
import com.markjmind.sample.propose.estory.common.FolioUnitRun;
import com.markjmind.sample.propose.estory.common.UnitAnimation;

public class Page5 extends Page{

	public Page5(Context context, int layout_id) {
		super(context, layout_id);
	}

	@Override
	public void initAnimation(int index, ViewGroup pageView, Page page1, Page page2) {
		final RatioFrameLayout scale_layout1 = (RatioFrameLayout)pageView.findViewById(R.id.scale_layout);
		final RatioFrameLayout scale_layout2 = (RatioFrameLayout)page1.getView().findViewById(R.id.scale_layout);
		//lion 애니메이션
		FolioUnitRun lion = new FolioUnitRun(scale_layout2, scale_layout1);
		putPageMotion(lion.getMotions(),"lion");
		lion.setDuration(2*1000);
		lion.setAnimation("lion",R.id.lion, View.TRANSLATION_X, View.TRANSLATION_X);
		lion.setMoveAnimation(new UnitAnimation() {
			@Override
			public AnimatorSet getAnimation(int index, View person) {
				View unit = person.findViewById(R.id.anim_img);
		  		ObjectAnimator shake = ObjectAnimator.ofFloat(unit, 
		  				View.ROTATION, 0,10,-20,-40,0);
		  		ObjectAnimator jump = ObjectAnimator.ofFloat(unit, View.TRANSLATION_Y, -20,30,0);
		  		jump.setDuration(300);
		  		shake.setDuration(300);
		  		AnimatorSet walk = new AnimatorSet();
		  		jump.setRepeatCount(ObjectAnimator.INFINITE);
		  		shake.setRepeatCount(ObjectAnimator.INFINITE);
		  		walk.play(shake).with(jump);
		  		return walk;
			}
		});
		lion.setFolioListener(new FolioListener() {
			@Override
			public void onTouch(boolean isMotionStart) {
				stopSound(R.raw.lion);
			}
			@Override
			public void onTouchUp(boolean isMotionStart) {
			}
			@Override
			public void onStart() {
				playSound(R.raw.lion, false);
			}
			@Override
			public void onEnd() {
			}
		});
		
		//duck 애니메이션
		FolioUnitRun duck = new FolioUnitRun(scale_layout2, scale_layout1);
		putPageMotion(duck.getMotions(),"duck");
		duck.setDuration(8*1000);
		duck.setAnimation("duck",R.id.duck, View.TRANSLATION_X, View.TRANSLATION_X);
		duck.setMoveAnimation(new UnitAnimation() {
			@Override
			public AnimatorSet getAnimation(int index, View person) {
				View unit = person.findViewById(R.id.anim_img);
		  		ObjectAnimator shake = ObjectAnimator.ofFloat(unit, 
		  				View.ROTATION, 0,-20,0,20,0);
		  		ObjectAnimator jump = ObjectAnimator.ofFloat(unit,
		  				View.TRANSLATION_Y, 0,-10,0,-10,0);
		  		ObjectAnimator rotationY = ObjectAnimator.ofFloat(unit, 
		  				View.ROTATION_Y, 0,25,0,-25,0);
		  		jump.setDuration(500);
		  		shake.setDuration(500);
		  		rotationY.setDuration(500);
		  		AnimatorSet walk = new AnimatorSet();
		  		jump.setRepeatCount(ObjectAnimator.INFINITE);
		  		shake.setRepeatCount(ObjectAnimator.INFINITE);
		  		rotationY.setRepeatCount(ObjectAnimator.INFINITE);
		  		walk.play(shake).with(jump).with(rotationY);
		  		return walk;
			}
		});
		duck.setFolioListener(new FolioListener() {
			@Override
			public void onTouch(boolean isMotionStart) {
				stopSound(R.raw.duck);
			}
			@Override
			public void onTouchUp(boolean isMotionStart) {
			}
			@Override
			public void onStart() {
				playSound(R.raw.duck, false);
			}
			@Override
			public void onEnd() {
			}
		});
		
		
		//dog 애니메이션
		FolioUnitRun dog = new FolioUnitRun(scale_layout2, scale_layout1);
		putPageMotion(dog.getMotions(),"dog");
		dog.setDuration(2*1000+500);
		dog.setAnimation("dog",R.id.dog, View.TRANSLATION_X, View.TRANSLATION_X);
		dog.setMoveAnimation(new UnitAnimation() {
			@Override
			public AnimatorSet getAnimation(int index, View person) {
				View unit = person.findViewById(R.id.anim_img);
		  		ObjectAnimator shake = ObjectAnimator.ofFloat(unit, 
		  				View.ROTATION, 0,10,-20,-40,0);
		  		ObjectAnimator jump = ObjectAnimator.ofFloat(unit, View.TRANSLATION_Y, 0,1,0);
		  		jump.setDuration(100);
		  		shake.setDuration(100);
		  		AnimatorSet walk = new AnimatorSet();
		  		jump.setRepeatCount(ObjectAnimator.INFINITE);
		  		shake.setRepeatCount(ObjectAnimator.INFINITE);
		  		walk.play(shake).with(jump);
		  		return walk;
			}
		});
		dog.setFolioListener(new FolioListener() {
			@Override
			public void onTouch(boolean isMotionStart) {
				stopSound(R.raw.squeaky);
			}
			@Override
			public void onTouchUp(boolean isMotionStart) {
			}
			@Override
			public void onStart() {
				playSound(R.raw.squeaky, false);
			}
			@Override
			public void onEnd() {
			}
		});
		
		
		//chicken 애니메이션
		FolioUnitRun chicken = new FolioUnitRun(scale_layout2, scale_layout1);
		putPageMotion(chicken.getMotions(),"chicken");
		chicken.setDuration(5*1000);
		chicken.setAnimation("chicken",R.id.chicken, View.TRANSLATION_X, View.TRANSLATION_X);
		chicken.setMoveAnimation(new UnitAnimation() {
			@Override
			public AnimatorSet getAnimation(int index, View person) {
				View unit = person.findViewById(R.id.anim_img);
		  		ObjectAnimator shake = ObjectAnimator.ofFloat(unit, 
		  				View.ROTATION, 0,-20,-10,10,0);
		  		ObjectAnimator jump = ObjectAnimator.ofFloat(unit, View.TRANSLATION_Y, 0,-100,-40,-80,0);
		  		jump.setDuration(800);
		  		shake.setDuration(800);
		  		AnimatorSet walk = new AnimatorSet();
		  		jump.setRepeatCount(ObjectAnimator.INFINITE);
		  		shake.setRepeatCount(ObjectAnimator.INFINITE);
		  		walk.play(shake).with(jump);
		  		return walk;
			}
		});
		chicken.setFolioListener(new FolioListener() {
			@Override
			public void onTouch(boolean isMotionStart) {
				stopSound(R.raw.chicken);
			}
			@Override
			public void onTouchUp(boolean isMotionStart) {
			}
			@Override
			public void onStart() {
				playSound(R.raw.chicken, false);
			}
			@Override
			public void onEnd() {
			}
		});
		
		
		//pig 애니메이션
		FolioUnitRun pig = new FolioUnitRun(scale_layout2, scale_layout1);
		putPageMotion(pig.getMotions(),"pig");
		pig.setDuration(10*1000);
		pig.setAnimation("pig",R.id.pig, View.TRANSLATION_X, View.TRANSLATION_X);
		pig.setMoveAnimation(new UnitAnimation() {
			@Override
			public AnimatorSet getAnimation(int index, View person) {
				View unit = person.findViewById(R.id.anim_img);
		  		ObjectAnimator shake = ObjectAnimator.ofFloat(unit, 
		  				View.ROTATION, 0,20,0,-10,0);
		  		ObjectAnimator jump = ObjectAnimator.ofFloat(unit,
		  				View.TRANSLATION_Y, 0,20,0);
		  		jump.setDuration(500);
		  		shake.setDuration(500);
		  		AnimatorSet walk = new AnimatorSet();
		  		jump.setRepeatCount(ObjectAnimator.INFINITE);
		  		shake.setRepeatCount(ObjectAnimator.INFINITE);
		  		walk.play(shake).with(jump);
		  		return walk;
			}
		});
		pig.setFolioListener(new FolioListener() {
			@Override
			public void onTouch(boolean isMotionStart) {
				stopSound(R.raw.pig);
			}
			@Override
			public void onTouchUp(boolean isMotionStart) {
			}
			@Override
			public void onStart() {
				playSound(R.raw.pig, false);
			}
			@Override
			public void onEnd() {
			}
		});
		
	}

}
