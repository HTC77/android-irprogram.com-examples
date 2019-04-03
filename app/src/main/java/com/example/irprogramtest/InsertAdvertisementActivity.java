package com.example.irprogramtest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class InsertAdvertisementActivity extends AppCompatActivity {
    private String url_ads;
    private String[] id;
    private String[] name;
    private ListView lvCats;
    private EditText title, intro, desc, seller, email, phone;
    private TextView selectedCat, selectedImg;
    private ImageView imgAdsInsert;
    private String[] selectedCatInfo = new String[2];
    private static final String TAG = "HTC_EXC";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_advertisement);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        Bundle data = getIntent().getExtras();
        url_ads = data.getString("url");
        id = data.getStringArray("cat_id");
        name = data.getStringArray("cat_name");

        ArrayList<String> cats = new ArrayList<>();
        for (int i = 0; i < id.length; i++)
            cats.add(i,name[i]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,cats);

        findViews();

        lvCats.setAdapter(adapter);
        lvCats.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long mId) {
                selectedCatInfo[0] = id[position];
                selectedCatInfo[1] = name[position];
                selectedCat.setText(name[position]);
            }
        });
    }

    private void findViews() {
        lvCats = findViewById(R.id.lvCatsInsertAds);
        title =findViewById(R.id.etTitleAdsInsert);
        intro =findViewById(R.id.etIntroAdsInsert);
        desc =findViewById(R.id.etDescAdsInsert);
        seller =findViewById(R.id.etSellerAdsInsert);
        email =findViewById(R.id.etEmailAdsInsert);
        phone =findViewById(R.id.etPhoneAdsInsert);
        selectedCat = findViewById(R.id.tvSelectedCatInsertAds);
        selectedImg = findViewById(R.id.tvSelectedImgInsertAds);
        imgAdsInsert  = findViewById(R.id.imgAdsInsert);
    }

    public void onBtnBackClicked(View v){
        finish();
    }
    public void onBtnSaveClicked(View v){
        if(inspectionPassed()){
            Bitmap bitmapImage =
                    ((BitmapDrawable)imgAdsInsert.getDrawable())
                            .getBitmap();
            HashMap<String, String> mData = new HashMap<>();
            mData.put("title",title.getText().toString());
            mData.put("intro",intro.getText().toString());
            mData.put("desc",desc.getText().toString());
            mData.put("seller",seller.getText().toString());
            mData.put("email",email.getText().toString());
            mData.put("phone",phone.getText().toString());
            mData.put("cat",selectedCatInfo[0]);// 0(id) ,1(name)

            UploadImage upload= new UploadImage(mData,bitmapImage,this);
            upload.execute();
        }
    }

    private int mRequestCamera = 1;
    private Bitmap mBitmap;
    private int mRequestGallery = 2;

    public void onBtnCameraClicked(View v){
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i, mRequestCamera);
        }else
            Toast.makeText(this,
                    R.string.no_camera_error, Toast.LENGTH_LONG).show();

    }

    public void onBtnGalleryClicked(View v){
        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i,mRequestGallery);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == mRequestCamera && resultCode == RESULT_OK){
            mBitmap = (Bitmap) data.getExtras().get("data");
            showCapturedImage();
            return;
        }else if(requestCode == mRequestGallery && resultCode == RESULT_OK){
            Uri image = data.getData();
            ShowInternalImage(image);
        }else {
            Toast.makeText(this,
                    R.string.insert_ads_form_error_camera, Toast.LENGTH_LONG).show();
        }
    }

    private void ShowInternalImage(final Uri imageUri) {
        try{
            AlertDialog.Builder imageLoader = new AlertDialog.Builder(this);
            LayoutInflater inflater =
                    (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.full_screen_image,
                    (ViewGroup) findViewById(R.id.imgFullAdsDetailsRoot));
            ImageView bigImage = layout.findViewById(R.id.imgFullAdsDetails);
            bigImage.setImageURI(imageUri);
            TextView tvTitle = layout.findViewById(R.id.tvFullImgTitle);
            tvTitle.setText(R.string.captured_img_title);

            imageLoader.setPositiveButton(R.string.captured_img_submit,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            imgAdsInsert.setImageURI(imageUri);
                            selectedImg.setText(R.string.captured_img_submited);
                            dialog.dismiss();
                        }
                    });
            imageLoader.setNegativeButton(R.string.captured_img_again,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBtnGalleryClicked(null);
                        }
                    });
            imageLoader.setNeutralButton(R.string.btn_back_title,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            imageLoader.setView(layout);
            imageLoader.setCancelable(false);
            imageLoader.show();
        }catch (Exception e){
            Log.e(TAG, "showCapturedImage:--> ", e);
        }
    }

    private void showCapturedImage() {
        try{
            AlertDialog.Builder imageLoader = new AlertDialog.Builder(this);
            LayoutInflater inflater =
                    (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.full_screen_image,
                    (ViewGroup) findViewById(R.id.imgFullAdsDetailsRoot));
            ImageView bigImage = layout.findViewById(R.id.imgFullAdsDetails);
            bigImage.setImageBitmap(mBitmap);
            TextView tvTitle = layout.findViewById(R.id.tvFullImgTitle);
            tvTitle.setText(R.string.captured_img_title);

            imageLoader.setPositiveButton(R.string.captured_img_submit,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            imgAdsInsert.setImageBitmap(mBitmap);
                            selectedImg.setText(R.string.captured_img_submited);
                            dialog.dismiss();
                        }
                    });
            imageLoader.setNegativeButton(R.string.captured_img_again,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           onBtnCameraClicked(null);
                        }
                    });
            imageLoader.setNeutralButton(R.string.btn_back_title,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            imageLoader.setView(layout);
            imageLoader.setCancelable(false);
            imageLoader.show();
        }catch (Exception e){
            Log.e(TAG, "showCapturedImage:--> ", e);
        }
    }

    private boolean inspectionPassed(){
        if (title.getText().length() > 1)
            if (intro.getText().length() > 1)
                if (desc.getText().length() > 1)
                    if (seller.getText().length() > 1)
                        if (email.getText().length() > 1)
                            if (phone.getText().length() > 1)
                                if (selectedCat.getText() !=
                                        getString(R.string.insert_ads_form_select_cat))
                                    if (selectedImg.getText() !=
                                            getString(R.string.insert_ads_form_select_img))
                                            return true;
                                    else
                                        Toast.makeText(this,
                                                R.string.insert_ads_form_error_img, Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(this,
                                            R.string.insert_ads_form_error_cat, Toast.LENGTH_SHORT).show();
                            else
                                phone.setHint(R.string.insert_ads_form_error_text);
                        else
                            email.setHint(R.string.insert_ads_form_error_text);
                    else
                        seller.setHint(R.string.insert_ads_form_error_text);
                else
                    desc.setHint(R.string.insert_ads_form_error_text);
            else
                intro.setHint(R.string.insert_ads_form_error_text);
        else
            title.setHint(R.string.insert_ads_form_error_text);

        return false;
    }

    private class UploadImage extends AsyncTask<Void,Void,Boolean>{
        private HashMap<String,String> mainHm;
        private Bitmap mainImage;
        private Context context;

        public UploadImage(HashMap<String, String> mainHm, Bitmap mainImage, Context context) {
            this.mainHm = mainHm;
            this.mainImage = mainImage;
            this.context = context;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            mainImage.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            String encodedImage = Base64.encodeToString(
                    outputStream.toByteArray(),Base64.DEFAULT
            );
            HashMap<String,String> dataToSend = new HashMap<>();
            dataToSend.put("title",mainHm.get("title"));
            dataToSend.put("intro",mainHm.get("intro"));
            dataToSend.put("desc",mainHm.get("desc"));
            dataToSend.put("seller",mainHm.get("seller"));
            dataToSend.put("email",mainHm.get("email"));
            dataToSend.put("phone",mainHm.get("phone"));
            dataToSend.put("cat",mainHm.get("cat"));
            dataToSend.put("image",encodedImage);

            // TODO send to server
            try{

                return true;
            }catch (Exception e){
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            String message=getString(result == true?
                    R.string.send_result_success: R.string.send_result_failure);

            AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setCancelable(false);
            alert.setTitle(R.string.send_result_title);
            alert.setMessage(message);
            alert.setPositiveButton(R.string.btn_back_title, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //finish();
                    dialog.dismiss();
                }
            });
            alert.show();
        }
    }
}
