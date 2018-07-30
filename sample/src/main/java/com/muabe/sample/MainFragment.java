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
import com.muabe.propose.combine.Combiner;
import com.muabe.propose.combine.TestCombination;
import com.muabe.propose.guesture.LeftGesture;
import com.muabe.propose.guesture.RightGesture;

import java.util.ArrayList;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
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
        TestCombination e5 = new TestCombination("E5", 2);

        Combination combination = Combiner.part(Combiner.mix(e1, e2), e3, Combiner.part(e4,e5));
        ArrayList<Combination> combinations = new ArrayList<>();
        Combiner.getPriorityElements(combination, combinations);
        for(Combination c : combinations){
            Log.e("dsf","필터리:"+((TestCombination)c).name);

        }

    }
}
