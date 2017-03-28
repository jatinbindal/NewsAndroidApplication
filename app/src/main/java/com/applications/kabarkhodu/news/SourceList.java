 package com.applications.kabarkhodu.news;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by my computer on 10/28/2016.
 */
public class SourceList{

    @SerializedName("sources")
    @Expose
    List<Source> sources=new ArrayList<Source>();



    public static SourceList parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        SourceList sourceList = gson.fromJson(response, SourceList.class);
        return sourceList;
    }

    public List<Source> getResults()
    {

        return this.sources;
    }

}

