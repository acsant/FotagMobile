package com.mobile.fotag.fotagmobile;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.DropBoxManager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.UUID;

/**
 * Created by Akash-Mac on 16-03-22.
 */
public class Model extends Observable {

    private boolean imagesLoaded = false;
    private HashMap<UUID, ImageModel> allImages;
//    private int[] imageIds = {R.drawable.twitter, R.drawable.twit1, R.drawable.twit2, R.drawable.twit3, R.drawable.twit4,
//    R.drawable.twit5, R.drawable.twit7};
    private int[] imageIds = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5,
        R.drawable.img6, R.drawable.img7, R.drawable.img11};
    public Model () {
        allImages = new HashMap<>();
    }

    public void changed() {
        this.setChanged();
    }

    public void addImage(Bitmap img) {
        UUID imageId = UUID.randomUUID();
        ImageModel imgM = new ImageModel(img, 0, imageId);
        allImages.put(imageId, imgM);
        setChanged();
        notifyObservers();
    }

    public void setImageLoaded(boolean setVal) {
        imagesLoaded = setVal;
        setChanged();
        notifyObservers();
    }

    public boolean isImageLoaded() {
        return imagesLoaded;
    }

    public int[] getImageIds () {
        return imageIds;
    }

    public ArrayList<ImageModel> getImageModels () {
        return new ArrayList<ImageModel>(allImages.values());
    }

}
