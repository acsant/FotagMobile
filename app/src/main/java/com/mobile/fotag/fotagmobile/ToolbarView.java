package com.mobile.fotag.fotagmobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Akash-Mac on 16-04-01.
 */
public class ToolbarView extends LinearLayout implements Observer {

    private Context context;
    private Model model;
    private RatingBar filterView;
    private ImageView searchView;
    private ImageView eraseAll;

    public ToolbarView (Context ctx, Model _model) {
        super(ctx);
        context = ctx;
        model = _model;
        View.inflate(context, R.layout.toolbar_view, this);
        model.addObserver(this);
        filterView = (RatingBar) findViewById(R.id.filter_view);
        searchView = (ImageView) findViewById(R.id.search_img_option);
        eraseAll = (ImageView) findViewById(R.id.clear_all);
        addFilterChangeListener();
        addSearchListener();
        addClearListener();
    }

    private void addClearListener () {
        eraseAll.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                model.clearImages();
            }
        });
    }

    private void addFilterChangeListener() {
        filterView.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                model.setFilter(Math.round(rating));
            }
        });
    }

    private void addSearchListener () {
        searchView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder searchDialogBuilder = new AlertDialog.Builder(context);
                LayoutInflater li = LayoutInflater.from(context);
                View imgSearchView = li.inflate(R.layout.search_dialog, null);
                searchDialogBuilder.setView(imgSearchView);
                final EditText inText = (EditText) imgSearchView.findViewById(R.id.in_url);
                searchDialogBuilder.setCancelable(false)
                        .setPositiveButton("Download", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String urlInDialog = inText.getText().toString();
                                model.setURL(urlInDialog);
                                model.addURLPermanent(urlInDialog);
                                new DecodeImageTask(context, model).execute(-1);
                            }
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                // Create dialog
                AlertDialog dialog = searchDialogBuilder.create();
                dialog.show();

            }
        });
    }

    @Override
    public void update(Observable observable, Object data) {

    }
}
