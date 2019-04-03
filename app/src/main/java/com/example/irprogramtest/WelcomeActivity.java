package com.example.irprogramtest;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    private NavigationFragment nd;
    private final String url_cat = "http://10.0.2.2/irprogram/get_cat.php";
    private final String url_ads = "http://10.0.2.2/irprogram/get_ads.php?page=";
    private final String url_ads_by_cat = "http://10.0.2.2/irprogram/get_ads_by_cat.php?cat=";
    private final String url_insert_ads = "http://10.0.2.2/irprogram/set_data.php";
    private List<HashMap<String, Object>> cats;
    private ListView lvCat;
    private static final String TAG = "MatiMessage";
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        checkConnection();
    }
    private void checkConnection() {
        if(isConnected()){
            // load categories
            makeCategoryList();
            nd =(NavigationFragment)
                    getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
            nd.setup((DrawerLayout)findViewById(R.id.welcome_layout));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            lvCat = findViewById(R.id.category_list);

            lvCat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle = new Bundle();
                    Intent i =new Intent(getApplicationContext(),AdvertisementsActivity.class);
                    i.putExtra("url",url_ads_by_cat+cats.get(position).get("id").toString());
                    i.putExtra("getByCat",true);
                    startActivity(i,bundle);
                }
            });
        }else {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setCancelable(false);
            alert.setTitle(R.string.internet_error_title);
            alert.setMessage(R.string.internet_error);
            alert.setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    checkConnection();
                }
            });
            alert.setNegativeButton(R.string.btn_back_title, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
        }
    }

    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] ni = cm.getAllNetworkInfo();
        for (NetworkInfo nInfo :
                ni)
            if (nInfo.getState() == NetworkInfo.State.CONNECTED)
                return true;

        return false;
    }
    public void onBtnShowAdsClicked(View v){
        Intent i =new Intent(this,AdvertisementsActivity.class);
        i.putExtra("url",url_ads);
        startActivity(i);
    }
    public void onBtnInsertAdsClicked(View v){
        Intent intent =new Intent(this,InsertAdvertisementActivity.class);
        intent.putExtra("url",url_insert_ads);

        String[] id = new String[cats.size()];
        String[] name = new String[cats.size()];
        int i=0;
        for (HashMap<String, Object> cat :
                cats) {
            id[i] = cat.get("id").toString();
            name[i++] =cat.get("name").toString();
        }
        intent.putExtra("cat_id",id);
        intent.putExtra("cat_name",name);

        startActivity(intent);
    }
    public void onBtnExitClicked(View v){
       onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_setting)
            return true;
        if (nd.mToggle.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    private class DownloadCats extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            String data="";
            try {
                JSONDownloader jd = new JSONDownloader();
                data = jd.downloadURL(strings[0]);
            }catch (Exception e){
                Log.e(TAG, "DownloadCats: WelcomeActivity --> ",e );
            }
            return data;
        }

        @Override
        protected void onPostExecute(String json) {
            CategoryParser parser = new CategoryParser();
            cats = new ArrayList<HashMap<String, Object>>();
            cats = parser.parse(json);
            String[] from ={"name", "amount"};
            int[] to ={R.id.tvNameCat, R.id.tvAmountCat};
            SimpleAdapter mAdapter = new SimpleAdapter(getBaseContext(),
                    cats, R.layout.cat_list_row, from, to);
            lvCat.setAdapter(mAdapter);
        }
    }
    public void makeCategoryList(){
       new DownloadCats().execute(url_cat);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("خروج")
                .setMessage("آیا مطمئن هسنید که می خواهید خارج شوید؟")
                .setPositiveButton("بله", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.exit(0);
                    }
                })
                .setNegativeButton("خیر", null).show();
    }

    public void onBtnContactClicked(View v){
        alertMe(getString(R.string.alert_cantact_title),
                Html.fromHtml("<a href='mailto:a77sabeghi@gmail.com'>" +
                        getString(R.string.alert_cantact_body)+"</a>").toString(),
                true);
    }
    public void onBtnAboutClicked(View v){
        alertMe(getString(R.string.alert_about_title),
                        getString(R.string.alert_about_body),
                true);
    }

    private void alertMe(String title, String body, boolean cancelabe){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setCancelable(cancelabe);
        alert.setTitle(title);
        alert.setMessage(body);
        alert.show();
    }
}