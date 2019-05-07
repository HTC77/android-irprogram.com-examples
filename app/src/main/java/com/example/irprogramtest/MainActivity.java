package com.example.irprogramtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private final static String LOGIN_URL = "http://10.0.2.2/MyServer/login.php";
    private final static String SIGN_UP_URL = "http://10.0.2.2/MyServer/reg.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void onBtnLoginClicked(View v){
        Intent i = new Intent(this,LoginActivity.class);
        i.putExtra("url",LOGIN_URL);
        startActivity(i);
    }

    public void onBtnRegisterClicked(View v){
        Intent i = new Intent(this, SignUpActivity.class);
        i.putExtra("url", SIGN_UP_URL);
        startActivity(i);
    }
}
