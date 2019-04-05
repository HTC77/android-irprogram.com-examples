package com.example.irprogramtest;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class BookContentActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private HashMap<String, Object> book;
    private TextView tvTitle, tvAuthor, tvDate;
    private WebView webViewContent;
    private ImageView imgFav, imgVisit, imgShare;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_content);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        db = new DatabaseHandler(this);
        String id = getIntent().getExtras().getString("id");

        db.open();
        book = db.getContentOfBook(id);
        db.close();
        findViews();
        tvTitle.setText(book.get("title").toString());
        tvAuthor.setText(book.get("author").toString());
        tvDate.setText(book.get("date").toString());
        String mainText = "<!DOCTYPE HTML>" +
                "<html>" +
                "<head></head>" +
                "<body dir='rtl' style='font-size:24px;" +
                "text-align:justify;'>" +
                book.get("content").toString() +
                "</body>" +
                "</html>";
        webViewContent.loadData(mainText,
                "text/html; charset=utf8",
                null);
        webViewContent.setBackgroundColor(0x00000000);
        imgFav.setImageResource(
                Integer.parseInt(book.get("fav_flag").toString())
        );
        imgVisit.setImageResource(
                Integer.parseInt(book.get("see_flag").toString())
        );
    }

    private void findViews() {
        tvTitle = findViewById(R.id.tvTitleContent);
        tvAuthor = findViewById(R.id.tvAuthorContent);
        tvDate = findViewById(R.id.tvDateContent);
        webViewContent = findViewById(R.id.webViewContent);
        imgFav = findViewById(R.id.imgFaveContent);
        imgVisit = findViewById(R.id.imgSeeContent);
        imgShare = findViewById(R.id.imgShareContent);
    }

    public void onImgFavoriteClick(View v) {
        db.open();
        int id = Integer.parseInt(book.get("id").toString());
        if (db.getBookFavoriteState(id) == 1) {
            if (db.setBookFavoriteState(id + "", 0))
                imgFav.setImageResource(R.drawable.not_favorite);
        } else {
            if (db.setBookFavoriteState(id + "", 1))
                imgFav.setImageResource(R.drawable.is_favorite);
        }
        db.close();
    }


    public void onImgSeeClick(View v) {
        db.open();
        int id = Integer.parseInt(book.get("id").toString());
        if (db.getBookVisitState(id) == 1) {
            if (db.setBookVisitState(id + "", 0))
                imgVisit.setImageResource(R.drawable.not_see);
        } else {
            if (db.setBookVisitState(id + "", 1))
                imgVisit.setImageResource(R.drawable.see);
        }
        db.close();
    }

    public void onImgShareClick(View v) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        intent.putExtra(Intent.EXTRA_TEXT, book.get("content").toString());
        startActivity(Intent.createChooser(intent, "sending"));
    }

    public void onBtnBackClick(View v) {
        finish();
    }
}