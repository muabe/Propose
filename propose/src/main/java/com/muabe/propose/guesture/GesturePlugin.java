package com.muabe.propose.guesture;


import com.muabe.propose.combine.MotionPriority;

public abstract class GesturePlugin<EventType> extends MotionPriority<EventType> {
    private Point point;

    public abstract float preemp(EventType event);
    public abstract float increase(EventType event);

    public GesturePlugin(float maxPoint){
        this(0f, maxPoint);
    }

    private GesturePlugin(float minPoint, float maxPoint){
        point = new Point(minPoint, maxPoint);
    }

    @Override
    protected float motionCompare(EventType event) {
        if(point.isMinPoint()){
            return preemp(event);
        }else{
            return getPoint().value() + increase(event);
        }
    }

    public Point getPoint(){
        return point;
    }

//    public boolean updatePoint(EventType event){
//        return point.updatePoint(increase(event));
//    }
//
//    public void play(EventType event, PlayListener playListener) {
//        if (playListener != null) {
//            if (point.updatePoint(event, this)) {
//                playListener.play(point.value(), point.getMaxPoint());
//            }
//        }
//    }
}
