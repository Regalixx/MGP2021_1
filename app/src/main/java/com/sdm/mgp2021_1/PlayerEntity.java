package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.graphics.Matrix;

import androidx.constraintlayout.helper.widget.Layer;

import org.w3c.dom.Entity;

public class PlayerEntity implements EntityBase, Collidable {

    public static PlayerEntity Instance = null;

    public Bitmap bmp = null;
    private Bitmap scaledbmp = null;

    private boolean isDone = false;

    int screenWidth,screenHeight;

    public float yStart;

    private int lives = 3;
    private int wavesurvived = 0;
    public float xPos = 0;
    public float yPos = 0;

    private float yLimit = 0;
    private float xLimit = 0;

    private float flapAmount = 0;
    private float gravityVec = 0;
    private boolean tapGuard = false;

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
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.sans2);

        isInit = true;

        //Setup all our variables
        xPos = _view.getWidth()* 0.5f; //setting the x position to spawn
        yStart = yPos = _view.getHeight() * 0.9f; //setting the y position to spawn
        yLimit = _view.getHeight()-bmp.getHeight() * 0.5f; //setting constraint

        Instance = this;

    }

    @Override
    public void Update(float _dt) {

        if (TouchManager.Instance.HasTouch()){
            //Check Collision here!
            float imgRadius = bmp.getWidth() * 0.5f;
            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(),
                    0.0f,xPos,yPos,imgRadius ) || hasCollided){

                hasCollided = true;
            }

            xPos = TouchManager.Instance.GetPosX();

            //yPos = TouchManager.Instance.GetPosY();
        }

        //Gravity
       // gravityVec += _dt * 10.0f;
        //yPos += gravityVec;

        if (lives <= 0){
            StateManager.Instance.ChangeState("GameOver");
        }
    }

    private void Flap() {
        gravityVec = -flapAmount;
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
        return RenderLayer;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        RenderLayer = _newLayer;
    }

    @Override
    public ENTITY_TYPE GetEntityType() {
        return ENTITY_TYPE.ENT_PLAYER;
    }

    public static PlayerEntity Create() {
        PlayerEntity result = new PlayerEntity();
        EntityManager.Instance.AddEntity(result,ENTITY_TYPE.ENT_PLAYER);
        return result;
    }

    @Override
    public String GetType() {
        return "PlayerEntity";
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
        if (_other.GetType() == "ENT_EVIL") //Change this to enemy entity
        {
            lives -= 1;
            //SetIsDone(true);
        }
    }
}