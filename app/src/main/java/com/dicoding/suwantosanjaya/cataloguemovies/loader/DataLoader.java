package com.dicoding.suwantosanjaya.cataloguemovies.loader;
/**
 * @AUTHOR Suwanto Sanjaya
 */

//import android.content.AsyncTaskLoader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.dicoding.suwantosanjaya.cataloguemovies.BuildConfig;
import com.dicoding.suwantosanjaya.cataloguemovies.models.Movies;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Suwanto Sanjaya on 27/11/2018.
 */

public class DataLoader extends AsyncTaskLoader<ArrayList<Movies>> {
    private ArrayList<Movies> mData;
    private boolean mHasResult = false;
    private String URL;

    public DataLoader(@NonNull Context context, String pilihan, String query) {
        super(context);
        onContentChanged();
        if (pilihan == "SEARCH")
            this.URL = BuildConfig.URL_SEARCH + "&query=" + query;
        else if (pilihan == "NOWPLAYING")
            this.URL = BuildConfig.URL_NOWPLAYING;
        else if (pilihan == "UPCOMING")
            this.URL = BuildConfig.URL_UPCOMMING;
    }


    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (this.mHasResult)
            deliverResult(this.mData);
    }

    @Override
    public void deliverResult(@Nullable ArrayList<Movies> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    public void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            onReleaseResources(mData);
            mData = null;
            mHasResult = false;
        }
    }

    private void onReleaseResources(ArrayList<Movies> mData) {
    }


    @Nullable
    @Override
    public ArrayList<Movies> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();

        final ArrayList<Movies> movieItems = new ArrayList<>();
        String url = this.URL;
        //Log.i("PESAN 4", url);
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");
                    //Log.i("PESAN 5", result);
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject item = list.getJSONObject(i);
                        Movies mItems = new Movies(item);
                        movieItems.add(mItems);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return movieItems;
    }
}
