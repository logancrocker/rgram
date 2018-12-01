package com.rgram.rgram;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class Aboutus extends AppCompatActivity {
    LinearLayout maboutContainer;
    AnimationDrawable mAnimationDrwable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        maboutContainer=findViewById(R.id.about_container);
        mAnimationDrwable= (AnimationDrawable) maboutContainer.getBackground();
        mAnimationDrwable.setEnterFadeDuration(2000);
        mAnimationDrwable.setExitFadeDuration(2000);
    }
    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(mAnimationDrwable!=null&&!mAnimationDrwable.isRunning()){
            mAnimationDrwable.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mAnimationDrwable!=null&&mAnimationDrwable.isRunning()){
            mAnimationDrwable.stop();
        }
    }
}
