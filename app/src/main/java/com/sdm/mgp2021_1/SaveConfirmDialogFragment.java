package com.sdm.mgp2021_1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

public class SaveConfirmDialogFragment extends DialogFragment {
    public static boolean IsShown = false;
    String username;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {


        IsShown = true;

        //Use the builder class for convenient dialog construction

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
      //  input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User triggered pause

                username = input.getText().toString();
                Log.v(username,"user");
                GameSystem.Instance.SaveEditBegin();
                GameSystem.Instance.SetIntInSave(username, GameSystem.Instance.GetIntFromSave("Score"));
                GameSystem.Instance.SaveEditEnd();

                if (GameSystem.Instance.isPlayerDied()) {
                    StateManager.Instance.ChangeState("GameOver");
                    GamePage.Instance.ChangeState(GameOver.class);
                }
                else {
                    StateManager.Instance.ChangeState("Victory");
                    GamePage.Instance.ChangeState(VictoryPage.class);
                }


                
                if (OptionsPage.Instance == null) {
                    AudioManager.Instance.StopAudio(R.raw.gamebg);
                }
                IsShown = false;

            }
        });
              //  .setNegativeButton("No", new DialogInterface.OnClickListener() {
                //    @Override
                  //  public void onClick(DialogInterface dialog, int which) {
                        //User Cancelled the pause
                    //    IsShown = false;
                    //}
                //});
        //Create the AlertDialog and return it
        return builder.create();
    }
}
