package com.markjmind.sample.propose.moving;

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
		ImageView lion_img = (ImageView )findViewById(R.id.lion_img);
		float end = (300-70)*Propose.getDensity(this);
		
		/** create animator **/
		ObjectAnimator rightMove = ObjectAnimator.ofFloat(moving_lyt, View.TRANSLATION_X, 0,end);
		ObjectAnimator rightDown = ObjectAnimator.ofFloat(moving_lyt, View.TRANSLATION_Y, 0,end);
		ObjectAnimator shake = ObjectAnimator.ofFloat(lion_img,View.ROTATION, 0,-10,20,40,0,-10,20,40,0,-10,20,40,0,-10,20,40,0);
		rightMove.setInterpolator(null);
		rightDown.setInterpolator(null);
		rightMove.setDuration(1000);
		rightDown.setDuration(1000);
		shake.setDuration(1000);
		
		/** create propose **/
		Propose propose = new Propose(this);
		propose.motionRight.play(rightMove,(int)(end)).with(shake); /** set right move Animator**/
		propose.motionRight.enableTabUp(false); 					/** right move option**/
		propose.motionDown.play(rightDown,(int)(end));				/** set down move Animator**/
		propose.motionDown.enableSingleTabUp(false).enableFling(false).enableTabUp(false); /** right move option**/
		moving_lyt.setOnTouchListener(propose); 					/** set touch listener **/
		
		/** set MotionListener for sound **/
		propose.setOnProposeListener(new ProposeListener() {
			SoundPool soundPool;
			int soundId,playId;
			@Override
			public void onStart() { // animation start 
				if(soundPool==null){
					soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
					soundId = soundPool.load(MainActivity.this, R.raw.lion, 1);
					playId = soundPool.play(soundId, 1.0f, 1.0f, 10, 0, 1);
				}else{
					soundPool.stop(playId);
					playId = soundPool.play(soundId, 1.0f, 1.0f, 10, 0, 1);
				}
			}
			@Override
			public void onScroll(int arg0, long arg1, long arg2) {}
			@Override
			public void onEnd() {findViewById(R.id.lion_img).setRotation(0);}
		});
		
	}
	
	
}
