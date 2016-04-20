package com.markjmind.propose;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-28
 */
public class Detector extends GestureDetector.SimpleOnGestureListener{

    protected PointEvent pointEventX, pointEventY;

    private Hashtable<Integer, MotionsInfo> motionMap = new Hashtable<>();
    private ActionState action;
    private boolean isFirstTouch;
    private float density;

    private DetectListener detectListener;

    protected int horizontalPriority = Motion.RIGHT;
    protected int verticalPriority = Motion.UP;

    protected Detector(float density, DetectListener detectListener){
        this.density = density;

        pointEventX = new PointEvent(Motion.LEFT, Motion.RIGHT);
        pointEventY = new PointEvent(Motion.UP, Motion.DOWN);
        action = new ActionState();
        this.detectListener = detectListener;
        reset();
    }

    protected void reset(){
        isFirstTouch = true;
        action.setAction(ActionState.STOP);
    }


    protected void addMotion(int direction, Motion motion){
        MotionsInfo info = new MotionsInfo();
        if (motionMap.containsKey(direction)) {
            info = motionMap.get(direction);
        }
        if(direction == Motion.LEFT || direction == Motion.RIGHT) {
            motion.setPointEvent(pointEventX);
        }else if(direction == Motion.UP || direction == Motion.DOWN){
            motion.setPointEvent(pointEventY);
        }
        info.add(motion);
        motionMap.put(direction, info);
    }

    protected int getActionState() {
        return this.action.getAction();
    }


    /****************************************** Gesture ****************************************/

    /******** Down *******/
    @Override
    public boolean onDown(MotionEvent event) {
        isFirstTouch = true;
        pointEventX.setCurrRaw(event.getRawX(), event.getEventTime());
        pointEventX.setAcceleration(0f);
        pointEventY.setCurrRaw(event.getRawY(), event.getEventTime());
        pointEventY.setAcceleration(0f);
        return super.onDown(event);
    }


    /******** Up *******/
    public boolean onUp(MotionEvent event){
        if(action.getAction() == ActionState.SCROLL){
            action.setAction(ActionState.STOP);
        }
        return false;
    }


    /******** TapUp *******/
    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        Log.i("Detector", "Tap:1");
        boolean result;
        result = tapUp(pointEventX, horizontalPriority);
        result = tapUp(pointEventY, verticalPriority) || result;
        return result;
    }

    private boolean tapUp(PointEvent pointEvent, int priority){
        boolean result = false;
        int direction = pointEvent.getDirection();
        if(direction==0){
            direction = priority;
        }
        MotionsInfo info;
        if(direction != Motion.NONE && (info = motionMap.get(direction)) != null) {
            for (Motion motion : info.motions) {
                result = motion.animate() || result;
            }
        }
        return result;
    }


    /******** LongPress *******/
    @Override
    public void onLongPress(MotionEvent e) {
    }


    /******** Scroll *******/
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        boolean result = false;
        if(action.getAction() != ActionState.ANIMATION){
            action.setAction(ActionState.SCROLL);
            pointEventX.setCurrRawAndAccel(e2.getRawX(), e2.getEventTime(), density);
            pointEventY.setCurrRawAndAccel(e2.getRawY(), e2.getEventTime(), density);

            if(isFirstTouch){ //최초 스크롤시
                isFirstTouch = false;
                pointEventX.saveRaw();
                pointEventY.saveRaw();
                pointEventX.setPreRaw(e2.getRawX());
                pointEventY.setPreRaw(e2.getRawX());
                result = true;
            } else {
                result = scroll(e2.getRawX(), pointEventX);
                result = scroll(e2.getRawY(), pointEventY) || result;

                result = detectListener.onScroll(pointEventX, pointEventY) || result;
            }

        }
        return result;
    }

    private boolean scroll(float raw, PointEvent pointEvent){
        float diff = raw - pointEvent.getRaw();
        pointEvent.saveRaw();
        int direction = pointEvent.getDirection(diff);

        boolean result = false;

        if(direction == Motion.NONE ){
            result = move(pointEvent.plus, 0f);
            result = move(pointEvent.minus, 0f) || result;
        }else{

            int changeDirection = pointEvent.getChangeDirection(diff);
            if(changeDirection!=Motion.NONE){
                result = move(changeDirection, 0f);
            }
            result = move(direction, Math.abs(pointEvent.getPoint() + diff)) || result;
        }
        return result;
    }

    private boolean move(int direction, float distance){
        MotionsInfo info;
        boolean result = false;
        if((info = motionMap.get(direction)) != null){
            for (Motion motion : info.motions) {
                result = motion.moveDistance(distance) || result;
            }
        }
        return result;
    }


    /******** Fling *******/
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i("Detector", "flingX:"+pointEventX.getAcceleration()+ " Y:"+pointEventY.getAcceleration()) ;
        boolean result = false;
        if(action.getAction() == ActionState.SCROLL){
            action.setAction(ActionState.FlING);
            float acceleration = pointEventX.getAcceleration();
            if(pointEventX.getAcceleration() < pointEventY.getAcceleration()){
                acceleration = pointEventY.getAcceleration();
            }

            result = fling(pointEventX, acceleration);
            result = fling(pointEventY, acceleration) || result;
            if(!result){
                action.setAction(ActionState.STOP);
            }
        }
        return result;
    }

    private boolean fling(PointEvent pointEvent, float acceleration){
        boolean result = false;
        int direction = pointEvent.getDirection();
        if(acceleration == 0f || direction == Motion.NONE){
            return false;
        }
        MotionsInfo info = motionMap.get(direction);
        if(info != null){
            for (Motion motion : info.motions) {
                long start = motion.getCurrDuration();
                long end = motion.getTotalDuration();
                if(pointEvent.getAcceleration() < 0){
                    end = 0;
                }
                Log.e("ds","start:"+start+" end:"+end);
                result = motion.animate(start, end, acceleration) || result;
            }
        }
        return result;
    }


    /****************************************** interface and inner class ****************************************/

    interface DetectListener {
        boolean onScroll(PointEvent pointEventX, PointEvent pointEventY);
        boolean onFling(PointEvent pointEventX, PointEvent pointEventY);
    }

    class MotionsInfo {
        protected long maxDuration=0;
        protected ArrayList<Motion> motions;
        protected MotionsInfo(){
            motions = new ArrayList<>();
        }

        protected void add(Motion motion){
            if(maxDuration < motion.getTotalDuration()){
                maxDuration = motion.getTotalDuration();
            }
            motions.add(motion);
        }
    }
}
