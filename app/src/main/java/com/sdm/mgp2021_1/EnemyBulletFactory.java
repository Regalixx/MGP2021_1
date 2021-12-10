package com.sdm.mgp2021_1;



import android.util.Log;

import java.util.Vector;

public class EnemyBulletFactory {


    enum PATTERN {
        STRAIGHT,
        SHOTGUN,
        CIRCLE,
        SPIRAL,
        DOPPLER,
        PISS
    }

    private Vector<PATTERN> patterns = new Vector<PATTERN>();

    private int index = 0;

    private float cooldown = 3;
    private float countup = 0;

    private Vector3 pos = new Vector3(300,0,0);

    public void AddPattern(PATTERN it) {
        patterns.add(it);
    }

    public void Update(double _dt) {


        if (patterns.isEmpty()) {
            return;
        }

        if (countup >= cooldown) {


            switch (patterns.get(index)) {
                case STRAIGHT:
                    Spawn(new Vector3(0, 1, 0));

            }

            countup = 0;
        }
        else {
            countup += 1 * _dt;

        }
    }

    public void SetPos(Vector3 _pos) {
        this.pos = _pos;
    }

    private void Spawn(Vector3 direction) {
        EnemyBullet.Create(pos, direction);
    }

}
