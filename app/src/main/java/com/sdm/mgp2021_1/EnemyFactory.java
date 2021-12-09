package com.sdm.mgp2021_1;


public class EnemyFactory {

   enum ENEMY_TYPE {
       SPAM_BASIC,
       SPAM_BOSS,
       BULLY_BASIC,
       BULLY_BOSS,
       GHOST_BASIC,
       GHOST_BOSS
   }

   public static void Create(ENEMY_TYPE type, Vector3 position) {
        EnemyBasic newguy = new EnemyBasic();
        newguy.SetPos(position);
        switch (type.name()) {
            case "SPAM_BASIC":
                newguy.SetHealth(30);
                newguy.SetBehaviour(EnemyBasic.BEHAVIOURS.AI_SMALLLEFTRIGHT);
                newguy.SetBehaviour(EnemyBasic.BEHAVIOURS.AI_SWEEPWIDTH);
                newguy.SetBehaviour(EnemyBasic.BEHAVIOURS.AI_UPDOWN);
                newguy.SetBehaviour(EnemyBasic.BEHAVIOURS.AI_FALL);
                break;
            
            case "SPAM_BOSS":
                EnemyBoss1 newguy = new EnemyBoss1();
                newhuy.SetHealth(100);
                break;

        }

        EntityManager.Instance.AddEntity(newguy, EntityBase.ENTITY_TYPE.ENT_EVIL);
   }


}
