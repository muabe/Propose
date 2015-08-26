package com.markjmind.eduction.propose.motion1;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.markjmind.propose.Propose;

public class MainActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		TextView left_txt = (TextView)findViewById(R.id.left_txt);
		TextView right_txt = (TextView)findViewById(R.id.right_txt);
		
		/** create alpha ObjectAnimator**/
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(left_txt, View.ALPHA, 1f,0f);
		
		/** create propose, Left alpha **/
		Propose propose1 = new Propose(this);
		propose1.motionLeft.play(anim1); //Left set play animator
		propose1.motionLeft.setMotionDistance(150*Propose.getDensity(this)); //set 100dp Distance
		propose1.motionLeft.enableTabUp(false).enableFling(false);
		left_txt.setOnTouchListener(propose1);
		
		/** create alpha ObjectAnimator**/
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(right_txt, View.ALPHA, 1f,0f);
		
		/** create propose, Right alpha **/
		Propose propose2 = new Propose(this);
		propose2.motionRight.play(anim2); //Right set play animator
		propose2.motionRight.setMotionDistance(150*Propose.getDensity(this)); //set 100dp Distance
		propose2.motionRight.enableTabUp(false).enableFling(false);
		right_txt.setOnTouchListener(propose2);
		
	}
}
