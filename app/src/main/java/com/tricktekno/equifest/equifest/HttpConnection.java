package com.tricktekno.equifest.equifest;

/**
 * Created by anura on 12/9/2016.
 */

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpConnection {

    public String readUrl(String mapsApiDirectionsUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(mapsApiDirectionsUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setInstanceFollowRedirects(true);
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
            Log.d("Exception while reading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    public List<String[]> downloadCSV(String csvUrl) throws IOException {
        List<String[]> data=null;
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(csvUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setInstanceFollowRedirects(true);
            urlConnection.connect();

            Log.d("Download Debug","response: " + urlConnection.getResponseCode()+" -> "+urlConnection.getResponseMessage());
            iStream = urlConnection.getInputStream();

            data=parseCSV(iStream);
        } catch (Exception e) {
            Log.d("Exception while reading url", e.toString());
        } finally {
            if(iStream!=null) iStream.close();
            urlConnection.disconnect();

            return data;
        }
    }

    public static List<String[]> parseCSV(InputStream in) throws IOException{
        List<String[]> data=new ArrayList<String[]>();

        BufferedReader br = new BufferedReader(new InputStreamReader(
                in));
        String line = "";
        while ((line = br.readLine()) != null) {
            String[] next = line.split(";");
            Log.i("Download Debug","row recieved : "+next[0]);
            data.add(next);
        }
        Log.i("Download Debug","data size : "+data.size());

        in.close();

        return data;
    }
}