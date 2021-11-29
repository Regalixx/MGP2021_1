package com.sdm.mgp2021_1;

import android.content.res.Resources;
import android.graphics.Bitmap;

import java.util.HashMap;

public class ResourceManager { //to load sprites

    public final static  ResourceManager Instance = new ResourceManager();

    private Resources res = null;

    //  bmp = BitmapFactory.decodeResource(_view.getResources(),R.drawable.gamescene);
    // Meant we need to retrieve the information from the _view, which is our surfaceview, and our image to be loaded on the view.

    //We will use a Hashmap

    private HashMap<Integer, Bitmap> resMap = new HashMap<Integer, Bitmap>();

}
