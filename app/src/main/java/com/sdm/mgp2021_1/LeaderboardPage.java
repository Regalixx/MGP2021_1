package com.sdm.mgp2021_1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Map;

public class LeaderboardPage extends Activity implements View.OnClickListener, StateBase {

    public static LeaderboardPage Instance = null;
    private ImageButton btn_back;
    int highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide titlebar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  // Hide topbar


        Instance = this;
        setContentView(R.layout.highscorepage);

        if (GameSystem.Instance.GetSharedPref() == null) {
            Log.d("Save","I don't got the shared Pref!");
        }

        else {
            TextView t1, t2;
            t2 = findViewById(R.id.username);
            t1 = (TextView) findViewById(R.id.score);

            String userText = "";
            String scoreText = "";

            int height = 0;
            for (Map.Entry<String, ?> pair : GameSystem.Instance.GetSharedPref().getAll().entrySet()) {

                Log.d( "Found text", "Text found: " + pair.getKey() + " " + pair.getValue());
                userText  += String.format(pair.getKey() + ": " + pair.getValue() + " \n");
                height += 1;
                scoreText += String.format(pair.getValue() + "\n");

            }

            t2.setText(userText);

            RelativeLayout.LayoutParams t2params = (RelativeLayout.LayoutParams) t2.getLayoutParams();

            t2params.height *= height;

            t2.setLayoutParams(t2params);

            //t1.setText(scoreText);



        }

    }

    @Override
    //Invoke a callback event in the view
    public void onClick(View v)
    {
        // Intent = action to be performed.
        // Intent is an object provides runtime binding.
        // new instance of this object intent

        Intent intent = new Intent();


        if (v == btn_back)
        {
            intent.setClass(this, Mainmenu.class);
        }


        startActivity(intent);


    }

    @Override
    public void Render(Canvas _canvas) {
    }

    @Override
    public void OnEnter(SurfaceView _view) {
    }

    @Override
    public void OnExit() {
    }

    @Override
    public void Update(float _dt) {
    }

    @Override
    public String GetName() {
        return "LeaderboardPage";
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
