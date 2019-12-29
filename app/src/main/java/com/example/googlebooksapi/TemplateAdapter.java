package com.example.googlebooksapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class TemplateAdapter extends ArrayAdapter<DataContainer> {
    public TemplateAdapter(Context context, ArrayList<DataContainer> data) {
        super(context, 0, data);
    }
    //now playing with data

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View ListViewItem = convertView;
        if (ListViewItem == null) {
            ListViewItem = LayoutInflater.from(getContext()).inflate(R.layout.template, parent, false);
        }
        //positioning the data now in the right way
        DataContainer currentData = getItem(position);
        String theFullUrl = currentData.getthumbnailURL();//this
        UrlImageView image = ListViewItem.findViewById(R.id.thumbnail);
        image.setImageURL(currentData.ConvertToURL(theFullUrl));
        return ListViewItem;
    }
}
