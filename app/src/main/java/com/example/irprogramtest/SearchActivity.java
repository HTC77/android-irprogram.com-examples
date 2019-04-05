package com.example.irprogramtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;

import java.util.HashMap;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private RadioGroup radioGroup;
    private RadioButton rbTitle;
    private RadioButton rbAuthor;
    private RadioButton rbContent;
    private ListView lvResult;
    private DatabaseHandler db;
    private EditText etSearch;
    private List<HashMap<String,Object>> searchResult;
    private final String BY_TITLE = "title";
    private final String BY_CONTENT = "content";
    private final String BY_AUTHOR = "author";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        findViews();
        etSearch.setText("متیما");
        db=new DatabaseHandler(this);

        lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(),BookContentActivity.class);
                String mId = searchResult.get(position).get("id").toString();
                intent.putExtra("id",mId);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        radioGroup = findViewById(R.id.rgSearchCriteria);
        rbTitle = findViewById(R.id.rbTitleSearch);
        rbAuthor = findViewById(R.id.rbAuthorSearch);
        rbContent = findViewById(R.id.rbContentSearch);
        etSearch = findViewById(R.id.etSearchTextSearch);
        lvResult = findViewById(R.id.lvSearchResultSearch);
    }

    public void onBtnBackFromSearchClicked(View v){
        finish();
    }
    public void onBtnFindClicked(View v){
        if (etSearch.getText().length()<1){
            Toast.makeText(this, getString(R.string.search_error), Toast.LENGTH_LONG).show();
            return;
        }

        lvResult.setAdapter(null);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        String searchBy ;
        if (selectedId == rbTitle.getId())
            searchBy = BY_TITLE;
        else if(selectedId == rbAuthor.getId())
            searchBy = BY_AUTHOR;
        else
            searchBy = BY_CONTENT;

        String searchKey = etSearch.getText().toString().trim();// clearing around spaces
        String[] keys = searchKey.split("\\s+"); // separate words by cleaning inside spaces

        StringBuilder queryCondition = new StringBuilder();
        queryCondition.append(searchBy + " LIKE '%" + keys[0] + "%'");

        for (int i = 1; i< keys.length; ++i)
             queryCondition.append(" OR " + searchBy + " LIKE '%" + keys[i] + "%'");

        db.open();
        searchResult = db.getResultOfSearch(queryCondition.toString());
        db.close();
        if (searchResult.size() < 1) {
            Toast.makeText(this, getString(R.string.search_not_found),
                    Toast.LENGTH_LONG).show();
            return;
        }
        String[] from = {"title", "author", "fav_flag", "see_flag"};
        int[] to = {R.id.tvTitleIndex, R.id.tvAuthorIndex,
                R.id.imgSetFavIndex,R.id.imgSetSeeIndex};
        SimpleAdapter adapter = new SimpleAdapter(
                getBaseContext(), searchResult,
                R.layout.row_index_book,
                from,to);
        lvResult.setAdapter(adapter);
    }
}
