package com.applications.kabarkhodu.news;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("v1/sources?language=en")
    Call<SourceList> loadSources();


    @GET("v1/articles")
    Call<ArticleList> loadArticles(@Query("source") String source, @Query("apiKey") String apiKey);
}
