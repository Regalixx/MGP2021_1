package com.sdm.mgp2021_1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class TutorialNextDialogFragment extends DialogFragment {
    public static boolean IsShown = false;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        IsShown = true;

        //Use the builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Congrats on making this far! Now you'll fight against enemies that can shoot back at you. Remember to dodge the bullets as much as possible. Clear 15 Enemies this time. Best of Luck!" +
                "").setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User triggered pause
                WaveManager.Instance.spawnEnemiesShoot = true;
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
