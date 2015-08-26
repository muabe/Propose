package com.markjmind.sample.propose.estory.book;

import java.util.ArrayList;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class PageManager {
	protected ViewGroup leftPageLayout, rightPageLayout;
	protected Page leftPage=null,rightPage=null;
	protected ArrayList<Page> pageList = new ArrayList<Page>();
	protected int currFolio = 0;
	protected Paper lPaper, rPaper;
	
	protected PageManager(ViewGroup leftPageLayout, ViewGroup rightPageLayout, ViewGroup leftPaperLayout, ViewGroup rightPaperLayout){
		this.leftPageLayout = leftPageLayout;
		this.rightPageLayout = rightPageLayout;
		lPaper = new Paper(leftPaperLayout, getPageView(Book.LEFT), Book.LEFT);
		rPaper = new Paper(rightPaperLayout, getPageView(Book.RIGHT), Book.RIGHT);
	}
	
	protected ViewGroup getPageLayout(int direction){
		if(direction==Book.LEFT){
			return leftPageLayout;
		}else{
			return rightPageLayout;
		}
	}
	
	private void setLeftPage(Page page){
		leftPageLayout.removeAllViews();
		if(leftPage!=null){
			leftPage.remove();
		}
		this.leftPage = page;
		if(leftPage!=null){
			if(leftPage.getView()==null){
				leftPageLayout.addView(leftPage.makeView());
			}else{
				leftPageLayout.addView(leftPage.getView());
			}
		}
	}
	
	private void setRightPage(Page page){
		rightPageLayout.removeAllViews();
		if(rightPage!=null){
			rightPage.remove();
		}
		this.rightPage = page;
		if(rightPage!=null){
			if(rightPage.getView()==null){
				rightPageLayout.addView(rightPage.makeView());
			}else{
				rightPageLayout.addView(rightPage.getView());
			}
		}
	}
	
	protected boolean isNextFolio(){
		if(pageList.size() > currFolio * 2 + 1){
			return true;
		}else{
			return false;
		}
	}
	
	protected boolean isBackFolio(){
		if(0 <= currFolio * 2 - 1){
			return true;
		}else{
			return false;
		}
	}
	
	protected void flip(int direction){
		Page front;
		Page back;
		Page newPage=null;
		if(direction==Book.LEFT){
			lPaper.showFront();
			front = leftPage;
			back = pageList.get(currFolio * 2 - 2);
			back.makeView();
			//새 페이지 
			leftPage = null;
			if (0 <= currFolio * 2 - 3) {
				newPage = pageList.get(currFolio * 2 - 3);
				setLeftPage(newPage);
				newPage.initAnimation(newPage.getIndex(), newPage.getView(), newPage, back);
			}else{
				leftPageLayout.removeAllViews();
			}
			//페이지를 페이퍼로 이동
			lPaper.flip(front, back);
			back.initAnimation(back.getIndex(), back.getView(), newPage, back);
		}else{
			rPaper.showFront();
			front = rightPage;
			back = pageList.get(currFolio * 2 + 1);
			back.makeView();
			//새 페이지 
			rightPage = null;
			if (currFolio * 2 + 2 < pageList.size()) {
				newPage = pageList.get(currFolio * 2 + 2);
				setRightPage(newPage);
				newPage.initAnimation(newPage.getIndex(),newPage.getView(), back, newPage);
			}else{
				rightPageLayout.removeAllViews();
			}
			//페이지를 페이퍼로 이동
			rPaper.flip(front, back);
			back.initAnimation(back.getIndex(), back.getView(), back,newPage);
		}
	}
	
	protected void endFlip(int direction, boolean isNext){
		if(isNext){
			if(direction==Book.LEFT){
				currFolio--;
				lPaper.getFrontPage().remove();
				Page page = lPaper.getBackPage();
				lPaper.clear();
				setRightPage(page);
			}else{
				currFolio++;
				rPaper.getFrontPage().remove();
				Page page = rPaper.getBackPage();
				rPaper.clear();
				setLeftPage(page);
			}
		}else{
			if(direction==Book.LEFT){
				lPaper.getBackPage().remove();
				Page page = lPaper.getFrontPage();
				lPaper.clear();
				setLeftPage(page);
			}else{
				rPaper.getBackPage().remove();
				Page page = rPaper.getFrontPage();
				rPaper.clear();
				setRightPage(page);
			}
		}
	}
	
	protected View getPageView(int direction){
		return getPageLayout(direction).getChildAt(0);
	}

	protected synchronized void addPage(Page page){
		page.index = pageList.size();
		pageList.add(page);
	}
	
	protected void setFolio(int folio){
		Page left=null, right=null;
		if(folio>0){
			left = pageList.get(folio*2-1);
		}
		if(folio*2<pageList.size()){
			right = pageList.get(folio*2);
		}
		setLeftPage(left);
		setRightPage(right);
		initAnimator();
		currFolio = folio;
	}
	
	protected void resetInitor(){
		if(leftPage!=null){
			leftPage.resetInitor();
		}
		if(rightPage!=null){
			rightPage.resetInitor();
		}
	}
	
	protected void initAnimator(){
		if(leftPage!=null){
			leftPage.initAnimation(leftPage.getIndex(), leftPage.getView(), leftPage, rightPage);
		}
		if(rightPage!=null){
			rightPage.initAnimation(rightPage.getIndex(), rightPage.getView(), leftPage, rightPage);
		}
	}
	
}
