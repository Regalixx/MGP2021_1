package com.sdm.mgp2021_1;



import android.util.Log;

import java.util.Vector;

public class EnemyBulletFactory {


    enum PATTERN {
        STRAIGHT,
        SHOTGUN,
        CIRCLE,
        SPIRAL,
        PISS
    }

    private Vector<PATTERN> patterns = new Vector<PATTERN>();

    private int index = 0;

    private float cooldown = 3;
    private float countup = 2;

    private float spawntimer = 0;

    private float spiralDegrees = 0;

    private float firerate = 0;


    private Vector3 pos = new Vector3(300,0,0);

    public void AddPattern(PATTERN it) {
        patterns.add(it);
    }

    public void SetCooldown(float _cd) { cooldown = _cd;}

    public void Update(double _dt) {


        if (patterns.isEmpty()) {
            return;
        }

        if (countup >= cooldown) {


            switch (patterns.get(index)) {
                case STRAIGHT:
                    AudioManager.Instance.PlayAudio(R.raw.explosion,0.9f);
                    Spawn(new Vector3(0, 1, 0));
                    countup = 0;
                    break;
                case SHOTGUN:
                    AudioManager.Instance.PlayAudio(R.raw.explosion,0.9f);
                    Spawn(new Vector3(0,1,0));
                    Spawn(new Vector3( 1,1,0));
                    Spawn(new Vector3(-1,1,0));
                    countup = 0;
                    break;
                case CIRCLE:
                    AudioManager.Instance.PlayAudio(R.raw.explosion,0.9f);
                    Spawn(new Vector3(0, 1,0));
                    Spawn(new Vector3(1, 1,0));
                    Spawn(new Vector3(-1, 1,0));
                    Spawn(new Vector3(0, -1,0));
                    Spawn(new Vector3(-1, 0,0));
                    Spawn(new Vector3(1, 0,0));
                    Spawn(new Vector3(1, -1,0));
                    Spawn(new Vector3(-1, -1,0));
                    countup = 0;
                    break;
                case SPIRAL:
                    AudioManager.Instance.PlayAudio(R.raw.explosion,0.9f);
                    if (spawntimer >= 1) {
                        spawntimer = 0;
                        countup = 0;
                    }
                    spawntimer += 1 * _dt;
                    spiralDegrees += 360 * _dt ;

                    if (firerate >= 0.1) {
                        Spawn(new Vector3((float) Math.cos(Math.toRadians(spiralDegrees)), (float) Math.sin(Math.toRadians(spiralDegrees)), 0));
                        firerate = 0;
                    }
                    firerate += 1 * _dt;

                    break;

                case PISS:
                    AudioManager.Instance.PlayAudio(R.raw.explosion,0.9f);
                    if (spawntimer >= 0.5) {
                        spawntimer = 0;
                        countup = 0;
                    }

                    spawntimer += 1 * _dt;

                    float random1 = (float)(Math.random());
                    float random2 = (float)(Math.random());
                    if (random1 <= 0.5) {
                        random1 = random1 - 1;
                    }
                    if (random2 <= 0.5) {
                        random2 = random2 - 1;
                    }

                    Spawn(new Vector3(random1, random2, 0));


                    break;

            }


        }
        else {
            countup += 1 * _dt;

        }

        if (countup == 0) {
            IncrementIndex();
        }

    }

    public void SetPos(Vector3 _pos) {
        this.pos = _pos;
    }

    public void IncrementIndex() {
        index += 1;
        if (index >= patterns.size()) {
            index = 0;
        }
    }

    private void Spawn(Vector3 direction) {
        EnemyBullet.Create(pos, direction);
    }

}
