package com.example.irprogramtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tvLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLink =findViewById(R.id.tvLink);
        tvLink.setClickable(true);
        tvLink.setMovementMethod(LinkMovementMethod.getInstance());
        String htmlText = "<a href='http://irprogram.com'> irProgram </a>";
        tvLink.setText(Html.fromHtml(htmlText));
    }
}
