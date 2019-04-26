package com.example.irprogramtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = findViewById(R.id.etName);
    }

    public void onBtnJoinClick(View v){
        if (etName.getText().toString().length() > 0){
            String name = etName.getText().toString().trim();
            Intent i = new Intent(this,SpeakingActivity.class);
            i.putExtra("name",name);
            startActivity(i);
        }else{
            Toast.makeText(this,
                    R.string.enter_name_error, Toast.LENGTH_LONG).show();
        }
    }
}
