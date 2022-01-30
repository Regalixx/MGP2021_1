package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;

public class ScoreMultiplierPowerupEntity implements  EntityBase,Collidable {
    public final static ScoreMultiplierPowerupEntity Instance = new ScoreMultiplierPowerupEntity();

    public Bitmap bmp = null;
    public boolean spawn = false;
    public boolean shoot = false;
    private boolean renderBullet = false;
    public boolean toggleshoot  = false;
    private int currscore;
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
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.scoremultiplier);

        isInit = true;

        //Setup all our variables
        // define the range
        int max = _view.getWidth();
        int min = 0;
        int range = max - min + 1;

        // generate random numbers within 1 to 10
        int rand = (int)(Math.random() * range) + min;

        xPos = rand; //setting the x position to spawn
        yStart = yPos = _view.getHeight() * 0.2f; //setting the y position to spawn
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


        spawn = true;

        if (yPos >  screenHeight){
            isDone = true;
            PlayerEntity.Instance.SetHP(PlayerEntity.Instance.GetHP()-5);

        }

        yPos += _dt *  550;
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

        return LayerConstants.SCOREMULTIPLIER_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        RenderLayer = _newLayer;
    }

    @Override
    public EntityBase.ENTITY_TYPE GetEntityType() {
        return EntityBase.ENTITY_TYPE.ENT_SCOREMULTIPLIER;
    }

    public static ScoreMultiplierPowerupEntity Create() {
        ScoreMultiplierPowerupEntity result = new ScoreMultiplierPowerupEntity();
        EntityManager.Instance.AddEntity(result, EntityBase.ENTITY_TYPE.ENT_SCOREMULTIPLIER);
        return result;
    }

    @Override
    public String GetType() {
        return "ScoreMultiplierPowerupEntity";
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
        if (_other.GetType() == "ENT_BULLET")
        {
            SetIsDone(true);
            currscore = GameSystem.Instance.GetIntFromSave("Score");
            currscore *= 2;
            GameSystem.Instance.SaveEditBegin();
            GameSystem.Instance.SetIntInSave("Score",currscore);
            GameSystem.Instance.SaveEditEnd();
        }
    }
}
