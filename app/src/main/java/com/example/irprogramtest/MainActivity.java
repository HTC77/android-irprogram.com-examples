package com.example.irprogramtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView =findViewById(R.id.listview);

        String[] data = new String[]{"Ubuntu", "Arch", "openSUSE", "kali"};
        ArrayList<String> myArray = new ArrayList<String>();
        for (int i = 0; i < data.length; i++)
            myArray.add(data[i]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,myArray);
        listView.setAdapter(adapter);
    }
}
