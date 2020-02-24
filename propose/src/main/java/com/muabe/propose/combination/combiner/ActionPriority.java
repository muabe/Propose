package com.muabe.propose.combination.combiner;

import com.muabe.propose.combination.Priority;

public abstract class ActionPriority<EventType> implements Priority<EventType> {
    private Point point;

    private int priority = 0;
    private EventFilter eventFilter;

    protected ActionPriority() {
//        eventFilter = new EventFilter(this);
    }

    /**
     * 먼저 선행되어야할 선점/경쟁값
     * @param event 해당 이벤트
     * @return 경쟁값
     */
    public abstract float compete(EventType event);

    /**
     * 증가값 계산
     * @param event 해당 이벤트
     * @return 증가값
     */
    public abstract float increase(EventType event);


    @Override
    public float compare(EventType param) {
        if (filter(param)) {
            float compareValue = motionCompare(param);
            if (compareValue < 0f) {
                compareValue = 0f;
            }
            return compareValue;
        } else {
            return getPoint().value();
        }
    }

    @Override
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


    boolean filter(Object event) {
        return eventFilter.filter(event);
    }

    protected ActionPriority(float maxPoint){
        this(0f, maxPoint);
    }

    protected ActionPriority(float minPoint, float maxPoint){
        point = new Point(minPoint, maxPoint);
    }

    protected float motionCompare(EventType event) {
        if(point.isMinPoint()){
            return compete(event);
        }else{
            return getPoint().value() + increase(event);
        }
    }

    public Point getPoint() {
        return point;
    }
}