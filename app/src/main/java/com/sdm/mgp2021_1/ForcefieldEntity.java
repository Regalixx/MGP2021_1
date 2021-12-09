package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;

public class ForcefieldEntity implements EntityBase,Collidable {

    public static ForcefieldEntity Instance = null;

    public Bitmap bmp = null;
    private Bitmap scaledbmp = null;

    private boolean isDone = false;

    int screenWidth,screenHeight;

    public float yStart;

    private float lifetime;
    private int HP;
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
        //bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.sans2);
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.forcefield);

        //spritePlayer = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.smurf_sprite),4,4,16);

        HP = 5;

        isInit = true;

        xPos = EnemyBoss1.Instance.GetPosX(); //setting the x position to spawn
        yStart = yPos = EnemyBoss1.Instance.GetPosY(); //setting the y position to spawn
        yLimit = _view.getHeight()-bmp.getHeight() * 0.5f; //setting constraint

        Instance = this;
    }

    @Override
    public void Update(float _dt) {


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

        xPos = EnemyBoss1.Instance.GetPosX();
        yPos = EnemyBoss1.Instance.GetPosY();

        if (HP <= 0)
        {
            SetIsDone(true);
        }


    }

    @Override
    public void Render(Canvas _canvas) {

        Matrix transform = new Matrix();
        transform.postTranslate(-bmp.getWidth() * 0.5f, 0); // make it not look so scuffed.

        //Scale and rotate here
        transform.postTranslate(xPos,yPos);
        _canvas.drawBitmap(bmp, transform, null);


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
        return ENTITY_TYPE.ENT_FORCEFIELD;
    }

    public static ForcefieldEntity Create() {
        ForcefieldEntity result = new ForcefieldEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_FORCEFIELD);
        return result;
    }

    @Override
    public String GetType() {
        return "ForcefieldEntity";
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
        return bmp.getHeight() * 0.5f;
    }

    @Override
    public void OnHit(Collidable _other) {
        if (_other.GetType() == "ENT_BULLET") //Change this to enemy entity
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
