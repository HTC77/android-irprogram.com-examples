package com.example.irprogramtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper myDB;
    private EditText etName;
    private EditText etID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new MyDatabaseHelper(this);
        etName = findViewById(R.id.etName);
        etID = findViewById(R.id.etId);
    }

    public void onBtnSaveClick(View v){
        String temp = etName.getText().toString();
        if(temp.length() < 1){
            etName.setHint("Enter a name");
            return;
        }
        String m ="";
        if(myDB.insertData(temp))
            m = "Data inserted";
        else
            m = "Error in inserting process";
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
    }
    public void onBtnDeleteClick(View v){
        String temp = etID.getText().toString();
        if(temp.length() < 1){
            etID.setHint("Enter a ID");
            return;
        }
        String m ="";
        if(myDB.deleteData(temp))
            m = "Data deleted";
        else
            m = "Error in deleting process";
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
    }
}
