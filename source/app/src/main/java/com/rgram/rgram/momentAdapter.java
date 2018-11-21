package com.rgram.rgram;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.*;

public class momentAdapter extends BaseAdapter {

    List<String> lists=new ArrayList<String>();
    int newlikenum;
    Context context;
    public ViewHolder viewHolder;
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
    public View getView(final int position, View view, ViewGroup parent) {
        viewHolder=null;
        if(view==null) {
            viewHolder = new ViewHolder();
            view=LayoutInflater.from(context).inflate(R.layout.moment, null);
            viewHolder.iv=(ImageView)view.findViewById(R.id.imageView);
            viewHolder.tv=(TextView)view.findViewById(R.id.textView);
            viewHolder.mp=(ImageView)view.findViewById(R.id.moment_profile);

            viewHolder.like=(ImageView)view.findViewById(R.id.like);
            viewHolder.likenum=(TextView)view.findViewById(R.id.likenum);
            viewHolder.chat=(ImageView)view.findViewById(R.id.chat);
            viewHolder.chatnum=(TextView)view.findViewById(R.id.chatnum);


            view.setTag(viewHolder);

        }else{
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.tv.setText("peter"+","+lists.get(position));


      final View finalView;
      finalView=view;
        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
   //             mOnItemaddlikeListener.onAddlikeClick(position,finalView);
//                newlikenum=Integer.parseInt(viewHolder.likenum.getText().toString());
//                newlikenum++;
//                viewHolder.likenum.setText(""+newlikenum);

            }
        });



        return view;
    }

    public interface OnItemaddlikeListener {
        //void onAddlikeClick(int i,View view);


    }

    private OnItemaddlikeListener mOnItemaddlikeListener;

    public void setOnaddlikeClickListener(OnItemaddlikeListener mOnItemaddlikeListener) {
        this.mOnItemaddlikeListener = mOnItemaddlikeListener;
    }


class ViewHolder{

    public ImageView iv;
    public TextView tv;
    public ImageView mp;

    public ImageView like;
    public ImageView chat;

    public TextView likenum;
    public TextView chatnum;

}
    public void addData(String text) {
        if (lists != null)
            lists.add(text);// 添加数据
    }


}
