package com.sdm.mgp2021_1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;
import android.graphics.Matrix;
import android.view.ViewDebug;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibrationAttributes;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.constraintlayout.helper.widget.Layer;

import org.w3c.dom.Entity;


public class PlayerEntity implements EntityBase, Collidable,SensorEventListener {

    public static PlayerEntity Instance = null;

    public Bitmap bmp = null;
    private Bitmap scaledbmp = null;

    private boolean isDone = false;

    int screenWidth,screenHeight;

    public float yStart;

    private float lifetime;
    private int HP = 200;
    int score = 0;
    private int wavesurvived = 0;
    public float xPos = 0;
    public float yPos = 0;
    public float bX = 0;
    public float bY = 0;

    private float yLimit = 0;
    private float xLimit = 0;

    private float flapAmount = 0;
    private float gravityVec = 0;
    private boolean tapGuard = false;
    private Vibrator _vibrator;

    private Sprite spritePlayer = null;

    private int RenderLayer = 0; //Layer1 to be rendered.  Check layerconstant.Java
    private boolean isInit = false;
    private boolean hasCollided = false;
    private float[] values = {0,0,0};


    private SensorManager sensor;
    private long lastTime = System.currentTimeMillis();
    //private boolean hasTouched = false;

    private float targetPos;


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

        spritePlayer = new Sprite(ResourceManager.Instance.GetBitmap(R.drawable.smurf_sprite),4,4,16);

        isInit = true;

        //Setup hardware vibrate
        _vibrator = (Vibrator)_view.getContext().getSystemService(_view.getContext().VIBRATOR_SERVICE);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        targetPos = screenWidth * 0.5f;

        //Setup all our variables
        xPos = _view.getWidth()* 0.5f; //setting the x position to spawn
        yStart = yPos = _view.getHeight() * 0.9f; //setting the y position to spawn
        yLimit = _view.getHeight()-bmp.getHeight() * 0.5f; //setting constraint
        
        sensor = (SensorManager)_view.getContext().getSystemService(Context.SENSOR_SERVICE);
        sensor.registerListener((SensorEventListener) this,sensor.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0),SensorManager.SENSOR_DELAY_NORMAL);
        Instance = this;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
        //Do something on here if sensor accuracy changes
    }

    @Override
    public void onSensorChanged(SensorEvent SenseEvent) {
        //Many sensors return 3 values, one for each axis
        //Do something with sensor value.

        if (SenseEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            values = SenseEvent.values;
            //tilt
            float
                    rx = values[0],
                    ry = values[1],
                    rz = values[2];

            float
                    x =  (float)Math.toDegrees ( Math.atan(rx / Math.sqrt(ry * ry + rz * rz))),
                    y =  (float)Math.toDegrees ( Math.atan(ry / Math.sqrt(rx * rx + rz * rz))),
                    z =  (float)Math.toDegrees(Math.atan(rz / Math.sqrt(ry * ry + rx * rx)));

            //Log.d("Acceloremter test", "X: " + x + " Y: " + y + " Z: " + z);


            int offset = 30;

            //xpos is the percentage of the max x tilt - min x tilt
            targetPos = screenWidth - (screenWidth * (  (x + offset) / (offset*2)  )) ;




        }


    }

    private float lerp(float a, float b, float f)
    {
        return a + f * (b - a);
    }

    public void SensorMove() {
        //Temp variables
        float tempX,tempY;

        //bX and bY are variables used for moving the object
        //values [1] - sensor values for x axis
        //values [0] - sensor values for y axis
        tempX = bX + (values[1]*((System.currentTimeMillis()-lastTime)/1000));
        tempY = bY + (values[0]*((System.currentTimeMillis()-lastTime)/1000));

        // Check if the ball is going out of screen along the x-axis
        if (tempX <= bmp.getWidth()/2 || tempX >= screenWidth - bmp.getWidth()/2)
        {
// Check if ball is still within screen along the y-axis
            if ( tempY > bmp.getHeight()/2 && tempY < screenHeight - bmp.getHeight()/2)
            {
                bY = tempY;
            }
        }
        // Check if the ball is going out of screen along the y-axis
        if (tempY <= bmp.getHeight()/2 || tempY >= screenHeight - bmp.getHeight()/2)
        {
// Check if ball is still within screen along the x-axis
            if (tempX > bmp.getWidth()/2 && tempX < screenWidth - bmp.getWidth()/2)
            {
                bX = tempX;
            }
        }

        // If not, both axis of the ball's position is still within screen
        else
        {
// Move the ball with frame independent movement
            bX = tempX;
            bY = tempY;
        }
    }


    @Override
    public void Update(float _dt) {

        if (GameSystem.Instance.GetIsPaused() == true)
        {
            return;
        }





      //  if (lifetime < 0.0f ) {
        //    SetIsDone(true);   // <--- This part here or this code, meant when time is up, kill the items.
        //}

        if (TouchManager.Instance.HasTouch()) {
            //Check Collision here!
            float imgRadius = bmp.getWidth() * 0.5f;
            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(),
                    0.0f, xPos, yPos, imgRadius)) {

                hasCollided = true;

                //SetIsDone(true);
            }



            //xPos = TouchManager.Instance.GetPosX();

        }

        if (OptionsPage.Instance != null)
        {
        if (OptionsPage.Instance.active == true) {
            //every frame, linearly interpolate to place.
            xPos = lerp(xPos, targetPos, 0.2f);
        }
        }
        if (OptionsPage.Instance == null || OptionsPage.Instance.active == false){
            xPos = TouchManager.Instance.GetPosX();
        }


    }

    private void Flap() {
        gravityVec = -flapAmount;
    }

    @Override
    public void Render(Canvas _canvas) {

      //  spritePlayer.Render(_canvas,100,100);
        Matrix transform = new Matrix();
        transform.postTranslate(-bmp.getWidth() * 0.5f, 0); // make it not look so scuffed.

        //Scale and rotate here
       transform.postTranslate(xPos,yPos);
    //   _canvas.drawBitmap(bmp, transform, null);
        _canvas.drawBitmap(bmp, xPos,yPos,null);
    }

    public void startVibrate(){
        if (Build.VERSION.SDK_INT >= 26)
        {
            _vibrator.vibrate(VibrationEffect.createOneShot(150,10));
        }
        else {
            long pattern[] = {0,50,0};
            _vibrator.vibrate(pattern,-1);
        }
    }

    public void stopVibrate(){
        _vibrator.cancel();
    }
    @Override
    public boolean IsInit() {
        return isInit;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.PLAYER_LAYER;
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
        return bmp.getWidth() * 0.25f;
    }

    @Override
    public void OnHit(Collidable _other) {
        if (_other.GetType() == "ENT_EVIL") //Change this to enemy entity
        {
            if (ForcefieldEntityPlayer.Instance.IsDone() == true) {
                SetHP(GetHP() - 5);
                startVibrate();
            }
        }
        if (_other.GetType() == "ENT_VIDEOGAMES")
        {
            HP-=10;
        }
    }

    public int GetHP(){
        return HP;
    }
    public void SetHP(int _hp){
        HP = _hp;
    }
}