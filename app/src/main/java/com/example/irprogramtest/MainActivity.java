package com.example.irprogramtest;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onFragBtnClicked(View v){
        Fragment fr;
        FragmentTransaction ft;
        switch (v.getId()){
            case R.id.btnFragment1:
                fr = new MyFragment1();
                ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragPlace,fr);
                ft.commit();
                break;
            case R.id.btnFragment2:
                fr = new MyFragment2();
                ft = getFragmentManager().beginTransaction().replace(R.id.fragPlace,fr);
                ft.commit();
                break;
        }
    }
}
