package com.example.irprogramtest;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        TextView tvLogo = findViewById(R.id.tvLogo);
        tvLogo.setTypeface(
                Typeface.createFromAsset(getAssets(),
                        getString(R.string.m_nastaliq_font_2))
        );
    }

    public void onBtnIndexClicked(View v){
        Intent intent = new Intent(this,IndexActivity.class);
        intent.putExtra("getFavorites",false);
        startActivity(intent);
    }
    public void onBtnFavoriteClicked(View v){
        Intent intent = new Intent(this,IndexActivity.class);
        intent.putExtra("getFavorites",true);
        startActivity(intent);
    }
    public void onBtnSearchClicked(View v){
        startActivity(new Intent(this,SearchActivity.class));
    }
    public void onBtnSettingClicked(View v){

    }
    public void onBtnAboutMeClicked(View v){
        alertMe(getString(R.string.about_me_title)
        ,getString(R.string.about_me_message),
                        true);
    }
    public void onBtnWebsiteClicked(View v){
        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.irprogram.com")));
    }
    public void onBtnContactMeClicked(View v){
        alertMe(getString(R.string.contact_me_title)
                ,getString(R.string.contact_me_message),
                true);
    }
    public void onBtnExitClicked(View v){
        finish();
    }

    private void alertMe(String title, String message, boolean cancelabe){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(message)
                .setTitle(title)
                .setCancelable(cancelabe)
                .show();
    }
}
