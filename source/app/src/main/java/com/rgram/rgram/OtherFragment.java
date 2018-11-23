package com.rgram.rgram;

import android.content.Context;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;


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

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO search firebase database
                queryText = searchbar.getText().toString();
                DatabaseReference users = database.child("users");
                final Query searchedUser = users.orderByChild("userName").equalTo(queryText);
                final ArrayList<User> result = new ArrayList<User>();
                searchedUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                        {
                            //Log.d("notebook", "found user");
                            //add the result user to the arraylist
                            for (DataSnapshot snapshot : dataSnapshot.getChildren())
                            {
                                User curr = snapshot.getValue(User.class);
                                result.add(curr);
                            }
                            //TODO here we need to display the info of the resulting user
                            card.setVisibility(View.VISIBLE);
                            //set post num
                            posts_num = getView().findViewById(R.id.posts_num_tv);
                            posts_num.setText(result.get(0).getPost_count().toString());
                            name = getView().findViewById(R.id.display_name_tv);
                            name.setText(result.get(0).getUserName());
                            description = getView().findViewById(R.id.description);
                            description.setText(result.get(0).getUserDescription());
                            String myuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            follow = getView().findViewById(R.id.follow_this_profile);
                            if (myuid.equals(result.get(0).getUid()))
                            {
                                follow.setVisibility(View.INVISIBLE);
                            }


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
    }
}
