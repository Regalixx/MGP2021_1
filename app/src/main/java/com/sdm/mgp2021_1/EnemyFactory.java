package com.sdm.mgp2021_1;


import android.util.Log;

import org.w3c.dom.Entity;

public class EnemyFactory {

   enum ENEMY_TYPE {
       SPAM_BASIC,
       SPAM_BOSS,
       SPAM_MINION,
       BULLY_BASIC,
       BULLY_BOSS,
       GHOST_BASIC,
       GHOST_MINION,
       GHOST_BOSS
   }

   public static void Create(ENEMY_TYPE type, Vector3 position) {

       EntityBase enemy = null;

        switch (type.name()) {
            case "SPAM_BASIC":
                EnemyBasic newguy = new EnemyBasic();
                newguy.SetHealth(30);
                newguy.SetBMP(R.drawable.threat);
                newguy.SetPos(position);
                newguy.SetBehaviour(EnemyBasic.BEHAVIOURS.AI_SMALLLEFTRIGHT);
                newguy.SetBehaviour(EnemyBasic.BEHAVIOURS.AI_SWEEPWIDTH);
                newguy.SetBehaviour(EnemyBasic.BEHAVIOURS.AI_FALL);
                newguy.SetPattern(EnemyBulletFactory.PATTERN.CIRCLE);
                newguy.SetPattern(EnemyBulletFactory.PATTERN.PISS);
                enemy = newguy;
                break;
            case "SPAM_MINION":
                EnemyBasic otherguy = new EnemyBasic();
                otherguy.SetBMP(R.drawable.threat);
                otherguy.SetPos(position);
                otherguy.SetHealth(10);
                otherguy.SetBehaviour(EnemyBasic.BEHAVIOURS.AI_FALL);
                otherguy.SetPattern(EnemyBulletFactory.PATTERN.STRAIGHT);
                enemy = otherguy;
                break;

            case "SPAM_BOSS":
                EnemyBoss1 boss = new EnemyBoss1();

                boss.SetHealth(100);
                //EnemyBoss1.Instance = boss;

                enemy = boss;

                break;
            case "BULLY_BOSS":
                BullyBoss boss2 = new BullyBoss();
                boss2.SetHealth(180);
                boss2.SetBMP(R.drawable.sans);
                enemy = boss2;
                break;
            case "GHOST_BOSS":
                EnemyBoss3 boss3 = new EnemyBoss3();
                boss3.SetBMP(R.drawable.boss3);
                boss3.SetPos(position);
                boss3.SetHealth(100);
                //EnemyBoss1.Instance = boss;

                enemy = boss3;

                break;
            case "GHOST_BASIC":
                GhostBasic basic = new GhostBasic();
                basic.SetBMP(R.drawable.minion);
                basic.SetPos(position);
                basic.SetHealth(10);
                basic.SetBehaviour(EnemyBasic.BEHAVIOURS.AI_FALL);
                basic.SetPattern(EnemyBulletFactory.PATTERN.STRAIGHT);
                enemy = basic;

                break;
            case "GHOST_MINION":
                GhostBasic minion = new GhostBasic();
                minion.SetBMP(R.drawable.minion);
                minion.SetPos(position);
                minion.SetHealth(10);
                minion.SetBehaviour(EnemyBasic.BEHAVIOURS.AI_UPDOWN);
                minion.SetPattern(EnemyBulletFactory.PATTERN.SHOTGUN);
                enemy = minion;
                break;

        }
        if (enemy != null) {

            EntityManager.Instance.AddEntity(enemy, EntityBase.ENTITY_TYPE.ENT_EVIL);
        }
   }


}
