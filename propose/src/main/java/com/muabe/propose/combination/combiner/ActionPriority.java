package com.muabe.propose.combination.combiner;

import com.muabe.propose.combination.Priority;

class ActionPriority implements Priority {
    private int priority = 0;
    private EventFilter eventFilter;
    private ActionPlugin actionPlugin;

    ActionPriority(ActionPlugin actionPlugin){
        this.actionPlugin = actionPlugin;
        eventFilter = new EventFilter(actionPlugin);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public float compare(Object param) {
        if (filter(param)) {
            float compareValue = 0f;
            if(actionPlugin.getPoint().isMinPoint()){
                compareValue = actionPlugin.compete(param);
            }else{
                compareValue = actionPlugin.getPoint().value() + actionPlugin.increase(param);
            }
            if (compareValue < 0f) {
                compareValue = 0f;
            }
            return compareValue;
        } else {
            return actionPlugin.getPoint().value();
        }
    }

    boolean filter(Object event) {
        return eventFilter.filter(event);
    }
}
