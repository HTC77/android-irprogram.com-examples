package com.example.irprogramtest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class SplashScreen extends AppCompatActivity {
    private static final long SPLASH_TIME = 2000;

    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mWebView =findViewById(R.id.mWebView);
        String tx = "<center>" +
                "<img src='file:///android_asset/wait.gif' alt='picture' />" +
                "</center>";

        mWebView.loadDataWithBaseURL(
                null, tx , "text/html; charset='utf-8'" , null , null
        );

        mWebView.setBackgroundColor( Color.TRANSPARENT );

        new Handler().postDelayed(
            new Runnable() {
                @Override
                public void run()
                {
                    Intent i = new Intent(SplashScreen.this , MainActivity.class);

                    SplashScreen.this.startActivity(i);
                    /* animation */

                    SplashScreen.this.finish();
                }
            } , SPLASH_TIME
        );
    }
}
