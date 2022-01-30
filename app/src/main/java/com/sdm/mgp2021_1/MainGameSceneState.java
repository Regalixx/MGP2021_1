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
import android.widget.TextView;

// Created by TanSiewLan2021

public class MainGameSceneState implements StateBase {
    //private float timer = 0.0f;
    public boolean shoot = false;
    private boolean spawnBoss3 = false;
    private float spawnPowerup = 0.0f;


    DisplayMetrics metrics2;
    String scoreText;

    float firstX_point, firstY_point;

    //Intent intent = new Intent();

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
        //EnemyFactory.Create(EnemyFactory.ENEMY_TYPE.SPAM_BASIC, new Vector3(1,700,0));
        TrashbinEntity.Create();
        PausebuttonEntity.Create();
        MainMenuButtonEntity.Create();

        // Restart score here
       // GameSystem.Instance.ResetScore();

        //timer = 0.0f;

        RenderTextEntity.Create(); // This  is the text

        // Restart score here
        GameSystem.Instance.ResetScore();

        //timer = 0.0f;

        int currScore = 0;
        GameSystem.Instance.SaveEditBegin();
        GameSystem.Instance.SetIntInSave("Score", currScore);
        GameSystem.Instance.SaveEditEnd();

        if (OptionsPage.Instance == null) { //means player did not open options state
            AudioManager.Instance.PlayAudio(R.raw.gamebg, 0.9f);
        }

        if (OptionsPage.Instance != null) { //player did open options state
            if (OptionsPage.Instance.musicactive == true)
            {
            AudioManager.Instance.PlayAudio(R.raw.gamebg, 0.9f);
            }
        }

        //Log.println(Log.ASSERT,"MainGameScene","Entering Main Game Scene");

        metrics2 = metrics;
        // Example to include another Renderview for Pause Button
    }


    @Override
    public void Render(Canvas _canvas)
    {
        EntityManager.Instance.Render(_canvas);

        Paint paint = new Paint();
        paint.setARGB(255,255,0,255);
        paint.setTextSize(64);

        _canvas.drawText(scoreText, 550, 220, paint);



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

        scoreText = String.format("SCORE : %d", GameSystem.Instance.GetIntFromSave("Score"));

        spawnPowerup+=_dt;

        if (spawnPowerup >= 10)
        {
            int max = 2;
            int min = 0;
            int range = max - min + 1;

            // generate random numbers within 1 to 10
            int rand = (int)(Math.random() * range) + min;

            if (rand == 0) { //setting the x position to spawn
                HealthPowerupEntity.Create();
            }
            if (rand == 1) { //setting the x position to spawn
                ScoreMultiplierPowerupEntity.Create();
            }
            if (rand == 2) {
                ShieldPowerupEntity.Create();
            }
            spawnPowerup = 0;

        }

        if (PlayerEntity.Instance.GetHP() <= 0)
        {

            //StateManager.Instance.ChangeState("GameOver");
            //GamePage.Instance.ChangeState(GameOver.class);

        }
        if (WaveManager.Instance.TimeToExit())
        {
            if (SaveConfirmDialogFragment.IsShown)
                return;

            SaveConfirmDialogFragment newSaveConfirm = new SaveConfirmDialogFragment();
            newSaveConfirm.show(GamePage.Instance.getSupportFragmentManager(), "SaveConfirm");

        }

        // Restart score here
        GameSystem.Instance.ResetScore();

    }

    @Override
    public void OnExit() {
        EntityManager.Instance.Clean();
        GamePage.Instance.finish();
        Log.d("MainScene","We Leave");
        ForcefieldEntity.Instance = null;


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



