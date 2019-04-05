package com.example.irprogramtest;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.List;

public class IndexActivity extends AppCompatActivity {
    private ListView lvBooksIndex;
    private DatabaseHandler db;
    private List<HashMap<String,Object>> books;
    private boolean getFavorites;
    private static final String TAG = "HTC_EXC";
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        lvBooksIndex = findViewById(R.id.lvBooksIndex);
        db = new DatabaseHandler(this);
        getFavorites = getIntent().getExtras().getBoolean("getFavorites");

        getData();

        lvBooksIndex.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(),BookContentActivity.class);
                String mId = books.get(position).get("id").toString();
                intent.putExtra("id",mId);
                startActivity(intent);
            }
        });
    }

    public void onBtnBackClicked(View v){
        finish();
    }

    private void getData(){
        db.open();
        books = getFavorites ? db.getFavoriteBooks() : db.getBooks();
        String[] from = {"title", "author", "fav_flag", "see_flag"};
        int[] to = {R.id.tvTitleIndex, R.id.tvAuthorIndex,
                R.id.imgSetFavIndex,R.id.imgSetSeeIndex};
        SimpleAdapter adapter = new SimpleAdapter(
                getBaseContext(), books,
                R.layout.row_index_book,
                from,to);
        lvBooksIndex.setAdapter(adapter);
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
}
