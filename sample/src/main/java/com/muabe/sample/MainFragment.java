package com.muabe.sample;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.markjmind.uni.UniFragment;
import com.markjmind.uni.common.Jwc;
import com.markjmind.uni.mapper.annotiation.GetView;
import com.markjmind.uni.mapper.annotiation.Layout;
import com.muabe.propose.Propose;
import com.muabe.propose.test.TestAction;
import com.muabe.propose.combine.Combination;
import com.muabe.propose.combine.Combine;
import com.muabe.propose.combine.TestCombination;
import com.muabe.propose.guesture.SingleTouchLeftGesture;
import com.muabe.propose.guesture.SingleTouchRightGesture;
import com.muabe.propose.test.TestEvent;
import com.muabe.propose.test.TestGesture;
import com.muabe.propose.motion.Motion;

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
    Button button, button2, button3;



    @Override
    public void onPost() {
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

        Combination combination =
                Combine.one(
                            Combine.all(
                                    e1,
                                    e2),
                            e3,
                            Combine.one(
                                    e4,
                                    e5,
                                    Combine.one(
                                            e6,
                                            e7,
                                            e8,
                                            Combine.all(
                                                    e9,
                                                    e10)
                                        )
                                )
                            );

//        e6.priority = 1;
        ArrayList<Combination> combinations;
        combinations = Combine.scan(combination, null);
        print(1, combinations);// 1:E8(15)

        e8.compare = 1;
        combinations = Combine.scan(combination, null);
        print(2, combinations);// 2:E8(4)

        e9.compare = 10;
        combinations = Combine.scan(combination, null);
        print(3, combinations);// 3:E8(4)

        e8.compare = 0;
        combinations = Combine.scan(combination, null);
        print(4, combinations);// 4:E9,E10(18<-19)

        e9.compare = 0;
        combinations = Combine.scan(combination, null);
        print(5, combinations);// 5:E10(6)

        e10.compare = 0;
        e9.compare = 1;
        combinations = Combine.scan(combination, null);
        print(6, combinations);// 6:E9(6)

        e10.compare = 1;
        e9.compare = 1;
        combinations = Combine.scan(combination, null);
        print(7, combinations);// 7:E9,E10(6)

        e10.compare = 0;
        e9.compare = 0;
        combinations = Combine.scan(combination, null);
        print(8, combinations);// 8:E5(18<-21)


        float maxMove = 300f* Jwc.getDensity(button);
        ObjectAnimator left = ObjectAnimator.ofFloat(button, "translationX", -maxMove);
        ObjectAnimator right = ObjectAnimator.ofFloat(button, "translationX", maxMove);


        final TestAction action = new TestAction();
        TestGesture testGesture = new TestGesture(1000);


        Motion test = new Motion(testGesture);
        Motion motionRight = new Motion(new SingleTouchRightGesture(maxMove));
        motionRight.begin(right);
        Motion motionLeft = new Motion(new SingleTouchLeftGesture(maxMove));
        motionLeft.begin(left);
        Motion motion = Combine.all(Combine.one(motionRight, motionLeft), test);

        final Propose propose = new Propose(getContext());
        propose.addActionMudle("ddd", action);
        propose.setMotion(motion);
        button.setOnTouchListener(propose);


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action.go(new TestEvent());
            }
        });

        ObjectAnimator right2 = ObjectAnimator.ofFloat(button3, "translationX", maxMove);
        Motion m = new Motion(new SingleTouchRightGesture(maxMove));
        m.begin(right2);
        Propose p = new Propose(getContext());
        p.setMotion(m);
        button3.setOnTouchListener(p);



    }

    void print(int num, ArrayList<Combination> combinations){
        String msg = "";
        for(Combination c : combinations){
            msg += " "+((TestCombination)c).name+"="+((TestCombination)c).compare;
        }
        Log.e("dsf","필터링"+num+":"+msg);
    }
}
