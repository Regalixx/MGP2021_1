package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;

public class EnemyBoss1 implements EntityBase,Collidable {

    public static EnemyBoss1 Instance = null;

    private Bitmap bmp = null;
    private GamePage activity = null;

    int screenWidth,screenHeight;

    private Vector3 pos = new Vector3(0,0,0);

    private EntityBase.ENTITY_TYPE type = EntityBase.ENTITY_TYPE.ENT_BOSS1;

    private boolean isInit = false;
    private boolean isDone = false;
    private boolean isActive = false;

    private boolean Phase1 = false;
    private boolean Phase2 = false;
    private int RenderLayer = 0;
    private boolean reverse = false;

    private float cooldown = 5;

    //Enemy Stats
    private float health = 0.0f;

    //Enemy Methods
    public void SetHealth(float hp) { health = hp;}
    public float GetHealth() { return health;}


    //EntityBase
    public boolean IsDone() {return isDone;};
    public void SetIsDone(boolean _isDone) { isDone = _isDone;};

    public void Init(SurfaceView _view) {
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.sans2);
        isInit = true;
        pos.x = 650;
        pos.y = 2;
        Phase1 = true;

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        screenWidth = metrics.widthPixels;
        screenHeight = metrics.heightPixels;

        Instance = this;

    };
    public void Update(float _dt) {
        //spawn bullets
        if (reverse == false) {
            pos.x += 1.5;
        }

        if (pos.x >= screenWidth){
            reverse = true;
        }


        if (reverse == true){
            pos.x -= 1.5;
        }

        if (Phase1 == true){
         BossPhase1();
        }

        if (cooldown <= 2) {
            cooldown += _dt;
        }



   if (GetHealth() <= 50)
   {
       Phase1 = false;
       Phase2 = true;
       BossPhase2();
   }

    };
    public void Render(Canvas _canvas) {
        Matrix transform = new Matrix();
        transform.postTranslate(-bmp.getWidth() * 0.5f, 0);

        transform.postTranslate(pos.x,pos.y);


        _canvas.drawBitmap(bmp, transform, null);
    };

    @Override
    public boolean IsInit() {
        return isInit;
    };

    public int GetRenderLayer() {return LayerConstants.ENEMYBOSS1_LAYER;};
    public void SetRenderLayer(int _newLayer) {RenderLayer = _newLayer;};

    public EntityBase.ENTITY_TYPE GetEntityType() {return type;};

    //Collidable
    public String GetType() {return type.name();};

    public float GetPosX() { return pos.x;};
    public float GetPosY() {return pos.y;};
    public float GetRadius() {
        return bmp.getWidth() * 0.5f;
    };

    public void OnHit(Collidable _other) {
       // if (_other.GetType() == "BulletEntity") {
         //   SetIsDone(true);
    };

    public void BossPhase1() {

        if (cooldown >= 2) {
            GamePage.Instance.DisplayEmails();
            cooldown = 0;
        }
    }

    public void BossPhase2(){

        if (cooldown >= 1) {
            GamePage.Instance.DisplayEmails();
            cooldown = 0;
        }
    }


}
