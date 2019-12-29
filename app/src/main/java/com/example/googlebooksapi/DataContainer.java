package com.example.googlebooksapi;

import java.net.MalformedURLException;
import java.net.URL;

public class DataContainer {
    //SECTION of private Data
    private String mthumbnailURL;
    //end of the private data

    //section of constructor
    public DataContainer(String thumbnailURL) {
        this.mthumbnailURL = thumbnailURL;
    }
    //END OF constructor data

    //section of getters
    public String getthumbnailURL() {
        return this.mthumbnailURL;
    }

    public URL ConvertToURL(String theURL) {
        URL url = null;
        try {
            url = new URL(theURL);
        } catch (MalformedURLException e) {
            e.fillInStackTrace();
        }
        return url;
    }

}
