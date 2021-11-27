package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceView;

public class EnemyBasic implements EntityBase, Collidable {

    private Bitmap bmp = null;


    //EntityBase
    public boolean IsDone() {return false;};
    public void SetIsDone(boolean _isDone) {};

    public void Init(SurfaceView _view) {};
    public void Update(float _dt) {};
    public void Render(Canvas _canvas) {};

    public boolean IsInit() {return false;};

    public int GetRenderLayer() {return 0;};
    public void SetRenderLayer(int _newLayer) {};

    public ENTITY_TYPE GetEntityType() {return ENTITY_TYPE.ENT_EVIL;};

    //Collidable
    public String GetType() {return "Evil";};

    public float GetPosX() { return 0;};
    public float GetPosY() {return 0;};
    public float GetRadius() {
        return bmp.getWidth() * 0.5f;
    };

    public void OnHit(Collidable _other) {


    };
}
