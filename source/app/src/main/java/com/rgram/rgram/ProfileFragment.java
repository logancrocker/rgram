package com.rgram.rgram;

;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    private android.support.v4.app.FragmentManager manager;
    private FragmentTransaction ft;
    //database instance
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    StorageReference storage = FirebaseStorage.getInstance().getReference();

    private List<Post> post_list;
    private List<Image> image_list;

    User myaccount = new User();
    TextView edit_profile, posts_num, following_num, followers_num, display_name, description;
    CircleImageView user_profile_image;
    GridView gridView;

    private List<Map<String, Object>> datalist;
    SimpleAdapter simadapter;

    public ImageView iv;
    int[] imgs = new int[6];
    ArrayList<String> urls;

    int user_id, posts, following, followers;
    String email, image;

    public ProfileFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        post_list = new ArrayList<>();

        //grab current user
        FirebaseUser currUser = FirebaseAuth.getInstance().getCurrentUser();
        String currUid = currUser.getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

        ref.child(currUid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final User user = dataSnapshot.getValue(User.class);
                description.setText(myaccount.getUserDescription());
                display_name.setText(user.getUserName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        //grab all layout components
        edit_profile = (TextView) getView().findViewById(R.id.follow_this_profile);
        posts_num = (TextView) getView().findViewById(R.id.posts_num_tv);
        following_num = (TextView) getView().findViewById(R.id.following_num_tv);
        followers_num = (TextView) getView().findViewById(R.id.followers_num_tv);

        display_name = (TextView) getView().findViewById(R.id.display_name_tv);
        description = (TextView) getView().findViewById(R.id.description);

        user_profile_image = (CircleImageView) getView().findViewById(R.id.profile_image);
        gridView = (GridView) getView().findViewById(R.id.images_grid_layout);
        iv = (ImageView) getView().findViewById(R.id.img_large);
        edit_profile.setText("Edit Profile");

        //add all posts by current user to a list
        final DatabaseReference posts = database.child("posts");
        Query myposts = posts.orderByChild("uid").equalTo(currUid);
        myposts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        Post curr = snapshot.getValue(Post.class);
                        post_list.add(curr);
                    }
                    //set post number
                    posts_num.setText(String.valueOf(post_list.size()));
                    //set up grid view
                    int gridWidth = getResources().getDisplayMetrics().widthPixels;
                    int imgWidth = gridWidth / 3;
                    gridView.setColumnWidth(imgWidth);
                    urls = new ArrayList<>();
                    for (int i = 0; i < post_list.size(); i++)
                    {
                        storage.child(post_list.get(i).getPath()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                urls.add(uri.toString());
                                Log.d("notebook", uri.toString());
                                //Picasso.get().load(urls.get(0)).into(iv);
                                GridAdapter gridAdapter = new GridAdapter(getActivity(), urls);
                                gridView.setAdapter(gridAdapter);
                            }
                        });
                    }

                    //add to adapter
                    //TODO
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //initiate setting activity
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);

            }
        });

    }


}
