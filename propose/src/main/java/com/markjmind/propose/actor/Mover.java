package com.markjmind.propose.actor;

import com.markjmind.propose.Motion;
import com.markjmind.propose.MotionBuilder;
import com.markjmind.propose.MotionScrollItem;
import com.markjmind.propose.PointEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-30
 */
public class Mover {

    public boolean move(MotionBuilder builder, long duration){
        if(duration<0){
            return false;
        }

        boolean result = false;
        for (int i = 0; i < builder.scrollItemList.size(); i++) {
            MotionScrollItem motionScrollItem = builder.scrollItemList.get(i);
            if(motionScrollItem.scroll(i, duration)){
                result = true;
            }
        }
        return result;
    }

    private long getDurationToDistance(Motion motion, float distance){
        long duration = motion.getDistanceToDuration(Math.abs(distance));
        if(duration >= motion.getTotalDuration()){
            if(Motion.STATUS.end.equals(motion.getStatus())){
                return -1;
            }
            motion.setStatus(Motion.STATUS.end);
            duration = motion.getTotalDuration();
        }else {
            motion.setStatus(Motion.STATUS.run);
        }
        return duration;
    }

    private long getPointToDuration(Motion motion, PointEvent pointEvent){
        long duration = motion.getDistanceToDuration(pointEvent.getAbsPoint());
        if(duration >= motion.getTotalDuration()){ //duration(point)가 Max 범위를 벗어 났을때
            pointEvent.setPoint(motion.getMotionDistance() * motion.getDirectionArg());
            if(Motion.STATUS.end.equals(motion.getStatus())){
                return -1;
            }
            motion.setStatus(Motion.STATUS.end);
            duration = motion.getTotalDuration();
        }else {
            motion.setStatus(Motion.STATUS.run);
        }
        return duration;
    }
}
