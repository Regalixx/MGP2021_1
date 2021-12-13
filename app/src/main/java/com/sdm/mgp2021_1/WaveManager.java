package com.sdm.mgp2021_1;



//Class to manage the waves of bosses.
//Manages spawning of bosses and the stats needed to be displayed

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;

public class WaveManager {
    //WaveManager is a singleton
    public final static WaveManager Instance = new WaveManager();

    private EnemyBasic bossToSpawn = null;

    private DisplayMetrics metrics = null;

    private int wave = 0;

    private Vector3 startingpos = null;

    private boolean toExit = false;


    public boolean TimeToExit() { return toExit;}

    public void Init(SurfaceView _view) {

        wave = 0;
        metrics = _view.getResources().getDisplayMetrics();

        startingpos = new Vector3(metrics.widthPixels*0.5f,2,0);
        bossToSpawn = EnemyFactory.Create(EnemyFactory.ENEMY_TYPE.SPAM_BOSS, startingpos);
    }

    public void Update(float _dt) {

        if (bossToSpawn.IsDone()) {
            Log.println(Log.DEBUG,"Wave Manager", "Boss is done. Spawn another");
            if (wave == 0) {
                bossToSpawn = EnemyFactory.Create(EnemyFactory.ENEMY_TYPE.BULLY_BOSS, startingpos);
                wave += 1;
            }
            else if (wave == 1) {
                bossToSpawn = EnemyFactory.Create(EnemyFactory.ENEMY_TYPE.GHOST_BOSS, startingpos);
                wave += 1;
            }
            else {
                //Return to menu first
                //figure out winscreen another time
                toExit = true;
            }
        }

    }


}
