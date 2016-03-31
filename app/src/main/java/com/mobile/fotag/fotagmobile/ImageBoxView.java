package com.mobile.fotag.fotagmobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Akash-Mac on 16-03-29.
 */
public class ImageBoxView extends RecyclerView.ViewHolder implements Observer {
    private Model model;
    private ImageView imgView;

    public ImageBoxView (View view, Model _model) {
        super(view);
        model = _model;
        imgView = (ImageView) view.findViewById(R.id.crd_img_view);
        Log.d("ImageViewBox: ", "Created image view");
    }

    public ImageView getView () {
        return imgView;
    }

    @Override
    public void update(Observable observable, Object data) {

    }
}
