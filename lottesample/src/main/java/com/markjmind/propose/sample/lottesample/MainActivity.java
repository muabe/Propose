package com.markjmind.propose.sample.lottesample;

import android.app.Activity;
import android.os.Bundle;

import com.markjmind.uni.boot.SimpleBoot;

public class MainActivity extends Activity {
    final String version = "2.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LottieFragment introFragment = new LottieFragment();

        SimpleBoot.putContentView(this)
                .setHomeFragment(introFragment);
    }

    @Override
    public void onBackPressed() {
        if(!SimpleBoot.onBackPressed(this)) {
            super.onBackPressed();
        }
    }
}
