package com.example.irprogramtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText etX, etY, etXMov, etYMov;
    private TextView tvTouch;
    float xAxis = 0f;
    float yAxis = 0f;
    float lastXAxis = 0f;
    float lastYAxis = 0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    private void findViews() {
        tvTouch = findViewById(R.id.tvTouch);
        etX =findViewById(R.id.etXAxis);
        etY =findViewById(R.id.etYAxis);
        etXMov =findViewById(R.id.etXMovement);
        etYMov =findViewById(R.id.etYMovement);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                lastXAxis = ev.getX();
                lastYAxis = ev.getY();
                etX.setText(Float.toString(lastXAxis));
                etY.setText(Float.toString(lastYAxis));
                break;
            case MotionEvent.ACTION_MOVE:
                float tempX = ev.getX() - lastXAxis;
                float tempY = ev.getY() - lastYAxis;
                xAxis += tempX;
                yAxis += tempY;
                etXMov.setText(Float.toString(xAxis));
                etYMov.setText(Float.toString(yAxis));
                break;
        }
       return true;
    }
}
