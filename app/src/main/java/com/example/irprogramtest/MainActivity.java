package com.example.irprogramtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUserName = findViewById(R.id.etUsername);
    }

    public void nextBtnClicked(View v){
        Intent intent = new Intent(MainActivity.this,NewActivity.class);
        intent.putExtra("uName",etUserName.getText().toString());
        startActivity(intent);
    }
}
