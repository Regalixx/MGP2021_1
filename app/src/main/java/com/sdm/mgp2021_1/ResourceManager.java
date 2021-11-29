package com.sdm.mgp2021_1;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.SurfaceView;

import java.util.HashMap;

public class ResourceManager { //to load sprites

    public final static  ResourceManager Instance = new ResourceManager();

    private Resources res = null;


    //  bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.gamescene);
    // Meant we need to retrieve the information from the _view, which is our surfaceview, and our image to be loaded on the view.

    //We will use a Hashmap
    private ResourceManager() {}

    private HashMap<Integer, Bitmap> resMap = new HashMap<Integer, Bitmap>();



    public void Init (SurfaceView _view) {
        res = _view.getResources();
    }

    public Bitmap GetBitmap(int _id) {
        if (resMap.containsKey(_id)) //use by key
            return resMap.get(_id);

        //What does this do o-o
        //This allow the images to be loaded.
        //Every image used there is always an ID tied to it.
        //If image is null --> program will clash
        //Image size too big will also crash the program.

        Bitmap results = BitmapFactory.decodeResource(res, _id);
            resMap.put(_id, results);

            return results;
        }
}
