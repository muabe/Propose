package com.markjmind.sample.propose.estory.book;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.markjmind.propose.JwAnimatorListener;
import com.markjmind.propose.Motion;
import com.markjmind.propose.MotionInitor;
import com.markjmind.propose.Propose;
import com.markjmind.propose.ProposeListener;
import com.markjmind.sample.propose.estory.R;
import com.markjmind.sample.propose.estory.sound.Sound;

/**
 * 
 * @author 오재웅
 * @phone 010-2898-7850
 * @email markjmind@gmail.com
 * @date 2015. 4. 29.
 */
public class Book {
	protected static int LEFT = -1, RIGHT = 1;
	private ViewGroup bookLayout;
	private Context context;
	public int DIRECTION = 0;
	private FrameLayout left_lyt, right_lyt;
	
	private ArrayList<Propose> block = new ArrayList<Propose>();

	public Propose leftMotion, rightMotion, upDownMotoin;
	private float cameraDistance;
	PageManager pm=null;
	private Sound sound = null;
	
	public Book(View bookLayout) {
		this.bookLayout = (ViewGroup) bookLayout;
		this.context = bookLayout.getContext();
		init();
	}

	public void setSound(Sound sound){
		this.sound = sound;
	}
	
	public View findViewById(int id) {
		return this.bookLayout.findViewById(id);
	}

	private void init() {
		cameraDistance = 10000 * Propose.getDensity(context);
		left_lyt = (FrameLayout) findViewById(R.id.left_lyt);
		right_lyt = (FrameLayout) findViewById(R.id.right_lyt);
		FrameLayout left_page = (FrameLayout) left_lyt.findViewById(R.id.page);
		FrameLayout right_page = (FrameLayout) right_lyt.findViewById(R.id.page);
		FrameLayout left_paper = (FrameLayout) findViewById(R.id.left_paper);
		FrameLayout right_paper = (FrameLayout) findViewById(R.id.right_paper);
		
		pm = new PageManager(left_page, right_page, left_paper, right_paper);
		pm.lPaper.setCameraDistance(cameraDistance);
		pm.rPaper.setCameraDistance(cameraDistance);
		
		leftMotion = new Propose(context);
		rightMotion = new Propose(context);
		leftMotion.setOnMotionInitor(new MotionInitor() {
			@Override
			public void touchUp(Propose jwm) {}
			@Override
			public void touchDown(Propose jwm) {
				int direct = (Integer)getParam("direction");
				pm.lPaper.initSize(pm.getPageView(LEFT));
				leftMotion.motionRight.setMotionDistance(pm.getPageView(direct).getWidth() * 2);
				/**메뉴 애니관련 pivot*/
				right_lyt.setPivotX(0);
				left_lyt.setPivotX(left_lyt.getWidth());
				Log.e("sfdf","left touchDown");
			}
		}.addParam("direction", LEFT));
		rightMotion.setOnMotionInitor(new MotionInitor() {
			@Override
			public void touchUp(Propose jwm) {}
			@Override
			public void touchDown(Propose jwm) {
				int direct = (Integer)getParam("direction");
				pm.rPaper.initSize(pm.getPageView(RIGHT));
				rightMotion.motionLeft.setMotionDistance(pm.getPageView(direct).getWidth() * 2);
				/**메뉴 애니관련 pivot*/
				right_lyt.setPivotX(0);
				left_lyt.setPivotX(left_lyt.getWidth());
				Log.e("sfdf","right touchDown");
			}
		}.addParam("direction", RIGHT));
		initUpDownAnimation();
		
	}

	public synchronized void addPage(Page page){
		page.setSound(sound);
		pm.addPage(page);
	}
	
	private void initUpDownAnimation() {
		upDownMotoin = new Propose(context);
		ObjectAnimator left_paper_UpDown = ObjectAnimator.ofFloat(pm.lPaper.getPaperLayout(), View.ROTATION_X, -50, 50);
		left_paper_UpDown.setDuration(700);
		ObjectAnimator right_paperUpDown = left_paper_UpDown.clone();
		right_paperUpDown.setTarget(pm.rPaper.getPaperLayout());
		ObjectAnimator left_page_UpDown = left_paper_UpDown.clone();
		left_page_UpDown.setTarget(left_lyt);
		ObjectAnimator right_page_UpDown = left_paper_UpDown.clone();
		right_page_UpDown.setTarget(right_lyt);
		left_lyt.setCameraDistance(cameraDistance);
		right_lyt.setCameraDistance(cameraDistance);
		upDownMotoin.motionUp.play(left_paper_UpDown, (int) (500 * rightMotion.density)).with(right_paperUpDown)
		.with(left_page_UpDown).with(right_page_UpDown);
		upDownMotoin.motionUp.enableFling(false).enableTabUp(false).enableSingleTabUp(false)
				.move((int) (250 * rightMotion.density));
	}

