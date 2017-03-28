package com.applications.kabarkhodu.news;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by my computer on 10/29/2016.
 */
public class ArticleList {

    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("articles")
    @Expose
    private List<Article> articles = new ArrayList<Article>();


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
