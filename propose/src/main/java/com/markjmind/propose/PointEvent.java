package com.markjmind.propose;

/**
 * <br>捲土重來<br>
 * 좌표의 저장과 연산을 담당하고 있는 클래스이다.<br>
 * 현재 좌표를 입력하면 아래 내용들이 자동 연산되어 필요한 정보를 얻을수 있다<br>
 * 1. 절대 좌표 <br>
 * 2. 좌표 히스토리 <br>
 * 3. 가속도 <br>
 * 4. 드래그 진행 방향 <br>
 * 5. 드래그 진행 시간<br>
 * 6. 터치 드래그 버퍼(드래그시 갑자기 애니메이션이 튀지 않는 현상을 방지해줌)
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 * @since 2016-03-28
 */
public class PointEvent {
    /**현재 좌표 */
    private float point;
    /**이전 좌표 */
    private float prePoint;
    /**현재 절대 좌표 */
    private float raw;
    /**이전 절대 좌표 */
    private float preRaw;
    /** 가속도 */
    private float velocity;
    /** 드래그 진행 방향 */
    protected int minus, plus;
    /** 드래그 진행 시간 */
    private long time;
    /** 드래그 시간*/
    protected long diffTime=0;
    /** 화면 해상도 압출율*/
    float density;
    /** 드래그 버퍼링*/
    protected float endBuffer;


    /**
     * 기본 생성자
     * @param minus 정방향 값
     * @param plus 반대방향 값
     * @param density 화면 해상도 압출율
     */
    protected PointEvent(int minus, int plus, float density){
        this.minus = minus;
        this.plus = plus;
        this.density = density;
        reset();
    }


    /**
     * 좌표를 초기화한다.
     */
    protected void reset(){
        point = 0f;
        prePoint = 0f;
        raw = 0f;
        velocity = 0f;
        endBuffer = 0f;
    }


    /**
     * 좌표의 절대값을 가져온다.
     * @return 좌표의 절대값
     */
    public float getAbsPoint(){
        return Math.abs(point);
    }


    /**
     * 현재 좌표를 가져온다.
     * @return 현재 좌표
     */
    public float getPoint() {
        return point;
    }

    /**
     * 현재 좌표를 설정한다.
     * @param point 현재 좌표값
     */
    public void setPoint(float point) {
        prePoint = this.point;
        this.point = point;
    }


    /**
     * 이전 좌표를 가져온다.
     * @return 이전 좌표값
     */
    protected float getPrePoint(){
        return prePoint;
    }


    /**
     * 절대 좌표를 가져온다.
     * @return 절대 좌표값
     */
    protected float getRaw(){
        return raw;
    }


    /**
     * 이전 절대 좌표를 가져온다.
     * @return 이전 절대 좌표값
     */
    protected float getPreRaw(){
        return this.preRaw;
    }


    /**
     * 절대 좌표를 셋팅한다.
     * @param raw 절대 좌표값
     */
    protected void setPreRaw(float raw){
        this.preRaw = raw;
    }


    /**
     * 드래그 이벤트시 좌표 연산한다.
     * @param raw 절대 좌표
     * @param time 드래그 시간
     */
    protected void setEvent(float raw, long time){
        setPreRaw(this.raw);
        this.setVelocity(raw, time);
        setTime(time);
        this.raw = raw;
    }

    /**
     * 가속도를 연산한다.
     * @param raw 절대 좌표
     * @param time 드래그 시간
     */
    protected void setVelocity(float raw, long time){
        diffTime = time-this.time;
        this.velocity = ((raw- this.raw)/density)/(time-this.time);
    }

    /**
     * 가속도를 가져온다.
     * @return 가속도
     */
    protected float getVelocity(){
        return velocity;
    }

    /**
     * 가속도를 설정한다.
     * @param velocity 가속도
     */
    protected void setVelocity(float velocity){
        this.velocity = velocity;
    }

    /**
     * 드래그 시간을 설정한다.
     * @param time 드래그 시간.
     */
    protected void setTime(long time){
        this.time = time;
    }


    /**
     * 드래그시 진행방향을 탐지한다.
     * @param movePoint 이동되는 좌표
     * @return 진행방향
     */
    protected int getChangeDirection(float movePoint){
        if((getPoint()+movePoint > 0 && getPoint() < 0)){
            return minus;
        }else if(getPoint()+movePoint < 0 && getPoint() > 0){
            return plus;
        }else {
            return Motion.NONE;
        }
    }

    /**
     * 현재 드래그 진행 방향을 가져온다.
     * @return 진행방향
     */
    protected int getDirection(){
        if(getPoint() < 0){
            return minus;
        }else if(getPoint() > 0){
            return plus;
        }else{
            if(getPrePoint() < 0) {
                return minus;
            }else if(getPrePoint() > 0){
                return plus;
            }
        }
        return Motion.NONE;
    }

    /**
     * 이동되는 좌표에 따른 진행방향을 가져온다.
     * @param movePoint 이동 좌표
     * @return 진행방향
     */
    protected int getDirection(float movePoint){
        if(getPoint()+movePoint < 0){
            return minus;
        }else if(getPoint()+movePoint > 0){
            return plus;
        }else{
            if(getPoint() < 0) {
                return minus;
            }else if(getPoint() > 0){
                return plus;
            }
        }
        return Motion.NONE;
    }
}
