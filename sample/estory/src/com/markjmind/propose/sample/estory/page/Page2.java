package com.markjmind.propose.sample.estory.page;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.markjmind.propose.MotionInitor;
import com.markjmind.propose.Propose;
import com.markjmind.propose.ProposeListener;
import com.markjmind.propose.sample.estory.R;
import com.markjmind.propose.sample.estory.book.Page;
import com.markjmind.propose.sample.estory.book.RatioFrameLayout;
import com.markjmind.propose.sample.estory.common.FolioListener;
import com.markjmind.propose.sample.estory.common.FolioUnit;
import com.markjmind.propose.sample.estory.common.UnitAnimation;

public class Page2 extends Page{
	FolioUnit frog;
	FolioUnit boy;
	public Page2(Context context, int layout_id) {
		super(context, layout_id);
	}

	@Override
	public void initAnimation(int index, ViewGroup pageView, Page page1, Page page2) {
		final RatioFrameLayout scale_layout1 = (RatioFrameLayout)pageView.findViewById(R.id.scale_layout);
		final RatioFrameLayout scale_layout2 = (RatioFrameLayout)page2.getView().findViewById(R.id.scale_layout);
	
		//문 애니메이션
		final ImageView door1 = (ImageView)scale_layout1.findViewById(R.id.door1);
		Propose motion_door1 = new Propose(scale_layout1.getContext());
		ObjectAnimator door_anim = ObjectAnimator.ofFloat(door1, View.ROTATION_Y, 0,-180);
		door1.setPivotX(0f);
		door1.setCameraDistance(3000 * Propose.getDensity(getContext()));
		door_anim.setDuration(700);
		motion_door1.setOnMotionInitor(new MotionInitor() {
			@Override
			public void touchDown(Propose jwm) {
				int distance = door1.getWidth()*2;
				jwm.motionLeft.setMotionDistance(distance);
			}
			@Override
			public void touchUp(Propose jwm) {
			}
		});
		motion_door1.setOnProposeListener(new ProposeListener() {
			@Override
			public void onStart() {
				stopSound(R.raw.door);
				playSound(R.raw.door, false);
			}
			
			@Override
			public void onScroll(int Direction, long currDuration, long totalDuration) {
			}
			@Override
			public void onEnd() {
			}
		});
		motion_door1.motionLeft.play(door_anim);
		putPageMotion(motion_door1,"door1");
		door1.setOnTouchListener(motion_door1);
		
		//개구리 애니메이션
		frog = new FolioUnit(scale_layout1, scale_layout2);
		frog.setAnimation("frog",R.id.frog, View.TRANSLATION_Y, View.TRANSLATION_X);
		putPageMotion(frog.getMotions(),"frog");
		frog.setMoveAnimation(new UnitAnimation() {
			@Override
			public AnimatorSet getAnimation(int index, View person) {
		  		ObjectAnimator jump = ObjectAnimator.ofFloat(person.findViewById(R.id.anim_img), View.TRANSLATION_Y, 0,person.getHeight()/20,0,person.getHeight()/2*-1,person.getHeight()/20,0,person.getHeight()/2*-1,0);
		  		jump.setDuration(1000);
		  		jump.setRepeatCount(ObjectAnimator.INFINITE);
		  		AnimatorSet set = new AnimatorSet();
		  		set.play(jump);
		  		return set;
			}
		});
		frog.setWaitAnimation(new UnitAnimation() {
			@Override
			public AnimatorSet getAnimation(int index, View person) {
				ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(person, "scaleX", 1.0f,1.07f,1.0f);
	            ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(person, "scaleY", 1.0f, 1.03f,1.0f);
	            scaleDownX.setDuration(1500);
	            scaleDownY.setDuration(1500);
	            AnimatorSet scaleDown = new AnimatorSet();
	            scaleDown.play(scaleDownX).with(scaleDownY);
	            scaleDownX.setRepeatCount(ObjectAnimator.INFINITE);
	            scaleDownY.setRepeatCount(ObjectAnimator.INFINITE);
				return scaleDown;

			}
		});
		frog.setTouchAnimation(new UnitAnimation() {
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
		
		frog.setFolioListener(new FolioListener() {
			@Override
			public void onTouch(boolean isMotionStart) {
				
			}
			@Override
			public void onTouchUp(boolean isMotionStart) {
			}
			@Override
			public void onStart() {
				playSound(R.raw.frog, true);
			}
			@Override
			public void onEnd() {
				stopSound(R.raw.frog);
			}
		});
		frog.startWaitAnimation();
		
		//boy 애니메이션
		boy = new FolioUnit(scale_layout1, scale_layout2);
		boy.setStartHeight(100);
		putPageMotion(boy.getMotions(),"boy");
		boy.setAnimation("boy",R.id.boy, View.TRANSLATION_Y, View.TRANSLATION_X);
		boy.setMoveAnimation(new UnitAnimation() {
			@Override
			public AnimatorSet getAnimation(int index, View person) {
		  		View unit = person.findViewById(R.id.anim_img);
		  		ObjectAnimator shake = ObjectAnimator.ofFloat(person.findViewById(R.id.anim_img), 
		  				View.ROTATION, 0,-10,0,10,0);
		  		ObjectAnimator jump = ObjectAnimator.ofFloat(person.findViewById(R.id.anim_img),
		  				View.TRANSLATION_Y, 0,10,0);
		  		jump.setDuration(500);
		  		shake.setDuration(500);
		  		AnimatorSet walk = new AnimatorSet();
		  		jump.setRepeatCount(ObjectAnimator.INFINITE);
		  		shake.setRepeatCount(ObjectAnimator.INFINITE);
		  		walk.play(shake).with(jump);
		  		return walk;
			}
		});
		boy.setWaitAnimation(new UnitAnimation() {
			@Override
			public AnimatorSet getAnimation(int index, View person) {
	            ObjectAnimator rotation = ObjectAnimator.ofFloat(person, View.ROTATION, 0,5,0,-5,0);
	            rotation.setDuration(1500);
	            AnimatorSet set = new AnimatorSet();
	            set.play(rotation);
	            rotation.setRepeatCount(ObjectAnimator.INFINITE);
				return set;

			}
		});
		boy.setTouchAnimation(new UnitAnimation() {
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
		boy.setFolioListener(new FolioListener() {
			@Override
			public void onTouch(boolean isMotionStart) {
				playSound(R.raw.male_hello, false);
			}
			@Override
			public void onTouchUp(boolean isMotionStart) {
			}
			@Override
			public void onStart() {
				playSound(R.raw.whistle1, false);
			}
			@Override
			public void onEnd() {
				playSound(R.raw.whistle2, false);
				stopSound(R.raw.whistle1);
			}
		});
		boy.startWaitAnimation();
		
	}

	@Override
	public void dispose() {
		if(boy!=null && frog!=null){
			boy.stopWaitAnimation();
			frog.stopWaitAnimation();
		}
	}
}
