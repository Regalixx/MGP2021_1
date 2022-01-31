package com.sdm.mgp2021_1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;

public class MainMenuButtonEntity implements EntityBase{

    public final static MainMenuButtonEntity Instance = new MainMenuButtonEntity();
    private Bitmap bmpP,bmpUP,ScaledbmpP,ScaledbmpUP;
    private float xPos = 0, yPos = 0;

    private boolean isDone = false;
    private boolean isInit = false;
    public boolean Paused = false;

    int ScreenWidth, ScreenHeight;

    private float buttonDelay = 0;

    @Override
    public boolean IsDone() {
        return isDone;
    }

    @Override
    public void SetIsDone(boolean _isDone) {
        isDone = _isDone;
    }

    @Override
    public void Init(SurfaceView _view) {

        bmpP = ResourceManager.Instance.GetBitmap(R.drawable.backbutton);

        DisplayMetrics metrics = _view.getResources().getDisplayMetrics();
        ScreenWidth = metrics.widthPixels;
        ScreenHeight = metrics.heightPixels;

        ScaledbmpP = Bitmap.createScaledBitmap(bmpP, (int) (ScreenWidth)/12, (int)(ScreenWidth)/7, true);

        xPos = ScreenWidth - 950;
        yPos = 260;
        isInit = true;


    }

    @Override
    public void Update(float _dt) {
        buttonDelay += _dt;

        if (TouchManager.Instance.HasTouch()) {
            float imgRadius = ScaledbmpP.getHeight() * 0.5f;
            if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(),
                    0.0f,xPos,yPos,imgRadius )) {
                Log.v("Touch", "Touched");
                if (!Paused) {
                    // Check Collision of button here!!



                    if (Collision.SphereToSphere(TouchManager.Instance.GetPosX(), TouchManager.Instance.GetPosY(), 0.0f, xPos, yPos, imgRadius) && buttonDelay >= 0.25) {
                        //Paused = true;
                        Log.v("Paused", "Successful");


                        //Button got clicked show the popup dialog
                        if (MenuConfirmDialogFragment.IsShown)
                            return;

                        MenuConfirmDialogFragment newMenuConfirm = new MenuConfirmDialogFragment();
                        newMenuConfirm.show(GamePage.Instance.getSupportFragmentManager(),"MenuConfirm");
                    }
                    buttonDelay = 0;
                    //GameSystem.Instance.SetIsPaused(!GameSystem.Instance.GetIsPaused());
                    //Log.v("Paused", "SuccessfuL2");
                }
                else if (Paused) {
                    AudioManager.Instance.PlayAudio(R.raw.gamebg,0.9f);
                    Log.v("unpaused","unpaused");
                }

            }
        }
    }

    @Override
    public void Render(Canvas _canvas) {


            _canvas.drawBitmap(ScaledbmpP,xPos - ScaledbmpP.getWidth() * 0.5f, yPos - ScaledbmpP.getHeight() * 0.5f, null);



    }

    @Override
    public boolean IsInit() {

        return isInit;
    }

    @Override
    public int GetRenderLayer() {
        return LayerConstants.MAINMENUBUTTON_LAYER;
    }

    @Override
    public void SetRenderLayer(int _newLayer) {
        return;
    }

    @Override
    public ENTITY_TYPE GetEntityType(){ return ENTITY_TYPE.ENT_MAINMENU;}

    public static MainMenuButtonEntity Create()
    {
        MainMenuButtonEntity result = new MainMenuButtonEntity();
        EntityManager.Instance.AddEntity(result, ENTITY_TYPE.ENT_MAINMENU);
        return result;
    }
}
