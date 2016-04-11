package com.markjmind.propose.actor;

import com.markjmind.propose.ActionEvent;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-30
 */
class Mover {

    protected boolean move(Motion motion, ActionEvent actionEvent){
        long duration = getPointToDuration(motion, actionEvent);
        return move(motion, duration);
    }

    public boolean move(Motion motion, long duration){
        motion.setCurrDuration(duration);
        if(duration<0){
            return false;
        }
        boolean result = false;
        for (int i = 0; i < motion.builder.scrollItemList.size(); i++) {
            MotionScrollItem motionScrollItem = motion.builder.scrollItemList.get(i);
            result = motionScrollItem.scroll(i, motion) || result;
        }
        return result;
    }

    public boolean move(Motion motion, float distance){
        long duration = getDurationToDistance(motion, distance);
        return move(motion, duration);

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

    private long getPointToDuration(Motion motion, ActionEvent actionEvent){
        long duration = motion.getDistanceToDuration(actionEvent.getAbsPoint());
        if(duration >= motion.getTotalDuration()){ //duration(point)가 Max 범위를 벗어 났을때
            actionEvent.setPoint(motion.getMotionDistance() * motion.getDirectionArg());
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
