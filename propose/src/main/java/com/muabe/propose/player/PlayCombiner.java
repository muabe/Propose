package com.muabe.propose.player;


import com.muabe.propose.combination.CombinationTypeBridge;
import com.muabe.propose.combination.Combine;
import com.muabe.propose.combination.ScanResult;
import com.muabe.propose.Player;

/**
 *  PlayCombiner 상속받는 클래스는 반드시 default 생성자를 가져야한다.
 *  combine에서 root combiner를 객체를 생성하는데 default 생성자로 객체를 만든다.
 * @param <PlayCombinerType>
 */
public abstract class PlayCombiner<PlayCombinerType extends PlayCombiner> extends CombinationTypeBridge<PlayCombinerType> {
    private PlayPriority priority = new PlayPriority();

    protected PlayCombiner(){}

    public abstract PlayPlugin getPlugin();

    public void setRatioRange(float start, float end){
        priority.setRatioRange(start, end);
    }

    public float getStartRatio(){
        return priority.getStartRatio();
    }

    public float getEndRatio(){
        return priority.getEndRatio();
    }

    public float getRatio(){
        return priority.getRatio();
    }

    private float getRawRatio(){
        PlayCombiner parentsPlayer = getParents();
        float ratio = priority.getRatio();
        if (parentsPlayer == null) {
            return 1f;
        } else {
            return ratio * parentsPlayer.getRawRatio();
        }
    }

    @Override
    public int getPriority() {
        return priority.getPriority();
    }

    @Override
    public float compare(Object param) {
        if (priority.getStartRatio() < (float)param && (float)param <= priority.getEndRatio()) {
            return 1f;
        }
        return 0f;
    }


    public boolean play(float ratio){
        boolean result = false;
        float rawRatio = ratio*getRatio()+getStartRatio();
        ScanResult<Player> scanResult = Combine.scan((Player)this, rawRatio);

        for (Player player : scanResult.getDeleteList()) {
            float relRatio = (rawRatio - player.getStartRatio())/player.getRatio();
            if (player.getPlugin() !=null){
                if(relRatio <= 0f) {
                    player.getPlugin().play(player, 0f);
                    priority.setCurrentRatio(0f);
                }else{
                    player.getPlugin().play(player, 1f);
                    priority.setCurrentRatio(1f);
                }
            }
        }

        for (Player player : scanResult.getScanList()) {
            float relRatio = (rawRatio - player.getStartRatio())/player.getRatio();
            if (player.getPlugin() !=null && player.getPlugin().play(player, relRatio)) {
                priority.setCurrentRatio(relRatio);
                result = true;
            }
        }
        return result;
    }

}
