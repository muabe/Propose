package com.markjmind.sample.propose.estory.common;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.ViewGroup;

import com.markjmind.propose.Propose;
import com.markjmind.propose.ProposeListener;
import com.markjmind.sample.propose.estory.R;
import com.markjmind.sample.propose.estory.book.Book;

/**
 * 
 * @author 오재웅
 * @phone 010-2898-7850
 * @email markjmind@gmail.com
 * @date 2015. 4. 29.
 */
public class LeftMenu {
	private ViewGroup menu_lyt,left_lyt,right_lyt,left_paper;
	private Propose leftMenuMotion;
	private Book book;
	
	public LeftMenu(Book book, ViewGroup menu_lyt){
		this.book = book;
		this.menu_lyt = menu_lyt;
		left_lyt = (ViewGroup) book.findViewById(R.id.left_lyt);
		right_lyt = (ViewGroup) book.findViewById(R.id.right_lyt);
		left_paper = (ViewGroup) book.findViewById(R.id.left_paper);
	}
	
	public void initLeftMenu() {
		leftMenuMotion = new Propose(menu_lyt.getContext());
		book.addBlockMotion(leftMenuMotion);
		final int point_width = menu_lyt.getWidth();
		ValueAnimator rightAnimator = ValueAnimator.ofInt(0, leftMenuMotion.getWindowWidth() / 2 - menu_lyt.getWidth());
		rightAnimator.addUpdateListener(new AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int value = (Integer) animation.getAnimatedValue();
				menu_lyt.getLayoutParams().width = point_width + value;
				menu_lyt.setLayoutParams(menu_lyt.getLayoutParams());
				menu_lyt.invalidate();
			}
		});
		rightAnimator.setDuration(1000);
		rightAnimator.setInterpolator(null);
		leftMenuMotion.motionRight.play(menu_lyt, rightAnimator, leftMenuMotion.getWindowWidth() / 2 - menu_lyt.getWidth());
		menu_lyt.setOnTouchListener(leftMenuMotion);
		leftMenuMotion.setOnProposeListener(new ProposeListener() {
			@Override
			public void onStart() {
				book.leftMotion.enableMotion(false);
				book.rightMotion.enableMotion(false);
			}

			@Override
			public void onScroll(int Direction, long currDuration, long totalDuration) {
				right_lyt.setPivotX(0);
				left_lyt.setPivotX(left_paper.getWidth());
				book.resetFolioInitor();
			}

			@Override
			public void onEnd() {
				book.leftMotion.enableMotion(true);
				book.rightMotion.enableMotion(true);
			}
		});
	}
}
