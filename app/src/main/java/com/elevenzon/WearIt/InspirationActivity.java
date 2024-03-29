package com.elevenzon.WearIt;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import me.relex.circleindicator.CircleIndicator;

public class InspirationActivity extends AppCompatActivity {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] img = {R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four, R.drawable.five, R.drawable.six, R.drawable.seven,R.drawable.eight,R.drawable.nine,R.drawable.ten};
    private ArrayList<Integer>  ImgArray = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspiration);
        init();
    }

    private void init (){
        ImgArray.addAll(Arrays.asList(img));

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyAdapter(InspirationActivity.this, ImgArray));
        CircleIndicator indicator = (CircleIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            @Override
            public void run() {
                if (currentPage == img.length){
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        //Auto start
       /* Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);*/
    }
}