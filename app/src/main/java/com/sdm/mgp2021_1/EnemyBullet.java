package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;

// Enemy Bullet is spawned by enemies who create bullets.
// Basic class.

public class EnemyBullet implements EntityBase, Collidable{


    private DisplayMetrics metrics;

    private Bitmap bmp;
    private Vector3 pos = new Vector3(0,0,0);
    private Vector3 direction = new Vector3(0,0,0);

    private ENTITY_TYPE type = ENTITY_TYPE.ENT_EVIL;

    private int RenderLayer = LayerConstants.BULLET_LAYER;

    private boolean isDone = false;
    private boolean isInit = false;

    private float speed = 1000.f;

    // <--- entitybase ---> //

    public boolean IsDone() {return isDone;};
    public void SetIsDone(boolean _isDone){isDone = _isDone;};

    public void Init(SurfaceView _view){
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.pellet);

        metrics = _view.getResources().getDisplayMetrics();

        isInit = true;
    };
    public void Update(float _dt){

        if (GameSystem.Instance.GetIsPaused() == true)
        {
            return;
        }




        //handle out of bounds
        if (pos.x > metrics.widthPixels + GetRadius() ||
                pos.x < -GetRadius() ||
                pos.y > metrics.heightPixels + GetRadius() ||
                pos.y < -GetRadius()
        ) {
            //remove
            SetIsDone(true);
        }

        // Every frame move in direction
        pos = pos.Plus(direction.Normalized().Times(speed * _dt));

    };
    public void Render(Canvas _canvas){
        Matrix transform = new Matrix();
        transform.postTranslate(-bmp.getWidth() * 0.5f, 0);

        transform.postTranslate(pos.x,pos.y);


        _canvas.drawBitmap(bmp, transform, null);

    };

    public boolean IsInit(){return isInit;};

    public int GetRenderLayer(){ return RenderLayer;};
    public void SetRenderLayer(int _newLayer){ RenderLayer = _newLayer;};

    public ENTITY_TYPE GetEntityType(){ return ENTITY_TYPE.ENT_EVIL;};

    // <--- collidable ---> //

    public String GetType(){ return "ENT_EVIL";};

    public float GetPosX(){ return pos.x;};
    public float GetPosY(){ return pos.y;};
    public float GetRadius(){
        return bmp.getWidth()*0.5f;
    };

    public void OnHit(Collidable _other){
        if (_other.GetType() == "ENT_PLAYER") {
            SetIsDone(true);
            Log.v("Hit2","hiit2");
        }
        if (_other.GetType() == "ENT_FFPLAYER") {
            SetIsDone(true);
        }

    };

    public static EnemyBullet Create(Vector3 _pos, Vector3 _direction) {
        EnemyBullet newbullet = new EnemyBullet();


        newbullet.pos = _pos;
        newbullet.direction = _direction;

        EntityManager.Instance.AddEntity(newbullet,ENTITY_TYPE.ENT_EVIL);

        return newbullet;
    }

}
