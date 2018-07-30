package com.muabe.propose;

import android.view.MotionEvent;
import android.view.View;

import com.muabe.propose.guesture.GesturePlugin;
import com.muabe.propose.touch.detector.OnTouchDetectListener;
import com.muabe.propose.touch.detector.multi.MultiTouchEvent;
import com.muabe.propose.touch.detector.single.SingleTouchEvent;
import com.muabe.propose.util.Mlog;

import java.util.ArrayList;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class Propose implements View.OnTouchListener{
    ArrayList<Motion> motionList = new ArrayList<>();
    TouchDetector touchDetector;
    TouchDetectListener touchDetectListener;

    public Propose(){
        touchDetectListener = new TouchDetectListener();
    }

    public Propose addMotion(Motion motion){
        motionList.add(motion);
        return this;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(touchDetector==null){
            touchDetector = new TouchDetector(view.getContext(), touchDetectListener);
        }
        return touchDetector.onTouchEvent(view, motionEvent);
    }


    private class TouchDetectListener implements OnTouchDetectListener{


        @Override
        public boolean onDown(SingleTouchEvent event) {
            Mlog.i(this, "onDown");
            return true;
        }

        @Override
        public boolean onUp(SingleTouchEvent event) {
            return true;
        }

        /**
         * 필터링 절차
         * 1. 제스쳐를 인식 및 분간
         * 2. 제스쳐로 인식된 것 중 가장 증감값 높은것을 선택해줌
         *  - 기존에 진행되고 있는 제스쳐가 최우선순위임
         *  - 설정에 따라 제스쳐의 중복을 허용할수 있음
         *  - 증감값은 기준에 따라 비교하기 어려울수 있음(절대절인 이동 거리로 하는것이 맞음)
         * 3. 좌에서 우로 가는 경우 같이 제스쳐가 변경된때 이전 제스쳐에 값을 0으로 초기화(switch)
         * 4. 이렇게 필터링된 제스쳐를 play 해줌
         *
         * 1.제스쳐의 증감값(이동거리) 리턴
         * 2.기존 모션을 저장해 두었다가 이어서 진행함
         *   - 저장된 모션은 여러개가 될수 있음
         * 3.제스쳐간 Switch할수 있는 연관관계가 필요
         *   - 현실적으로 연관관계를 맺기는 개발상 난해함
         *   - 기존 모션이 0값이 되면 새로 루틴을 다시 탐
         *   - 여기서 중복의 모션이 있을때 어떻게 새로이 루틴을 탈지가 문제임
         *   - 모든 중복 모션이 종료될때 새로 루틴을 타는것은 맞지 않음
         *   - 모션을 그룹핑하고 각 각 따로 루틴을 타야함
         * @param event
         * @return
         */
        @Override
        public boolean onDrag(SingleTouchEvent event) {
//        List<DragFilter> dragFilterList = Filter.getSingleValues();
//        for(DragFilter filter : dragFilterList){
//            filter.onDrag(event);
//        }
            for(Motion motion : motionList){
                GesturePlugin plugin = motion.getGesture();
                float increase = plugin.distancePriority(event);
                float point = plugin.getPointValue(increase);
                plugin.getPoint().add(point);
                if(plugin.getPoint().get()>0) {
                    motion.play();
                }
            }
            return true;
        }

        @Override
        public boolean onMulitBegin(MultiTouchEvent event) {
            Mlog.e(this, "onMulitBegin");
            return true;
        }

        @Override
        public boolean onMultiEnd(MultiTouchEvent event) {
            Mlog.e(this, "onMultiEnd");
            return true;
        }

        @Override
        public boolean onMultiDrag(MultiTouchEvent event) {

            return true;
        }

        @Override
        public boolean onMultiUp(MultiTouchEvent multiEvent) {
            Mlog.e(this, "onMultiUp");
            return true;
        }
    }
}
