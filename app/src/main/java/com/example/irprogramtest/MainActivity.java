package com.example.irprogramtest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.mRView);

        RecyclerViewItem[] items = getMyItems();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter( items , this );

        recyclerView.setAdapter( mAdapter );

        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    public RecyclerViewItem[] getMyItems()
    {
        RecyclerViewItem[] items = {
                new RecyclerViewItem("add" , R.drawable.add_48 ) ,
                new RecyclerViewItem("address" , R.drawable.address_book_48 ) ,
                new RecyclerViewItem("clock" , R.drawable.clock_48 ) ,
                new RecyclerViewItem("flag" , R.drawable.flag_green_48 ) ,
                new RecyclerViewItem("global" , R.drawable.global ) ,
                new RecyclerViewItem("key" , R.drawable.key_48 ) ,
                new RecyclerViewItem("preferences" , R.drawable.preferences_48 ) ,
                new RecyclerViewItem("rating" , R.drawable.rating_48 ) ,
                new RecyclerViewItem("save" , R.drawable.save_48 ) ,
                new RecyclerViewItem("terminal" , R.drawable.terminal_48 ) ,
                new RecyclerViewItem("thumb up" , R.drawable.thumb_up_48 ) ,
                new RecyclerViewItem("usb" , R.drawable.usb_48 ) ,
                new RecyclerViewItem("wallet" , R.drawable.wallet_48 ) ,
                new RecyclerViewItem("add" , R.drawable.add_48 ) ,
                new RecyclerViewItem("address" , R.drawable.address_book_48 ) ,
                new RecyclerViewItem("clock" , R.drawable.clock_48 ) ,
                new RecyclerViewItem("flag" , R.drawable.flag_green_48 ) ,
                new RecyclerViewItem("global" , R.drawable.global ) ,
                new RecyclerViewItem("key" , R.drawable.key_48 ) ,
                new RecyclerViewItem("preferences" , R.drawable.preferences_48 ) ,
                new RecyclerViewItem("rating" , R.drawable.rating_48 ) ,
                new RecyclerViewItem("save" , R.drawable.save_48 ) ,
                new RecyclerViewItem("terminal" , R.drawable.terminal_48 ) ,
                new RecyclerViewItem("thumb up" , R.drawable.thumb_up_48 ) ,
                new RecyclerViewItem("usb" , R.drawable.usb_48 ) ,
                new RecyclerViewItem("wallet" , R.drawable.wallet_48 )
        };
        return items;
    }
}