	public void setFolio(int folio){
		pm.setFolio(folio);
	}
	
	public void resetFolioInitor(){	
		pm.resetInitor();
	}
	
	public void loadBook() {
		initAnimation(LEFT);
		initAnimation(RIGHT);
	}

	private void initAnimation(int direction) {
		final int dir = direction;
		final Propose motion;
		final Motion motionSet;
		
		ViewGroup anim_paperLayout;
		if (direction == RIGHT) {
			motion = rightMotion;
			motionSet = motion.motionLeft;
			anim_paperLayout = pm.rPaper.getPaperLayout();

		} else if (direction == LEFT) {
			motion = leftMotion;
			motionSet = motion.motionRight;
			anim_paperLayout = pm.lPaper.getPaperLayout();
		} else {
			return;
		}
		
		motionSet.enableReverse(false);
		ObjectAnimator anim1 = ObjectAnimator.ofFloat(anim_paperLayout, View.ROTATION_Y, 0, -90 * dir);
		ObjectAnimator anim2 = ObjectAnimator.ofFloat(anim_paperLayout, View.ROTATION_Y, 90 * dir, 0);
		
		anim1.setDuration(500);
		anim2.setDuration(500);
		motionSet.play(anim1).next(anim2);
		motionSet.enableSingleTabUp(false);

		anim1.addListener(new JwAnimatorListener() {
			@Override
			public void onStart(Animator animation) {
			}

			@Override
			public void onReverseStart(Animator animation) {
				if(dir==LEFT){
					pm.lPaper.showFront();
				}else{
					pm.rPaper.showFront();
				}
			}

			@Override
			public void onReverseEnd(Animator animation) {
			}

			@Override
			public void onEnd(Animator animation) {
				if(dir==LEFT){
					pm.lPaper.showBack();
				}else{
					pm.rPaper.showBack();
				}

			}
		});

		motion.setOnProposeListener(new ProposeListener() {
			@Override
			public void onStart() {
				pm.flip(dir);
				enableBlcok(false);
				if (dir == LEFT) {
					rightMotion.enableMotion(false);
				}else{
					leftMotion.enableMotion(false);
				}
			}

			@Override
			public void onScroll(int Direction, long currDuration, long totalDuration) {
			}

			@Override
			public void onEnd() {
				if (Motion.STATUS.end.equals(motionSet.getStatus())) {
					motionSet.reset();
					pm.endFlip(dir, true);
				} else {
					pm.endFlip(dir, false);
				}
				if(pm.getPageView(LEFT)!=null){
					pm.lPaper.initSize(pm.getPageView(LEFT));
					leftMotion.motionRight.setMotionDistance(pm.getPageView(LEFT).getWidth() * 2);
				}
				if(pm.getPageView(RIGHT)!=null){
					pm.rPaper.initSize(pm.getPageView(RIGHT));
					rightMotion.motionLeft.setMotionDistance(pm.getPageView(RIGHT).getWidth() * 2);
				}
				/**메뉴 애니관련 pivot*/
				right_lyt.setPivotX(0);
				left_lyt.setPivotX(left_lyt.getWidth());
				
				enableBlcok(true);
				rightMotion.enableMotion(true);
				leftMotion.enableMotion(true);
			}
		});
		
		pm.getPageLayout(dir).setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				motion.onTouch(v, event);
				upDownMotoin.onTouch(v, event);
				return true;
			}
		});
	}

	public void addBlockMotion(Propose blockMotion){
		block.add(blockMotion);
	}
	
	private void enableBlcok(boolean enable){
		for(int i=0;i<block.size();i++){
			block.get(i).enableMotion(enable);
		}
	}

	public void disposeAll(){
		for(int i=0; i<pm.pageList.size();i++){
			pm.pageList.get(i).dispose();
		}
	}
}
