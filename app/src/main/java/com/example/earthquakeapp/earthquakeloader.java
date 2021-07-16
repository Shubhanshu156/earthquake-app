package com.example.earthquakeapp;
import android.content.Context;

import android.content.AsyncTaskLoader;

import java.util.List;

public class earthquakeloader extends AsyncTaskLoader<List<earthquake>> {

    private String mUrl;

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    public earthquakeloader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public List<earthquake> loadInBackground() {
        if(mUrl == null) {
            return null;
        }

        return QueryUtils.fetchEarthquakeData(mUrl);
    }

}