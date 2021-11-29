package com.sdm.mgp2021_1;

// Created by TanSiewLan2021
// Create a GamePage is an activity class used to hold the GameView which will have a surfaceview

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class GamePage extends Activity {

    public static GamePage Instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide titlebar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  // Hide topbar

        Instance = this;

        setContentView(new GameView(this)); // Surfaceview = GameView
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // WE are hijacking the touch event into our own system
        int x = (int) event.getX();
        int y = (int) event.getY();

        TouchManager.Instance.Update(x, y, event.getAction());

        return true;
    }

    private  class SwipeListener implements View.OnTouchListener {
        //Initialize variable
        GestureDetector gestureDetector;

        //Create constructor


        SwipeListener(View view) {
            int threshold = 100;
            //int_velocity_threshold = 100;

            //Initialize  simple gesture listener
           // GestureDetector.SimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener()
        }
        @Override
        public boolean onTouch (View view, MotionEvent motionEvent){
            return false;
        }

    }

}

