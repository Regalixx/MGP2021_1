package com.sdm.mgp2021_1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class MenuConfirmDialogFragment extends DialogFragment {

    public static boolean IsShown = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        IsShown = true;

        //Use the builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Go back to main menu?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User triggered pause
                StateManager.Instance.ChangeState("MainMenu");
                GamePage.Instance.ChangeState(Mainmenu.class);
                if (OptionsPage.Instance == null) {
                    AudioManager.Instance.StopAudio(R.raw.gamebg);
                }
                if (OptionsPage.Instance != null) {
                    if (OptionsPage.Instance.musicactive == true){
                        AudioManager.Instance.StopAudio(R.raw.gamebg);
                    }
                }

                IsShown = false;
            }
        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //User Cancelled the pause
                        IsShown = false;
                    }
                });
        //Create the AlertDialog and return it
        return builder.create();
    }
}
