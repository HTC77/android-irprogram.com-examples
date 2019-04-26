package com.example.irprogramtest.other;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONException;
import org.json.JSONObject;

public class Utils {
    private Context context;
    private SharedPreferences sharedPref;
    private final static String KEY_SHARED_PREF = "ANDROID_WEB_CHAT";
    private final static String KEY_SESSION_ID = "sessionId";
    private final static String FLAG_MESSAGE = "message";

    public Utils(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences(
                KEY_SHARED_PREF,Context.MODE_PRIVATE);
    }

    public void storeSessionId(String sessionId){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_SESSION_ID,sessionId);
        editor.apply();
    }

    public String getSessionId(){
        return sharedPref.getString(KEY_SESSION_ID,null);
    }

    public String getMessageJSON(String message){
        String json = null;

        try{
            JSONObject jObj = new JSONObject();
            jObj.put("flag",FLAG_MESSAGE);
            jObj.put("sessionId",getSessionId());
            jObj.put("message", message);
            json = jObj.toString();
        }catch (JSONException e){
            e.printStackTrace();
        }
        return json;
    }
}
