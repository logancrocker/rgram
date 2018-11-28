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
import java.util.Date;

import static com.rgram.rgram.R.color.colorPrimary;

public class FeedFragment extends Fragment {
    ListView moments_list_layout;
    ArrayList<String> listdata=new ArrayList<String>();
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


        adapter=new momentAdapter(this.getContext(),listdata);
        moments_list_layout.setAdapter(adapter);
        //  moments_list_layout.setOnScrollListener((AbsListView.OnScrollListener) this);
        moments_list_layout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                //Toast.makeText(getApplicationContext(), "p:"+position+",id:"+id, 1).show();
                Log.i("msg", "p:"+position+",id:"+id);
            }

        });
        adapter.setOnaddlikeClickListener(new momentAdapter.OnItemaddlikeListener() {


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



    public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
        // Toast.makeText(getApplicationContext(), "f:"+firstVisibleItem+",v:"+visibleItemCount+",t"+totalItemCount, 1).show();
        Log.i("msg", "f:"+firstVisibleItem+",v:"+visibleItemCount+",t"+totalItemCount);
    }

    public void add(View view) {

        adapter.addData(listdata.size()+"");
        adapter.notifyDataSetChanged();


    }*/

    }
}
