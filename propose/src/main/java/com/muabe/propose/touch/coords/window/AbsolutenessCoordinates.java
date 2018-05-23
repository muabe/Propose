package com.muabe.propose.touch.coords.window;

import android.view.MotionEvent;

/**
 * <br>捲土重來<br>
 * 외부에서 사용하면 안됌(java9에서 모듈 사용할 예정)
 *
 * MotionEvent 객체를 복사하고 변환한다.<br>
 * 예를 들어 최상위 윈도우의 MotionEvent를 복사하고 변환하는데<br>
 * MotionEvent는 단일 객체이기 때문에 변경하면 안되기 때문이다.<br>
 * 윈도우의 MotionEvent 트리구조로 하위 View까지 변화되면서 내려가서<br>
 * 임의로 변경하면 문제가 생기기 때문이다.<br>
 * 여기서 하는일은 우리가 원하는 절대 좌표를 뽑아내기 위해<br>
 * MotionEvent를 복사하고 필요한 절대좌표를 변환하는 것이다.
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

class AbsolutenessCoordinates {

    interface OnDispatchTouchListener {
        void onDispatchTouchEvent(MotionEvent event);
    }

    private MotionEvent event;

    protected void dispatchTouchEvent(MotionEvent event) {
        this.event = MotionEvent.obtain(event);
    }

    protected MotionEvent convertMotionEvent(MotionEvent event, boolean isRaw) {
        int[] pointerIds = new int[event.getPointerCount()];
        for (int i = 0; i < pointerIds.length; i++) {
            pointerIds[i] = event.getPointerId(i);
        }
        return obtain(this.event, event, isRaw, pointerIds);
    }

    protected MotionEvent convertMotionEvent(MotionEvent event) {
        return this.convertMotionEvent(event, false);
    }

    private MotionEvent obtain(MotionEvent originEvent, MotionEvent cloneEvent, boolean isRaw, int... pointerIds) {
        MotionEvent.PointerCoords[] coords = new MotionEvent.PointerCoords[pointerIds.length];
        MotionEvent.PointerProperties[] properties = new MotionEvent.PointerProperties[pointerIds.length];

        for (int i = 0; i < pointerIds.length; i++) {
            coords[i] = new MotionEvent.PointerCoords();
            properties[i] = new MotionEvent.PointerProperties();

            int index = originEvent.findPointerIndex(pointerIds[i]);
            originEvent.getPointerCoords(index, coords[i]);
            cloneEvent.getPointerProperties(i, properties[i]);
        }

        MotionEvent motionEvent = MotionEvent.obtain(
                cloneEvent.getDownTime(),
                cloneEvent.getEventTime(),
                cloneEvent.getAction(),
                cloneEvent.getPointerCount(),
                properties,
                coords,
                cloneEvent.getMetaState(),
                cloneEvent.getButtonState(),
                cloneEvent.getXPrecision(),
                cloneEvent.getYPrecision(),
                cloneEvent.getDeviceId(),
                cloneEvent.getEdgeFlags(),
                cloneEvent.getSource(),
                cloneEvent.getFlags()
        );

        //절대좌표로 변환
        if(isRaw) {
            motionEvent.offsetLocation(motionEvent.getRawX() - motionEvent.getX(0), motionEvent.getRawY() - motionEvent.getY(0));
        }
        return motionEvent;
    }
}
