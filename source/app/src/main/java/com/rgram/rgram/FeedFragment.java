package com.rgram.rgram;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import java.util.Arrays;

public class FeedFragment extends Fragment {
    ListView moments_list_layout;
    momentAdapter adapter;
    public FeedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        moments_list_layout = (ListView) getView().findViewById(R.id.show_moment);

        adapter= new momentAdapter(this.getContext(),Arrays.asList("123","234","345","456","567","678","123","234","345","456","567"));
        moments_list_layout.setAdapter(adapter);
        //  moments_list_layout.setOnScrollListener((AbsListView.OnScrollListener) this);
//        moments_list_layout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
//                //Toast.makeText(getApplicationContext(), "p:"+position+",id:"+id, 1).show();
//                Log.i("msg", "p:"+position+",id:"+id);
//            }
//        });
    }
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //Toast.makeText(getApplicationContext(), "s:"+scrollState, 1).show();
        Log.i("msg",  "s:"+scrollState);
    }


    public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
        // Toast.makeText(getApplicationContext(), "f:"+firstVisibleItem+",v:"+visibleItemCount+",t"+totalItemCount, 1).show();
        Log.i("msg", "f:"+firstVisibleItem+",v:"+visibleItemCount+",t"+totalItemCount);
    }
}
