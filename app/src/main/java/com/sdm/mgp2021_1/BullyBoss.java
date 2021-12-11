package com.sdm.mgp2021_1;

public class BullyBoss extends EnemyBasic{

    private ENTITY_TYPE type = ENTITY_TYPE.ENT_EVIL;

    private  int RenderLayer = 0;


    //Boss phases stuff


    @Override
    public void Update(float _dt) {
        //do movement
        doBehaviour(_dt);

        bulletspawner.SetPos(pos);
        bulletspawner.Update(_dt);



    }


}
