package com.markjmind.propose.sample.slidingmenu;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.markjmind.propose.MotionListener;
import com.markjmind.propose.Propose;

public class MainActivity extends Activity{
	
	private LinearLayout sliding_lyt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		/** Layout init **/
		sliding_lyt = (LinearLayout)findViewById(R.id.sliding_lyt);
		sliding_lyt.setClickable(true);
		
		/** Animator create **/
		float start = -200*Propose.getDensity(this);
		float end = 0;
		sliding_lyt.setX(start);
		ObjectAnimator rightAnimator = ObjectAnimator.ofFloat(sliding_lyt,View.TRANSLATION_X,start,end);
		rightAnimator.setDuration(500); /** "duration" use to onClick **/
		
		/** Propose create **/
		Propose propose = new Propose(this); 
		propose.motionRight.play(rightAnimator);		 /** set right move Animator **/
		sliding_lyt.setOnTouchListener(propose); 		 /** set touch listener **/
		propose.motionRight.setMotionDistance(-start); 	 /** set Drag Distance **/		
		
		/**set MotionListener for black Alpha**/
		propose.motionRight.setOnMotionListener(new MotionListener() {
			@Override
			public void onStart(boolean isForward) {}
			@Override
			public void onScroll(long currDuration, long totalDuration, boolean isForward) {
				float alpha = (float)currDuration/totalDuration/2; // max 0.5
				findViewById(R.id.alpha_lyt).setAlpha(alpha); 
			}
			@Override
			public void onEnd(boolean isForward) {}
		});
	}
	
	
}
