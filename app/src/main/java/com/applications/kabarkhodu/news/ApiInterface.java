package com.applications.kabarkhodu.news;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by my computer on 10/28/2016.
 */
public interface ApiInterface {

    public static final String s="techcrunch";
    @GET("v1/sources?language=en")
    Call<SourceList> loadSources();


    @GET("v1/articles")
     Call<ArticleList> loadArticles(@Query("source")String source,@Query("apiKey")String apiKey);
}
