package com.example.irprogramtest;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private RecyclerViewItem[] items;
    private Context context;

    public RecyclerViewAdapter( RecyclerViewItem[] dataObjects , Context c )
    {
        this.items = dataObjects;

        this.context = c;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View itemLayoutView = LayoutInflater.from(
                parent.getContext() ) .inflate(R.layout.recycler_view_item, null
        );
        final TextView tvTitle = itemLayoutView.findViewById(R.id.item_title);

        itemLayoutView.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    itemLayoutView.setBackgroundColor( Color.GRAY );

                    Toast.makeText(context, tvTitle.getText(), Toast.LENGTH_SHORT).show();

                    itemLayoutView.setBackgroundColor( Color.TRANSPARENT );
                }
            }
        );

        ViewHolder viewHolder = new ViewHolder( itemLayoutView );

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.txtViewTitle.setText( items[position].getTitle() );
        holder.imgViewIcon.setImageResource(items[position].getImage());
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txtViewTitle;
        public ImageView imgViewIcon;

        public ViewHolder(View itemLayoutView)
        {
            super(itemLayoutView);
            txtViewTitle = itemLayoutView.findViewById(R.id.item_title);
            imgViewIcon = itemLayoutView.findViewById(R.id.item_icon);
        }
    }
}
