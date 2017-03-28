package com.applications.kabarkhodu.news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by my computer on 10/29/2016.
 */
public class UrlToLogos {
    @SerializedName("small")
    @Expose
    private String small;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }
}
