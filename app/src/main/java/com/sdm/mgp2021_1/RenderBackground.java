package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceView;

public class RenderBackground implements EntityBase { //renderbackground is an entity

    private boolean isDone = false;
    private Bitmap bmp = null;

    private Bitmap scaledbmp;
    int ScreenWidth, ScreenHeight;

    private float  xPos, yPos, offset;
    private SurfaceView view = null;

    // Check if anything that we want to do with the entity is done. (use for pause_
    @Override
    public boolean IsDone(){
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {
        bmp = BitmapFactory.decodeResource(_view.getResources(), R.drawable.cyberbackground); //LOAD GAME SCENE BACKGROUND

        //Find the screen height and width
        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels; //get the width of screen
        ScreenHeight = metrics.heightPixels; //get the height of screen

        scaledbmp = Bitmap.createScaledBitmap(bmp, ScreenWidth, ScreenHeight, true);


    }

    @Override
    public void Update(float _dt) {

        yPos -= _dt *  500; //deals w the speed of moving the screen

        if (yPos < - ScreenHeight) { //if image position goes less than the widh of screen, set it to 0
            yPos = 0;
        }

    }

    @Override
    public void Render(Canvas _canvas) {
        // to draw things on the screen

        _canvas.drawBitmap(scaledbmp, xPos, yPos, null); // 1st image
        _canvas.drawBitmap(scaledbmp, xPos , yPos + ScreenHeight, null); //2nd image, draw the next one if it goes off screen


    }

    @Override
    public boolean IsInit() {
        return bmp != null;
    }

    @Override
    public int GetRenderLayer() {

        return LayerConstants.BACKGROUND_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        return;
    }


   public static RenderBackground Create() {
        RenderBackground result = new RenderBackground();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_DEFAULT);
        return result;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){
        return ENTITY_TYPE.ENT_DEFAULT;
    }
}
