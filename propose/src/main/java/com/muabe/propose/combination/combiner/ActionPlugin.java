package com.muabe.propose.combination.combiner;

public abstract class ActionPlugin<EventType>{

    private Point point;

    protected ActionPlugin(float maxPoint){
        this(0f, maxPoint);
    }

    protected ActionPlugin(float minPoint, float maxPoint){
        point = new Point(minPoint, maxPoint);
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

    public Point getPoint(){
        return this.point;
    }

}
