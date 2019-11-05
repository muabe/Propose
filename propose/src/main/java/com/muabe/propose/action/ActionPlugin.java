package com.muabe.propose.action;

import com.muabe.propose.combination.combiner.ActionPlugBridge;
import com.muabe.propose.combination.combiner.Point;

public abstract class ActionPlugin<EventType> extends ActionPlugBridge<EventType> {
    private Point point;

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

    public ActionPlugin(float maxPoint){
        this(0f, maxPoint);
    }

    private ActionPlugin(float minPoint, float maxPoint){
        point = new Point(minPoint, maxPoint);
    }

    @Override
    protected float motionCompare(EventType event) {
        if(point.isMinPoint()){
            return compete(event);
        }else{
            return getPoint().value() + increase(event);
        }
    }

    @Override
    public Point getPoint() {
        return point;
    }
}
