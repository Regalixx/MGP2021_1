package com.sdm.mgp2021_1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class ReadyConfirmDialogFragment extends DialogFragment {
    public static boolean IsShown = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        IsShown = true;

        //Use the builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Welcome to this Tutorial. In this tutorial, you will learn the basic mechanics of the game and how to win. Let's start by first clearing 10 enemies. Swipe up to shoot. Drag/Tilt to move your character." +
                "").setPositiveButton("Start Tutorial", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User triggered pause
                WaveManager.Instance.startTutorial = true;
                IsShown = false;

            }
        })
                .setNegativeButton("Go back to main Menu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //User Cancelled the pause
                        StateManager.Instance.ChangeState("MainMenu");
                        GamePage.Instance.ChangeState(Mainmenu.class);
                        IsShown = false;
                        if (OptionsPage.Instance == null) {
                            AudioManager.Instance.StopAudio(R.raw.gamebg);
                        }
                        if (OptionsPage.Instance != null) {
                            if (OptionsPage.Instance.musicactive == true){
                                AudioManager.Instance.StopAudio(R.raw.gamebg);
                            }
                        }
                    }
                });

        //Create the AlertDialog and return it
        return builder.create();
    }
}
