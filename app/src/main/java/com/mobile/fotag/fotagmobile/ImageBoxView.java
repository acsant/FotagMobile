package com.mobile.fotag.fotagmobile;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

/**
 * Created by Akash-Mac on 16-03-29.
 */
public class ImageBoxView extends RecyclerView.ViewHolder implements Observer {
    private Model model;
    private ImageView imgView;
    private RatingBar imgRanking;
    private UUID imgId;

    public ImageBoxView (View view, Model _model) {
        super(view);
        model = _model;
        imgRanking = (RatingBar) view.findViewById(R.id.img_rankings_bar);
        addRankingListener();
        imgView = (ImageView) view.findViewById(R.id.crd_img_view);
        Log.d("ImageViewBox: ", "Created image view");
    }

    public void setId (UUID id) {
        imgId = id;
    }

    public ImageView getView () {
        return imgView;
    }

    public void setImgRanking (int _rank) {
        imgRanking.setRating(_rank);
    }

    @Override
    public void update(Observable observable, Object data) {

    }

    private void addRankingListener () {
        imgRanking.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                model.setImageRank(imgId, Math.round(rating));
            }
        });
    }

}
