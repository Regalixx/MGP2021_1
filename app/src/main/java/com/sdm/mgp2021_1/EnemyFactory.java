package com.sdm.mgp2021_1;


public class EnemyFactory {

   enum ENEMY_TYPE {
       SPAM_BASIC,
       SPAM_BOSS,
       BULLY_BASIC,
       BULLY_BOSS,
       GHOST_BASIC,
       GHOST_BOSS
   };

   public static void Create(ENEMY_TYPE type) {
        if (type.name() == "SPAM_BASIC") {
            SpawnEnemy(30);
        }
       if (type.name() == "SPAM_BOSS") {
           SpawnEnemy(100);
       }
   }

   private static void SpawnEnemy(float hp) {
       // EnemyBasic newguy = new EnemyBasic();
       // newguy.SetHealth(hp);
       // EntityManager.Instance.AddEntity(newguy,EntityBase.ENTITY_TYPE.ENT_EVIL);

       EnemyBoss1 Boss = new EnemyBoss1();
       Boss.SetHealth(100);
       EntityManager.Instance.AddEntity(Boss,EntityBase.ENTITY_TYPE.ENT_BOSS1);

   }

}
