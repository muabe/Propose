package com.muabe.sample;

import android.widget.Button;

import com.markjmind.uni.UniFragment;
import com.markjmind.uni.mapper.annotiation.GetView;
import com.markjmind.uni.mapper.annotiation.Layout;
import com.muabe.propose.Motion;
import com.muabe.propose.OnPlayListener;
import com.muabe.propose.Propose;
import com.muabe.propose.guesture.LeftGesture;
import com.muabe.propose.guesture.RightGesture;

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

    }
}
