package com.example.irprogramtest;

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
    }
}
