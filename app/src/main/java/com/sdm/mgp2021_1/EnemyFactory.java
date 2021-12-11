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
       GHOST_BOSS
   }

   public static void Create(ENEMY_TYPE type, Vector3 position) {

       EntityBase enemy = null;

        switch (type.name()) {
            case "SPAM_BASIC":
                EnemyBasic newguy = new EnemyBasic();
                newguy.SetHealth(30);
                newguy.SetBMP(R.drawable.sans2);
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
                otherguy.SetBMP(R.drawable.sans2);
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

        }
        if (enemy != null) {

            EntityManager.Instance.AddEntity(enemy, EntityBase.ENTITY_TYPE.ENT_EVIL);
        }
   }


}
