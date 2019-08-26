/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    EarthquakeAdapter adapter;

    private static final int EARTHQUAKE_LOADER_ID = 1;

    private TextView mEmptyStateTextView;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        progressBar = (ProgressBar) findViewById(R.id.loading_spinner);

        LoaderManager loaderManager = getLoaderManager();

        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        // Create a new {@link ArrayAdapter} of earthquakes
        adapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);


        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Earthquake earthquake = adapter.getItem(position);
                String url = earthquake.getWebsiteUrl();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });
    }

    /*private class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>> {

        protected List<Earthquake> doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Earthquake> result = QueryUtils.fetchEarthquakeData(urls[0]);
            return result;
        }


        protected void onPostExecute(List<Earthquake> data) {

            adapter.clear();

            if(data != null && !data.isEmpty())
                adapter.addAll(data);
        }
    }*/

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int i, Bundle bundle){

        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes){

        progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        progressBar.setVisibility(View.GONE);

        mEmptyStateTextView.setText(R.string.no_earthquakes);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()) {
        }
        else {
            progressBar.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_connection);
        }

        adapter.clear();

        if(earthquakes != null && !earthquakes.isEmpty())
            adapter.addAll(earthquakes);
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        // Loader reset, so we can clear out our existing data.
        adapter.clear();
    }
    }


