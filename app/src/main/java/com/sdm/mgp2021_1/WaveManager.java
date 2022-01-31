package com.sdm.mgp2021_1;



//Class to manage the waves of bosses.
//Manages spawning of bosses and the stats needed to be displayed

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;

import java.util.Vector;

public class WaveManager {
    //WaveManager is a singleton
    public final static WaveManager Instance = new WaveManager();

    private EnemyBasic bossToSpawn = null;

    private EnemyBasic enemiesToSpawn = null;

    public boolean spawnEnemiesShoot = false;

    private float spawnEnemiesTimer =  0;

    private DisplayMetrics metrics = null;

    private int wave = 0;

    private Vector3 startingpos = null;

    private Vector3 randompos = null;

    private int tutorialenemieskilled = 0;

    public boolean toExit = false;

    public boolean tutorial = false;

    public boolean startTutorial = false;

    public boolean TimeToExit() { return toExit;}

    public void Init(SurfaceView _view) {

        wave = 0;

        metrics = _view.getResources().getDisplayMetrics();

        toExit = false;
        startingpos = new Vector3(metrics.widthPixels*0.5f,150,0);


        if (tutorial == true)
        {
            ReadyConfirmDialogFragment newReadyConfirm = new ReadyConfirmDialogFragment();
            newReadyConfirm.show(GamePage.Instance.getSupportFragmentManager(), "ReadyConfirm");
        }
        if (tutorial == false ) {
            bossToSpawn = EnemyFactory.Create(EnemyFactory.ENEMY_TYPE.SPAM_BOSS, startingpos);
        }
    }

    public int GetBossHealth() {
         return (int)Math.ceil(bossToSpawn.GetHealth());

    }

    public int GetWave() {
        return wave;
    }

    public void Update(float _dt) {

        if (tutorial == true && startTutorial == true) {
            spawnEnemiesTimer += _dt;
            int currscore = GameSystem.Instance.GetIntFromSave("Score");
            PlayerEntity.Instance.SetHP(100);



            if (spawnEnemiesTimer >= 2 && currscore < 10)
            {
                int max = metrics.widthPixels;
                int min = 0;
                int range = max - min + 1;
                randompos = new Vector3((int) (Math.random() * range)+min, 150, 0);
                enemiesToSpawn = EnemyFactory.Create(EnemyFactory.ENEMY_TYPE.TUTORIAL_MINION,randompos);
                spawnEnemiesTimer = 0;
            }

            if (currscore == 10 && spawnEnemiesShoot == false)
            {
                if (TutorialNextDialogFragment.IsShown)
                    return;

                Log.v("It works","huh?");
                TutorialNextDialogFragment nextConfirm = new TutorialNextDialogFragment();
                nextConfirm.show(GamePage.Instance.getSupportFragmentManager(), "NextConfirm");
            }

            if (spawnEnemiesShoot == true && spawnEnemiesTimer >= 3)
            {
                int max = metrics.widthPixels;
                int min = 0;
                int range = max - min + 1;
                randompos = new Vector3((int) (Math.random() * range)+min, 150, 0);
                enemiesToSpawn = EnemyFactory.Create(EnemyFactory.ENEMY_TYPE.SPAM_MINION,randompos);
                spawnEnemiesTimer = 0;
            }

            if (currscore >= 25 && spawnEnemiesShoot == true)
            {
                spawnEnemiesShoot = false;
                if (FinishTutorialDialogFragment.IsShown)
                    return;

                Log.v("It works","huh?");
                FinishTutorialDialogFragment finishConfirm = new FinishTutorialDialogFragment();
                finishConfirm.show(GamePage.Instance.getSupportFragmentManager(), "finishConfirm");
            }

        }
//If player plays the game normally
    if (tutorial == false) {
        if (bossToSpawn.IsDone()) {
            //Log.println(Log.DEBUG,"Wave Manager", "Boss is done. Spawn another");
            if (wave == 0) {
                bossToSpawn = EnemyFactory.Create(EnemyFactory.ENEMY_TYPE.BULLY_BOSS, startingpos);
                wave += 1;
                PlayerEntity.Instance.SetHP(200);
                PlayerEntity.Instance.startVibrate();


            } else if (wave == 1) {

                bossToSpawn = EnemyFactory.Create(EnemyFactory.ENEMY_TYPE.GHOST_BOSS, startingpos);
                wave += 1;
                PlayerEntity.Instance.SetHP(200);
                PlayerEntity.Instance.startVibrate();
            } else {
                //Return to menu first
                //figure out winscreen another time
                toExit = true;
            }
        }
    }
    }


}
