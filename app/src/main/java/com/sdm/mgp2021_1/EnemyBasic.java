package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;
import android.view.SurfaceView;

public class EnemyBasic implements EntityBase, Collidable {

    private Bitmap bmp = null;

    private Vector3 pos = new Vector3(0,0,0);

    private ENTITY_TYPE type = ENTITY_TYPE.ENT_EVIL;

    private boolean isInit = false;
    private boolean isDone = false;
    private boolean isActive = false;

    private int RenderLayer = 0;


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

    };
    public void Update(float _dt) {
        //spawn bullets
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

    public int GetRenderLayer() {return RenderLayer;};
    public void SetRenderLayer(int _newLayer) {RenderLayer = _newLayer;};

    public ENTITY_TYPE GetEntityType() {return type;};

    //Collidable
    public String GetType() {return type.name();};

    public float GetPosX() { return pos.x;};
    public float GetPosY() {return pos.y;};
    public float GetRadius() {
        return bmp.getWidth() * 0.5f;
    };

    public void OnHit(Collidable _other) {
        if (_other.GetType() == "BulletEntity") {
            SetIsDone(true);

        }

    };
}
