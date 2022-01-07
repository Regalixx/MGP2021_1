package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.SurfaceView;

import java.util.Random;

public class ObstacleEntity implements EntityBase,Collidable {
    public Bitmap bmp = null;

    private Bitmap scaledbmp = null;

    private boolean isDone = false;

    int screenWidth,screenHeight;

    public float yStart;

    private int health = 3;
    private float speed = 5;
    public float xPos = 0,yPos = 0, xStart = 0;


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
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.ship2_1);

        isInit = true;

        //Option 1
        // Make this entity destroyable/ respwawned all the time (using lives variable)

        //Option 2
        //Make this object IMMORTAL. loop it all again and again.

        //Randomize a location to spawn on screen
        Random ranGen = new Random();
        xStart = xPos =  _view.getWidth();
        screenHeight = _view.getHeight();
        yPos =  ranGen.nextInt() % screenHeight;

        // Set a speed to cross the screen
        speed = _view.getWidth() * 0.5f;

        //Setup all our variables
        xPos = _view.getWidth()* 0.5f; //setting the x position to spawn
        yStart = yPos = _view.getHeight() * 0.9f; //setting the y position to spawn
        yLimit = _view.getHeight()-bmp.getHeight() * 0.5f; //setting constraint



    }

    @Override
    public void Update(float _dt) {

        //Check if  current state is where?
        //If (StateManager.Instance.GetCurrentState()  != "MainGame");
        //return;

        xPos -= speed * _dt;
        //Check if we are out of the screen
        if (xPos <= -bmp.getHeight() * 0.5f){
            //Move it to another location
            xPos = xStart;
            Random ranGen = new Random();
            yPos = ranGen.nextInt() % screenHeight;
        }


        //Check collision player or another object

       // if (Collision.SphereToSphere(xPos, yPos, bmp.getWidth() * 0.5f,GameSystem.Instance.Ship.xPos, GameSystem.Instance.Ship.yPos,GameSystem.Instance.Ship.bmp.getWidth() * 0.5f)) {

        //}

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
        return 0;
    }

    @Override
    public void OnHit(Collidable _other) {
        if (_other.GetType() == "ENT_EVIL") //Change this to enemy entity
        {

            SetIsDone(true);
        }
    }

}
