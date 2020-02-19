package com.muabe.propose.combination.combiner;

import com.muabe.propose.combination.CombinationBridge;
import com.muabe.propose.combination.Combine;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-18
 */
public class ActionCombiner<thisCombination extends ActionCombiner, ActionPriorityType extends ActionPlugBridge> extends CombinationBridge<thisCombination> {
    private ActionPriorityType actionPlugin;

    protected ActionCombiner(){}

    protected ActionCombiner(ActionPriorityType actionPlugin) {
        setActionPlugin(actionPlugin);
    }

    public void setActionPlugin(ActionPriorityType actionPlugin) {
        this.actionPlugin = actionPlugin;
        setName(actionPlugin.getClass().getSimpleName());
    }

    public ActionPriorityType getActionPlugin() {
        return actionPlugin;
    }

    @Override
    public int getPriority() {
        if (actionPlugin == null) {
            return 0;
        }
        return actionPlugin.getPriority();
    }

    @Override
    public float compare(Object event) {
        if (actionPlugin == null) {
            return 0;
        }
        return actionPlugin.compare(event);
    }
    protected boolean filter(Object event) {
        return actionPlugin.filter(event);
    }

    public static <T extends ActionCombiner>T and(T... actionCombiners){
        return Combine.all(actionCombiners);
    }

    public static <T extends ActionCombiner>T or(T... actionCombiners){
        return Combine.oneof(actionCombiners);
    }
}
