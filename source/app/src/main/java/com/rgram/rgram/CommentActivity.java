package com.rgram.rgram;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CommentActivity extends AppCompatActivity {

    EditText edt_title,edt_content;
    Button btn_post;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    FirebaseRecyclerAdapter<Comment,MyRecyclerViewHolder> adapter;
    FirebaseRecyclerOptions<Comment> options;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        edt_content = (EditText)findViewById(R.id.edt_content);
        edt_title = (EditText)findViewById(R.id.edt_title);
        btn_post = (Button)findViewById(R.id.btn_postcomment);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        String postId = "";
        Bundle b = intent.getExtras();
        if(b != null){
            postId = (String)b.get("postId");
            Log.d("postId is",postId);
        }

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("posts" + postId + "Comments");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                displayComment();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postComment();
            }
        });

        displayComment();
    }

    @Override
    protected void onStop() {
        if (adapter != null)
            adapter.stopListening();
        super.onStop();
    }

    private void postComment() {
        String title = edt_title.getText().toString();
        String content = edt_content.getText().toString();
        Comment cmt = new Comment(title,content);

        databaseReference.push().setValue(cmt);

        adapter.notifyDataSetChanged();
    }

    private void displayComment() {
        options = new FirebaseRecyclerOptions.Builder<Comment>()
                .setQuery(databaseReference,Comment.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Comment, MyRecyclerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, int position, @NonNull Comment model) {
                holder.txt_title.setText(model.getTitle());
                holder.txt_content.setText(model.getContent());
            }

            @NonNull
            @Override
            public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View itemview = LayoutInflater.from(getBaseContext()).inflate(R.layout.comment_item,parent,false);
                return new MyRecyclerViewHolder(itemview);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}
