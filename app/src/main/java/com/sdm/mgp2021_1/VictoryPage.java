package com.sdm.mgp2021_1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class VictoryPage extends Activity implements View.OnClickListener, StateBase{


    //public final static VictoryPage Instance = new VictoryPage();
    private Button btn_retry;
    private Button btn_menu;
    private Button btn_quit;


int highscore;
    TextView scoretext;
    TextView highscoretext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //To make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide titlebar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  // Hide topbar

        setContentView(R.layout.victoryscreen);


       // highscore = GameSystem.Instance.GetIntFromSave("Score");
        TextView wavetext = (TextView)findViewById(R.id.waves);
        wavetext.setText("Waves Survived: " + WaveManager.Instance.GetWave() + 1);

        highscoretext = (TextView)findViewById(R.id.scores);
        String scoreText = String.format("%d", GameSystem.Instance.GetIntFromSave("Score"));
        highscoretext.setText(scoreText);

        scoretext = (TextView)findViewById(R.id.TotalScore);
        // highscoretext = (TextView)findViewById(R.id.scores);


        btn_retry = (Button)findViewById(R.id.btn_retry);
        btn_retry.setOnClickListener(this); //Set Listener to this button --> Start Button

        btn_menu = (Button)findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(this); //Set Listener to this button --> Start Button

        btn_quit = (Button)findViewById(R.id.btn_quit);
        btn_quit.setOnClickListener(this); //Set Listener to this button --> Start Button



    }

    @Override
    //Invoke a callback event in the view
    public void onClick(View v)
    {
        // Intent = action to be performed.
        // Intent is an object provides runtime binding.
        // new instance of this object intent

        Intent intent = new Intent();


        if (v == btn_retry)
        {

            intent.setClass(this, GamePage.class);
            StateManager.Instance.ChangeState("GamePage");
            startActivity(intent);

        }

        else if (v == btn_menu)
        {
            intent.setClass(this, Mainmenu.class);
            StateManager.Instance.ChangeState("Mainmenu");
            startActivity(intent);
        }

        else if (v == btn_quit)
        {
            this.finishAffinity();
        }


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

       // WaveManager.Instance.TimeToExit();

    }

    @Override
    public String GetName() {
        return "Victory";
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
