package com.markjmind.propose.actor;

import com.markjmind.propose.Propose;

import java.util.HashMap;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2013-09-14
 */
public abstract class MotionInitor {
	protected HashMap<String,Object> params;
	
	public MotionInitor(){
		params = new HashMap<String, Object>();
	}
	
	public HashMap<String, Object> getParams() {
		return params;
	}
	
	public Object getParam(String key){
		return params.get(key);
	}
	
	public MotionInitor addParam(String key,Object value){
		params.put(key, value);
		return this;
	}
	public abstract void touchDown(Propose jwm);
	public abstract void touchUp(Propose jwm);
}
