package com.example.irprogramtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public final static String DB_NAME = "student.db";
    public final static String TBL_NAME = "stu_info";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE "+ TBL_NAME +
                "( Id INTEGER PRIMARY KEY AUTOINCREMENT , Name TEXT )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_NAME);
        onCreate(db);
    }

    boolean insertData(String name){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Name",name);
        return db.insert(TBL_NAME,null,cv) == -1 ? false : true;
    }
    boolean deleteData(String id){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TBL_NAME,"Id = ?",new String[] {id}) < 1 ? false : true;
    }
    boolean updateData(String id,String name){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Id",id);
        cv.put("Name",name);
        return db.update(TBL_NAME,cv,"Id = ?",new String[] {id}) < 1 ? false : true;
    }
    public Cursor showAllData(){
        return getReadableDatabase().rawQuery("SELECT * FROM "+TBL_NAME,null);
    }
}
