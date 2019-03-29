package com.example.irprogramtest;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdvertisementParser {
    private static final String TAG = "MatiMessage";

    public List<HashMap<String, Object>> parse(String json){
        List<HashMap<String, Object>> allAds = new ArrayList<>();
        try {
            JSONObject jObj = new JSONObject(json);
            JSONArray jArr = jObj.getJSONArray("ads");
            for (int i = 0; i < jArr.length(); i++) {
                JSONObject temp = jArr.getJSONObject(i);
                HashMap<String,Object> ads = new HashMap<>();

                ads.put("id", temp.getString("id"));
                ads.put("title", temp.getString("title"));
                ads.put("intro", temp.getString("intro"));
                ads.put("desc", temp.getString("desc"));
                ads.put("imagePath", temp.getString("image"));
                ads.put("seller", temp.getString("seller"));
                ads.put("email", temp.getString("email"));
                ads.put("phone", temp.getString("phone"));
                ads.put("date", temp.getString("date"));
                ads.put("cat", temp.getString("cat"));

                allAds.add(ads);
            }
        }catch (Exception e){
            Log.e(TAG, "parse: Error Advertisement parser --> ", e);
        }
        return allAds;
    }
}
