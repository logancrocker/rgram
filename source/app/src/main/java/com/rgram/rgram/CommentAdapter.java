package com.rgram.rgram;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {

    Context context;
    ArrayList<Comment> comments;
    ViewHolder holder;

    public CommentAdapter(Context context, ArrayList<Comment> comments)
    {
        this.context = context;
        this.comments = comments;
    }

    private class ViewHolder
    {
        TextView name;
        TextView content;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {

        holder = null;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.comment, null);

            holder = new ViewHolder();
            holder.name = convertView.findViewById(R.id.commentName);
            holder.content = convertView.findViewById(R.id.commentContent);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        final Comment c = getItem(position);

        holder.name.setText(c.getUid());
        holder.content.setText(c.getContent());

        return convertView;

    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Comment getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return comments.indexOf(getItem(position));
    }
}
