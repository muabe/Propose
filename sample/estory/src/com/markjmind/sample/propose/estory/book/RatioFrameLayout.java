package com.markjmind.sample.propose.estory.book;


import java.util.HashMap;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @author 오재웅
 * @phone 010-2898-7850
 * @email markjmind@gmail.com
 * @date 2015. 4. 29.
 */
public class RatioFrameLayout extends FrameLayout{

	private float density;
	private int ratioWidth=0,ratioHeight=0;
	private HashMap<View,Integer> ratioPivot;
	
	public RatioFrameLayout(Context context) {
		super(context);
		density = getContext().getResources().getDisplayMetrics().density;
	}
	public RatioFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context,attrs);
	}
	public RatioFrameLayout(Context context, AttributeSet attrs,int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context,attrs);
	}
	
	private void init(Context context, AttributeSet attrs){
		density = getContext().getResources().getDisplayMetrics().density;
		
		String tag = (String)getTag();
		if(tag!=null){
			String[] split = tag.split(",");
			ratioWidth = Integer.valueOf(split[0].trim());
			ratioHeight = Integer.valueOf(split[1].trim());
		}
		
//	    TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RatioFrameLayout, 0, 0);
//	    try {
//	        ratioWidth = ta.getInteger(R.styleable.RatioFrameLayout_ratioX, 0);
//	        ratioHeight = ta.getInteger(R.styleable.RatioFrameLayout_ratioY, 0);
//	    } finally {
//	        ta.recycle();
//	    }
	}
	
	
	
	
	
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		parentWidth = MeasureSpec.getSize(widthMeasureSpec);
		parentHeight = MeasureSpec.getSize(heightMeasureSpec);
		drawScale(parentWidth, parentHeight);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec); 
		
	}
	int parentWidth, parentHeight;
	float scale_width=1f,temp_scale_width=1f, scale_height=1f, temp_scale_height=1f;
	boolean isFirst = true;
	public void drawScale(int width, int height){
		if(ratioWidth>0 && ratioHeight>0){
			temp_scale_width = width/(float)ratioWidth;
			temp_scale_height = height/(float)ratioHeight;
			for(int i=0;i<this.getChildCount();i++){
				View view = this.getChildAt(i);
				int[] size = getTagChildSize(view);
				if(size!=null){
					LayoutParams lp = (LayoutParams) view.getLayoutParams();
					lp.width = (int)(size[0]*temp_scale_width);
					lp.height = (int)(size[1]*temp_scale_width);
					view.setLayoutParams(view.getLayoutParams());
				}
			}
		}
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		if(scale_width!=temp_scale_width || scale_height!=temp_scale_height){
			for(int i=0;i<this.getChildCount();i++){
				View view = this.getChildAt(i);
				int[] xy = getTagChildXY(view);
				if(xy!=null){
					if(isFirst){
						view.setX((float)xy[0]*temp_scale_width);
						view.setY((float)xy[1]*temp_scale_height);
//						view.setPivotX(getPivotX()*temp_scale_width);
//						view.setPivotY(getPivotY()*temp_scale_height/2);
					}else{
						view.setX(view.getX()*temp_scale_width/scale_width);
						view.setY(view.getY()*temp_scale_height/scale_height);
//						view.setPivotX(getPivotX()*temp_scale_width/scale_width);
//						view.setPivotY(getPivotY()*temp_scale_height/scale_height/2);
					}
//					Log.e("test","drawScale "+"X:"+view.getX()+" Y:"+view.getY());
				}
			}
			scale_width = temp_scale_width;
			scale_height = temp_scale_height;
			isFirst = false;
		}
		super.onLayout(changed, left, top, right, bottom);
	}
	
	public static int[] getTagChildXY(View view){
		String tag = (String)view.getTag();
		int[] point = null;
		if(tag!=null){
			String[] split = tag.split(",");
			if(split.length==4){
				point = new int[2];
				point[0] = Integer.valueOf(split[2]);
				point[1] = Integer.valueOf(split[3]);
			}
		}
		return point;
	}
	
	public static int[] getTagChildSize(View view){
		String tag = (String)view.getTag();
		if(tag==null){
			return null;
		}
		String[] split = tag.split(",");
		if(split.length<2){
			return null;
		}
		int[] size = {Integer.valueOf(split[0].trim()), Integer.valueOf(split[1].trim())};
		return size;
	}
	
	public static Integer getTagChildWidth(View view){
		String tag = (String)view.getTag();
		if(tag==null){
			return null;
		}
		String[] split = tag.split(",");
		if(split.length<2){
			return null;
		}
		return Integer.valueOf(split[0].trim());
	}
	
	public static Integer getTagChildHeight(View view){
		String tag = (String)view.getTag();
		if(tag==null){
			return null;
		}
		String[] split = tag.split(",");
		if(split.length<2){
			return null;
		}
		return Integer.valueOf(split[1].trim());
	}
	
	public float getChildRatioX(View view){
		return view.getX()*scale_width;
	}
	
	public float getChildRatioY(View view){
		return view.getY()*scale_height;
	}
	
	public float getFractionX(){
		return scale_width;
	}
	public float getFractionY(){
		return scale_height;
	}
}
