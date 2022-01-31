package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.metrics.PlaybackErrorEvent;
import android.util.Log;
import android.view.SurfaceView;

public class ForcefieldEntityPlayer implements EntityBase,Collidable {
    public static ForcefieldEntityPlayer Instance = new ForcefieldEntityPlayer();

    public Bitmap bmp = ResourceManager.Instance.GetBitmap(R.drawable.forcefield2);

    public boolean forceFieldplayer = false;
    private boolean isDone = false;

    int screenWidth,screenHeight;

    public float yStart;

    private boolean spawn = false;
    private float lifetime;
    private int HP = 0;
    private float shieldTimer = 0;
    public float xPos = 0;
    public float yPos = 0;

    private float yLimit = 0;
    private float xLimit = 0;

    private int RenderLayer = 0; //Layer1 to be rendered.  Check layerconstant.Java
    private boolean isInit = false;
    private boolean hasCollided = false;

    @Override
    public boolean IsDone() {

        return isDone;
    }
    @Override
    public void SetIsDone(boolean _isDone){
        isDone = _isDone;

    }

    @Override
    public void Init(SurfaceView _view){


        bmp = ResourceManager.Instance.GetBitmap(R.drawable.forcefield2);


        HP = 8;


        isInit = true;


        xPos = PlayerEntity.Instance.GetPosX(); //setting the x position to spawn
        yStart = yPos = PlayerEntity.Instance.GetPosY(); //setting the y position to spawn
        yLimit = _view.getHeight()-bmp.getHeight() * 0.5f; //setting constraint


    }

    @Override
    public void Update(float _dt) {


        shieldTimer += _dt;

        if (shieldTimer >= 10 )
        {
            SetIsDone(true);
        }

        //  if (lifetime < 0.0f ) {
        //    SetIsDone(true);   // <--- This part here or this code, meant when time is up, kill the items.
        //}
        if (TouchManager.Instance.HasTouch()){ //the moment player touch on the screen
            //Check Collision here!
            float imgRadius = bmp.getWidth() * 0.5f;
            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(),
                    0.0f,xPos,yPos,imgRadius ) || hasCollided){

                hasCollided = true;


            }

        }
spawn = true;


        xPos = PlayerEntity.Instance.GetPosX();
        yPos = PlayerEntity.Instance.GetPosY();


        if (HP <= 0 ) {
            SetIsDone(true);
        }


    }

    @Override
    public void Render(Canvas _canvas) {

        if (spawn == true) {
            _canvas.drawBitmap(bmp, xPos, yPos, null); // 1st image
        }

    }


    @Override
    public boolean IsInit() {
        return isInit;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.FORCEFIELD_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        RenderLayer = _newLayer;
    }

    @Override
    public EntityBase.ENTITY_TYPE GetEntityType() {
        return EntityBase.ENTITY_TYPE.ENT_FORCEFIELD;
    }

    public static ForcefieldEntityPlayer Create() {
        Log.v("Forcefield","Created");
        ForcefieldEntityPlayer result = new ForcefieldEntityPlayer();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_FORCEFIELD);
        return result;
    }

    @Override
    public String GetType() {
        return "ENT_FFPLAYER";
    }

    @Override
    public float GetPosX() {
        return xPos;
    }

    @Override
    public float GetPosY() {
        return yPos;
    }

    @Override
    public float GetRadius() {

      return bmp.getWidth() * 0.5f;
    }

    @Override
    public void OnHit(Collidable _other) {
        if (_other.GetType() == "ENT_EVIL") //Change this to enemy entity
        {
            HP -= 1;
        }
    }

    public void SetHP(int _hp) {
        HP = _hp;
    }
    public int GetHP() {
        return HP;
    }
}
