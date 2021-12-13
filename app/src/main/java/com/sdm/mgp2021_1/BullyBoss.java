package com.sdm.mgp2021_1;

public class BullyBoss extends EnemyBasic{

    private ENTITY_TYPE type = ENTITY_TYPE.ENT_EVIL;

    private  int RenderLayer = 0;

    //Boss phases variables
    private int phase = 0;
    private boolean toSwitch = false;

    private int HealthPhase1 = 60;
    private int HealthPhase2 = 30;

    @Override
    public void Update(float _dt) {
        //do movement
        doBehaviour(_dt);


        bulletspawner.SetPos(pos);
        bulletspawner.Update(_dt);

        //loses health every second
        health -= 2*_dt;

        HandlePhases();

        if (health <= 0) {
            SetIsDone(true);
        }

    }

    @Override
    public void OnHit(Collidable _other) {
        //Heal from bullets
        //Bullies shouldn't be encouraged.
        if (_other.GetType() == "ENT_BULLET") {
            health += 10;
        }
    }

    private void HandlePhases() {
        //if health healed back to normal, return to stronger phase 0.
        if (health >=  HealthPhase1 && phase > 0) {
            PhaseSwitch(0);
        }

        //after 1 minute, become angry
        if (health < HealthPhase1 && phase == 0) {
            PhaseSwitch(1);
        }

        //after 2 minutes, final enraged form
        if (health < HealthPhase2 && phase < 2) {
            PhaseSwitch(2);
        }
        //return to phase 1 after healing
        if (health > HealthPhase2 && phase == 2) {
            PhaseSwitch(1);
        }
    }


    private void Phase1() {
        bigspeed.x = 150.f;
        SetBehaviour(BEHAVIOURS.AI_UPDOWN);
        SetPattern(EnemyBulletFactory.PATTERN.PISS);
        SetBMP(R.drawable.bully2);
    }

    private void Phase2() {
        smallspeed.y = 300.f;
        SetBMP(R.drawable.bully3);
    }

    private void PhaseSwitch(int phaseDiff) {

        phase = phaseDiff;

        switch (phase) {
            case 0:
                bigspeed.x = 50.f;
                RemoveBehavior(BEHAVIOURS.AI_UPDOWN);
                SetBMP(R.drawable.bully1);
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
