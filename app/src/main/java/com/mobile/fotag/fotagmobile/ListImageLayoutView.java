package com.mobile.fotag.fotagmobile;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by Akash-Mac on 16-03-29.
 */
public class ListImageLayoutView extends RelativeLayout implements Observer {

    private Model model;
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager rLayoutMgr;
    private RecyclerView.Adapter rDataAdapter;
    public ListImageLayoutView (Context _context, Model _model) {
        super(_context);
        model = _model;
        context = _context;
        model.addObserver(this);
        View.inflate(context, R.layout.port_list, this);
        recyclerView = (RecyclerView) findViewById(R.id.fotag_recycler_view);
        recyclerView.setHasFixedSize(true);
        rLayoutMgr = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ?
                new LinearLayoutManager(context) : new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(rLayoutMgr);
        rDataAdapter = new CustomRecyclerviewAdapter(model.getImageModels(), model);
    }

    @Override
    public void update(Observable observable, Object data) {
        rLayoutMgr = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ?
                new LinearLayoutManager(context) : new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(rLayoutMgr);
        rDataAdapter = new CustomRecyclerviewAdapter(model.getImageModels(), model);
        recyclerView.setAdapter(rDataAdapter);
        rDataAdapter.notifyDataSetChanged();
        invalidate();
    }

    // This is an adapter to handle the binding between images and views and rankings and ranking bars
    private class CustomRecyclerviewAdapter extends RecyclerView.Adapter<ImageBoxView>{

        private ArrayList<ImageModel> dataSet;
        private Model model;

        public CustomRecyclerviewAdapter (ArrayList<ImageModel> _dataSet, Model _model) {
            model = _model;
            dataSet = _dataSet;
        }

        @Override
        public ImageBoxView onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.img_list_view, parent, false);
            ImageBoxView imBoxView = new ImageBoxView(view, model);
            return imBoxView;
        }

        @Override
        public void onBindViewHolder(ImageBoxView holder, int position) {
            holder.setId(dataSet.get(position).getImgId());
            holder.setImgRanking(dataSet.get(position).getRank());
            holder.getView().setImageBitmap(dataSet.get(position).getImage());
        }

        @Override
        public int getItemCount() {
            return dataSet.size();
        }
    }

}
