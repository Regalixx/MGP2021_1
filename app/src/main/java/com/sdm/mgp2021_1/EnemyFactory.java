package com.sdm.mgp2021_1;


import android.util.Log;

import org.w3c.dom.Entity;

public class EnemyFactory {

   enum ENEMY_TYPE {
       TUTORIAL_MINION,
       SPAM_BASIC,
       SPAM_BOSS,
       SPAM_MINION,
       BULLY_BASIC,
       BULLY_MINION,
       BULLY_BOSS,
       GHOST_BASIC,
       GHOST_MINION,
       GHOST_BOSS
   }

   public static EnemyBasic Create(ENEMY_TYPE type, Vector3 position) {

       EnemyBasic enemy = null;

        switch (type) {
            case SPAM_BASIC:
                enemy = new EnemyBasic();
                enemy.SetHealth(30);
                enemy.SetBMP(R.drawable.threat);
                enemy.SetPos(position);
                enemy.SetBehaviour(EnemyBasic.BEHAVIOURS.AI_SMALLLEFTRIGHT);
                enemy.SetBehaviour(EnemyBasic.BEHAVIOURS.AI_SWEEPWIDTH);
                enemy.SetBehaviour(EnemyBasic.BEHAVIOURS.AI_FALL);
                enemy.SetPattern(EnemyBulletFactory.PATTERN.CIRCLE);
                enemy.SetPattern(EnemyBulletFactory.PATTERN.PISS);
                break;
            case SPAM_MINION:
                enemy = new EnemyBasic();
                enemy.SetBMP(R.drawable.threat);
                enemy.SetPos(position);
                enemy.SetHealth(10);
                enemy.SetBehaviour(EnemyBasic.BEHAVIOURS.AI_FALL);
                enemy.SetPattern(EnemyBulletFactory.PATTERN.STRAIGHT);

                break;
            case TUTORIAL_MINION:
                enemy = new EnemyBasic();
                enemy.SetBMP(R.drawable.threat);
                enemy.SetPos(position);
                enemy.SetHealth(10);
                enemy.SetBehaviour(EnemyBasic.BEHAVIOURS.AI_FALL);

                break;

            case SPAM_BOSS:
                enemy = new EnemyBoss1();

                enemy.SetHealth(100);
                //EnemyBoss1.Instance = boss;


                break;
            case BULLY_BOSS:
                enemy = new BullyBoss();
                enemy.SetHealth(90);
                enemy.SetPos(position);
                enemy.SetCooldown(1.f);
                enemy.SetBMP(R.drawable.bully1);
                enemy.SetBehaviour(EnemyBasic.BEHAVIOURS.AI_SWEEPWIDTH);
                enemy.SetPattern(EnemyBulletFactory.PATTERN.SHOTGUN);
                enemy.bigspeed.x = 100.f;
                break;

            case BULLY_BASIC:
                enemy = new EnemyBasic();
                enemy.SetHealth(10);
                enemy.SetPos(position);
                enemy.SetBehaviour(EnemyBasic.BEHAVIOURS.AI_FALL);
                enemy.SetPattern(EnemyBulletFactory.PATTERN.CIRCLE);
                enemy.SetBMP(R.drawable.bullyminion);
                break;

            case BULLY_MINION:
                enemy = new EnemyBasic();
                enemy.SetHealth(10);
                enemy.SetPos(position);
                enemy.SetBehaviour(EnemyBasic.BEHAVIOURS.AI_FALL);
                enemy.SetPattern(EnemyBulletFactory.PATTERN.SPIRAL);
                enemy.SetBMP(R.drawable.bullyminion);
                break;

            case GHOST_BOSS:
                enemy = new EnemyBoss3();
                enemy.SetBMP(R.drawable.boss3);
                enemy.SetPos(position);
                enemy.SetHealth(100);
                //EnemyBoss1.Instance = boss;



                break;
            case GHOST_BASIC:
                enemy = new EnemyBasic();
                enemy.SetBMP(R.drawable.minion);
                enemy.SetPos(position);
                enemy.SetHealth(10);
                enemy.SetBehaviour(EnemyBasic.BEHAVIOURS.AI_FALL);
                enemy.SetPattern(EnemyBulletFactory.PATTERN.STRAIGHT);


                break;
            case GHOST_MINION:
                enemy = new EnemyBasic();
                enemy.SetBMP(R.drawable.minion);
                enemy.SetPos(position);
                enemy.SetHealth(10);
                enemy.SetBehaviour(EnemyBasic.BEHAVIOURS.AI_UPDOWN);
                enemy.SetPattern(EnemyBulletFactory.PATTERN.SHOTGUN);
                break;

        }
        if (enemy != null) {

            EntityManager.Instance.AddEntity(enemy, EntityBase.ENTITY_TYPE.ENT_EVIL);
        }

        return enemy;
   }


}
