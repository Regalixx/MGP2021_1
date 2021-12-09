package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;

import java.util.HashSet;
import java.util.Set;

public class EnemyBasic implements EntityBase, Collidable {

    private Bitmap bmp = null;

    private Vector3 pos = new Vector3(0,0,0);

    private ENTITY_TYPE type = ENTITY_TYPE.ENT_EVIL;

    private boolean isInit = false;
    private boolean isDone = false;

    private int RenderLayer = 0;

    //Enemy Stats
    private float health = 0.0f;

    private DisplayMetrics metrics;
    private Vector3 sinespeed = new Vector3(0,0,0);
    private Vector3 bigspeed = new Vector3(50.f,150.f,0);
    private Vector3 smallspeed = new Vector3(300.f,-300.f,0);
    private Vector3 original_pos;

    enum BEHAVIOURS {
        AI_FALL,
        AI_SWEEPWIDTH,
        AI_SMALLLEFTRIGHT,
        AI_UPDOWN,
        AI_SIDEDIE
    }

    private Set<BEHAVIOURS> behaviours = new HashSet<BEHAVIOURS>();

    //     ---- < Enemy Methods > ----     //
    //  Methods that belong to enemyBasic  //

    public void SetHealth(float hp) { health = hp;}
    public float GetHealth() { return health;}
    public void SetPos(Vector3 _pos) {
        pos = _pos;
    }


    private void doBehaviour(float _dt) {



        if (behaviours.contains(BEHAVIOURS.AI_SMALLLEFTRIGHT))
        {
            float offset = 50.f;
            pos.x += smallspeed.x * _dt;
            if (pos.x < original_pos.x - offset || pos.x > original_pos.x + offset) {
                smallspeed.x = -smallspeed.x;

                if (pos.x < original_pos.x - offset)
                { pos.x = original_pos.x - offset; }
                else { pos.x = original_pos.x + offset; }
            }

        }

        if (behaviours.contains(BEHAVIOURS.AI_SWEEPWIDTH)) {
            pos.x += bigspeed.x * _dt;
            //updates anchor point
            original_pos.x += bigspeed.x * _dt;
        }

        if (behaviours.contains(BEHAVIOURS.AI_UPDOWN))
        {
            float offset = 50.f;
            pos.y += smallspeed.y * _dt;
            if (pos.y > original_pos.y + offset || pos.y < original_pos.y - offset) {
                smallspeed.y = -smallspeed.y;

                if (pos.y > original_pos.y + offset)
                { pos.y = original_pos.y + offset; }
                else { pos.y = original_pos.y - offset;}

            }
        }
        if (behaviours.contains(BEHAVIOURS.AI_FALL)) {
            pos.y += bigspeed.y * _dt;
            //updates anchor point
            original_pos.y += bigspeed.y * _dt;
        }


        //Perish when touching the sides (if behaviour is set)
        if (behaviours.contains(BEHAVIOURS.AI_SIDEDIE) ){
            if  (pos.x > metrics.widthPixels + bmp.getWidth()*0.5f || pos.x < -bmp.getWidth()*0.5f) {
                SetIsDone(true);
            }
        }
        //Behaviour when touching the sides of the screen without side death
        else if (pos.x > metrics.widthPixels - bmp.getWidth()*0.5f || pos.x < bmp.getWidth()*0.5f) {

            bigspeed.x = -bigspeed.x;
            smallspeed.x = -smallspeed.x;


            //bring enemy back to edge
            ConstrainToScreen();

        }



        //When reach bottom of the screen, destroy
        if (pos.y > metrics.heightPixels - bmp.getHeight() * 0.5f) {
            SetIsDone(true);
        }
    }

    private void ConstrainToScreen() {
        if (pos.x < bmp.getWidth()*0.5f) {
            pos.x = bmp.getWidth() * 0.5f;
        }
        else if (pos.x > metrics.widthPixels - bmp.getWidth()*0.5f) {
            pos.x = metrics.widthPixels - bmp.getWidth() * 0.5f;
        }
    }

    //    ---- < EntityBase > ----    //
    //     Methods from EntityBase    //
    public boolean IsDone() {return isDone;}
    public void SetIsDone(boolean _isDone) { isDone = _isDone;}

    public void SetBehaviour(BEHAVIOURS ai) {
        behaviours.add(ai);
    }

    public void Init(SurfaceView _view) {
        bmp = ResourceManager.Instance.GetBitmap(R.drawable.sans2);
        isInit = true;
        metrics = _view.getResources().getDisplayMetrics();
        original_pos = new Vector3(pos);

    };
    public void Update(float _dt) {
        //spawn bullets

        //Update movement
        doBehaviour(_dt);
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

    //  ---- < Collidable > ----  //
    //  Methods from Collidable   //
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
