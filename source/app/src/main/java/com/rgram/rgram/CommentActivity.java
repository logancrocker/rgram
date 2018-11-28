package com.rgram.rgram;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CommentActivity extends AppCompatActivity {

    ListView commentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_comment);

        commentList = findViewById(R.id.commentList);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        //grab all comments
        final ArrayList<Comment> comments = new ArrayList<Comment>();
        DatabaseReference commRef = FirebaseDatabase.getInstance().getReference("comments/" + id);
        commRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    comments.clear();
                    for (DataSnapshot d : dataSnapshot.getChildren())
                    {
                        Comment curr = d.getValue(Comment.class);
                        comments.add(curr);
                    }
                    final CommentAdapter commentAdapter = new CommentAdapter(getBaseContext(), comments);
                    commentList.setAdapter(commentAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
