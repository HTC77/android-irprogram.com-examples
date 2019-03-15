package com.example.irprogramtest;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText etCopy, etPaste;
    private ClipboardManager clipboardManager;
    private ClipData clipData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etCopy = findViewById(R.id.etCopy);
        etPaste =findViewById(R.id.editPaste);

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

    }

    public void onBtnCopyClicked(View v){
        String text = etCopy.getText().toString();
        clipData = ClipData.newPlainText("matn",text);
        clipboardManager.setPrimaryClip(clipData);
        Toast.makeText(this, "Text copied", Toast.LENGTH_SHORT).show();
    }

    public void onBtnPasteClicked(View v){
        ClipData mClipData = clipboardManager.getPrimaryClip();
        String text = mClipData.getItemAt(0).getText().toString();
        etPaste.setText(text);
        Toast.makeText(this, "Pasted", Toast.LENGTH_SHORT).show();
    }
}
