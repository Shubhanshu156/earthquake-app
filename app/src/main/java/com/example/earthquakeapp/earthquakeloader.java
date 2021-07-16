package com.example.earthquakeapp;
import android.content.Context;
import android.content.Loader;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
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