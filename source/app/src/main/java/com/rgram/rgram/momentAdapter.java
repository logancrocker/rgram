package com.rgram.rgram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class momentAdapter extends BaseAdapter {

    List<String> lists=new ArrayList<String>();

    Context context;
    public momentAdapter(Context context,List<String> lists){
        this.context=context;
        this.lists=lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {

        return null;
    }

    @Override
    public long getItemId(int position) {

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=LayoutInflater.from(context).inflate(R.layout.moment, null);

        ImageView iv=(ImageView)view.findViewById(R.id.imageView);
        TextView tv=(TextView)view.findViewById(R.id.textView);
        ImageView mp=(ImageView)view.findViewById(R.id.moment_profile);

        ImageView like=(ImageView)view.findViewById(R.id.like);
        TextView likenum=(TextView)view.findViewById(R.id.likenum);
        ImageView chat=(ImageView)view.findViewById(R.id.chat);
        TextView chatnum=(TextView)view.findViewById(R.id.chatnum);
        tv.setText("peter"+","+lists.get(position));

        return view;
    }
}
