package com.sdm.mgp2021_1;

import android.graphics.Canvas;
import android.view.SurfaceView;

// Created by TanSiewLan2021

public interface EntityBase
{
 	 //used for entities such as background
    enum ENTITY_TYPE{
        ENT_PLAYER, //our titular main character
         ENT_EVIL, //things that kill the player
         ENT_PAUSE, //pause button

        ENT_DEFAULT,
    }

        public boolean IsDone();
        public void SetIsDone(boolean _isDone);

        public void Init(SurfaceView _view);
        public void Update(float _dt);
        public void Render(Canvas _canvas);

        public boolean IsInit();

        public int GetRenderLayer();
        public void SetRenderLayer(int _newLayer);

	public ENTITY_TYPE GetEntityType();
}
