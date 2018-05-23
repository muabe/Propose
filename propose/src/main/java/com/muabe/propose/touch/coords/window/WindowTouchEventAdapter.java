package com.muabe.propose.touch.coords.window;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;

/**
 * <br>捲土重來<br>
 * 외부에서 사용하면 안됌(java9에서 모듈 사용할 예정)
 *
 * 윈도우의 터치 이벤트를 가로 채서 OnDispatchTouchListener 으로 보내준다.<br>
 * OnDispatchTouchListener는 절대좌표 변환기인 AbsolutenessCoordinates에서 사용한다.
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

class WindowTouchEventAdapter implements Window.Callback{

    private Window.Callback callback;
    private AbsolutenessCoordinates.OnDispatchTouchListener onDispatchTouchListener;

    private WindowTouchEventAdapter(Window.Callback callback){
        this.callback = callback;
    }

//    public static void dispatchTouchEvent(Window window, OnDispatchTouchListener onDispatchTouchListener){
//        WindowTouchEventAdapter adapter = new WindowTouchEventAdapter(window.getCallback());
//        Window.Callback callback = adapter.getCallback();
//        if(!(callback instanceof WindowTouchEventAdapter)){
//            adapter.setOnDispatchTouchListener(onDispatchTouchListener);
//            window.setCallback(adapter);
//        }else{
//            ((WindowTouchEventAdapter)callback).setOnDispatchTouchListener(onDispatchTouchListener);
//        }
//    }

    public static void dispatchTouchEvent(Window window, AbsolutenessCoordinates.OnDispatchTouchListener onDispatchTouchListener){
        WindowTouchEventAdapter adapter = new WindowTouchEventAdapter(window.getCallback());
        Window.Callback callback = adapter.getCallback();
        if(isRegistry(window)){
            ((WindowTouchEventAdapter)callback).setOnDispatchTouchListener(onDispatchTouchListener);
        }else{
            adapter.setOnDispatchTouchListener(onDispatchTouchListener);
            window.setCallback(adapter);
        }
    }

    public static boolean isRegistry(Window window){
        return window.getCallback() instanceof WindowTouchEventAdapter;
    }

    public Window.Callback getCallback(){
        return this.callback;
    }

    public static void dispatchTouchEvent(Activity activity, AbsolutenessCoordinates.OnDispatchTouchListener onDispatchTouchListener){
        WindowTouchEventAdapter.dispatchTouchEvent(activity.getWindow(), onDispatchTouchListener);
    }

    public void setOnDispatchTouchListener(AbsolutenessCoordinates.OnDispatchTouchListener onDispatchTouchListener){
        this.onDispatchTouchListener = onDispatchTouchListener;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return callback.dispatchKeyEvent(event);
    }

    @Override
    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        return callback.dispatchKeyShortcutEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        onDispatchTouchListener.onDispatchTouchEvent(event);
        return callback.dispatchTouchEvent(event);
    }

    @Override
    public boolean dispatchTrackballEvent(MotionEvent event) {
        return callback.dispatchTrackballEvent(event);
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        return callback.dispatchGenericMotionEvent(event);
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        return callback.dispatchPopulateAccessibilityEvent(event);
    }

    @Override
    public View onCreatePanelView(int featureId) {
        return callback.onCreatePanelView(featureId);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        return callback.onCreatePanelMenu(featureId, menu);
    }

    @Override
    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        return callback.onPreparePanel(featureId,  view, menu);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        return callback.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return callback.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onWindowAttributesChanged(WindowManager.LayoutParams attrs) {
        callback.onWindowAttributesChanged(attrs);
    }

    @Override
    public void onContentChanged() {
        callback.onContentChanged();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        callback.onWindowFocusChanged(hasFocus);
    }

    @Override
    public void onAttachedToWindow() {
        callback.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        callback.onDetachedFromWindow();
    }

    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        callback.onPanelClosed(featureId, menu);
    }

    @Override
    public boolean onSearchRequested() {
        return callback.onSearchRequested();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public boolean onSearchRequested(SearchEvent searchEvent) {
        return callback.onSearchRequested(searchEvent);
    }

    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback1) {
        return callback.onWindowStartingActionMode(callback1);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback1, int type) {
        return callback.onWindowStartingActionMode(callback1, type);
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        callback.onActionModeStarted(mode);
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        callback.onActionModeFinished(mode);
    }
}
