package com.markjmind.propose;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.markjmind.propose.actor.Motion;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-28
 */
public class Detector extends GestureDetector.SimpleOnGestureListener {

    protected ActionEvent actionEventX, actionEventY;

    private ActionState action;
    private boolean isFirstTouch;
    private float density;

    private DetectListener detectListener;
    interface DetectListener {
        boolean onScroll(ActionEvent actionEventX, ActionEvent actionEventY);
        boolean onFling(ActionEvent actionEventX, ActionEvent actionEventY);
    }


    protected Detector(float density, DetectListener detectListener){
        this.density = density;

        actionEventX = new ActionEvent(Motion.LEFT, Motion.RIGHT);
        actionEventY = new ActionEvent(Motion.UP, Motion.DOWN);
        action = new ActionState();
        this.detectListener = detectListener;
        reset();
    }

    protected void reset(){
        isFirstTouch = true;
        action.setAction(ActionState.STOP);
    }

    private Hashtable<Integer, ActorInfo> actorMap = new Hashtable<>();


    protected void addMotion(int direction, Motion motion){
        ActorInfo info = new ActorInfo();
        if (actorMap.containsKey(direction)) {
            info = actorMap.get(direction);
        }
        info.add(motion);
        actorMap.put(direction, info);
    }

    @Override
    public boolean onDown(MotionEvent ev) {
        isFirstTouch = true;
        return super.onDown(ev);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        boolean result = false;

        return result;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        boolean result = false;
        if(action.getAction() != ActionState.ANIMATION){
            action.setAction(ActionState.SCROLL);
            if(isFirstTouch){ //최초 스크롤시
                isFirstTouch = false;
                actionEventX.setRaw(e2.getRawX());
                actionEventY.setRaw(e2.getRawY());
                actionEventX.setPreRaw(e2.getRawX());
                actionEventY.setPreRaw(e2.getRawX());
                return true;
            } else {
                if(setDpPoint(e2.getRawX(), actionEventX)){
                    int direction = actionEventX.getDirection();
                    result = scroll(direction, actionEventX);
                }
                if(setDpPoint(e2.getRawY(), actionEventY)){
                    int direction = actionEventY.getDirection();
                    result = scroll(direction, actionEventY) || result;
                }
                result = detectListener.onScroll(actionEventX, actionEventY) || result;

            }
        }
        return result;
    }
    private boolean scroll(int direction, ActionEvent actionEvent){
        boolean result = false;
        ActorInfo info;
        if(direction != Motion.NONE && (info = actorMap.get(direction)) != null) {
            for (Motion motion : info.motions) {
                result = motion.move(actionEvent.getPointToDuration(motion)) || result;
            }
        }else{
            actionEvent.setPoint(0);
        }
        return result;
    }

    private boolean setDpPoint(float raw, ActionEvent actionEvent){
        float diff = raw - actionEvent.getRaw();
        if(Math.abs(diff)>=density){
            actionEvent.setRaw(raw);
            actionEvent.setPoint(actionEvent.getPoint() + diff);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i("Detector","flingX:"+velocityX);
        boolean result = false;
        if(action.getAction() == ActionState.SCROLL){
            action.setAction(ActionState.FlING);
            actionEventX.setAcceleration(velocityX);
            actionEventY.setAcceleration(velocityY);
        }
        return result;
    }

    class ActorInfo{
        protected long maxDuration=0;
        protected ArrayList<Motion> motions;
        protected ActorInfo(){
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
