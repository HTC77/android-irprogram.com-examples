package com.example.irprogramtest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    private BluetoothAdapter ba;
    private Set<BluetoothDevice> pairedDevices;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.listViewPairedD);
        ba = BluetoothAdapter.getDefaultAdapter();
        if(ba == null){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Bluetooth error")
                    .setMessage("Bluetooth adapter not found!")
                    .setCancelable(true)
                    .show();
        }

    }
    public void onBtnTurnOnClick(View v){
        if (ba.isEnabled())
            Toast.makeText(this, "Bluetooth already is on", Toast.LENGTH_SHORT).show();
        else{
            startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),0);
            Toast.makeText(this, "turned on", Toast.LENGTH_SHORT).show();
        }
    }
    public void onBtnSetVisibleClick(View v){
        startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE),0);
        Toast.makeText(this, "Your device is visible", Toast.LENGTH_SHORT).show();
    }
    public void onBtnDevicesListClick(View v){
        pairedDevices = ba.getBondedDevices();
        ArrayList<String> devices = new ArrayList<String>();
        for (BluetoothDevice bt : pairedDevices)
            devices.add(bt.getName());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                devices
        );
        lv.setAdapter(adapter);
    }
    public void onBtnTurnOffClick(View v){
        ba.disable();
        Toast.makeText(this, "turned off", Toast.LENGTH_SHORT).show();
    }
}
