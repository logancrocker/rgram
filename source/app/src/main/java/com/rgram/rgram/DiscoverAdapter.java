package com.rgram.rgram;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DiscoverAdapter extends BaseAdapter {

    Context context;
    ArrayList<Post> posts;
    ViewHolder holder;

    public DiscoverAdapter(Context context, ArrayList<Post> posts)
    {
        this.context = context;
        this.posts = posts;
    }

    private class ViewHolder
    {
        ImageView profilePic;
        TextView username;
        ImageView img;
        TextView description;
        ImageView likeBtn;
        TextView likeCount;
        ImageView commBtn;
        TextView commCount;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        holder = null;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.moment, null);
            holder = new ViewHolder();
            holder.description = convertView.findViewById(R.id.imgDesc);
            holder.img = convertView.findViewById(R.id.imageView);
            holder.username = convertView.findViewById(R.id.textView);
            holder.profilePic = convertView.findViewById(R.id.moment_profile);
            holder.likeBtn = convertView.findViewById(R.id.like);
            holder.likeCount = convertView.findViewById(R.id.likenum);
            holder.commBtn = convertView.findViewById(R.id.chat);
            holder.commCount = convertView.findViewById(R.id.chatnum);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        final Post p = getItem(position);

        holder.description.setText(p.getImgDescription());
        Picasso.get()
                .load(p.getPath())
                .fit()
                .centerCrop()
                .into(holder.img);

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users/" + p.getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    User u = dataSnapshot.getValue(User.class);
                    if (!u.getPicture().isEmpty())
                    {
                        Picasso.get()
                                .load(u.getPicture())
                                .fit()
                                .centerCrop()
                                .into(holder.profilePic);
                    }
                    holder.username.setText(u.getUserName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return convertView;

    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Post getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return posts.indexOf(getItem(position));
    }
}
