package com.markjmind.propose3;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-30
 */
public class Mover extends DirectionAdapter {
    private Motion left, right, up, down;

    protected Mover(Motion left, Motion right, Motion up, Motion down){
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
    }

    @Override
    public boolean left(Point point, boolean isChangeDirection) {
        if(isChangeDirection){
            move(right, 0);
        }
        return checkAndMove(left, point);

    }

    @Override
    public boolean right(Point point, boolean isChangeDirection) {
        if(isChangeDirection){
            move(left, 0);
        }
        return checkAndMove(right, point);
    }

    @Override
    public boolean up(Point point, boolean isChangeDirection) {
        if(isChangeDirection){
            move(down, 0);
        }
        return checkAndMove(up, point);
    }

    @Override
    public boolean down(Point point, boolean isChangeDirection) {
        if(isChangeDirection){
            move(up, 0);
        }
        return checkAndMove(down, point);
    }

    private boolean checkAndMove(Motion motion, Point point){
        if(motion.hasAnimation()) {
            long duration = getPointToDuration(motion, point);
            if(duration<0){
                return false;
            }
            move(motion, duration);
            return true;
        }else {
            point.setPoint(0);
        }
        return false;
    }

    private void move(Motion motion, long duration){
        motion.setCurrDuration(duration);
        for (int i = 0; i < motion.builder.scrollItemList.size(); i++) {
            MotionScrollItem motionScrollItem = motion.builder.scrollItemList.get(i);
            motionScrollItem.scroll(i, motion);
        }
    }

    private long getPointToDuration(Motion motion, Point point){
        long duration = motion.getDistanceToDuration(point.getAbsPoint());
        if(duration >= motion.totalDuration){ //duration(point)가 Max 범위를 벗어 났을때
            point.setPoint(motion.getMotionDistance() * motion.getDirectionArg());
            if(Motion.STATUS.end.equals(motion.getStatus())){
                return -1;
            }
            motion.setStatus(Motion.STATUS.end);
            duration = motion.totalDuration;
        }else {
            motion.setStatus(Motion.STATUS.run);
        }
        return duration;
    }
}
