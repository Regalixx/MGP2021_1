package com.sdm.mgp2021_1;

import android.util.Log;
import android.view.MotionEvent;

// Created by TanSiewLan2021

// Manages the touch events

public class TouchManager {

    float firstX_point, firstY_point;

    public final static TouchManager Instance = new TouchManager();

    private TouchManager(){

    }

    public enum TouchState{
        NONE,
        DOWN,
        MOVE
    }

    private int posX, posY;
    private TouchState status = TouchState.NONE; //Set to default as NONE

    public boolean HasTouch(){  // Check for a touch status on screen
        return status == TouchState.DOWN || status == TouchState.MOVE;
    }

    public boolean IsDown(){
        return status == TouchState.DOWN;
    }

    public int GetPosX(){
        return posX;
    }

    public int GetPosY(){
        return posY;
    }

    public void Update(int _posX, int _posY, int _motionEventStatus){
        posX = _posX;
        posY = _posY;

        switch (_motionEventStatus){
            case MotionEvent.ACTION_DOWN:
                status = TouchState.DOWN;
                break;

            case MotionEvent.ACTION_MOVE:
                status = TouchState.MOVE;
                break;

            case MotionEvent.ACTION_UP:
                status = TouchState.NONE;
                break;
        }
    }

    public void onTouchEvent(MotionEvent event) {

        int action = event.getAction();

        switch (action) {

            case MotionEvent.ACTION_DOWN:
                firstX_point = event.getRawX();
                firstY_point = event.getRawY();
                break;

            case MotionEvent.ACTION_UP:

                float finalX = event.getRawX();
                float finalY = event.getRawY();

                int distanceX = (int) (finalX - firstX_point);
                int distanceY = (int) (finalY - firstY_point);

                //   if (Math.abs(distanceX) > Math.abs(distanceY)) {
                //      if ((firstX_point < finalX)) {
                //Log.d("Test", "Left to Right swipe performed");
                //    } else {
                //      Log.d("Test", "Right to Left swipe performed");
                // }
                //}
                //
            {
                    if ((firstY_point < finalY)) {
                        Log.d("Test", "Up to Down swipe performed");
                    } else {
                        Log.d("Test", "Down to Up swipe performed");
                    }
                }


                break;
        }
    }
}

