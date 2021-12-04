package com.sdm.mgp2021_1;

// Created by TanSiewLan2021
// Create a GamePage is an activity class used to hold the GameView which will have a surfaceview

import android.app.Activity;
import android.os.Bundle;
import android.os.Debug;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.GestureDetectorCompat;

public class GamePage extends AppCompatActivity {

    public static GamePage Instance = null;
    private GestureDetectorCompat gestureDetectorCompat = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide titlebar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  // Hide topbar

        Instance = this;

        setContentView(new GameView(this)); // Surfaceview = GameView

        // Create a common gesture listener object.
        DetectSwipeGestureListener gestureListener = new DetectSwipeGestureListener();
        // Set activity to the gesture listener
        gestureListener.setActivity(this);
        // Create the gesture detector with the gesture listener.
        gestureDetectorCompat = new GestureDetectorCompat(this, gestureListener);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // WE are hijacking the touch event into our own system

        gestureDetectorCompat.onTouchEvent(event);

        int x = (int) event.getX();
        int y = (int) event.getY();

        TouchManager.Instance.Update(x, y, event.getAction());

        return true;
    }

    public void DisplayBullet(){
        BulletEntity.Create();
    }






}
