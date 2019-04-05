package com.example.irprogramtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private Context mContext;
    private static String DB_PATH;
    private static int DB_VERSION = 1;
    private final static String DB_NAME = "ebook_db.db";
    private final static String TBL_BOOKS = "books";
    private final static String TAG = "HTC_EXC";
    private final static String TBL_SETTINGS = "settings";
    private SQLiteDatabase db;

    public DatabaseHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
        DB_PATH = mContext.getCacheDir().getPath() + "/" + DB_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private boolean dbExists(){
        File f = new File(DB_PATH);
        return f.exists() ? true : false;
    }

    private boolean copyDb(){
        try {
            FileOutputStream outputStream = new FileOutputStream(DB_PATH);
            InputStream inputStream = mContext.getAssets().open(DB_NAME);
            byte[] buffer = new byte[1024];
            int ch;
            while ((ch = inputStream.read(buffer)) >0)
                outputStream.write(buffer,0,ch);
            outputStream.flush();
            outputStream.close();
            inputStream.close();
            return true;
        }catch (Exception e){
            Log.e(TAG, "copyDb DatabaseHandler: ", e);
            return false;
        }
    }

    public void open(){
        if (dbExists()){
            try {
                File temp = new File(DB_PATH);
                db = SQLiteDatabase.openDatabase(
                        temp.getAbsolutePath(),
                        null,
                        SQLiteDatabase.OPEN_READWRITE
                );
            }catch (Exception e){
                Log.e(TAG, "open DatabaseHandler: ", e);
            }
        }else {
            if (copyDb())
                open();
        }
    }

    @Override
    public synchronized void close() {
        db.close();
    }

    public List<HashMap<String,Object>> getBooks(){
        Cursor result = db.rawQuery("SELECT * FROM "+TBL_BOOKS,
                null);
        List<HashMap<String,Object>> allData = new ArrayList<>();
        while (result.moveToNext()){
            HashMap<String,Object> temp = new HashMap<>();
            temp.put("id",result.getString(0));
            temp.put("title",result.getString(1));
            temp.put("author",result.getString(3));
            temp.put("fav_flag",result.getString(5).equals("1")?
                    R.drawable.is_favorite:
                    R.drawable.not_favorite);
            temp.put("see_flag",result.getString(6).equals("1")?
                    R.drawable.see:
                    R.drawable.not_see);

            allData.add(temp);
        }
        return allData;
    }

    public HashMap<String,Object> getContentOfBook(String id){
        Cursor result = db.rawQuery(
          "SELECT * FROM " + TBL_BOOKS + " WHERE id = '"+id+"' ",null);

        result.moveToFirst();

        HashMap<String,Object> allData = new HashMap<>();

        allData.put("id",result.getString(0));
        allData.put("title",result.getString(1));
        allData.put("content",result.getString(2));
        allData.put("author",result.getString(3));
        allData.put("date",result.getString(4));
        allData.put("fav_flag",result.getString(5).equals("1")?
                R.drawable.is_favorite:
                R.drawable.not_favorite);
        allData.put("see_flag",result.getString(6).equals("1")?
                R.drawable.see:
                setBookVisitState(id,1) ? R.drawable.see : R.drawable.not_see);

        return allData;
    }

    public boolean setBookVisitState(String id, int state){
        ContentValues cv = new ContentValues();
        cv.put("see_flag",state);
        long result = db.update(TBL_BOOKS, cv, "id = ?",new String[]{id});
        return result < 0 ? false : true;
    }
    public boolean setBookFavoriteState(String id, int state){
        ContentValues cv = new ContentValues();
        cv.put("fav_flag",state);
        long result = db.update(TBL_BOOKS, cv, "id = ?",new String[]{id});
        return result < 0 ? false : true;
    }

    public int getBookFavoriteState(int id){
        Cursor result = db.rawQuery(
          "SELECT * FROM "+TBL_BOOKS+" WHERE id = '"+id+"'",null
        );
        result.moveToFirst();
        return Integer.parseInt(result.getString(5));
    }
    public int getBookVisitState(int id){
        Cursor result = db.rawQuery(
                "SELECT * FROM "+TBL_BOOKS+" WHERE id = '"+id+"'",null
        );
        result.moveToFirst();
        return Integer.parseInt(result.getString(6));
    }
}
