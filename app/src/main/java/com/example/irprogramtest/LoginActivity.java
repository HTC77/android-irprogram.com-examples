package com.example.irprogramtest;

import android.media.RingtoneManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private TextView tvResult;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        url = getIntent().getExtras().getString("url");
    }

    private void findViews() {
        etUsername = findViewById(R.id.etUsername);
        etPassword =findViewById(R.id.etPassword);
        tvResult = findViewById(R.id.tvResult);
    }

    public void onBtnLoginClicked(View v){
        final String uname = etUsername.getText().toString();
        final String upass = etPassword.getText().toString();
        if (!uname.isEmpty()){
            if (!upass.isEmpty()){
                playBeep();
                RequestQueue queue = Volley.newRequestQueue(this);
                StringRequest mRequest = new StringRequest(
                    Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jObj = new JSONObject(response);
                                String state = jObj.getString("state");
                                tvResult.setText(state);
                                playBeep();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            showToast("Error in Connecting to server");
                            playBeep();
                        }
                    }
                ){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("uname",uname);
                        params.put("upass",upass);
                        return params;
                    }
                };
                queue.add(mRequest);
            }else{
                showToast("Enter Password");
                playBeep();
            }
        }else  {
            showToast("Enter Username");
            playBeep();
        }
    }
    private void showToast(final String message){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),
                        message, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void playBeep(){
        RingtoneManager.getRingtone(
                this,RingtoneManager.getDefaultUri(
                        RingtoneManager.TYPE_NOTIFICATION)).play();
    }
}
