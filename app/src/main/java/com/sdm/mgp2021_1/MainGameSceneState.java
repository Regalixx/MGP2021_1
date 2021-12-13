package com.sdm.mgp2021_1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.View;

// Created by TanSiewLan2021

public class MainGameSceneState implements StateBase {
    private float timer = 0.0f;
    public boolean shoot = false;
    private boolean spawnBoss3 = false;
    DisplayMetrics metrics2;

    float firstX_point, firstY_point;



    @Override
    public String GetName() {
        return "MainGame";
    } //if press start should come here

    @Override
    public void OnEnter(SurfaceView _view)
    {
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        WaveManager.Instance.Init(_view);
        RenderBackground.Create(); //Background is an entity
        PlayerEntity.Create();

        TrashbinEntity.Create();
        PausebuttonEntity.Create();

        RenderTextEntity.Create(); // This  is the text

        Log.println(Log.ASSERT,"MainGameScene","Entering Main Game Scene");

        metrics2 = metrics;
        // Example to include another Renderview for Pause Button
    }


    @Override
    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);


    }

    @Override
    public void Update(float _dt) {

        EntityManager.Instance.Update(_dt);

        //if (EnemyBoss1.Instance.IsDone() == true && spawnBoss3 == false){
        //    PlayerEntity.Instance.SetHP(500);
        //    Log.d("Created","Boss3");
        //    EnemyFactory.Create(EnemyFactory.ENEMY_TYPE.GHOST_BOSS, new Vector3(metrics2.widthPixels*0.5f,2,0));
        //    spawnBoss3 = true;
        //}

        WaveManager.Instance.Update(_dt);

        if (PlayerEntity.Instance.GetHP() <= 0)
        {
            StateManager.Instance.ChangeState("GameOver");

        }
        if (WaveManager.Instance.TimeToExit())
        {
            StateManager.Instance.ChangeState("Victory");

        }

    }

    @Override
    public void OnExit() {
        EntityManager.Instance.Clean();
        GamePage.Instance.finish();
    }

    public boolean onTouchEvent(MotionEvent event) {

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

                if (Math.abs(distanceX) > Math.abs(distanceY)) {
                    if ((firstX_point < finalX)) {
                        Log.d("Test", "Left to Right swipe performed");
                    } else {
                        Log.d("Test", "Right to Left swipe performed");
                    }
                }else{
                    if ((firstY_point < finalY)) {
                        Log.d("Test", "Up to Down swipe performed");
                    } else {
                        Log.d("Test", "Down to Up swipe performed");
                    }
                }


                break;
        }

        return true;
    }
}



