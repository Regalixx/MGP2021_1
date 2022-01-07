package com.sdm.mgp2021_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.SurfaceView;


// Created by TanSiewLan2021

public class GameSystem {
    public final static GameSystem Instance = new GameSystem();
    public static final String SHARED_PREF_ID = "GameSaveFile";

    //Game Stuff
  //  private boolean isPaused = false;
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;
    int waves = 1;

    // Game stuff
    public boolean isPaused = false;

    // Singleton Pattern : Blocks others from creating
    private GameSystem()
    {

    }

    public void Update(float _deltaTime)
    {
    }

    public void Init(SurfaceView _view)
    {

        sharedPref = GamePage.Instance.getSharedPreferences(SHARED_PREF_ID,0);

        // We will add all of our states into the state manager here!
        StateManager.Instance.AddState(new Mainmenu());
        StateManager.Instance.AddState(new MainGameSceneState());
        StateManager.Instance.AddState(new VictoryPage());
        StateManager.Instance.AddState(new GameOver());



    }

    public void SaveEditBegin()
    {
        //Safety check, only allow if not already editing
        if (editor != null)
            return;
        //Start the editing
        editor = sharedPref.edit();
    }

    public void SaveEditEnd()
    {
        //Check if has editor
        if (editor == null)
            return;

        editor.commit();
        editor = null; // Safety to ensure  other functions will fail once commit done
    }

    public void SetIntInSave(String _key, int _value)
    {
        if (editor == null)
            return;
        editor.putInt(_key, _value);
    }

    public int GetIntFromSave(String _key)
    {
        return sharedPref.getInt(_key,10);
    }

    public void SetIsPaused(boolean _newIsPaused)
    {
        isPaused = _newIsPaused;
    }

    public boolean GetIsPaused()
    {
        return isPaused;
    }

    public int GetWave(){
        return waves;
    }

}
