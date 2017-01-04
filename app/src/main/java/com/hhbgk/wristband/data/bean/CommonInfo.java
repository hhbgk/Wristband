package com.hhbgk.wristband.data.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by bob on 17-1-4.
 */

public class CommonInfo {
    private String title;
    private String subtitle;
    private Drawable subicon;

    public CommonInfo(String title, String subtitle, Drawable drawable){
        this.title = title;
        this.subtitle = subtitle;
        this.subicon = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public Drawable getSubicon() {
        return subicon;
    }

    public void setSubicon(Drawable subicon) {
        this.subicon = subicon;
    }
}
