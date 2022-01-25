package com.sdm.mgp2021_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.SurfaceView;


// Created by TanSiewLan2021

public class GameSystem {
    public final static GameSystem Instance = new GameSystem();
    public static final String SHARED_PREF_ID = "GameSaveFile";

    //Game Stuff
  //  private boolean isPaused = false;
    // For scoring
    private int currScore = 0;
    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;
    int waves = 1;

    // Game stuff
    public boolean isPaused = false;

    // Better to place in a getter/setter.
    //public PlayerEntity playerEntityInstance = null;

    // Singleton Pattern : Blocks others from creating
    private GameSystem()
    {

    }

    public void Update(float _deltaTime)
    {
    }

    public void Init(SurfaceView _view)
    {



        // We will add all of our states into the state manager here!
        StateManager.Instance.AddState(new Mainmenu());
        StateManager.Instance.AddState(new OptionsPage());
        StateManager.Instance.AddState(new MainGameSceneState());
        StateManager.Instance.AddState(new VictoryPage());
        StateManager.Instance.AddState(new LeaderboardPage());
        StateManager.Instance.AddState(new GameOver());

        // Get our shared preferences (Save file)
        sharedPref = GamePage.Instance.getSharedPreferences(SHARED_PREF_ID,0);
      //  if (SHARED_PREF_ID == null)
       // {
        //    Log.v("Null","null");
       // }

    }



    public void SaveEditBegin()
    {
        // Safety check, only allow if not already editing
        if (editor != null)
            return;

        // Start the editing
        editor = sharedPref.edit();
    }

    public void SaveEditEnd()
    {
        // Check if has editor
        if (editor == null)
            return;

        editor.commit();
        editor = null; // Safety to ensure other functions will fail once commit done
    }

    public void SetIntInSave(String _key, int _value)
    {
        if (editor == null)
            return;

        editor.putInt(_key, _value);
    }

    public int GetIntFromSave(String _key)
    {
        return sharedPref.getInt(_key, 10);
    }

    public void SetUserInSave(String _key, String _name)
    {
        if (editor == null)
            return;

        editor.putString(_key, _name);

    }

    public SharedPreferences GetSharedPref() {
        return sharedPref;
    }

    public String GetUserFromSave(String _key)
    {
        return sharedPref.getString(_key,"" );

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

    // For scoring
    public int GetScore(){
        return currScore;
    }

    public void AddScore(){
        AddScore(1);
    }

    public void AddScore (int _amt){
        currScore += _amt;
    }

    public void ResetScore(){
        currScore = 0;
    }


}
