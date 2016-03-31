package com.mobile.fotag.fotagmobile;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;

import java.util.UUID;

/**
 * Created by Akash-Mac on 16-03-29.
 */
public class ImageModel {
    private Bitmap img;
    private int rank;
    private UUID imgId;

    public ImageModel (Bitmap image, int _rank, UUID id) {
        img = image;
        rank = _rank;
        imgId = id;
    }

    public Bitmap getImage () {
        return img;
    }

    public int getRank () {
        return rank;
    }

}
