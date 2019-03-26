package com.example.irprogramtest;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSONDownloader {
    private static final String TAG = "MatiMessage";
    public static String downloadURL(String url){
        String data = "";
        InputStream mStream;
        try {
            URL mUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) mUrl.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();
            mStream = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(mStream));
            StringBuilder sb= new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
                sb.append(line);
            data = sb.toString();
            br.close();
            connection.disconnect();
            mStream.close();
        }catch (Exception e){
            Log.e(TAG, "error JSONDownloader downloadURL: -> ", e);

        }
        return (data);
    }
}
