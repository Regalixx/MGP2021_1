package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;

import java.util.Set;

public class VideogameEntity implements EntityBase,Collidable{


    public final static VideogameEntity Instance = new VideogameEntity();

    public Bitmap bmp = null;
    private Bitmap scaledbmp = null;


    private boolean isDone = false;

    int screenWidth,screenHeight;

    public float yStart;

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
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.videogame1);

        isInit = true;

        //Setup all our variables

        xPos = EnemyBoss3.Instance.GetPosX(); //setting the x position to spawn
        yStart = yPos = EnemyBoss3.Instance.GetPosY(); //setting the y position to spawn
       // yLimit = _view.getHeight()-bmp.getHeight() * 0.5f; //setting constraint

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

    }

    @Override
    public void Update(float _dt) {


        if (yPos >  screenHeight){
            isDone = true;
            PlayerEntity.Instance.SetHP(PlayerEntity.Instance.GetHP()-5);
            EnemyBoss3.Instance.SetHealth(EnemyBoss3.Instance.GetHealth()+5);

        }

        yPos += _dt *  350;
    }

    @Override
    public void Render(Canvas _canvas) {
        Matrix transform = new Matrix();
        transform.postTranslate(-bmp.getWidth() * 0.5f, 0);

        transform.postTranslate(xPos,yPos);

        _canvas.drawBitmap(bmp, transform, null);

    }

    @Override
    public boolean IsInit() {
        return isInit;
    }

    @Override
    public int GetRenderLayer() {

        return LayerConstants.VIDEOGAMES_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        RenderLayer = _newLayer;
    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_EVIL;
    }

    public static VideogameEntity Create() {
        VideogameEntity result = new VideogameEntity();
        EntityManager.Instance.AddEntity(result,ENTITY_TYPE.ENT_VIDEOGAME);
        return result;
    }

    @Override
    public String GetType() {
        return "ENT_EVIL";
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
        if (_other.GetType() == "ENT_BULLET") //Change this to enemy entity
        {
            SetIsDone(true);
            EnemyBoss3.Instance.SetHealth(EnemyBoss3.Instance.GetHealth()-5);
            //EnemyBoss3.Instance.SetHealth(EnemyBoss1.Instance.GetHealth()-5);
        }
    }
}
