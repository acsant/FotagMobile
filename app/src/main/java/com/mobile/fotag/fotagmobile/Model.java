package com.mobile.fotag.fotagmobile;

import android.graphics.Bitmap;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.UUID;

/**
 * Created by Akash-Mac on 16-03-22.
 */
public class Model extends Observable implements Serializable {

    private boolean imagesLoaded = false;
    private HashMap<UUID, ImageModel> allImages;
    private int[] imageIds = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4, R.drawable.img5,
        R.drawable.img6, R.drawable.img7, R.drawable.img11};
    private ArrayList<String> allUrls = new ArrayList<>();
    private int filter = 0;
    private String url;

    public Model () {
        allImages = new HashMap<>();
    }

    public void changed() {
        this.setChanged();
    }

    public void addImage(Bitmap img) {
        UUID imageId = UUID.randomUUID();
        ImageModel imgM = new ImageModel(img, 0, imageId);
        allImages.put(imageId, imgM);
        setChanged();
        notifyObservers();
    }

    protected void onConfigChanged () {
        setChanged();
        notifyObservers();
    }

    public void setImageLoaded(boolean setVal) {
        imagesLoaded = setVal;
        setChanged();
        notifyObservers();
    }

    public boolean isImageLoaded() {
        return imagesLoaded;
    }

    public int[] getImageIds () {
        return imageIds;
    }

    public ArrayList<ImageModel> getImageModels () {
        return new ArrayList<ImageModel>(allImages.values());
    }

    public void setImageRank (UUID id, int rank) {
        allImages.get(id).rankImage(rank);
    }

    public void setFilter (int num) {
        filter = num;
        setChanged();
        notifyObservers();
    }

    public ArrayList<ImageModel> getFilteredDataSet () {
        ArrayList<ImageModel> toRet = new ArrayList<>();
        for (ImageModel im : allImages.values()) {
            if (im.getRank() >= filter) {
                toRet.add(im);
            }
        }
        return toRet;
    }

    public void setURL (String link) {
        url = link;
    }

    public void addURLPermanent (String link) {
        allUrls.add(link);
    }

    public String getURL () {
        return url;
    }

    public ArrayList<String> loadedURLS () {
        return allUrls;
    }

    public void clearImages() {
        allUrls.clear();
        allImages.clear();
        imagesLoaded = false;
        setChanged();
        notifyObservers();
    }

}
