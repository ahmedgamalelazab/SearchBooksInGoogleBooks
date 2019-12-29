package com.example.googlebooksapi;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

public class BookLoader extends AsyncTaskLoader<ArrayList<DataContainer>> {

    //my data
    private String mURL;

    public BookLoader(Context context, String mURL) {
        super(context);
        this.mURL = mURL;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<DataContainer> loadInBackground() {
        if (mURL == null) {
            return null;
        }
        ArrayList<DataContainer> data = BookUtitlies.FetchDataFromURL(mURL);
        return data;
    }
}
