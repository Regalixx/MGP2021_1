package com.sdm.mgp2021_1;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.SurfaceView;

public class RenderTextEntity implements EntityBase{

    public final static RenderTextEntity Instance = new RenderTextEntity();

    private BulletEntity bulletentity = new BulletEntity();
    private boolean isDone = false;
    boolean EnemyKilled = false;
    int realEnemiesKilled;
    int PlayerHP;
    int waves;

    Paint paint = new Paint();
    Paint paint2 = new Paint();
    Paint paint3 = new Paint();
    Paint paint4 = new Paint();
    private int red = 0, green = 0, blue = 0; // 0 - 255

    Typeface myFont;

    //Use for loading FPS so need more parameters!
    //we want to load FPS on my screen
    int frameCount;
    int Enemieskill = 0;
    int BOSS1HP;
    long lastTime = 0;
    long lastFPSTime = 0;
    float FPS;

    public boolean IsDone() {
        return isDone;
    }

    public void SetIsDone(boolean _isDone){
        isDone = _isDone;
    }

    @Override
    // For us to intialize or load resource eg: images
    public void Init(SurfaceView _view){
        myFont = Typeface.createFromAsset(_view.getContext().getAssets(), "fonts/Gemcut.otf"); //initialize the font here

    }
    @Override
    public void Update(float _dt) {
        //get actual fps


        if (GameSystem.Instance.GetIsPaused())
        {
            return;
        }

        frameCount++;

     if (EnemyDead()){
         Enemieskill++;
         bulletentity.SetEnemiesKilled(Enemieskill);
     }

     PlayerHP = PlayerEntity.Instance.GetHP();

     BOSS1HP = (int)EnemyBoss1.Instance.GetHealth();

     waves = GameSystem.Instance.GetWave();

     realEnemiesKilled = bulletentity.GetEnemiesKilled();


        long currentTime = System.currentTimeMillis();

        lastTime = currentTime;

        if (currentTime - lastFPSTime > 1000){

            FPS = (frameCount * 1000.f)  / (currentTime - lastFPSTime);
            lastFPSTime = currentTime;
            frameCount = 0;
        }

    }

    @Override
    public void Render(Canvas _canvas){

        paint.setARGB(255,0,0,255);
        paint.setStrokeWidth(200);
        paint.setTypeface(myFont);
        paint.setTextSize(50);

        paint2.setARGB(255,0,235,63);
        paint2.setStrokeWidth(200);
        paint2.setTypeface(myFont);
        paint2.setTextSize(50);

        paint3.setARGB(255,255,0,0);
        paint3.setStrokeWidth(200);
        paint3.setTypeface(myFont);
        paint3.setTextSize(50);

        paint4.setARGB(255,255,255,0);
        paint4.setStrokeWidth(200);
        paint4.setTypeface(myFont);
        paint4.setTextSize(50);

        _canvas.drawText ("FPS: " + (int)FPS, 30, 80, paint); //For now, default member but u can use _view.getWidth /
        //_canvas.drawText("Killed:" + realEnemiesKilled,50,400,paint);
        _canvas.drawText ("HP: " + (int)PlayerHP, 250, 80, paint2); //For now, default m
        _canvas.drawText ("Boss HP: " + (int)BOSS1HP, 500, 80, paint3);
        _canvas.drawText ("Wave: " + (int)waves, 850, 80, paint4);
    }


    @Override
    public boolean IsInit(){
       return true;
    }

    @Override
    public int GetRenderLayer(){
        return LayerConstants.RENDERTEXT_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer){
        return;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){
        return ENTITY_TYPE.ENT_TEXT;
    }

    public static RenderTextEntity Create(){
        RenderTextEntity result = new RenderTextEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_TEXT);
        return result;
    }

    public boolean EnemyDead () {
        return EnemyKilled;
    }
}
