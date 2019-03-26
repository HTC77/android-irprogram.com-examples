package com.example.irprogramtest;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity {
    private NavigationFragment nd;
    private final String url_cat = "http://10.0.2.2/irprogram/get_cat.php";
    private final String url_ads = "http://10.0.2.2/irprogram/get_ads.php";
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        nd =(NavigationFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        nd.setup((DrawerLayout)findViewById(R.id.welcome_layout));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // add categories
        makeCategoryList();
    }

    public void onBtnShowAdsClicked(View v){

    }
    public void onBtnInsertAdsClicked(View v){

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

    public void makeCategoryList(){
       Thread t = new Thread(new Runnable() {
           @Override
           public void run() {
               JSONDownloader jd = new JSONDownloader();
               String temp = jd.downloadURL(url_cat);

               CategoryParser parser = new CategoryParser();

               List<HashMap<String, Object>> cats
                       = new ArrayList<HashMap<String, Object>>();

               cats = parser.parse(temp);

               String[] from ={"name", "amount"};
               int[] to ={R.id.tvNameCat, R.id.tvAmountCat};

               SimpleAdapter mAdapter = new SimpleAdapter(getBaseContext(),
                       cats, R.layout.cat_list_row, from, to);

               ListView lv = findViewById(R.id.category_list);
               lv.setAdapter(mAdapter);
           }
       });
       t.start();
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
}