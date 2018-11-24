package com.rgram.rgram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {
    TextView edit_image;
    final int CHANGE_PROFILE_IMAGE = 1;
    CircleImageView profile_image;
    TextView submit;
    TextView description;
    TextView username;
    String path;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    String myuid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        edit_image = findViewById(R.id.edit_image);

        profile_image = findViewById(R.id.profile_image);

        description = findViewById(R.id.desc_et);

        username = findViewById(R.id.username_et);

        submit = findViewById(R.id.done_edit);

        edit_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNewProfileImage();
            }
        });

        ref.child("users").child(myuid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final User me = dataSnapshot.getValue(User.class);

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Log.d("notebook", description.getText().toString());
                        if (!description.getText().toString().isEmpty()) { me.setUserDescription(description.getText().toString()); }
                        //dont want to set username if its null
                        if (!username.getText().toString().isEmpty()) { me.setUserName(username.getText().toString()); }

                        //TODO change profile pic

                        //push our changes to database
                        ref.child("users").child(myuid).setValue(me);

                        finish();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void getNewProfileImage() {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");


        startActivityForResult(intent, CHANGE_PROFILE_IMAGE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHANGE_PROFILE_IMAGE) {

            if (resultCode == RESULT_OK) {
                Uri uri = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    Drawable drawable = new BitmapDrawable(bitmap);// 转换成drawable
                    profile_image.setImageDrawable(drawable);

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == RESULT_CANCELED) {

            } else {

            }

        }
    }
}



