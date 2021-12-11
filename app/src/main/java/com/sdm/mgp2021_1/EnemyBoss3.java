package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.view.SurfaceView;

public class EnemyBoss3 implements  EntityBase,Collidable{
    public static EnemyBoss3 Instance = null;

    private Bitmap bmp = null;
    private GamePage activity = null;

    int screenWidth,screenHeight;

    private Vector3 pos = new Vector3(0,0,0);

    private EntityBase.ENTITY_TYPE type = EntityBase.ENTITY_TYPE.ENT_BOSS3;

    private boolean isInit = false;
    private boolean isDone = false;
    private boolean isActive = false;

    private boolean Phase1 = false;
    private boolean Phase2 = false;
    private int RenderLayer = 0;
    private boolean reverse = false;
    private boolean renderForcefield = false;
    private float enemyCooldown = 0.f;

    private float cooldown = 5;
    private float popupcooldown = 0;

    //Enemy Stats
    private float health = 0.0f;

    //Enemy Methods
    public void SetHealth(float hp) { health = hp;}
    public float GetHealth() { return health;}


    //EntityBase
    public boolean IsDone() {return isDone;};
    public void SetIsDone(boolean _isDone) { isDone = _isDone;};

    @Override
    public void Init(SurfaceView _view) {

        bmp = ResourceManager.Instance.GetBitmap(R.drawable.boss3);
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

        if (GetHealth() <= 0) {
            SetIsDone(true);
            return;
        }

        if (reverse == false) {
            if (Phase1 == true) {
                pos.x += _dt * 200;
            }
        }
        if (reverse == true){
            if (Phase1 == true) {
                pos.x -= _dt * 200;
            }
        }

        if (pos.x >= screenWidth){
            reverse = true;
        }

        if (pos.x < 0){
            reverse = false;
        }



    };

    public void Render(Canvas _canvas) {
        Matrix transform = new Matrix();
        transform.postTranslate(-bmp.getWidth() * 0.5f, 0);

        transform.postTranslate(pos.x,pos.y);


        _canvas.drawBitmap(bmp, transform, null);
    };

    public void SetPos(Vector3 _pos) {
        pos = _pos;
    }
    //Choose the image you want
    public void SetBMP(int _id) {
        bmp = ResourceManager.Instance.GetBitmap(_id);
    }

    @Override
    public boolean IsInit() {
        return isInit;
    };

    public int GetRenderLayer() {return LayerConstants.ENEMYBOSS3_LAYER;};
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


    }

    public void BossPhase2(){


    }
}
