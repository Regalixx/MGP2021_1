package com.sdm.mgp2021_1;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class DetectSwipeGestureListener extends GestureDetector.SimpleOnGestureListener {


    public static final DetectSwipeGestureListener Instance = new DetectSwipeGestureListener();


    // Minimal x and y axis swipe distance.
    private static int MIN_SWIPE_DISTANCE_X = 100;
    private static int MIN_SWIPE_DISTANCE_Y = 100;
    // Maximal x and y axis swipe distance.
    private static int MAX_SWIPE_DISTANCE_X = 1000;
    private static int MAX_SWIPE_DISTANCE_Y = 1000;
    // Source activity that display message in text view.
    public boolean isShot = false;
    private GamePage activity = null;
    public GamePage getActivity() {
        return activity;
    }
    public void setActivity(GamePage activity) {
        this.activity = activity;
    }
    /* This method is invoked when a swipe gesture happened. */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // Get swipe delta value in x axis.



        float deltaX = e1.getX() - e2.getX();
        // Get swipe delta value in y axis.
        float deltaY = e1.getY() - e2.getY();
        // Get absolute value.
        float deltaXAbs = Math.abs(deltaX);
        float deltaYAbs = Math.abs(deltaY);
        // Only when swipe distance between minimal and maximal distance value then we treat it as effective swipe
        if((deltaXAbs >= MIN_SWIPE_DISTANCE_X) && (deltaXAbs <= MAX_SWIPE_DISTANCE_X))
        {
            if(deltaX > 0)
            {
                Log.d("myTag", "Swiped left");
            }else
            {
                Log.d("myTag", "Swiped right");
            }
        }
        if((deltaYAbs >= MIN_SWIPE_DISTANCE_Y) && (deltaYAbs <= MAX_SWIPE_DISTANCE_Y))
        {
            if(deltaY > 0)
            {
                if (OptionsPage.Instance == null) {
                    BulletEntity.Create();// if its swiped up, make a bullet
                    AudioManager.Instance.PlayAudio(R.raw.laserbeam,1.0f);
                }
                else if (OptionsPage.Instance.active == false) {

                    BulletEntity.Create();// if its swiped up, make a bullet
                    AudioManager.Instance.PlayAudio(R.raw.laserbeam,1.0f);
                }

            }else
            {
                Log.d("myTag", "Swiped Down");
                //BulletEntity.Instance.shoot = false;
            }
        }
        return true;
    }
    // Invoked when single tap screen.
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
      //  this.activity.displayMessage("Single tap occurred.");
        if (OptionsPage.Instance == null) {
            return true;
        }
        else if (OptionsPage.Instance.active == true) {

            BulletEntity.Create();// if its swiped up, make a bullet
            AudioManager.Instance.PlayAudio(R.raw.laserbeam,1.0f);
        }
        return true;
    }
    // Invoked when double tap screen.
    @Override
    public boolean onDoubleTap(MotionEvent e) {
       // this.activity.displayMessage("Double tap occurred.");

        if (OptionsPage.Instance == null) {
            return true;
        }
        else if (OptionsPage.Instance.active == true) {

            BulletEntity.Create();// if its swiped up, make a bullet
            AudioManager.Instance.PlayAudio(R.raw.laserbeam,1.0f);
        }

        return true;
    }
}
