package com.muabe.sample;

import android.widget.TextView;

import com.markjmind.uni.UniIntroFragment;
import com.markjmind.uni.mapper.annotiation.GetView;
import com.markjmind.uni.mapper.annotiation.Layout;
import com.markjmind.uni.mapper.annotiation.Param;
import com.markjmind.uni.mapper.annotiation.Timeout;
import com.muabe.sample.menu.MenuFragment;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong - Oh)
 * @email markjmind@gmail.com
 * @since 2018-04-30
 */

@Timeout(1500)
@Layout(R.layout.intro)
public class IntroFragment extends UniIntroFragment{
    @Param
    String version;

    @GetView
    TextView test_version;

    @Override
    public void onPre() {
        test_version.setText(version);
    }

    @Override
    public void onPost() {
        getBuilder()
                .setHistory(false)
                .replace(new MenuFragment());
    }
}
