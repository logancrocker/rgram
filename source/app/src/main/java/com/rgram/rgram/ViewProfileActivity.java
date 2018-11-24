package com.rgram.rgram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewProfileActivity extends AppCompatActivity {

    //database instance
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    TextView follow, posts_num, following_num, followers_num, display_name, description;
    CircleImageView user_profile_image;
    GridView gridView;
    ImageView iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        Intent intent = getIntent();
        final String uid = intent.getStringExtra("uid");
        //Log.d("notebook", uid);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

        //grab layout components
        //grab all layout components
        follow = findViewById(R.id.follow_this_profile);
        posts_num = findViewById(R.id.posts_num_tv);
        following_num = findViewById(R.id.following_num_tv);
        followers_num = findViewById(R.id.followers_num_tv);

        display_name = findViewById(R.id.display_name_tv);
        description = findViewById(R.id.description);

        user_profile_image = findViewById(R.id.profile_image);
        gridView = findViewById(R.id.images_grid_layout);
        iv = findViewById(R.id.img_large);

        if (uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
        {
            follow.setVisibility(View.INVISIBLE);
        }

        //setting basic info
        ref.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    final User user = dataSnapshot.getValue(User.class);
                    description.setText(user.getUserDescription());
                    display_name.setText(user.getUserName());
                    if (!user.getPicture().isEmpty())
                    {
                        Picasso.get().load(user.getPicture()).fit().centerCrop().into(user_profile_image);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //get reference to this user
        DatabaseReference meRef = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(uid);

        //set numbers and display posts
        meRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final ArrayList<String> urls = new ArrayList<String>();

                User myself = dataSnapshot.getValue(User.class);

                if (myself.getFollowing() != null) { following_num.setText(String.valueOf(myself.getFollowing().size())); }
                if (myself.getPosts() != null) { posts_num.setText(String.valueOf(myself.getPosts().size())); }
                if (myself.getPosts() != null)
                {
                    ArrayList<String> myPosts = myself.getPosts();
                    //loop through all posts
                    Collections.reverse(myPosts);
                    for (String postID : myPosts) {
                        //for each post ID, add its download url to the grid view
                        //Log.d("notebook", postID);
                        database.child("posts").child(postID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Post currPost = dataSnapshot.getValue(Post.class);
                                //Log.d("notebook", currPost.getImgDescription());
                                //TODO try and display in the right order
                                urls.add(currPost.getPath());
                                GridAdapter gridAdapter = new GridAdapter(getBaseContext(), urls);
                                gridView.setAdapter(gridAdapter);

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    //reference to ourself
                    //follow button functionality
                    final DatabaseReference myselfRef = database.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    myselfRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //get our user info
                            final User myself = dataSnapshot.getValue(User.class);
                            //if we follow the user, button must be gray and say unfollow
                            if (myself.getFollowing() != null && myself.getFollowing().contains(uid))
                            {
                                follow.setBackgroundColor(getResources().getColor(R.color.gray));
                                follow.setText(getResources().getString(R.string.Unfollow));
                            }
                            //if we dont follow them, show follow appearance
                            else
                            {
                                follow.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                follow.setText(getResources().getString(R.string.Follow));
                            }

                            follow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (!uid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
                                    {
                                        ArrayList<String> newFollowing = new ArrayList<String>();
                                        //this click listener must perform based on whether we follow the user
                                        //first check if we follow the user
                                        //this case is for when we do not follow anyone
                                        if (myself.getFollowing() == null)
                                        {
                                            newFollowing.add(uid);
                                            myself.setFollowing(newFollowing);
                                            myselfRef.setValue(myself);
                                        }
                                        //we dont follow them, so we add them to the list
                                        else if (!myself.getFollowing().contains(uid))
                                        {
                                            newFollowing = myself.getFollowing();
                                            newFollowing.add(uid);
                                            myself.setFollowing(newFollowing);
                                            myselfRef.setValue(myself);
                                        }
                                        //we do follow them, so remove from the list
                                        else
                                        {
                                            newFollowing = myself.getFollowing();
                                            newFollowing.remove(uid);
                                            myself.setFollowing(newFollowing);
                                            myselfRef.setValue(myself);
                                        }
                                        //TODO we must add ourself to their list of followers
                                    }
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
