package com.muabe.sample;

import android.util.Log;
import android.widget.Button;

import com.markjmind.uni.UniFragment;
import com.markjmind.uni.mapper.annotiation.GetView;
import com.markjmind.uni.mapper.annotiation.Layout;
import com.muabe.propose.Motion;
import com.muabe.propose.OnPlayListener;
import com.muabe.propose.Propose;
import com.muabe.propose.combine.Combination;
import com.muabe.propose.combine.Combine;
import com.muabe.propose.combine.TestCombination;
import com.muabe.propose.guesture.LeftGesture;
import com.muabe.propose.guesture.RightGesture;

import java.util.ArrayList;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @since 2018-04-30
 */
@Layout(R.layout.main)
public class MainFragment extends UniFragment{
    @GetView
    Button button;



    @Override
    public void onPost() {
        final float start = button.getX();
        Motion motionLeft = new Motion(new LeftGesture());
        motionLeft.setOnPlayListener(new OnPlayListener() {
            @Override
            public boolean play(float pointValue) {
                button.setX(start-pointValue);
                return true;
            }
        });
        motionLeft.setMaxPoint(500);
        Motion motionRight = new Motion(new RightGesture());
        motionRight.setOnPlayListener(new OnPlayListener() {
            @Override
            public boolean play(float pointValue) {
                button.setX(start+pointValue);
                return true;
            }
        });
        motionRight.setMaxPoint(500);
        Propose propose = new Propose();
        propose.addMotion(motionLeft).addMotion(motionRight);
        button.setOnTouchListener(propose);

        TestCombination e1 = new TestCombination("E1", 1);
        TestCombination e2 = new TestCombination("E2", 4);

        TestCombination e3 = new TestCombination("E3", 5);

        TestCombination e4 = new TestCombination("E4", 3);
        TestCombination e5 = new TestCombination("E5", 7);

        TestCombination e6 = new TestCombination("E6", 2);
        TestCombination e7 = new TestCombination("E7", 1);
        TestCombination e8 = new TestCombination("E8", 10);

        TestCombination e9 = new TestCombination("E9", 2);
        TestCombination e10 = new TestCombination("E10", 6);

        Combination combination = Combine.one(Combine.all(e1, e2), e3, Combine.one(e4, e5, Combine.one(e6, e7, e8, Combine.all(e9, e10))));

        ArrayList<Combination> combinations;
        combinations = Combine.scan(combination);
        for(Combination c : combinations){
            Log.e("dsf","필터링1:"+((TestCombination)c).name+"="+((TestCombination)c).priority);
        }

        Log.e("dsf","---------------------------------------");
        e8.priority = 1;
        combinations = Combine.scan(combination);
        for(Combination c : combinations){
            Log.e("dsf","필터링2:"+((TestCombination)c).name+"="+((TestCombination)c).priority);
        }

        Log.e("dsf","---------------------------------------");
        e9.priority = 10;
        combinations = Combine.scan(combination);
        for(Combination c : combinations){
            Log.e("dsf","필터링3:"+((TestCombination)c).name+"="+((TestCombination)c).priority);
        }

        Log.e("dsf","---------------------------------------");
        e8.priority = 0;
        combinations = Combine.scan(combination);
        for(Combination c : combinations){
            Log.e("dsf","필터링4:"+((TestCombination)c).name+"="+((TestCombination)c).priority);
        }

        Log.e("dsf","---------------------------------------");
        e9.priority = 0;
        combinations = Combine.scan(combination);
        for(Combination c : combinations){
            Log.e("dsf","필터링5:"+((TestCombination)c).name+"="+((TestCombination)c).priority);
        }

        Log.e("dsf","---------------------------------------");
        e10.priority = 0;
        e9.priority = 1;
        combinations = Combine.scan(combination);
        for(Combination c : combinations){
            Log.e("dsf","필터링6:"+((TestCombination)c).name+"="+((TestCombination)c).priority);
        }

    }
}
