package com.rgram.rgram;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class OtherFragment extends Fragment  {

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    EditText searchbar;
    Button searchbutton;
    String queryText;
    CardView card;
    TextView posts_num;
    TextView followers_num;
    TextView following_num;
    TextView name;
    TextView description;
    TextView follow;
    Button visit_profile;
    CircleImageView profile_image;

    public OtherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_other, container, false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        searchbar = getView().findViewById(R.id.search_bar);
        searchbutton = getView().findViewById(R.id.search_button);
        card = getView().findViewById(R.id.cardview);
        card.setVisibility(View.INVISIBLE);
        visit_profile = getView().findViewById(R.id.visit_profile);
        visit_profile.setVisibility(View.INVISIBLE);
        profile_image = getView().findViewById(R.id.profile_image);

        final ArrayList<User> result = new ArrayList<>();

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryText = searchbar.getText().toString();
                final DatabaseReference users = database.child("users");
                Query searchedUser = users.orderByChild("userName").equalTo(queryText);
                searchedUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                        {
                            //add the result user to the arraylist
                            //this is redundant but oh well
                            result.clear();
                            for (DataSnapshot d : dataSnapshot.getChildren())
                            {
                                User curr = d.getValue(User.class);
                                result.add(curr);
                            }
                            //TODO here we need to display the info of the resulting user
                            //load profile picture
                            profile_image.setImageResource(R.drawable.user);
                            if (!result.get(0).getPicture().isEmpty())
                            {
                                Picasso.get().load(result.get(0).getPicture()).fit().centerCrop().into(profile_image);
                            }
                            //show the card and visit button
                            card.setVisibility(View.VISIBLE);
                            visit_profile.setVisibility(View.VISIBLE);
                            //set post num
                            posts_num = getView().findViewById(R.id.posts_num_tv);
                            //Log.d("notebook", String.valueOf(result.get(0).getPosts().size()));
                            if (result.get(0).getPosts() != null) { posts_num.setText(String.valueOf(result.get(0).getPosts().size())); }
                            else { posts_num.setText(String.valueOf(0)); }
                            //set following num
                            following_num = getView().findViewById(R.id.following_num_tv);
                            if (result.get(0).getFollowing() != null) { following_num.setText(String.valueOf(result.get(0).getFollowing().size())); }
                            else { following_num.setText(String.valueOf(0)); }
                            //set name
                            name = getView().findViewById(R.id.display_name_tv);
                            name.setText(result.get(0).getUserName());
                            //set description
                            description = getView().findViewById(R.id.description);
                            description.setText(result.get(0).getUserDescription());
                            //get our uid
                            final String myuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            //hide follow button if the result is ourself
                            follow = getView().findViewById(R.id.follow_this_profile);
                            follow.setVisibility(View.VISIBLE);
                            if (myuid.equals(result.get(0).getUid()))
                            {
                                follow.setVisibility(View.INVISIBLE);
                            }

                            //reference to ourself
                            //this decides how to present the follow button
                            final DatabaseReference myselfRef = database.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                            myselfRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    //get our user info
                                    final User myself = dataSnapshot.getValue(User.class);
                                    //if we follow the user, button must be gray and say unfollow
                                    if (myself.getFollowing() != null && myself.getFollowing().contains(result.get(0).getUid()))
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
                                            ArrayList<String> newFollowing = new ArrayList<String>();
                                            //this click listener must perform based on whether we follow the user
                                            //first check if we follow the user
                                            //this case is for when we do not follow anyone
                                            if (myself.getFollowing() == null)
                                            {
                                                newFollowing.add(result.get(0).getUid());
                                                myself.setFollowing(newFollowing);
                                                myselfRef.setValue(myself);
                                            }
                                            //we dont follow them, so we add them to the list
                                            else if (!myself.getFollowing().contains(result.get(0).getUid()))
                                            {
                                                newFollowing = myself.getFollowing();
                                                newFollowing.add(result.get(0).getUid());
                                                myself.setFollowing(newFollowing);
                                                myselfRef.setValue(myself);
                                            }
                                            //we do follow them, so remove from the list
                                            else
                                            {
                                                newFollowing = myself.getFollowing();
                                                newFollowing.remove(result.get(0).getUid());
                                                myself.setFollowing(newFollowing);
                                                myselfRef.setValue(myself);
                                            }
                                            //TODO we must add ourself to their list of followers
                                            //get reference to other person
                                            Log.d("notebook", result.get(0).getUid());
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        else
                        {
                            Log.d("notebook", "no user with username " + queryText + " exists");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        visit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //we will pass the uid to the new activity so we can populate the profile page
                Intent newIntent = new Intent(getContext(), ViewProfileActivity.class);
                newIntent.putExtra("uid", result.get(0).getUid());
                startActivity(newIntent);
            }
        });
    }
}
