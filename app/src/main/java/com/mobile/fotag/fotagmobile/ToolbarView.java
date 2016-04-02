package com.mobile.fotag.fotagmobile;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Akash-Mac on 16-04-01.
 */
public class ToolbarView extends LinearLayout implements Observer {

    private Context context;
    private Model model;

    public ToolbarView (Context ctx, Model _model) {
        super(ctx);
        context = ctx;
        model = _model;
        View.inflate(context, R.layout.toolbar_view, this);
        model.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object data) {

    }
}
