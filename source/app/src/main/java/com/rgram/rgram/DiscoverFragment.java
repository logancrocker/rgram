package com.rgram.rgram;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class DiscoverFragment extends Fragment {

    private Spinner spinner;
    private Button submit;
    private ListView listView;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public DiscoverFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        spinner = getView().findViewById(R.id.spinner1);
        submit = getView().findViewById(R.id.btnSubmit);
        listView = getView().findViewById(R.id.discoverView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(), R.array.tag_array, R.layout.dropdown_item);
        adapter.setDropDownViewResource(R.layout.dropdown_item);
        spinner.setAdapter(adapter);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clear listview
                listView.setAdapter(null);
                //get all posts with this tag from database
                DatabaseReference tagRef = databaseReference.child("tags").child(spinner.getSelectedItem().toString());
                tagRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<Post> posts = new ArrayList<Post>();
                        if (dataSnapshot.exists())
                        {
                            for (DataSnapshot d : dataSnapshot.getChildren())
                            {
                                Post p = d.getValue(Post.class);
                                posts.add(p);
                            }
                            //TODO first lets try displaying the images only
                            //reverse list (chronological order)
                            Collections.reverse(posts);
                            DiscoverAdapter discoverAdapter = new DiscoverAdapter(getContext(), posts);
                            listView.setAdapter(discoverAdapter);
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
