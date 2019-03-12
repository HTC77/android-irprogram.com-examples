package com.example.irprogramtest;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnClicked(View v){

        switch (v.getId()){
            case R.id.btnFragment:
                Toast.makeText(this, "Fragment button clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnMain:
                Toast.makeText(this, "Main Activity Button Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
