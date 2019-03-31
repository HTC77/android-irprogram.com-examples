package com.example.irprogramtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.HashMap;

public class AdvertisementDetailsActivity extends AppCompatActivity {
    private ImageView imgAds;
    private TextView tvAdsTitle;
    private TextView tvAdsIntro;
    private TextView tvAdsDesc;
    private TextView tvAdsSeller;
    private TextView tvAdsEmail;
    private TextView tvAdsPhone;
    private TextView tvAdsCat;
    private TextView tvAdsDate;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement_details);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        Bundle data = getIntent().getExtras();
        HashMap<String, Object> hm =
                (HashMap<String, Object>) data.get("ads");
        findViews();
        try{
            Double.parseDouble(hm.get("image").toString());
            imgAds.setImageResource(R.drawable.no_picture);
        }catch (Exception e){
            File imgFile = new File(hm.get("image").toString());
            if (imgFile.exists()){
                Bitmap mBitmap = BitmapFactory.decodeFile(
                        imgFile.getAbsolutePath());
                imgAds.setImageBitmap(mBitmap);
            }
            else {
                imgAds.setImageResource(R.drawable.no_picture);
            }
        }

        tvAdsTitle.setText(hm.get("title").toString());
        tvAdsIntro.setText(hm.get("intro").toString());
        tvAdsDesc.setText(hm.get("desc").toString());
        tvAdsSeller.setText(hm.get("seller").toString());
        tvAdsEmail.setText(hm.get("email").toString());
        tvAdsPhone.setText(hm.get("phone").toString());
        tvAdsDate.setText(hm.get("date").toString());
        tvAdsCat.setText(hm.get("cat").toString());
    }

    private void findViews() {
        imgAds = findViewById(R.id.imgAdsDetails);
        tvAdsTitle = findViewById(R.id.tvTitleAdsDetails);
        tvAdsIntro = findViewById(R.id.tvIntroAdsDetails);
        tvAdsDesc = findViewById(R.id.tvDescAdsDetails);
        tvAdsSeller = findViewById(R.id.tvSellerAdsDetails);
        tvAdsEmail = findViewById(R.id.tvEmailAdsDetails);
        tvAdsPhone = findViewById(R.id.tvPhoneAdsDetails);
        tvAdsCat = findViewById(R.id.tvCatAdsDetails);
        tvAdsDate = findViewById(R.id.tvDateAdsDetails);
    }

    public void onBtnBackClicked(View v){
        finish();
    }
}
