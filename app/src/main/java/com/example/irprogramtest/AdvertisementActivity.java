package com.example.irprogramtest;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdvertisementActivity extends AppCompatActivity {
    private static final String TAG = "MatiMessage";
    private List<HashMap<String, Object>> allAds = new ArrayList<>();
    private String url_ads;
    private int current_page =0;
    private ListView lv;
    private int lastLastItemPos;
    private boolean goNext;
    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        lv= findViewById(R.id.adsList);
        clearCache();
        url_ads = getIntent().getExtras().getString("url");
        goNext=true;
        makeAdsList();
        lastLastItemPos = lv.getLastVisiblePosition();
        lv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
               if(v.getScrollBarStyle()==View.SCROLLBARS_INSIDE_OVERLAY)
                   checkScrolLv();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearCache();
    }

    public void clearCache(){
        try{
            File[] f = getBaseContext().getCacheDir().listFiles();
            for (File file :
                    f) {
                file.delete();
            }
        }catch (Exception e){
            Log.e(TAG, "clear cache: AdvertisementActivity --> ",e );
        }
    }

    private void checkScrolLv(){
        if (lastLastItemPos < lv.getLastVisiblePosition()){
            lastLastItemPos = lv.getLastVisiblePosition();
            makeAdsList();
        }
    }
    public void makeAdsList(){
        if(goNext){
            goNext=false;
            try {
                new DownloadTask().execute(url_ads + current_page);
                current_page++;
            }catch (Exception e){
                Log.e(TAG, "makeAdsList: AdvertisementActivity --> ",e );
            }
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
            if(s!= null && s != "" && s.length()>0){
                ListViewLoaderTask loader = new ListViewLoaderTask();
                loader.execute(s);
            }
        }
    }

    private class ListViewLoaderTask extends AsyncTask<String, Void, SimpleAdapter> {

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
            for (int i = 0; i < simpleAdapter.getCount(); i++) {
                HashMap<String, Object> hm=
                        (HashMap<String, Object>) simpleAdapter.getItem(i);
                String imgUrl = (String) hm.get("imagePath");
                HashMap<String, Object> forDownload = new HashMap<String, Object>();
                forDownload.put("imagePath", imgUrl);
                forDownload.put("position", i);
                ImageDownloadTask imgDownloader = new ImageDownloadTask();
                imgDownloader.execute(forDownload);
            }
        }
    }
    private class ImageDownloadTask extends
            AsyncTask<HashMap<String, Object>, Void, HashMap<String, Object>>{

        @Override
        protected HashMap<String, Object> doInBackground(HashMap<String, Object>... hashMaps) {
            InputStream mStream;
            String imgUrl = (String) hashMaps[0].get("imagePath");
            int position = (Integer) hashMaps[0].get("position");
            try {
                URL url = new URL(imgUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                mStream = connection.getInputStream();
                File cacheDirectory = getBaseContext().getCacheDir();
                File temp = new File(cacheDirectory.getPath()
                        + "/image_" + position + "_" + current_page + ".png" );
                FileOutputStream outputStream = new FileOutputStream(temp);
                Bitmap b = BitmapFactory.decodeStream(mStream);
                b.compress(Bitmap.CompressFormat.PNG, 100,outputStream);
                outputStream.flush();
                outputStream.close();
                HashMap<String, Object> bitmap = new HashMap<>();
                bitmap.put("image", temp.getPath());
                bitmap.put("position", position);

                return bitmap;
            }catch (Exception e){
                Log.e(TAG, "makeAdsList (ImageDownloadTask): AdvertisementActivity --> ",e );
            }
            return null;
        }

        @Override
        protected void onPostExecute(HashMap<String, Object> result) {
            String image = (String) result.get("image");
            int position = (Integer) result.get("position");
            SimpleAdapter adapter = (SimpleAdapter) lv.getAdapter();
            HashMap<String, Object> hm =
                    (HashMap<String, Object>) adapter.getItem(position);
            hm.put("image", image);
            adapter.notifyDataSetChanged();
            goNext=true;
        }
    }
    public void onBtnBackToHomeClicked(View v){
        finish();
    }
}