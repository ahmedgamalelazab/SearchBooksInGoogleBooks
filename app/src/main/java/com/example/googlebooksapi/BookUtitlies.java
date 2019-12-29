package com.example.googlebooksapi;

import android.util.Log;

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

public final class BookUtitlies {
    //this class is final because it's not able to be inherited after declaration
    private static final String TAG = BookUtitlies.class.getSimpleName();

    private BookUtitlies() {
        //this is private swalled
    }

    public static ArrayList<DataContainer> FetchDataFromURL(String mURL) {

        //open connection between the user and the device using the url of the user
        //starting with the url
        URL url = MakeUrl(mURL);
        //second working with the json response
        String JsonResponse = ""; //this where our file will be saved
        try {
            JsonResponse = MakeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<DataContainer> data = FetchDataFromJsonResponse(JsonResponse);
        return data;

    }

    private static String MakeHttpRequest(URL url) throws IOException {
        String JsonResponse = "";
        if (url == null) {
            return JsonResponse; //this will return an empty value if u failed with the goddamit url
        }
        HttpURLConnection URLConnection = null;
        InputStream inputStream = null;
        try {
            URLConnection = (HttpURLConnection) url.openConnection(); //openened the connection
            URLConnection.setRequestMethod("GET");
            URLConnection.setReadTimeout(15000);
            URLConnection.setConnectTimeout(10000);
            URLConnection.connect();

            //if the connection failed ??
            if (URLConnection.getResponseCode() == 200 || URLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = URLConnection.getInputStream();
                JsonResponse = ReadFromInputStream(inputStream);
            }
        } catch (IOException e) {
            //HANDLED LATER

        } finally {
            if (URLConnection != null) {
                URLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        Log.e(TAG, JsonResponse);
        return JsonResponse;
    }

    private static String ReadFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader readInputStream = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader read = new BufferedReader(readInputStream);
            String Line = read.readLine();
            while (Line != null) {
                output.append(Line);
                Line = read.readLine();
            }

        }
        return output.toString();
    }

    private static URL MakeUrl(String murl) {
        URL url = null;
        try {
            url = new URL(murl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "failed to connected to your url");
            return null;
        }
        return url;
    }


    private static ArrayList<DataContainer> FetchDataFromJsonResponse(String JsonResponse) {

        ArrayList<DataContainer> arr = new ArrayList<>();
        try {


            JSONObject Root = new JSONObject(JsonResponse);
            JSONArray innerRootArr = Root.getJSONArray("items");

            for (int i = 0; i < innerRootArr.length(); i++) {

                try {
                    JSONObject innerArrayObject = innerRootArr.getJSONObject(i);
                    JSONObject volumeInfo = innerArrayObject.getJSONObject("volumeInfo");
                    JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");

                    String thumbnail = imageLinks.getString("thumbnail");

                    arr.add(new DataContainer(thumbnail));
                } catch (JSONException e) {
                    continue;
                }

            }


        } catch (JSONException e) {
            Log.e(TAG, "failed parsing the json file ", e);
        }
        return arr;
    }


}
