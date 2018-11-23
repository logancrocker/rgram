package com.rgram.rgram;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rgram.rgram.CropImage.CropImage;
import com.rgram.rgram.CropImage.InternalStorageContentProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

public class PostFragment extends Fragment {

    public static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    public static final int REQUEST_CODE_GALLERY = 0x1;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3;
    private File mFileTemp;
    private int newitemnum;
    String TAG = "cropimage";
    ImageView image;
    String path;
    View view;
    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    CheckBox checkBox5;
    CheckBox checkBox6;
    CheckBox checkBox7;
    CheckBox checkBox8;
    CheckBox checkBox9;
    CheckBox checkBox10;
    CheckBox checkBox11;
    CheckBox checkBox12;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    StorageReference postRef;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    public PostFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_post, container, false);
        checkBox1=view.findViewById(R.id.checkBox1);
        checkBox2=view.findViewById(R.id.checkBox2);
        checkBox3=view.findViewById(R.id.checkBox3);
        checkBox4=view.findViewById(R.id.checkBox4);
        checkBox5=view.findViewById(R.id.checkBox5);
        checkBox6=view.findViewById(R.id.checkBox6);
        checkBox7=view.findViewById(R.id.checkBox7);
        checkBox8=view.findViewById(R.id.checkBox8);
        checkBox9=view.findViewById(R.id.checkBox9);
        checkBox10=view.findViewById(R.id.checkBox10);
        checkBox11=view.findViewById(R.id.checkBox11);
        checkBox12=view.findViewById(R.id.checkBox12);
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(Environment.getExternalStorageDirectory(), TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(this.getActivity().getFilesDir(), TEMP_PHOTO_FILE_NAME);

        }
        image = view.findViewById(R.id.image);
        Button fab = view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetCameraPermission();
            }
        });
        Button post=view.findViewById(R.id.button2);
        post.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                newitemnum=FeedFragment.getItemnum();
                newitemnum++;
                FeedFragment.setItemnum(newitemnum);

            }
        });
        return view;
    }

    public void AvatarFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE_GALLERY);
    }

    public void AvatarFromCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            Uri mImageCaptureUri = null;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    mImageCaptureUri = FileProvider.getUriForFile(this.getContext(), "com.rgram.rgram.fileProvider", mFileTemp);
                } else {
                    mImageCaptureUri = Uri.fromFile(mFileTemp);
                }

            } else {
                mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
            }

            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        } catch (ActivityNotFoundException e) {
            Log.d(TAG, "cannot take picture", e);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {

            return;
        }

        Bitmap bitmap;

        switch (requestCode) {

            case REQUEST_CODE_GALLERY:

                try {

                    InputStream inputStream = this.getContext().getContentResolver().openInputStream(data.getData());
                    FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
                    copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();

                    startCropImage();

                } catch (Exception e) {
                    Log.e(TAG, "Error while creating temp file", e);
                }

                break;
            case REQUEST_CODE_TAKE_PICTURE:

                startCropImage();
                break;
            case REQUEST_CODE_CROP_IMAGE:

                path = data.getStringExtra(CropImage.IMAGE_PATH);
                if (path == null) {

                    return;
                }
                bitmap = BitmapFactory.decodeFile(mFileTemp.getPath());
                image.setImageBitmap(bitmap);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //crop selected image
    private void startCropImage() {

        Intent intent = new Intent(this.getActivity(), CropImage.class);
        intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
        intent.putExtra(CropImage.SCALE, true);

        intent.putExtra(CropImage.ASPECT_X, 5);
        intent.putExtra(CropImage.ASPECT_Y, 5);

        startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
    }

    public static void copyStream(InputStream input, OutputStream output)
            throws IOException {

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
    }

    //set persmission in android 6.0
    public void GetCameraPermission() {

        if (ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            final String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

            ActivityCompat.requestPermissions(this.getActivity(), permissions,
                    2);
        } else {
            ChangePic();
        }

    }

    public void ChangePic() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        //  builder.setTitle("Change Event picture");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    AvatarFromCamera();

                } else if (options[item].equals("Choose from Gallery")) {
                    AvatarFromGallery();
                }

                final Button postBtn = view.findViewById(R.id.button2);
                postBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //show what tags are selected
                        String result="selected tags:";
                        final ArrayList tagsList = new ArrayList<String>();
                        if(checkBox1.isChecked()){
                            result+=" "+checkBox1.getText().toString();
                            tagsList.add(checkBox1.getText().toString());
                        }
                        if(checkBox2.isChecked()){
                            result+=" "+checkBox2.getText().toString();
                            tagsList.add(checkBox2.getText().toString());
                        }
                        if(checkBox3.isChecked()){
                            result+=" "+checkBox3.getText().toString();
                            tagsList.add(checkBox3.getText().toString());
                        }
                        if(checkBox4.isChecked()){
                            result+=" "+checkBox4.getText().toString();
                            tagsList.add(checkBox4.getText().toString());
                        }
                        if(checkBox5.isChecked()){
                            result+=" "+checkBox5.getText().toString();
                            tagsList.add(checkBox5.getText().toString());
                        }
                        if(checkBox6.isChecked()){
                            result+=" "+checkBox6.getText().toString();
                            tagsList.add(checkBox6.getText().toString());
                        }
                        if(checkBox7.isChecked()){
                            result+=" "+checkBox7.getText().toString();
                            tagsList.add(checkBox7.getText().toString());
                        }
                        if(checkBox8.isChecked()){
                            result+=" "+checkBox8.getText().toString();
                            tagsList.add(checkBox8.getText().toString());
                        }
                        if(checkBox9.isChecked()){
                            result+=" "+checkBox9.getText().toString();
                            tagsList.add(checkBox9.getText().toString());
                        }
                        if(checkBox10.isChecked()){
                            result+=" "+checkBox10.getText().toString();
                            tagsList.add(checkBox10.getText().toString());
                        }
                        if(checkBox11.isChecked()){
                            result+=" "+checkBox11.getText().toString();
                            tagsList.add(checkBox11.getText().toString());
                        }
                        if(checkBox12.isChecked()){
                            result+=" "+checkBox12.getText().toString();
                            tagsList.add(checkBox12.getText().toString());
                        }
                        Toast.makeText(view.getContext(),result,Toast.LENGTH_LONG).show();
                        //create URI for image
                        final Uri file = Uri.fromFile(new File(path));
                        //get reference to image storage
                        //we create a unique file name, using the current time and retain the file extension
                        final String postPath = "images/"+System.currentTimeMillis()+path.substring(path.lastIndexOf("."));
                        StorageReference postRef = storageRef.child(postPath);
                        //upload image
                        UploadTask uploadTask = postRef.putFile(file);

                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Log.d("notebook", "upload failed");
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                EditText desc = view.findViewById(R.id.textView2);
                                String postDesc = desc.getText().toString();
                                DatabaseReference postsRef = database.child("posts");
                                DatabaseReference newpostRef = postsRef.push();
                                //get current user id
                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                String id = newpostRef.getKey();
<<<<<<< HEAD
                                newpostRef.setValue(new Post(0, postPath, postDesc, uid, tagsList));
                                //now we want to update some properties about ourself
                                //get a ref to ourself
                                final DatabaseReference me = database.child("users").child(uid);
                                //increment post_count
                                me.child("post_count").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Long count = (Long) dataSnapshot.getValue();
                                        count++;
                                        Log.d("notebook", String.valueOf(count));
                                        dataSnapshot.getRef().setValue(count);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                

                                newpostRef.setValue(new Post(0,
                                        postPath,
                                        postDesc, uid, tagsList));


//                                newpostRef.setValue(new Post(0, postPath, postDesc, uid));
//
//                                newpostRef.setValue(new Post(0,
//                                        "images/"+id+file.getPath().substring(file.getPath().lastIndexOf(".")),
//                                        postDesc, uid,tagsList));


                                //we want to go to the post page
                                Activity a = getActivity();
                                a.finish();
                                Intent intent = new Intent();
                                intent.setClass(a, a.getClass());
                                a.startActivity(intent);
                            }
                        });

                    }
                });

            }
        });
        builder.show();
    }

}
