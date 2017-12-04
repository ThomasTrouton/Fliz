package com.example.thoma.fliz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import static com.example.thoma.fliz.MainActivity.UI_ANIMATION_DELAY;

public class Lesson1 extends FragmentActivity {
    private SeekBar mseekBar;
    private GraphView graph;
    private TextView percentText;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private static final int NUM_PAGES = 4;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;





    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };


    public void hide() {
        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        hide();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson1);
        mContentView = findViewById(R.id.l1);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);




        graph = (GraphView) findViewById(R.id.graph);
        percentText = findViewById(R.id.textView2);

        LineGraphSeries mSeries1 = new LineGraphSeries<>(generateData(0.0));
        graph.addSeries(mSeries1);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(21);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(300);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setScrollable(true); // enables vertical scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling

        mSeries1.setDrawBackground(true);
        mSeries1.setBackgroundColor(Color.parseColor("#5500FF00"));

        GridLabelRenderer gridLabel = graph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Years");
        gridLabel.setVerticalAxisTitle("£");
        mseekBar = findViewById(R.id.seekBar);
        mseekBar.setMax(10);
        mseekBar.setX(0);

        mseekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            graph.removeAllSeries();
            LineGraphSeries series =new LineGraphSeries<>(generateData((double)i));
            series.setDrawBackground(true);
            series.setBackgroundColor(Color.parseColor("#5500FF00"));
            graph.addSeries(series);
            percentText.setText("Interest: "+ i +"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        hide();
    }



    private DataPoint[] generateData(double pc ) {
        int count = 22;
        DataPoint[] values = new DataPoint[count];
        values[0] = new DataPoint(0,0);
        for (int i = 1; i < count; i++) {
            double x = i-1;
            double y = 100*Math.pow((1+0.01*pc),i);
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }


    public void launchquiz(View view) {
        Intent intent = new Intent(this, Lesson2.class);
        finish();
        startActivity(intent);
    }

    public void finish(View v){
        finish();
    }

    private static class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        String[] textContents;

        public ScreenSlidePagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new ScreenSlidePageFragment().newInstance(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

    }

    public void leaderboard(View view){
        Intent intent = new Intent(this, Leaderboard.class);
        finish();
        startActivity(intent);
    }


    public void social(View view){
        Intent intent = new Intent(this, Social.class);
        finish();
        startActivity(intent);
    }


    public void search(View view){
        Intent intent = new Intent(this, Search.class);
        finish();
        startActivity(intent);
    }

}
