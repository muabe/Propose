package com.markjmind.eduction.propose.basic1;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.markjmind.eduction.propose.basic1.R;
import com.markjmind.propose.Propose;

public class MainActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		TextView textView1 = (TextView)findViewById(R.id.textView1);
		float move = 200*getResources().getDisplayMetrics().density;//move 200dp
		
		/** create ObjectAnimator**/
		final ObjectAnimator rightMove = ObjectAnimator.ofFloat(textView1, View.TRANSLATION_X, 0,move);
		rightMove.setDuration(1000);
		rightMove.setInterpolator(null);
		
		/** create propose for all motion **/
		Propose pro = new Propose(this);
		pro.motionRight.play(rightMove);
		pro.motionRight.setMotionDistance(move); //set Drag Distance
		textView1.setOnTouchListener(pro);
	}
}
