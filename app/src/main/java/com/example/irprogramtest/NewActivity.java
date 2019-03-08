package com.example.irprogramtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class NewActivity extends AppCompatActivity {
    private TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        tvResult = findViewById(R.id.tvResult);
        tvResult.setText(this.getIntent().getStringExtra("uName"));
    }

    public void onBackClick(View v){
        finish();
    }
}
