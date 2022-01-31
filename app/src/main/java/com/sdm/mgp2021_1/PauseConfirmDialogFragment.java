package com.sdm.mgp2021_1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.DialogFragment;

public class PauseConfirmDialogFragment extends DialogFragment {

    public static boolean IsShown = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        IsShown = true;

        //Use the builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Confirm Pause?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User triggered pause
                GameSystem.Instance.SetIsPaused(!GameSystem.Instance.GetIsPaused());
                IsShown = false;
                if (GameSystem.Instance.GetIsPaused() == true) {
                    AudioManager.Instance.Release();

                }
                if (GameSystem.Instance.GetIsPaused() == false) {
                    Log.v("It does work","lol");
                    PausebuttonEntity.Instance.resumeAudio = true;

                }


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
