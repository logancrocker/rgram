package com.rgram.rgram;

import android.app.ActionBar;
import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private ArrayList<String> urls;
    private Context context;

    public GridAdapter(Context context, ArrayList<String> urls)
    {
        this.urls = urls;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        int gridWidth = context.getResources().getDisplayMetrics().widthPixels;
        int imgWidth = (gridWidth / 3) - 30;
        ImageView imageView;
        if (convertView == null)
        {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(imgWidth, imgWidth));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(5,5,5,5);
        }
        else
        {
            imageView = (ImageView) convertView;
        }
        String url = getItem(position).toString();
        Picasso.get()
                .load(url)
                .fit()
                .centerCrop()
                .into(imageView);
        return imageView;
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public Object getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
