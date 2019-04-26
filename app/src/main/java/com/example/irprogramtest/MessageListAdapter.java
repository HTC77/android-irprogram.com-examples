package com.example.irprogramtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.irprogramtest.other.Message;

import java.util.List;

public class MessageListAdapter extends BaseAdapter {
    private Context context;
    private List<Message> messageList;

    public MessageListAdapter(Context context, List<Message> messageList) {
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       Message message =messageList.get(position);
        LayoutInflater mInflater =
                (LayoutInflater) context.getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
        if (messageList.get(position).isSelf())
            convertView =
                    mInflater.inflate(R.layout.list_item_message_right,null);
        else
            convertView =
                    mInflater.inflate(R.layout.list_item_message_left,null);

        TextView tvFrom = convertView.findViewById(R.id.tvFrom);
        TextView tvMessage = convertView.findViewById(R.id.tvMessage);
        tvFrom.setText(message.getFromName());
        tvMessage.setText(message.getMessage());
        return convertView;
    }
}
