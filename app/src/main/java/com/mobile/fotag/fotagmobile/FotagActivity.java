package com.mobile.fotag.fotagmobile;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


public class FotagActivity extends AppCompatActivity {

    Model model;
    private ViewGroup listViewGroup;
    private ListImageLayoutView portView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotag);
        Log.d("Fotag Activity", "onCreate");
        model = new Model();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        listViewGroup = (ViewGroup) findViewById(R.id.main_activity_id);
        portView = new ListImageLayoutView(this, model);
        listViewGroup.addView(portView);
        setSupportActionBar(toolbar);

    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.d("Fotag Activity", "onPostCreate");

        //model.addObserver(listImageView);
        model.addObserver(portView);
        model.changed();
        model.notifyObservers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fotag, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadImageController (View view) {
        // call the image loader in the model
        int[] imageIds = model.getImageIds();
        BitmapFactory.Options boptions = new BitmapFactory.Options();
        boptions.inJustDecodeBounds = true;
        if (!model.isImageLoaded()) {
            for (int id : imageIds) {
                new DecodeImageTask(this, model).execute(id);
            }
            model.setImageLoaded(true);
        }
    }

}
