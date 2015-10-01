package com.markjmind.eduction.propose.motion2;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.markjmind.propose.MotionListener;
import com.markjmind.propose.Propose;

public class MainActivity extends Activity{
	private float density;
	private TextView move_txt,distance_txt;
	private Propose propose;
	private Button dp150,dp300;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		density = Propose.getDensity(this);
		move_txt = (TextView)findViewById(R.id.move_txt);
		float moveWidth = Propose.getWindowWidth(this)-100*density;
		
		/** create move ObjectAnimator**/
		ObjectAnimator move_anim = ObjectAnimator.ofFloat(move_txt, View.TRANSLATION_X, 0f,moveWidth);
		move_anim.setDuration(500); 
				
		/** create propose, use Right motion **/
		propose = new Propose(this); 
		propose.motionRight.play(move_anim).with(move_anim); 
		propose.motionRight.setMotionDistance(300*density);  /** Set motionDistance**/
		propose.motionRight.enableSingleTabUp(false);
		move_txt.setOnTouchListener(propose);
		
		/************* Change motionDistance *************/
		dp150 = (Button)findViewById(R.id.dp150);
		dp300 = (Button)findViewById(R.id.dp300);
		dp150.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				propose.motionRight.setMotionDistance(150*density); /** Reset motionDistance**/
				move_txt.setText("Distance\n300dp");
				dp150.setTextColor(Color.RED);
				dp300.setTextColor(Color.BLACK);
			}
		});
		dp300.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				propose.motionRight.setMotionDistance(300*density); /** Reset motionDistance**/
				move_txt.setText("Distance\n300dp");
				dp150.setTextColor(Color.BLACK);
				dp300.setTextColor(Color.RED);
			}
		});
		
		/************* Current Distance display *************/
		distance_txt = (TextView)findViewById(R.id.distance_txt);
		propose.motionRight.setOnMotionListener(new MotionListener() {
			@Override
			public void onStart(boolean isForward) {}
			@Override
			public void onScroll(long currDuration, long totalDuration, boolean isForward) {
				int currDistance = (int)(propose.motionRight.getCurrDistance()/density);
				int totalDistance = (int)(propose.motionRight.getMotionDistance()/density);
				distance_txt.setText("Distance "+currDistance+"/"+totalDistance);
			}
			@Override
			public void onEnd(boolean isForward) {}
		});
		
	}
	
	
}
