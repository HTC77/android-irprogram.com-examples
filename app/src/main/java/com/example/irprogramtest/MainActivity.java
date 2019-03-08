package com.example.irprogramtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private RadioGroup rgColor;
    private RadioButton selectedRb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        rgColor = findViewById(R.id.rgColor);
    }

    public void onSelectBtnClicked(View v){
        int selectedRbId = rgColor.getCheckedRadioButtonId();
        selectedRb = findViewById(selectedRbId);
        Toast.makeText(this, selectedRb.getText(), Toast.LENGTH_SHORT).show();
    }
}
