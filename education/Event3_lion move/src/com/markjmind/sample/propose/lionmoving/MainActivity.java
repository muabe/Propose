package com.markjmind.sample.propose.lionmoving;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.markjmind.propose.Propose;
import com.markjmind.propose.ProposeListener;

public class MainActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		/** Init layout **/
		ViewGroup moving_lyt = (ViewGroup)findViewById(R.id.moving_lyt);
		final ImageView lion_img = (ImageView )findViewById(R.id.lion_img);
		float end = (300-70)*Propose.getDensity(this);
		
		/** create animator **/
		ObjectAnimator rightMove = ObjectAnimator.ofFloat(moving_lyt, View.TRANSLATION_X, 0,end);
		ObjectAnimator rightDown = ObjectAnimator.ofFloat(moving_lyt, View.TRANSLATION_Y, 0,end);
		rightMove.setInterpolator(null);
		rightDown.setInterpolator(null);
		rightMove.setDuration(1000);
		rightDown.setDuration(1000);
			
		/** create propose **/
		Propose propose = new Propose(this);
		propose.motionRight.play(rightMove);
		propose.motionRight.setMotionDistance(end).enableTabUp(false); // right move option
		
		propose.motionDown.play(rightDown);
		propose.motionDown.setMotionDistance(end).enableSingleTabUp(false).enableFling(false).enableTabUp(false);
		
		propose.motionRight.move(end/2);// center_horizontal lion
		propose.motionDown.move(end/2); // center_vertical lion
		moving_lyt.setOnTouchListener(propose);
	}
	
	
}
