package com.markjmind.sample.propose.estory.book;

import android.view.View;
import android.view.ViewGroup;

import com.markjmind.sample.propose.estory.R;

public class Paper {
	
	private ViewGroup parents, paperLayout;
	protected ViewGroup front, back;
	private Page frontPage, backPage;
	private int direction;
	private float paper_x;
	private int paper_width;
	private Float cameraDistance = null;
	
	protected Paper(ViewGroup paperLayout, View pageView, int direction){
		this.paperLayout = paperLayout;
		this.front = (ViewGroup)paperLayout.findViewById(R.id.paper1);
		this.back = (ViewGroup)paperLayout.findViewById(R.id.paper2);
		this.frontPage = null;
		this.backPage = null;
		this.direction = direction;
		this.parents = (ViewGroup)paperLayout.getParent();
	}
	
	protected void initSize(View pageView){
		if(pageView==null){
			return;
		}
		front.getLayoutParams().width = pageView.getWidth();
		front.getLayoutParams().height = pageView.getHeight();
		front.setLayoutParams(front.getLayoutParams());
		
		paperLayout.setRotationY(0);
		paperLayout.setPivotY(paperLayout.getHeight() / 2);
		paper_width = paperLayout.getWidth();
		if(direction==Book.LEFT){
			paper_x = 0;
			paperLayout.setPivotX(paper_width);
		}else{
			paper_x = parents.getWidth()/2;
			paperLayout.setPivotX(0);
		}
		showFront();
	}
	
	protected ViewGroup getPaperLayout(){
		return paperLayout;
	}
	
	protected Page getFrontPage(){
		return frontPage;
	}
	
	protected Page getBackPage(){
		return backPage;
	}
	
	/**
	 * 페이지를 넘김
	 * @param frontPage
	 * @param backPage
	 */
	protected void flip(Page frontPage, Page backPage){
		this.frontPage = frontPage;
		this.backPage = backPage;
		front.addView(this.frontPage.getView());
		back.addView(this.backPage.getView());
	}
	
	/**
	 * 넘기는 페이퍼의 앞페이지를 보여줌
	 */
	protected void showFront(){
		front.setVisibility(View.VISIBLE);
		back.setVisibility(View.GONE);

		if (direction == Book.LEFT) {
			paperLayout.setX(paper_x);
			paperLayout.setPivotX(paper_width);
		} else {
			paperLayout.setX(paper_x);
			paperLayout.setPivotX(0);
		}
	}
	
	/**
	 * 넘기는 페이퍼의 뒤페이지를 보여줌
	 */
	protected void showBack(){
		front.setVisibility(View.GONE);
		back.setVisibility(View.VISIBLE);
		
		if (direction == Book.RIGHT) {
			paperLayout.setX(paper_x - paper_width * direction);
			paperLayout.setPivotX(paper_width);
		} else {
			paperLayout.setX(paper_x - paper_width * direction);
			paperLayout.setPivotX(0);
		}
	}
	
	protected void clear(){
		front.removeAllViews();
		back.removeAllViews();
		this.frontPage = null;
		this.backPage = null;
	}
	
	public void setCameraDistance(float distance){
		paperLayout.setCameraDistance(distance);
	}
}
