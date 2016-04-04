package com.mobile.fotag.fotagmobile;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.TypedValue;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Akash-Mac on 16-03-30.
 */
public class DecodeImageTask extends AsyncTask<Integer, Void, Bitmap> {

    private Context context;
    private Model model;
    private static int defSizeHeight;
    private static int defSizeWidth;

    public DecodeImageTask (Context ctx, Model _model) {
        context = ctx;
        model = _model;
        defSizeHeight = context.getResources().getInteger(R.integer.def_img_height);
        defSizeWidth = context.getResources().getInteger(R.integer.def_img_width);

    }

    @Override
    protected Bitmap doInBackground(Integer... params) {
        if (params[0] != -1) {
            return decodeBitmap(params[0]);
        } else {
            return downloadFromWeb();
        }
    }

    private Bitmap downloadFromWeb () {
        String url = model.getURL();
        Bitmap downloadedImg = null;
        if (!url.isEmpty()) {
            downloadedImg = decodeBitmapFromResource(context.getResources(), -1, url);
        }
        return downloadedImg;
    }

    @Override
    protected void onPostExecute (Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }
        if (bitmap != null) {
            bitmap = Bitmap.createScaledBitmap(bitmap, defSizeHeight, defSizeWidth, true);
            model.addImage(bitmap);
        }
    }

    private Bitmap decodeBitmap (int resId) {
        Bitmap bitmap = decodeBitmapFromResource(context.getResources(), resId, null);
        return bitmap;
    }

    private static Bitmap decodeBitmapFromResource (Resources res, int resId, String url) {
        // Check Image Dimension
        final BitmapFactory.Options boptions = new BitmapFactory.Options();
        boptions.inJustDecodeBounds = true;
        BufferedInputStream is = null;
        if (resId != -1) {
            BitmapFactory.decodeResource(res, resId, boptions);
        } else {
            try {
                URL connection = new URL(url);
                URLConnection conn = connection.openConnection();
                is = new BufferedInputStream((InputStream) conn.getInputStream());
            } catch (Exception e) {
                Log.e("Error: ", "Invalid url: " + url);
            }
            BitmapFactory.decodeStream(is, null, boptions);

        }
        // Calculating the size
        boptions.inSampleSize = calcSampleSize(boptions);
        boptions.inJustDecodeBounds = false;
        if (is == null) {
            return BitmapFactory.decodeResource(res, resId, boptions);
        } else {
            Bitmap to_return = null;
            try {
                is.mark(is.available());
                is.close();
                to_return = BitmapFactory.decodeStream((InputStream) (new URL(url)).openConnection().getInputStream(), null, boptions);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return to_return;
        }
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
