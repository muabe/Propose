package com.muabe.propose.action;

import com.muabe.propose.combination.CombinationTypeBridge;
import com.muabe.propose.combination.Combine;

/**
 * <br>捲土重來<br>
 * ActionCombiner 상속받는 클래스는 반드시 default 생성자를 가져야한다.
 * combine에서 root combiner를 객체를 생성하는데 default 생성자로 객체를 만든다.
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-10-18
 */
public abstract class ActionCombiner<ActionCombinerType extends ActionCombiner> extends CombinationTypeBridge<ActionCombinerType> {
    private ActionPriority actionPriority;

    protected ActionCombiner(){
    }

    /**
     *  PlayerPlugin은 priority가 시간을 기준으로 모두 동일하여 자체적으로 구현되어지지만 이와 달리
     *  ActionPlugin은 priority가 각 plugin 마다 기준이 다르기 때문에 compare를 하기 위해
     *  하위 상속 클래스가(Motion 등) getActionPlugin을(compare가 구현) 구현해야된다.
     *  또한 ActionPlugin은 compete, increase로 compare를 분기한다.
     * @return
     */
    public abstract ActionPlugin getPlugin();

    @Override
    public int getPriority() {
        if (getPlugin() == null) {
            return 0;
        }
        return actionPriority.getPriority();
    }

    @Override
    public float compare(Object event) {
        if (getPlugin() == null) {
            return 0;
        }
        if(actionPriority == null){
            actionPriority = new ActionPriority(getPlugin());
        }
        return actionPriority.compare(event);
    }

    protected boolean filter(Object event) {
        return actionPriority.filter(event);
    }

    public static <T extends ActionCombiner>T and(T... actionCombiners){
        return Combine.all(actionCombiners);
    }

    public static <T extends ActionCombiner>T or(T... actionCombiners){
        return Combine.oneof(actionCombiners);
    }
}
