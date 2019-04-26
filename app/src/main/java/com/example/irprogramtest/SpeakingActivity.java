package com.example.irprogramtest;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.irprogramtest.other.Message;
import com.example.irprogramtest.other.Utils;
import com.example.irprogramtest.other.WsConfig;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SpeakingActivity extends AppCompatActivity {

    private WebSocket client = null;
    private EditText etMessage;
    private MessageListAdapter adapter;
    private List<Message> messages;
    private ListView lvChat;
    private Utils mUtils;
    private String name;
    private final static String TAG_SELF = "self";
    private final static String TAG_NEW = "new";
    private final static String TAG_EXIT = "exit";
    private final static String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaking);
        etMessage = findViewById(R.id.etMessage);
        lvChat = findViewById(R.id.lvMessages);

        mUtils = new Utils(this);

        name = getIntent().getExtras().getString("name");
        messages = new ArrayList<Message>();
        adapter = new MessageListAdapter(this,messages);
        lvChat.setAdapter(adapter);

        AsyncHttpClient.getDefaultInstance().websocket(
                WsConfig.WEB_SOCKET_URL + name, null,
                new AsyncHttpClient.WebSocketConnectCallback() {
                    @Override
                    public void onCompleted(Exception ex, WebSocket webSocket) {
                        if (ex != null){
                            showToast(ex.toString());
                            return;
                        }
                        client = webSocket;
                        client.setStringCallback(new WebSocket.StringCallback() {
                            @Override
                            public void onStringAvailable(String s) {
                                parseMessage(s);
                            }
                        });
                        client.setDataCallback(new DataCallback() {
                            @Override
                            public void onDataAvailable(DataEmitter emitter, ByteBufferList bb) {
                                parseMessage(byteToHex(bb.getAllByteArray()));
                            }
                        });

                        CompletedCallback disconnect = new CompletedCallback() {
                            @Override
                            public void onCompleted(Exception ex) {
                                showToast("Disconnect Because of--> "+ex.toString());
                                mUtils.storeSessionId(null);
                            }
                        };
                        client.setClosedCallback(disconnect);
                        client.setEndCallback(disconnect);
                    }
                }
        );
    }

    private String byteToHex(byte[] byteArray) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int i = 0; i < byteArray.length; i++) {
            int v = byteArray[i] & 0xFF;
            hexChars[i*2] = hexArray[v >>> 4];
            hexChars[i*2+1] = hexArray[v & 0x0F];
        }
        return new String( hexChars );
    }

    public void parseMessage( final String str )
    {
        try
        {
            JSONObject jobj = new JSONObject(str);

            String flag = jobj.getString("flag");

            if( flag.equalsIgnoreCase(TAG_SELF) )
            {
                mUtils.storeSessionId(
                    jobj.getString("sessionId")
                );
            }
            else if( flag.equalsIgnoreCase(TAG_NEW) )
            {
                showToast(
                    jobj.getString("name") + " " + jobj.getString("message") +
                        ". Online peoples: " + jobj.getString("onlineCount")
                );
            }
            else if( flag.equalsIgnoreCase(TAG_EXIT) )
            {
                showToast(
                    jobj.getString("name") + " " + jobj.getString("message")
                );
            }
            else if( flag.equalsIgnoreCase(TAG_MESSAGE) )
            {
                String fromName = name;
                String message = jobj.getString("message");
                String sessionId = jobj.getString("sessionId");
                boolean isSelf = true;

                if( ! sessionId.equals( mUtils.getSessionId() ) )
                {
                    fromName = jobj.getString("name");
                    isSelf = false;
                }

                Message m = new Message(fromName, message, isSelf);

                appendMessage(m);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void appendMessage( final Message m )
    {
        runOnUiThread(
            new Runnable() {
                @Override
                public void run() {
                    messages.add( m );

                    adapter.notifyDataSetChanged();

                    playBeep();
                }
            }
        );
    }

    public void playBeep()
    {
        try
        {
            Uri notification = RingtoneManager.getDefaultUri(
                    RingtoneManager.TYPE_NOTIFICATION
            );

            Ringtone r = RingtoneManager.getRingtone(
                    getApplicationContext(), notification
            );

            r.play();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    public void onBtnSendClick(View v){
        sendMessageToServer(
            mUtils.getMessageJSON(
                etMessage.getText().toString()
            ));
        etMessage.setText("");
    }

    public void sendMessageToServer(String message){
        if( client != null && client.isOpen() )
        {
            client.send( message );
        }
    }

    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
