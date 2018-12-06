package com.rgram.rgram;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
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
    String userNameOfPost = "";



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
        ToggleButton likeBtn;
        TextView likeCount;
        TextView submitComment;
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
            holder.submitComment = convertView.findViewById(R.id.submitComment);
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
                    userNameOfPost = u.getUserName();
                    holder.username.setText(u.getUserName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



//

        holder.submitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CommentActivity.class);
                String postID = p.getPostID();
                Log.d("PostIDinDiscoverAdap", postID);
                intent.putExtra("postId",postID);
                intent.putExtra("userNameOfPost",userNameOfPost);
                Log.d("UserNameOfPost",userNameOfPost);
                context.startActivity(intent);
            }
        });
//

        //like listener
        final DatabaseReference likeRef = FirebaseDatabase.getInstance().getReference("likes/" + p.getPostID());
        //set like count
        likeRef.addValueEventListener(new ValueEventListener() {
            final ArrayList<String> likes = new ArrayList<String>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                likes.clear();
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot d : dataSnapshot.getChildren())
                    {
                        likes.add(d.getValue(String.class));
                    }
                    //set like number
                    if (likes.contains(FirebaseAuth.getInstance().getCurrentUser().getUid())) { holder.likeBtn.setChecked(true); }
                    holder.likeCount.setText(String.valueOf(likes.size()));
                }
                else
                {
                    //set likes to zero
                    holder.likeCount.setText(String.valueOf(0));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //like button functionality
        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<String> likes = new ArrayList<String>();
                likeRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                        {
                            for (DataSnapshot d : dataSnapshot.getChildren())
                            {
                                likes.add(d.getValue(String.class));
                            }
                            //set like number
                            //holder.likeCount.setText(String.valueOf(likes.size()));
                            //we have liked the post, so we must unlike it
                            if (likes.contains(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                            {
                                likes.remove(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                likeRef.setValue(likes);
                            }
                            //we have not liked it, so we must
                            else
                            {
                                likes.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                likeRef.setValue(likes);
                            }
                        }
                        else
                        {
                            //set likes to zero
                            //holder.likeCount.setText(String.valueOf(0));
                            likes.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            likeRef.setValue(likes);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
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