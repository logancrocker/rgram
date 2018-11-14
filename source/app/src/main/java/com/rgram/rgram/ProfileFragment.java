package com.rgram.rgram;

;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

    User myaccount = new User();
    TextView edit_profile, posts_num, following_num, followers_num, display_name, description;
    CircleImageView user_profile_image;
    GridView images_grid_layout;

    private List<Map<String, Object>> datalist;
    private SimpleAdapter simadapter;

    public ImageView iv;
    int[] imgs = new int[3];

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
        images_grid_layout = (GridView) getView().findViewById(R.id.images_grid_layout);
        iv = (ImageView) getView().findViewById(R.id.img_large);
        edit_profile.setText("Edit Profile");

        //set name and description
        initData();
        datalist = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 3; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", imgs[i]);
            datalist.add(map);
        }

        simadapter = new SimpleAdapter(this.getActivity(), datalist, R.layout.images, new String[]{"image"}, new int[]{R.id.image});
        images_grid_layout.setAdapter(simadapter);
        images_grid_layout.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                iv.setImageResource(imgs[position]);
            }
        });

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);


                // TODO Auto-generated method stub
//                Intent settingsIntenet = new Intent(MyProfileActivity.this, SettingActivity.class);
//                settingsIntenet.putExtra("user_id", 43);
//                settingsIntenet.putExtra("username", "lynn");
//                settingsIntenet.putExtra("email", email);
//                settingsIntenet.putExtra("imageProfile", image);

//                settingsIntenet.putExtra("following", following);
//                settingsIntenet.putExtra("followers", followers);
//                settingsIntenet.putExtra("posts", posts);
    //            startActivityForResult(settingsIntenet, 1);
                // Log.i("匿名内部类", "点击事件");


            }
        });

    }

    private void initData() {
        for (int i = 0; i < imgs.length; i++) {
            int imgI = i;
            // 获取其他类的属性，通过属性获取属性对应得值
            // null代表的是静态变量，非静态变量需要传递对象
            try {
                imgs[i] = R.drawable.class.getField("img0" + imgI).getInt(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
