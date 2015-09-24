package com.markjmind.propose.sample.estory.page;

import java.util.Random;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.markjmind.propose.sample.estory.R;
import com.markjmind.propose.sample.estory.book.Page;
import com.markjmind.propose.sample.estory.book.RatioFrameLayout;

public class Page4 extends Page{
	boolean isStart = false;
	CloudAnimator[] clouds=null;
	public Page4(Context context, int layout_id) {
		super(context, layout_id);
	}

	@Override
	public void initAnimation(int index, ViewGroup pageView, Page page1, Page page2) {
		if(!isStart){
			clouds = new CloudAnimator[7];
			clouds[0] = new CloudAnimator(page1.getView(),page2.getView(),R.id.cloud1).start(20000);
			clouds[1] = new CloudAnimator(page1.getView(),page2.getView(),R.id.cloud2).start(80000);
			clouds[2] = new CloudAnimator(page1.getView(),page2.getView(),R.id.cloud3).start(70000);
			clouds[3] = new CloudAnimator(page1.getView(),page2.getView(),R.id.cloud4).start(30000);
			clouds[4] = new CloudAnimator(page1.getView(),page2.getView(),R.id.cloud5).start(50000);
			clouds[5] = new CloudAnimator(page1.getView(),page2.getView(),R.id.cloud6).start(60000);
			clouds[6] = new CloudAnimator(page1.getView(),page2.getView(),R.id.cloud7).start(20000);
		}
	}
	
	class CloudAnimator implements AnimatorUpdateListener{
		ImageView front_cloud,back_cloud;
		ViewGroup back;
		float preValue = 0;
		boolean isFrist = true;
		AnimatorSet anim=null; 
		
		public CloudAnimator(ViewGroup front, ViewGroup back, int front_id){
			this.back = back;
			front_cloud = (ImageView)(front.findViewById(front_id));
			back_cloud = (ImageView)(back.findViewById(front_id));
		}
		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			int pageWidth = back.getWidth()*2;
			float frax1 = pageWidth*(Float)animation.getAnimatedValue()/(1024*2);
			float frax2 = pageWidth*(Float)animation.getAnimatedValue()/(1024*2)-back.getWidth();
			front_cloud.setX(frax1);
			back_cloud.setX(frax2);
		}
		
		public CloudAnimator start(long duration){
			float cloudWidth = RatioFrameLayout.getTagChildWidth(front_cloud);
			float start = cloudWidth*-1;
			float end = 1024*2;
			float childX = RatioFrameLayout.getTagChildXY(front_cloud)[0];
			float childY = RatioFrameLayout.getTagChildXY(front_cloud)[1];
			
			ValueAnimator transX = ValueAnimator.ofFloat(start,end);
			transX.setTarget(front_cloud);
			transX.addUpdateListener(this);
			transX.setDuration(duration);
			transX.setInterpolator(null);
			transX.setRepeatCount(ObjectAnimator.INFINITE);
			
			float transRan1 = childY+(float)Math.random()*180-90;
			float transRan2 = childY+(float)Math.random()*180-90;
			float transRan3 = childY+(float)Math.random()*180-90;
			ValueAnimator transY = ValueAnimator.ofFloat(childY,transRan1,transRan2,transRan3,childY);
			transY.setTarget(front_cloud);
			transY.addUpdateListener(new AnimatorUpdateListener() {
				
				@Override
				public void onAnimationUpdate(ValueAnimator animation) {
					float frax1 = (Float)animation.getAnimatedValue()*back.getHeight()/1536;
					front_cloud.setY(frax1);
					back_cloud.setY(frax1);
				}
			});
			transY.setDuration(duration);
			transY.setRepeatCount(ObjectAnimator.INFINITE);
			
			float[] alphaVlaues = getAlphaRandom(5);
			ObjectAnimator alpha1 = ObjectAnimator.ofFloat(front_cloud, View.ALPHA,alphaVlaues);
			ObjectAnimator alpha2 = ObjectAnimator.ofFloat(back_cloud, View.ALPHA,alphaVlaues);
			alpha1.setDuration(duration);
			alpha1.setRepeatCount(ObjectAnimator.INFINITE);
			alpha2.setDuration(duration);
			alpha2.setRepeatCount(ObjectAnimator.INFINITE);
			
			
			float[] scaleVlaues = getScaleRandom(5);
			ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(front_cloud, View.SCALE_X, scaleVlaues);
			ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(front_cloud, View.SCALE_Y, scaleVlaues);
			ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(back_cloud, View.SCALE_X, scaleVlaues);
			ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(back_cloud, View.SCALE_Y, scaleVlaues);
			scaleX1.setDuration(duration);
			scaleX1.setRepeatCount(ObjectAnimator.INFINITE);
			scaleY1.setDuration(duration);
			scaleY1.setRepeatCount(ObjectAnimator.INFINITE);
			scaleX2.setDuration(duration);
			scaleX2.setRepeatCount(ObjectAnimator.INFINITE);
			scaleY2.setDuration(duration);
			scaleY2.setRepeatCount(ObjectAnimator.INFINITE);
			
			anim = new AnimatorSet();
			anim.play(transX).with(transY).with(alpha1).with(alpha2).with(scaleX1).with(scaleY1).with(scaleX2).with(scaleY2);
			anim.start();
			transX.setCurrentPlayTime((long)((childX-start)*transX.getDuration()/end));
			transY.setCurrentPlayTime((long)((childX-start)*transX.getDuration()/end));
			return this;
		}
		private float[] getScaleRandom(int count){
			Random rand = new Random();
			float[] result = new float[count];
			for(int i=0;i<count; i++){
				if(i==0 || i+1==count){
					result[i]=1f;
				}else{
					result[i]=1f-(rand.nextFloat()-0.5f);
				}
			}
			return result;
		}
		
		private float[] getAlphaRandom(int count){
			Random rand = new Random();
			float[] result = new float[count];
			for(int i=0;i<count; i++){
				if(i==0 || i+1==count){
					result[i]=0.8f;
				}else{
					result[i]=rand.nextFloat()+0.3f;
					if(result[i]>1f){
						result[i] = 1f;
					}
				}
				
			}
			return result;
		}
		
		public void dispose(){
			   if(anim!=null && anim.isRunning()){
				   anim.cancel();
			   }
		}
	}
	
	@Override
	public void dispose() {
//		if(clouds!=null){
//			for(int i=0;i<clouds.length;i++){
//				clouds[i].dispose();
//			}
//		}
	}
}
