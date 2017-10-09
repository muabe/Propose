package com.markjmind.propose.listener;

import com.markjmind.propose.Propose;

import java.util.HashMap;

/**
 * <br>捲土重來<br>
 *
 * 모션 시작시 필요한 내용의 추가 및 정의 하는 리스너를 제공하는 인터페이스이다.<br>
 * 모션의 시작은 터치가 시작될때(touchDown) 발생하고 종료는 터치가 끝날때(touchUp) 발생한다.
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2013-09-14
 */
public abstract class MotionInitor {
	/** 리스너 추가시 필요한 파라미터를 관리 */
	protected HashMap<String,Object> params;

	/**
	 * 기본 생성자
	 */
	public MotionInitor(){
		params = new HashMap<String, Object>();
	}

	/**
	 * 파라미터 Map을 가져온다.
	 * @return Map 객체
	 */
	public HashMap<String, Object> getParams() {
		return params;
	}

	/**
	 * 파라미터 키에 대한 값을 가져온다.
	 * @param key 파라미터 키
	 * @return 파리미터 값
	 */
	public Object getParam(String key){
		return params.get(key);
	}

	/**
	 * 파라미터를 추가한다.
	 * @param key 파라이미터 키
	 * @param value 파라미터 값
	 * @return 체이닝 MotionInitor 객체
	 */
	public MotionInitor addParam(String key,Object value){
		params.put(key, value);
		return this;
	}

	/**
	 * 모션이 시작될때 이벤트가 발생한다.
	 * @param propose Propose 객체
	 */
	public abstract void touchDown(Propose propose);

	/**
	 * 모션이 종료될때 이벤트가 발생한다.
	 * @param propose Propose 객체
	 */
	public abstract void touchUp(Propose propose);
}
