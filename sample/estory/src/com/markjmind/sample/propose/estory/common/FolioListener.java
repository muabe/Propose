package com.markjmind.sample.propose.estory.common;

public interface FolioListener {
	public void onTouch(boolean isMotionStart);
	public void onStart();
	public void onEnd();
	public void onTouchUp(boolean isMotionStart);
}
