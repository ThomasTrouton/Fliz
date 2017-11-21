package com.example.thoma.fliz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {

    private static final int UI_ANIMATION_DELAY = 100;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private ScrollView mScrollView;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContentView = findViewById(R.id.fullscreen_content);

        //This bit of code is needed to make the app start at the bottom of the picture
        mScrollView = findViewById(R.id.scrollView1);
        mScrollView.postDelayed(new Runnable(){
            @Override
            public void run() {
                mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 100L);
       }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        hide();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hide();
    }

    private void hide() {
        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }


    //The following three methods launch the learning activities,
    //There is probably a better way to do this.

    public void lesson1(android.view.View view)
    {
        Intent intent = new Intent(this, Lesson1.class);
        startActivity(intent);
    }

    public void lesson2(android.view.View view)
    {
        Intent intent = new Intent(this, Lesson2.class);
        startActivity(intent);
    }

    public void lesson3(android.view.View view)
    {
        Intent intent = new Intent(this, Lesson3.class);
        startActivity(intent);
    }

}

