package com.example.googlebooksapi;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class books extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<DataContainer>> {


    //our url
    private String REQUEST_URL;
    private TemplateAdapter BookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        ListView BooksList = findViewById(R.id.List);
        BookAdapter = new TemplateAdapter(this, new ArrayList<DataContainer>());
        BooksList.setAdapter(BookAdapter);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String search = extras.getString("Key");
            //The key argument here must match that used in the other activity
            Log.e(books.class.getSimpleName(), "a777777777aaaaaaaaa" + search);
            REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=" + search + "&key=AIzaSyDxXZT1gUcFlTEaC95oysA3MHrZl7qmHss";
        }


        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1, null, this);
    }

    @Override
    public Loader<ArrayList<DataContainer>> onCreateLoader(int id, Bundle args) {
        return new BookLoader(this, REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<DataContainer>> loader, ArrayList<DataContainer> data) {
        BookAdapter.clear();
        if (data != null && !data.isEmpty()) {
            BookAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<DataContainer>> loader) {
        BookAdapter.clear();
    }
}
