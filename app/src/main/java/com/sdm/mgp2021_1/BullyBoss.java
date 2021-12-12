package com.sdm.mgp2021_1;

public class BullyBoss extends EnemyBasic{

    private ENTITY_TYPE type = ENTITY_TYPE.ENT_EVIL;

    private  int RenderLayer = 0;


    //Boss phases variables
    private int phase = 0;
    private boolean toSwitch = false;


    @Override
    public void Update(float _dt) {
        //do movement
        doBehaviour(_dt);


        bulletspawner.SetPos(pos);
        bulletspawner.Update(_dt);

        //loses health every second
        health -= _dt;

        //if health healed back to normal, return to stronger phase 0.
        if (health >= 60 && phase > 0) {
            PhaseSwitch(0);
        }

        //after 1 minute, become angry
        if (health < 60 && phase == 0) {
            PhaseSwitch(1);
        }

        //after 2 minutes, final enraged form
        if (health < 120 && phase < 2) {
            PhaseSwitch(2);
        }


        //return to phase 1 after healing
        if (health > 120 && phase == 2) {
            PhaseSwitch(1);
        }

    }

    @Override
    public void OnHit(Collidable _other) {
        if (_other.GetType() == "ENT_BULLET") {
            health += 10;
        }
    }

    private void Phase1() {
        bigspeed.x = 150.f;
        SetBehaviour(BEHAVIOURS.AI_UPDOWN);
        SetPattern(EnemyBulletFactory.PATTERN.PISS);
        SetBMP(R.drawable.sans2);
    }

    private void Phase2() {
        smallspeed.y = 300.f;
    }

    private void PhaseSwitch(int phaseDiff) {

        phase = phaseDiff;

        switch (phase) {
            case 0:
                bigspeed.x = 50.f;
                RemoveBehavior(BEHAVIOURS.AI_UPDOWN);
                SetBMP(R.drawable.sans);
                break;
            case 1:
                Phase1();
                break;
            case 2:
                Phase2();
                break;
        }
        toSwitch = false;
    }


}
