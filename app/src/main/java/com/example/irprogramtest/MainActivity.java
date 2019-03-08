package com.example.irprogramtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ImageView img1;
    private boolean picToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        picToggle = false;
        findViews();
    }

    private void findViews() {
        img1 = findViewById(R.id.img1);
    }

    public void imgBtnClicked(View v){
        img1.setImageResource(picToggle ? R.mipmap.m_pic1 : 0);
        picToggle = picToggle ? false : true;
    }
}
