package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;

public class EmailsEntity implements  EntityBase,Collidable{

    public final static EmailsEntity Instance = new EmailsEntity();

    public Bitmap bmp = null;
    private Bitmap scaledbmp = null;

    public boolean shoot = false;
    private boolean renderBullet = false;
    public boolean toggleshoot  = false;
    public static int EnemiesKilled;

    private boolean isDone = false;

    int screenWidth,screenHeight;

    public float yStart;

    public float xPos = 0;
    public float yPos = 0;


    private float yLimit = 0;
    private float xLimit = 0;

    int damage = 0;

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
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.email);

        isInit = true;

        //Setup all our variables

        xPos = EnemyBoss1.Instance.GetPosX(); //setting the x position to spawn
        yStart = yPos = EnemyBoss1.Instance.GetPosY(); //setting the y position to spawn
        yLimit = _view.getHeight()-bmp.getHeight() * 0.5f; //setting constraint

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

    }

    @Override
    public void Update(float _dt) {

        if (GameSystem.Instance.GetIsPaused() == true)
        {
            return;
        }

        if (TouchManager.Instance.HasTouch()){ //the moment player touch on the screen
            //Check Collision here!
            float imgRadius = bmp.getWidth() * 0.5f;
            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(),
                    0.0f,xPos,yPos,imgRadius ) || hasCollided){

                hasCollided = true;


            }

        }

        if (yPos >  screenHeight){
            isDone = true;
            PlayerEntity.Instance.SetHP(PlayerEntity.Instance.GetHP()-5);

        }

        yPos += _dt *  550;

        if (EnemyBoss1.Instance.GetHealth() <= 50){
            yPos += _dt * 700;
        }

        //Gravity
        // gravityVec += _dt * 10.0f;
        //yPos += gravityVec;
    }

    @Override
    public void Render(Canvas _canvas) {
        //Matrix transform = new Matrix();
        // transform.postTranslate(-bmp.getWidth() * 0.5f, 0); // make it not look so scuffed.

        //Scale and rotate here
        //transform.postTranslate(xPos,yPos);
        //_canvas.drawBitmap(bmp, transform, null);

            //Log.d("Shoot","Has been RENDERED");
            _canvas.drawBitmap(bmp, xPos, yPos, null); // 1st image



    }

    @Override
    public boolean IsInit() {
        return isInit;
    }

    @Override
    public int GetRenderLayer() {

        return LayerConstants.EMAIL_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        RenderLayer = _newLayer;
    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_EMAILS;
    }

    public static EmailsEntity Create() {
        EmailsEntity result = new EmailsEntity();
        EntityManager.Instance.AddEntity(result,ENTITY_TYPE.ENT_EMAILS);
        return result;
    }

    @Override
    public String GetType() {
        return "EmailsEntity";
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
        return 0;
    }

    @Override
    public void OnHit(Collidable _other) {
        if (_other.GetType() == "TrashbinEntity")
        {
            SetIsDone(true);
            if (ForcefieldEntity.Instance == null || ForcefieldEntity.Instance.IsDone() == true) {
                EnemyBoss1.Instance.SetHealth(EnemyBoss1.Instance.GetHealth() - 5);
                AudioManager.Instance.PlayAudio(R.raw.correct,0.9f);
            }
            else {
                Log.d("Collided", "Forcefield exists somehow");
            }
        }
    }




}
