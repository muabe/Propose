package com.markjmind.propose.actor;

import com.markjmind.propose.Motion;
import com.markjmind.propose.MotionBuilder;
import com.markjmind.propose.MotionScrollItem;
import com.markjmind.propose.PointEvent;

/**
 * 터치 드래그 발생시 화면에 view를 이동시키는 구현체 클래스 이다.<br>
 * 터치 드래그시 각 애니메이션의 특성에 맞게 이동되며 재료로는 애니메이션의 duration을 사용한다.<br>
 * Mover 클래스는 View의 이동에 대해서 많이 사용되고 있지만<br>
 * 드래그시 움직임의 표현이 필요한 어떤곳에서나 사용이 가능하다
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-30
 */
public class Mover {

    /**
     * 해당 duration에 따른 모션을 이동시킨다.<br>
     * MotionBuilder를 받아 조합된 애니메이션으로 모션을 play(이동) 시킨다.
     * @param builder MotionBuilder 객체
     * @param duration 애니메이션 play 시간
     * @return 이동 성공 여부
     */
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


    /**
     * 이동 거리를 duration으로 환산한다.
     * @param motion 환산할 motion 객체
     * @param distance 이동거리
     * @return duration
     */
    private long getDistanceToDuration(Motion motion, float distance){
        long duration = motion.getDurationToDistance(Math.abs(distance));
        if(duration >= motion.getTotalDuration()){
            if(Motion.Position.end.equals(motion.getPosition())){
                return -1;
            }
            motion.setPosition(Motion.Position.end);
            duration = motion.getTotalDuration();
        }else {
            motion.setPosition(Motion.Position.between);
        }
        return duration;
    }


    /**
     * 좌표 정보를 받아 duration으로 환산한다.
     * @param motion 환산할 motion 객체
     * @param pointEvent 좌표정보 객체
     * @return duration
     */
    private long getPointToDuration(Motion motion, PointEvent pointEvent){
        long duration = motion.getDurationToDistance(pointEvent.getAbsPoint());
        if(duration >= motion.getTotalDuration()){ //duration(point)가 Max 범위를 벗어 났을때
            pointEvent.setPoint(motion.getMotionDistance() * motion.getDirectionArg());
            if(Motion.Position.end.equals(motion.getPosition())){
                return -1;
            }
            motion.setPosition(Motion.Position.end);
            duration = motion.getTotalDuration();
        }else {
            motion.setPosition(Motion.Position.between);
        }
        return duration;
    }
}
