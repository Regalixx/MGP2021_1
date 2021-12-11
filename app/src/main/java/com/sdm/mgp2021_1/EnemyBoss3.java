package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;

public class EnemyBoss3 implements  EntityBase,Collidable{
    public static EnemyBoss3 Instance = null;

    private Bitmap bmp = null;
    private GamePage activity = null;

    int screenWidth,screenHeight;

    private Vector3 pos = new Vector3(0,0,0);

    private EntityBase.ENTITY_TYPE type = EntityBase.ENTITY_TYPE.ENT_EVIL;

    private boolean isInit = false;
    private boolean isDone = false;
    private boolean isActive = false;

    private boolean Phase1 = false;
    private boolean Phase2 = false;
    private boolean Phase3 = false;
    private int RenderLayer = 0;

    private boolean reverse = false;
    private boolean renderForcefield = false;
    private float enemyCooldown = 0.f;
    private float phaseCooldown = 2;

    private float cooldown = 5;
    private float popupcooldown = 0;

    //Enemy Stats
    private float health = 0.0f;
    private boolean isCollided = false;

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
            VideogameEntity.Instance.SetIsDone(true);
            return;
        }

        if (reverse == false) {

                pos.x += _dt * 400;
        }
        if (reverse == true){
                pos.x -= _dt * 400;
        }


        if (pos.x >= screenWidth){
            reverse = true;
        }

        if (pos.x < 0){
            reverse = false;
        }

        if (cooldown <= 2 && Phase1 == true) {
            cooldown += _dt;
        }

        if (cooldown <= 1.5 && Phase2 == true) {
            cooldown += _dt;
        }

        if (cooldown <= 2.5 && Phase3 == true) {
            cooldown += _dt;
        }

        //Enemy Attack Patterns

        if (Phase1 == true){
            BossPhase1();
        }

        if (Phase2 == true){
            BossPhase2();
        }

        if (Phase3 == true){
            BossPhase3();
        }

        if (enemyCooldown <= 4 && Phase1 == true) {
            enemyCooldown += 1 * _dt;
        }

        if (enemyCooldown <= 7 && Phase2 == true) {
            enemyCooldown += 1 * _dt;
        }

        if (enemyCooldown <= 10 && Phase3 == true) {
            enemyCooldown += 1 * _dt;
        }

        if (GetHealth() <= 70)
        {
            Phase1 = false;
            Phase2 = true;
        }

        if (GetHealth() <= 30)
        {
            Phase2 = false;
            Phase3 = true;
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


    }
    public void BossPhase1() {
        if (cooldown >= 2) {
            VideogameEntity.Create();
            cooldown = 0;
        }

        if (enemyCooldown >= 4) {
            EnemyFactory.Create(EnemyFactory.ENEMY_TYPE.GHOST_MINION, pos.Plus(new Vector3(0, bmp.getHeight(), 0)));
            enemyCooldown = 0;
        }
    }

    public void BossPhase2(){
        if (cooldown >= 1.5) {
            VideogameEntity.Create();
            cooldown = 0;
        }

        if (enemyCooldown >= 7) {
            EnemyFactory.Create(EnemyFactory.ENEMY_TYPE.GHOST_BASIC, pos.Plus(new Vector3(0, bmp.getHeight(), 0)));
            EnemyFactory.Create(EnemyFactory.ENEMY_TYPE.GHOST_BASIC, pos.Plus(new Vector3(250, bmp.getHeight(), 0)));
            EnemyFactory.Create(EnemyFactory.ENEMY_TYPE.GHOST_BASIC, pos.Plus(new Vector3(500, bmp.getHeight(), 0)));
            enemyCooldown = 0;
        }

    }

    public void BossPhase3(){
        if (cooldown >= 2.5) {
            VideogameEntity.Create();
            cooldown = 0;
        }


        if (enemyCooldown >= 10) {
            EnemyFactory.Create(EnemyFactory.ENEMY_TYPE.GHOST_BASIC, pos.Plus(new Vector3(0, bmp.getHeight(), 0)));
            EnemyFactory.Create(EnemyFactory.ENEMY_TYPE.GHOST_MINION, pos.Plus(new Vector3(200, bmp.getHeight(), 0)));

            EnemyFactory.Create(EnemyFactory.ENEMY_TYPE.GHOST_BASIC, pos.Plus(new Vector3(400, bmp.getHeight(), 0)));
            EnemyFactory.Create(EnemyFactory.ENEMY_TYPE.GHOST_MINION, pos.Plus(new Vector3(600, bmp.getHeight(), 0)));
            enemyCooldown = 0;
        }
    }
}
