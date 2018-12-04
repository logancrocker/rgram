package com.rgram.rgram;

import android.content.Context;

import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import static android.support.v7.widget.RecyclerView.*;

public class momentAdapter extends BaseAdapter {
    private List<Messages> Datas;
    private Context mContext;

//    List<String> lists=new ArrayList<String>();
//    int newlikenum;
//    Context context;
    public ViewHolder viewHolder;
    public momentAdapter(List<Messages> Datas, Context mContext){
        this.Datas=Datas;
        this.mContext=mContext;
    }

    @Override
    public int getCount() {
        return Datas.size();
    }

    @Override
    public Object getItem(int position) {

        return Datas.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        if(view==null) {
            viewHolder = new ViewHolder();
            view=LayoutInflater.from(mContext).inflate(R.layout.moment, null);
            viewHolder.iv=(ImageView)view.findViewById(R.id.imageView);   //post
            viewHolder.tv=(TextView)view.findViewById(R.id.textView);
            viewHolder.mp=(ImageView)view.findViewById(R.id.moment_profile);   //avatars
            viewHolder.desc=(TextView)view.findViewById(R.id.imgDesc);  //describe
            viewHolder.like=(ImageView) view.findViewById(R.id.like);
            viewHolder.likenum=(TextView)view.findViewById(R.id.likenum);


            view.setTag(viewHolder);

        }else{
            viewHolder=(ViewHolder)view.getTag();
        }

          String urlAvatars = Datas.get(position).getAvatars().toString();
        Log.d("tag", "avatars-url"+Datas.get(position).getAvatars());
        Picasso.get()
                .load(urlAvatars)
                .fit()
                .centerCrop()
                .transform(new PicassoCircleTransformation())
                .into(viewHolder.mp);
        viewHolder.tv.setText(Datas.get(position).getName());
////        Log.d("tag", "messages"+getItem(position));
        String urlPost=Datas.get(position).getPost().toString();
        Log.d("tag", "post-url"+Datas.get(position).getPost());
        Picasso.get()
                .load(urlPost)
                .fit()
        //        .centerCrop()
                .into(viewHolder.iv);

        Log.d("notebook", "likenum "+Datas.get(position).getLikenum());
        viewHolder.desc.setText(Datas.get(position).getPostdes());
       viewHolder.likenum.setText(String.valueOf(Datas.get(position).getLikenum()));
       viewHolder.chatnum.setText(String.valueOf(Datas.get(position).getChatnum()));
//        viewHolder.mp
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
    public TextView desc;

    public ImageView like;
    public ImageView chat;

    public TextView likenum;
    public TextView chatnum;

}
//    public void addData(String text) {
//        if (Datas != null)
//            Datas.add(text);// 添加数据
//    }


}
