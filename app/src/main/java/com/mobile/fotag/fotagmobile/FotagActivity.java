package com.mobile.fotag.fotagmobile;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

public class FotagActivity extends AppCompatActivity {

    Model model;
    private ViewGroup listViewGroup;
    private ListImageLayoutView portView;
    private ToolbarView toolsView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotag);
        Log.d("Fotag Activity", "onCreate");
        model = new Model();
        toolsView = new ToolbarView(this, model);
        ViewGroup actionGroup = (ViewGroup) findViewById(R.id.toolbar_layout);
        actionGroup.addView(toolsView);
        listViewGroup = (ViewGroup) findViewById(R.id.main_activity_id);
        portView = new ListImageLayoutView(this, model);
        listViewGroup.addView(portView);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.d("Fotag Activity", "onPostCreate");

        model.changed();
        model.notifyObservers();
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

    @Override
    public void onConfigurationChanged(Configuration updated) {
        super.onConfigurationChanged(updated);
        if (updated.orientation == Configuration.ORIENTATION_LANDSCAPE ||
                updated.orientation == Configuration.ORIENTATION_PORTRAIT) {
            model.onConfigChanged();
        }
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
            for (String uris : model.loadedURLS()) {
                model.setURL(uris);
                new DecodeImageTask(this, model).execute(-1);
            }
            model.setImageLoaded(true);
        }
    }

}
