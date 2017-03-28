package com.applications.kabarkhodu.news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by my computer on 10/28/2016.
 */
public class Source {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("urlsToLogos")
    @Expose
    private UrlToLogos urlsToLogos;

    public Source() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
/*
    public String getUrlToLogos() {
        return urlToLogos;
    }

    public void setUrlToLogos(String urlToLogos) {
        this.urlToLogos = urlToLogos;
    }
*/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UrlToLogos getUrlsToLogos() {
        return urlsToLogos;
    }

    public void setUrlsToLogos(UrlToLogos urlsToLogos) {
        this.urlsToLogos = urlsToLogos;
    }
/*
    public String getUrlToLogos() {
        return urlToLogos;
    }

    public void setUrlToLogos(String urlToLogos) {
        this.urlToLogos = urlToLogos;
    }
    */
}


