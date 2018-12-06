package com.rgram.rgram;



import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView txt_title;
    TextView txt_content;
    TextView txt_username;

    public MyRecyclerViewHolder(View itemView) {
        super(itemView);

        txt_title = (TextView)itemView.findViewById(R.id.txt_title);
        txt_content = (TextView)itemView.findViewById(R.id.txt_content);
        txt_username = (TextView)itemView.findViewById(R.id.txt_user);
    }
}