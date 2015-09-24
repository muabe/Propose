package com.markjmind.propose.sample.slidingdrawer;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.markjmind.propose.MotionInitor;
import com.markjmind.propose.MotionListener;
import com.markjmind.propose.Propose;

@SuppressLint("NewApi")
public class MainActivity extends Activity{
	private FrameLayout lyt;
	private TextView text;
	private ImageView bg1;
	private ObjectAnimator rightAnimator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		/** Layout init **/
		lyt = (FrameLayout)findViewById(R.id.sling_framelayout);
		text = (TextView)findViewById(R.id.text);
		bg1 = (ImageView)findViewById(R.id.bg1);  
		text.setVisibility(View.GONE);
		
		/** create animator **/
		rightAnimator = ObjectAnimator.ofFloat(lyt,View.TRANSLATION_Y,0,0);
		rightAnimator.setDuration(1000);
		
		/** create propose **/
		Propose propose = new Propose(getApplicationContext());
		propose.motionDown.play(rightAnimator); /** set down move animator **/ 
		lyt.setOnTouchListener(propose); /** set touch listener **/
		
		/**set MotionInitor for get ViewSize**/
		propose.setOnMotionInitor(new MotionInitor() {
			@Override
			public void touchDown(Propose propose) {
				float height = lyt.getHeight()-50*Propose.getDensity(getApplicationContext());
				rightAnimator.setFloatValues(0,height);
				propose.motionDown.setMotionDistance(height);
			}
			@Override
			public void touchUp(Propose arg0) {}
		});
		 
		/** set MotionListener for blur **/
		propose.motionDown.setOnMotionListener(new MotionListener() {
			@Override
			public void onStart(boolean isForward) {
				text.setVisibility(View.VISIBLE);
				text.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
			}
			@Override
			public void onScroll(long currDuration, long totalDuration, boolean isForward) {
				text.setText(currDuration*100/totalDuration+"%");
				float alpha = (float)(totalDuration-currDuration*2)/(float)totalDuration; 
				bg1.setAlpha(alpha);
			}
			@Override
			public void onEnd(boolean isForward) {
				text.setVisibility(View.GONE);
				text.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));
			}
		});
	}
}
