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
   }

   private static void SpawnEnemy(float hp) {
        EnemyBasic newguy = new EnemyBasic();
        newguy.SetHealth(hp);
        EntityManager.Instance.AddEntity(newguy,EntityBase.ENTITY_TYPE.ENT_EVIL);

   }

}
