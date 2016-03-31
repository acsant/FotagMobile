package com.mobile.fotag.fotagmobile;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by Akash-Mac on 16-03-30.
 */
public class DecodeImageTask extends AsyncTask<Integer, Void, Bitmap> {

    private Context context;
    private Model model;
    private static int defSizeHeight = 265;
    private static int defSizeWidth = 150;

    public DecodeImageTask (Context ctx, Model _model) {
        context = ctx;
        model = _model;
    }

    @Override
    protected Bitmap doInBackground(Integer... params) {
        return decodeBitmap(params[0]);
    }

    @Override
    protected void onPostExecute (Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }
        bitmap = Bitmap.createScaledBitmap(bitmap, defSizeHeight, defSizeWidth, true);
        model.addImage(bitmap);
    }

    private Bitmap decodeBitmap (int resId) {
        Bitmap bitmap = decodeBitmapFromResource(context.getResources(), resId);
        return bitmap;
    }

    private static Bitmap decodeBitmapFromResource (Resources res, int resId) {
        // Check Image Dimension
        final BitmapFactory.Options boptions = new BitmapFactory.Options();
        boptions.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, boptions);
        // Calculating the size
        boptions.inSampleSize = calcSampleSize(boptions);
        boptions.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, boptions);
    }

    private static int calcSampleSize (BitmapFactory.Options boptions) {
        final int imgHeight = boptions.outHeight;
        final int imgWidth = boptions.outWidth;
        int sampleSize = 1;

        if (imgHeight > defSizeHeight || imgWidth > defSizeWidth) {
            final int halfHeight = imgHeight/2;
            final int halfWidth = imgWidth/2;
            while ((halfHeight / sampleSize) > defSizeHeight
                    && (halfWidth / sampleSize) > defSizeWidth) {
                sampleSize *= 2;
            }
        }
        System.out.println(sampleSize);
        return sampleSize;
    }
}
