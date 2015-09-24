package com.markjmind.propose.sample.estory.common;

public interface FolioListener {
	public void onTouch(boolean isMotionStart);
	public void onStart();
	public void onEnd();
	public void onTouchUp(boolean isMotionStart);
}
