package com.sdm.mgp2021_1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;

public class OptionsPage extends Activity implements View.OnClickListener, StateBase{
    public static OptionsPage Instance = null;
    private ImageButton btn_back;
    private ToggleButton btn_toggle, btn_musictoggle;
    public boolean active = false;
    public boolean musicactive;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //To make fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE); // Hide titlebar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  // Hide topbar


        setContentView(R.layout.options);
        Instance = this;
        musicactive = true;
        active = false;

        btn_back = (ImageButton)findViewById(R.id.btn_back);
        btn_toggle = (ToggleButton)findViewById(R.id.btn_toggle);
        btn_musictoggle = (ToggleButton)findViewById(R.id.btn_musictoggle);
        btn_back.setOnClickListener(this); //Set Listener to this button --> Start Button

        btn_toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_toggle.isChecked()){
                    active = true;
                }
                else {
                    active = false;
                }
            }
        });

        btn_musictoggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_toggle.isChecked()){
                   musicactive = true;
                }
                else {
                    musicactive = false;

                }
            }
        });


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
        return "OptionsPage";
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
