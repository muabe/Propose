package com.markjmind.sample.alpha;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.markjmind.propose.JwAnimatorListener;
import com.markjmind.propose.MotionListener;
import com.markjmind.propose.Propose;

public class MainActivity extends Activity{
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		final ImageView flip_img =  (ImageView)findViewById(R.id.flip_img);
		flip_img.setCameraDistance(Propose.getCameraDistanceX(this)*2);
		
		/** create animator **/
		ObjectAnimator front = ObjectAnimator.ofFloat(flip_img, View.ROTATION_Y, 0,90);
		front.setDuration(500);  
		ObjectAnimator back= ObjectAnimator.ofFloat(flip_img, View.ROTATION_Y, -90,0);
		back.setDuration(500);  
		
		/** set AnimatorListener for flip **/
		front.addListener(new JwAnimatorListener() {
			@Override
			public void onStart(Animator arg0) {}
			@Override
			public void onEnd(Animator arg0) {
				flip_img.setBackgroundResource(R.drawable.girl_background);
				flip_img.setImageResource(R.drawable.girl);
				
			}
			@Override
			public void onReverseStart(Animator arg0) {
				flip_img.setBackgroundResource(R.drawable.boy_background);
				flip_img.setImageResource(R.drawable.boy);
			}
			@Override
			public void onReverseEnd(Animator arg0) {}
		});
		
		/** Propose create **/
		Propose propose = new Propose(this); 
		propose.motionRight.play(front).next(back); /** set right move Animator **/
		propose.motionRight.setMotionDistance(200*Propose.getDensity(this)); /** set Drag Distance **/	
		flip_img.setOnTouchListener(propose);	  /** set touch listener **/
		
		/**set MotionListener for Alpha**/
		propose.motionRight.setOnMotionListener(new MotionListener() {
			@Override 
			public void onStart(boolean isForward) {}
			@Override
			public void onScroll(long currDuration, long totalDuration, boolean isForward) {
				float alpha = (float)currDuration/totalDuration*0.8f;
				findViewById(R.id.back_lyt).setAlpha(alpha); 
			}
			@Override
			public void onEnd(boolean isForward) {}
		});
	}
}
