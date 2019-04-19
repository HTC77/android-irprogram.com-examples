package com.example.irprogramtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    private DatabaseHandler db;
    private ImageView imgSound, imgScreen, imgFontUp, imgFontDown;
    private TextView tvSound, tvScreen, tvFontSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        findViews();
        db = new DatabaseHandler(this);

        db.open();

        if (db.getSoundState() == 1){
            imgSound.setImageResource(R.drawable.sound_play);
            tvSound.setText(getString(R.string.sound_settings_on));
        }else{
            imgSound.setImageResource(R.drawable.sound_stop);
            tvSound.setText(getString(R.string.sound_settings_off));
        }

        if (db.getScreenState() == 1){
            imgScreen.setImageResource(R.drawable.screen_on);
            tvScreen.setText(getString(R.string.screen_light_settings_on));
        }else{
            imgScreen.setImageResource(R.drawable.screen_off);
            tvScreen.setText(getString(R.string.screen_light_settings_normal));
        }

        int fontSize = db.getFontSize();
        tvFontSize.setText(getString(R.string.font_size_settings_sample)+
                " "+ fontSize);
        tvFontSize.setTextSize(fontSize);

        db.close();
    }

    private void findViews() {
        imgSound = findViewById(R.id.imgSoundStateSettings);
        imgScreen = findViewById(R.id.imgLightStateSettings);
        imgFontDown = findViewById(R.id.imgFontSizeDownSetting);
        imgFontUp = findViewById(R.id.imgFontSizeUpSetting);
        tvSound = findViewById(R.id.tvSoundStateSettings);
        tvScreen = findViewById(R.id.tvLightStateSettings);
        tvFontSize = findViewById(R.id.tvFontSizeSettings);
    }

    public void onBtnBackOfSettingClicked(View v){
        finish();
        overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

    public void onImgSoundStateClicked(View v){
        db.open();
        if (db.getSoundState() == 1){
            if (db.setSoundState(0)){
                imgSound.setImageResource(R.drawable.sound_stop);
                tvSound.setText(R.string.sound_settings_off);
            }
        }else{
            if (db.setSoundState(1)){
                imgSound.setImageResource(R.drawable.sound_play);
                tvSound.setText(R.string.sound_settings_on);
            }
        }
        db.close();
    }
    public void onImgLightStateClicked(View v){
        db.open();
        if (db.getScreenState() == 1){
            if (db.setScreeState(0)){
                imgScreen.setImageResource(R.drawable.screen_off);
                tvScreen.setText(R.string.screen_light_settings_normal);
            }
        }else{
            if (db.setScreeState(1)){
                imgScreen.setImageResource(R.drawable.screen_on);
                tvScreen.setText(R.string.screen_light_settings_on);
            }
        }
        db.close();
    }
    public void onImgFontSizeClicked(View v){
        db.open();
        int fontSize = db.getFontSize();
        switch (v.getId()){
            case R.id.imgFontSizeUpSetting:
                fontSize++;
                break;
            case R.id.imgFontSizeDownSetting:
                fontSize--;
                break;
        }
        if (db.setFontSize(fontSize)){
            tvFontSize.setTextSize(fontSize);
            tvFontSize.setText(getString(R.string.font_size_settings_sample) +
                    " "+
                    fontSize);
        }
        db.close();
    }
}
