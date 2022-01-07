package com.sdm.mgp2021_1;

import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.SurfaceView;

import java.util.HashMap;

public class AudioManager {
    public  final static AudioManager Instance = new AudioManager();

    private SurfaceView view = null;
    private HashMap<Integer, MediaPlayer> audioMap = new HashMap<Integer,MediaPlayer>();

    private AudioManager() {

    }

    public void Init(SurfaceView _view)
    {
        view = _view;
        Release(); //clear the audiomap
    }

    public void PlayAudio(int _id, float _volume) {
        //if audio is loaded
        if (audioMap.containsKey(_id))
        {
            //have the clip
            MediaPlayer curr = audioMap.get(_id);
            curr.seekTo(0);
            curr.setVolume(_volume,_volume);
            curr.start();
        }
        else {
            MediaPlayer curr = MediaPlayer.create(view.getContext(), _id);
            audioMap.put(_id, curr);
            curr.start();
        }
    }

    public void StopAudio(int _id)
    {
        MediaPlayer curr = audioMap.get(_id);
        curr.stop();
    }

    //release the resources
    public void Release(){
        for (HashMap.Entry<Integer, MediaPlayer> entry : audioMap.entrySet())
        {
            entry.getValue().stop();
            entry.getValue().reset();
            entry.getValue().release();
        }
        //empty it
        audioMap.clear();
    }

    private MediaPlayer GetAudio(int _id) {
        //check if the audio is loaded or nto
        if (audioMap.containsKey(_id))
        {
            return audioMap.get(_id);
        }

        //load it if not
        MediaPlayer result = MediaPlayer.create(view.getContext(),_id);
        audioMap.put(_id,result);
        return result;
    }
}
