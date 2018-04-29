package com.muabe.propose;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class State {
    Action action = Action.NONE;
    Position position = Position.START;
    Direction direction = Direction.NONE;
    Gesture gesture = Gesture.NONE;

    public enum MotionState{
        NONE(0, 0),
        LEFT(-1, 1),
        RIGHT(1, 1),
        UP(-1, 1),
        DOWN(1, 1),

        MULTI_LEFT(-1, 2),
        MULTI_RIGHT(1, 2),
        MULTI_UP(-1, 2),
        MULTI_DOWN(1, 2),
        MULTI_ROTATION(0, 2),

        RUB(0, 1),
        ROTATION(0, 1),
        PINCH(1, 2),
        PINCH_VERTICAL(1,2),
        PINCH_HORIZONTAL(1,2),
        PRESS_TIME(1,1);

        int orientation;
        int pointerCount;

        MotionState(int orientation, int pointerCount){
            this.orientation = orientation;
            this.pointerCount = pointerCount;
        }

        public int getOrientation(){
            return orientation;
        }

        public MotionState setPointerCount(int pointerCount){
            this.pointerCount = pointerCount;
            return this;
        }

        public int getPointerCount(){
            return pointerCount;
        }
    }

    public enum Action {
        NONE,
        PRESS_DOWN,
        PRESS_UP,
        ANIMATION,
        DRAG;

    }

    public enum Position {
        START,
        BETWEEN,
        END;

        boolean forward = true;

        boolean isForward(){
            return forward;
        }

        void setForward(boolean isForward){
            this.forward = isForward;
        }
    }

    public enum Direction {
        NONE(0),
        LEFT(-1),
        RIGHT(1),
        UP(-1),
        DOWN(1);

        int sign;

        Direction(int sign){
            this.sign = sign;
        }

        int getSign(){
            return sign;
        }
    }

    public enum Gesture {
        NONE,
        PRESS_UP,
        PRESS_DOWN,
        CLICK,
        LONGPRESS,
        FLING;
    }

    public Action getAction(){
        return this.getAction();
    }

    public boolean is(Action action){
        return this.action == action;
    }

    public boolean set(Action action){
        if(this.action == action){
            return false;
        }

        if(action == Action.PRESS_DOWN){
            set(Gesture.PRESS_DOWN);
        }

        this.action = action;
        if(action == Action.NONE){
            if(position == Position.END){
                position.setForward(!position.isForward());
            }
        }
        return true;
    }

    private boolean set(Position position){
        if(this.position == position){
            return false;
        }
        this.position = position;
        return true;
    }

    private boolean set(Gesture gesture){
        if(this.gesture == gesture){
            return false;
        }
        this.gesture = gesture;
        return true;
    }
}

