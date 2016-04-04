package com.markjmind.propose3;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-28
 */
public class Detector extends GestureDetector.SimpleOnGestureListener {

    protected Point pointX, pointY;

    private ActionState action;
    private boolean isFirstTouch;
    private float density;

    protected Motion left, right, up, down;
    private Mover mover;

    protected Detector(float density){
        this.density = density;
        pointX = new Point();
        pointY = new Point();
        action = new ActionState();

        left = new Motion(-1);
        right = new Motion(1);
        up = new Motion(-1);
        down = new Motion(1);
        mover = new Mover(left, right, up, down);
        reset();
    }

    protected void reset(){
        isFirstTouch = true;
        action.setAction(ActionState.STOP);
    }

    @Override
    public boolean onDown(MotionEvent ev) {
        isFirstTouch = true;
        return super.onDown(ev);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        boolean result = false;
        if(action.getAction() != ActionState.ANIMATION){
            action.setAction(ActionState.SCROLL);
            if(isFirstTouch){ //최초 스크롤시
                isFirstTouch = false;
                pointX.setCoordinates(e2.getRawX());
                pointY.setCoordinates(e2.getRawY());
                return true;
            } else {
                if(setDpPoint(e2.getRawX(), pointX)){
                    result = scroll(pointX, mover.getHorizontal());
                }

                if(setDpPoint(e2.getRawY(), pointY)){
                    result = getResult(result, scroll(pointY, mover.getVertical()));
                }
            }
        }
        return result;
    }

    private boolean scroll(Point point, DirectionAdapter.DirectionObserver adapter){
        boolean isChangeDirection = point.isChangeDirection();
        if(point.getPoint() < 0){
            return adapter.minus(point, isChangeDirection);
        }else if(point.getPoint() > 0){
            return adapter.plus(point, isChangeDirection);
        }else{
            if(point.getPrePoint() < 0){
                return adapter.minus(point, isChangeDirection);
            }else if(point.getPrePoint() > 0){
                return adapter.plus(point, isChangeDirection);
            }
        }
        return false;
    }

    private boolean setDpPoint(float raw, Point point){
        float diff = raw - point.getCoordinates();
        if(Math.abs(diff)>=density){
            point.setCoordinates(raw);
            point.setPoint(point.getPoint() + diff);
            return true;
        }else{
            return false;
        }
    }

    private boolean getResult(boolean result, boolean newResult){
        if(result || newResult){
            return true;
        }
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
//        float accX = pointX.getPrePoint()
//        if(action.getAction() == ActionState.SCROLL && ){
//            currAction = Propose.ACTION.fling;
//
//            Motion motionSetH = getDirectMotionSet(currX.getAccDirection(),Propose.HORIZONTAL);
//            Motion motionSetV = getDirectMotionSet(currY.getAccDirection(),Propose.VERTICALITY);
//            if(motionSetH!=null && motionSetH.isEnableFling() && Motion.STATUS.run.equals(motionSetH.getStatus())){
//				/* X 가속도 재조정*/
//                if(getFlingMaxAccelerator()<currX.getAcc()){
//                    currX.setAcc(getFlingMaxAccelerator());
//                }else if(currX.getAcc()<getFlingMinAccelerator()){
//                    currX.setAcc(getFlingMinAccelerator());
//                }
//            }else{
//                motionSetH =null;
//            }
//
//            if(motionSetV!=null && motionSetV.isEnableFling() && Motion.STATUS.run.equals(motionSetV.getStatus())){
//				/* Y 가속도 재조정*/
//                if(getFlingMaxAccelerator()<currY.getAcc()){
//                    currY.setAcc(getFlingMaxAccelerator());
//                }else if(currY.getAcc()<getFlingMinAccelerator()){
//                    currY.setAcc(getFlingMinAccelerator());
//                }
//            }else{
//                motionSetV = null;
//            }
//			/* fling 이전 move에서 Forward를 셋팅해주기때문에 따로 forward를 구할 필요가없다.*/
//            boolean result = anim.startAnimation(currX, motionSetH, currY, motionSetV, -1);
//        }
        return true;
    }


}
