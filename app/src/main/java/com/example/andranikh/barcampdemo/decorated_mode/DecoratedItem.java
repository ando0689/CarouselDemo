package com.example.andranikh.barcampdemo.decorated_mode;

import android.support.annotation.DrawableRes;

/**
 * Created by andranikh on 5/27/17.
 */

public class DecoratedItem {

    private String distance;
    private @DrawableRes int picResId;
    private boolean selected;


    public DecoratedItem(String distance, int picResId) {
        this.distance = distance;
        this.picResId = picResId;
    }

    public String getDistance() {
        return distance;
    }

    public int getPicResId() {
        return picResId;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
