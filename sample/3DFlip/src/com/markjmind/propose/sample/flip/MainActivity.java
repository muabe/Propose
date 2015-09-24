package com.markjmind.propose.sample.flip;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.markjmind.propose.JwAnimatorListener;
import com.markjmind.propose.Propose;

public class MainActivity extends Activity{
	
	private FrameLayout flip_lyt;
	private ImageView boy_img, girl_img;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		/** Layout init **/
		flip_lyt = (FrameLayout)findViewById(R.id.flip_lyt);
		flip_lyt.setCameraDistance(Propose.getCameraDistanceX(this)*2);
		boy_img =  (ImageView)findViewById(R.id.boy_img);
		girl_img =  (ImageView)findViewById(R.id.girl_img);
		
		/** create animator **/
		ObjectAnimator font = ObjectAnimator.ofFloat(flip_lyt, View.ROTATION_Y, 0,90);
		font.setDuration(500);  /** "duration" use to onClick **/
		ObjectAnimator back= ObjectAnimator.ofFloat(flip_lyt, View.ROTATION_Y, -90,0);
		back.setDuration(500);  /** "duration" use to onClick **/
		
		/** Propose create **/
		Propose propose = new Propose(this); 
		propose.motionRight.play(font).next(back); /** set right move Animator **/
		propose.motionRight.setMotionDistance(200*Propose.getDensity(this)); /** set Drag Distance **/	
		flip_lyt.setOnTouchListener(propose);	  /** set touch listener **/
		
		/** set AnimatorListener for flip **/
		font.addListener(new JwAnimatorListener() {
			@Override
			public void onStart(Animator arg0) {}
			@Override
			public void onEnd(Animator arg0) {
				boy_img.setVisibility(View.INVISIBLE);
				girl_img.setVisibility(View.VISIBLE);
				flip_lyt.setBackgroundResource(R.drawable.shape_alpha2);
			}
			@Override
			public void onReverseStart(Animator arg0) {
				boy_img.setVisibility(View.VISIBLE);
				girl_img.setVisibility(View.INVISIBLE);
				flip_lyt.setBackgroundResource(R.drawable.shape_alpha1);
			}
			@Override
			public void onReverseEnd(Animator arg0) {}
		});
		
		/** set Down Motion **/
		ObjectAnimator down = ObjectAnimator.ofFloat(flip_lyt, View.ROTATION_X, 180,-180);
		down.setDuration(500);
		propose.motionDown.play(down);
		propose.motionDown.setMotionDistance(500*Propose.getDensity(this));
		propose.motionDown.enableFling(false).enableTabUp(false);
		propose.motionDown.move(propose.motionDown.getMotionDistance()/2);// vertical center
	}
	
	
}
