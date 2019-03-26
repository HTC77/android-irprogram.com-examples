package com.example.irprogramtest;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryParser {
    private static final String TAG = "MatiMessage";

    public static List<HashMap<String, Object>> parse(String json){

        List<HashMap<String, Object>> allCats =
                new ArrayList<HashMap<String, Object>>();

        JSONObject jObj;

        try {
            jObj = new JSONObject(json);
            JSONArray jArr = jObj.getJSONArray("cat");
            for (int i = 0; i < jArr.length(); i++) {
                HashMap<String, Object> cat = new HashMap<String, Object>();
                String id="";
                String name="";
                String amount="";

                JSONObject temp = jArr.getJSONObject(i);

                id = temp.getString("id");
                name = temp.getString("name");
                amount ="["+ temp.getString("amount")+"]";

                cat.put("id", id);
                cat.put("name", name);
                cat.put("amount", amount);

                allCats.add(cat);
            }
        }catch (Exception e){
            Log.e(TAG, "Error in CategoryParser parser: -> ", e);
        }
        return allCats;
    }
}
