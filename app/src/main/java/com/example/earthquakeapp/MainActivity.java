package com.example.earthquakeapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.AsyncTaskLoader;
//import android.support.v4.content.As;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.quakereport.R;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<earthquake>> {



        private static final int EARTHQUAKE_LOADER_ID = 1;
        ArrayList<earthquake> earthquakes = new ArrayList<>();
        earthquakeadapter mAdapter;
        ListView earthquakeListView;
        TextView mEmptyState;
        ProgressBar mProgressBar;

        public final String LOG_TAG = com.example.earthquakeapp.MainActivity.class.getName();
        private static final String USGS_REQUEST_URL =
                "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=2&limit=20";


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

//        DownloadData downloadData = new DownloadData();
//        downloadData.execute(USGS_REQUEST_URL);

            mEmptyState = (TextView) findViewById(R.id.empty_view);
            mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

            // Checking the internet connection before initializing the loader
            ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                LoaderManager loaderManager = getLoaderManager();
                loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

            } else {
                mProgressBar.setVisibility(View.GONE);
                mEmptyState.setText(R.string.no_internet_connection);
            }

            earthquakeListView = (ListView) findViewById(R.id.list);

            earthquakeListView.setEmptyView(mEmptyState);

            mAdapter = new earthquakeadapter(this, earthquakes);
            earthquakeListView.setAdapter(mAdapter);

            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String url = earthquakes.get(i).getUrl();

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            });
            // Create a new {@link ArrayAdapter} of earthquakes

        }

        @Override
        public Loader<List<earthquake>> onCreateLoader(int i, Bundle bundle) {
            return (Loader<List<earthquake>>) new  earthquakeloader(this,USGS_REQUEST_URL);
        }

        @Override
        public void onLoadFinished(Loader<List<earthquake>> loader, List<earthquake> earthquakes) {
            mAdapter.clear();
            if (earthquakes == null) {
                return;
            }
            mAdapter.addAll(earthquakes);

            mProgressBar.setVisibility(View.GONE);
            mEmptyState.setText(R.string.no_earthquake_found);

        }

        @Override
        public void onLoaderReset(Loader<List<earthquake>> loader) {
            mAdapter.clear();
        }

//    private class DownloadData extends AsyncTask<String, Void, List<Earthquake> > {
//
//        @Override
//        protected List<Earthquake> doInBackground(String... urls) {
//
//            List<Earthquake> earthquakes = new ArrayList<>();
//            if(urls.length < 1 || urls[0] == null) {
//                return null;
//            }
//
//            earthquakes = QueryUtils.fetchEarthquakeData(urls[0]);
//            return earthquakes;
//        }
//
//        @Override
//        protected void onPostExecute(List<Earthquake> earthquakesList) {
//

//            mAdapter.clear();
//            if(earthquakesList != null && !earthquakesList.isEmpty() ) {
//                mAdapter.addAll(earthquakesList);
//            }
//        }
//    }

    }
