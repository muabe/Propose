package com.markjmind.propose3;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-28
 */
class Point {
    private float point;
    private float prePoint;
    private float coordinates;

    protected Point(){
        reset();
    }

    protected void reset(){
        point = 0f;
        prePoint = 0f;
        coordinates = 0f;
    }

    protected void setPoint(float point) {
        prePoint = this.point;
        this.point = point;
    }

    protected float getPoint() {
        return point;
    }

    protected float getPrePoint(){
        return prePoint;
    }

    protected float getAbsPoint(){
        return Math.abs(point);
    }

    protected void setCoordinates(float coordinates){
        this.coordinates = coordinates;
    }

    protected float getCoordinates(){
        return coordinates;
    }

    protected boolean isChangeDirection(float moveLot){
        float diffPoint = point + moveLot;
        if(diffPoint==0){
            return false;
        }else if(point > 0 && diffPoint > 0){
            return false;
        }else if(point <0 && diffPoint <0){
            return false;
        }else{
            return true;
        }
    }

    protected boolean isChangeDirection(){
        if((point > 0 && prePoint <0) || (point < 0 && prePoint >0)){
            return true;
        }else{
            return false;
        }
    }
}
