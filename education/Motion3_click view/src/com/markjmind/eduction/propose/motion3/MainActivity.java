package com.markjmind.eduction.propose.motion3;

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
	private TextView move_txt,duration_txt;
	private ObjectAnimator move_anim;
	private Propose propose;
	private Button dur500,dur2000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		
		density = Propose.getDensity(this);
		move_txt = (TextView)findViewById(R.id.move_txt);
		float moveWidth = Propose.getWindowWidth(this)-100*density;
		
		/** create move ObjectAnimator**/
		move_anim = ObjectAnimator.ofFloat(move_txt, View.TRANSLATION_X, 0f,moveWidth);
		move_anim.setDuration(2000); 
				
		/** create propose, use Right motion **/
		propose = new Propose(this); 
		propose.motionRight.play(move_anim); 
		propose.motionRight.enableMove(false).enableFling(false);
		move_txt.setOnTouchListener(propose);
		
		
		
		/************* Change motionDistance *************/
		dur500 = (Button)findViewById(R.id.dur500);
		dur2000 = (Button)findViewById(R.id.dur2000);
		dur500.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				move_anim.setDuration(500); /** Reset Duration**/
				propose.motionRight.play(move_anim);
				move_txt.setText("Duration\n500");
				dur500.setTextColor(Color.RED);
				dur2000.setTextColor(Color.BLACK);
			}
		});
		dur2000.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				move_anim.setDuration(2000); /** Reset Duration**/
				propose.motionRight.play(move_anim);
				move_txt.setText("Duration\n2000");
				dur500.setTextColor(Color.BLACK);
				dur2000.setTextColor(Color.RED);
			}
		});
		
		/************* Current Duration display *************/
		duration_txt = (TextView)findViewById(R.id.duration_txt);
		propose.motionRight.setOnMotionListener(new MotionListener() {
			@Override
			public void onStart(boolean isForward) {}
			@Override
			public void onScroll(long currDuration, long totalDuration, boolean isForward) {
				duration_txt.setText("Duration "+currDuration+"/"+totalDuration);
			}
			@Override
			public void onEnd(boolean isForward) {}
		});
		
	}
	
	
}
