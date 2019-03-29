package com.example.irprogramtest;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdvertisementActivity extends AppCompatActivity {
    private static final String TAG = "MatiMessage";
    private List<HashMap<String, Object>> allAds = new ArrayList<>();
    private String url_ads;
    private int current_page =1;
    private ListView lv;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        lv= findViewById(R.id.adsList);
        url_ads = getIntent().getExtras().getString("url");
        makeAdsList();
    }

    public void makeAdsList(){
        try {
            new DownloadTask().execute(url_ads + current_page);
            current_page++;
        }catch (Exception e){
            Log.e(TAG, "makeAdsList: AdvertisementActivity --> ",e );
        }
    }

    private class DownloadTask extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... strings) {
            String temp="";
            JSONDownloader jd = new JSONDownloader();
            temp = jd.downloadURL(strings[0]);
            return temp;
        }

        @Override
        protected void onPostExecute(String s) {
            ListViewLoaderTask loader = new ListViewLoaderTask();
            loader.execute(s);
        }
    }

    class ListViewLoaderTask extends AsyncTask<String, Void, SimpleAdapter> {

        @Override
        protected SimpleAdapter doInBackground(String... strings) {
            try {
                AdvertisementParser parser = new AdvertisementParser();
                allAds.addAll(parser.parse(strings[0]));
            }catch (Exception e){
                Log.e(TAG, "makeAdsList (ListViewLoaderTask): AdvertisementActivity --> ",e );
            }

            String[] from = {"title", "intro", "image", "date", "cat"};
            int[] to = {R.id.tvTitleAds, R.id.tvIntroAds,
                    R.id.imgAds, R.id.tvDateAds, R.id.tvCatAds};

            SimpleAdapter adapter = new SimpleAdapter(
              getBaseContext(), allAds, R.layout.ads_list_row, from, to
            );

            return adapter;
        }

        @Override
        protected void onPostExecute(SimpleAdapter simpleAdapter) {
            lv.setAdapter(simpleAdapter);
        }
    }
    public void onBtnBackToHomeClicked(View v){
        finish();
    }
}