package com.rgram.rgram;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import static com.rgram.rgram.R.color.colorPrimary;

public class FeedFragment extends Fragment {
    ListView moments_list_layout;
    List <Messages> listdata=new ArrayList<Messages>();
    List <Messages> datas=new ArrayList<Messages>();
    private static int itemnum=0;
    momentAdapter adapter;
    ListView myFeed;
    public FeedFragment() {
        // Required empty public constructor
    }
    public static int getItemnum(){
        return itemnum;
    }
    public static void setItemnum(int a){
        FeedFragment.itemnum=a;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        myFeed = getView().findViewById(R.id.show_moment);

        /*for(int i=0;i<itemnum;i++){
            listdata.add("Item"+listdata.size());
            itemnum--;
        }
        moments_list_layout = (ListView) getView().findViewById(R.id.show_moment);

=======
//        for(int i=0;i<itemnum;i++){
//            listdata.add("Item"+listdata.size());
//            itemnum--;
//        }
>>>>>>> bc13faeee58eb2ed3bf61c572b4fc88ebac47e2f

        //initialize listdata
//        Messages testmessage=new Messages("name1","https://firebasestorage.googleapis.com/v0/b/rgram-885a2.appspot.com/o/avatars%2F1543095759003.jpg?alt=media&token=24315e5b-7d2c-4920-a7b7-ec8e505cf2d0","https://firebasestorage.googleapis.com/v0/b/rgram-885a2.appspot.com/o/avatars%2F1543095759003.jpg?alt=media&token=24315e5b-7d2c-4920-a7b7-ec8e505cf2d0","like",3,"chat",6);
//        Messages tmessage=new Messages("name2","avatars","post","like",4,"chat",7);
//        listdata.add(testmessage);
//        listdata.add(tmessage);
        moments_list_layout = (ListView) getView().findViewById(R.id.show_moment);
//        //initialize listdata
        DatabaseReference meFollowRef = FirebaseDatabase.getInstance().getReference().child("feeds").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        meFollowRef.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                ArrayList<Post> posts=new ArrayList<Post>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                     posts.add(postSnapshot.getValue(Post.class));
                }
                Collections.reverse(posts);
                for(Post mpost:posts){
                    String followId=mpost.getUid();
                 final String postPic=mpost.getPath();
                 final String postDesc=mpost.getImgDescription();
                 final int likenum=mpost.getLikes();
                //    int chatnum=mpost.getComment();
                    FirebaseDatabase.getInstance().getReference().child("users").child(followId).addListenerForSingleValueEvent(new ValueEventListener(){
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                            User followUser = dataSnapshot.getValue(User.class);
                           final String  name=followUser.userName;
                           final String avatars=followUser.picture;
                            datas.add(new Messages(name,avatars,postPic,postDesc,"like",likenum,"chat",10));

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

<<<<<<< HEAD
    }*/

        //grab all feed posts
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference feedRef = ref.child("feeds").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        final ArrayList<Post> feedPosts = new ArrayList<Post>();
        feedRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                feedPosts.clear();
                if (dataSnapshot.exists())
                {
                    for (DataSnapshot d : dataSnapshot.getChildren())
                    {
                        Post curr = d.getValue(Post.class);
                        Log.d("notebook", curr.getImgDescription());
                        feedPosts.add(curr);
                    }
                    DiscoverAdapter discoverAdapter = new DiscoverAdapter(getContext(), feedPosts);
                    myFeed.setAdapter(discoverAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    /*public void onScrollStateChanged(AbsListView view, int scrollState) {
        //Toast.makeText(getApplicationContext(), "s:"+scrollState, 1).show();
        Log.i("msg",  "s:"+scrollState);
    }
=======
        adapter=new momentAdapter(datas,getActivity());
//        adapter=new momentAdapter(listdata,getActivity());
        moments_list_layout.setAdapter(adapter);
        //  moments_list_layout.setOnScrollListener((AbsListView.OnScrollListener) this);
//        moments_list_layout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
//                //Toast.makeText(getApplicationContext(), "p:"+position+",id:"+id, 1).show();
//                Log.i("msg", "p:"+position+",id:"+id);
//            }
>>>>>>> bc13faeee58eb2ed3bf61c572b4fc88ebac47e2f

//        });
//        adapter.setOnaddlikeClickListener(new momentAdapter.OnItemaddlikeListener() {
//
//
////            @Override
////            public void onAddlikeClick(int i, View view) {
////            view.getTag(4).
////                newlikenum=Integer.parseInt(viewHolder.likenum.getText().toString());
////
////            }
//        });


    }
//    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        //Toast.makeText(getApplicationContext(), "s:"+scrollState, 1).show();
//        Log.i("msg",  "s:"+scrollState);
//    }



//    public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
//        // Toast.makeText(getApplicationContext(), "f:"+firstVisibleItem+",v:"+visibleItemCount+",t"+totalItemCount, 1).show();
//        Log.i("msg", "f:"+firstVisibleItem+",v:"+visibleItemCount+",t"+totalItemCount);
//    }

<<<<<<< HEAD
    }*/
//    public void add(View view) {
//
//        adapter.addData(listdata.size()+"");
//        adapter.notifyDataSetChanged();
//
//
//    }

    }
}
