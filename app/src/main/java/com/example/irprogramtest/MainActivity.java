package com.example.irprogramtest;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper myDB;
    private EditText etName;
    private EditText etID;
    private EditText etName2;
    private EditText etID2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new MyDatabaseHelper(this);
        etName = findViewById(R.id.etName);
        etID = findViewById(R.id.etId);
        etName2 = findViewById(R.id.etName2);
        etID2 = findViewById(R.id.etId2);
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
    public void onBtnUpdateClick(View v){
        String id = etID2.getText().toString();
        String name = etName2.getText().toString();
        if(id.length() > 0 && name.length() > 0){

        }else{
            etID2.setHint("Enter a ID");
            etName2.setHint("Enter a Name");
            return;
        }
        String m ="";
        if(myDB.updateData(id,name))
            m = "Data updated";
        else
            m = "Error in updating process";
        Toast.makeText(this, m, Toast.LENGTH_SHORT).show();
    }
    public void onBtnShowAllClick(View v){
        Cursor res = myDB.showAllData();
        if (res.getCount() == 0)
            Toast.makeText(this, "There is no data", Toast.LENGTH_SHORT).show();
        else {
            StringBuffer data = new StringBuffer();
            while (res.moveToNext()){
                data.append("ID: "+res.getString(0)+'\n');
                data.append("Name: "+res.getString(1)+'\n');
                data.append('\n');
            }
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("All data");
            alert.setMessage(data.toString());
            alert.setCancelable(true);
            alert.show();
        }
    }
}
