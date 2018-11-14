package com.rgram.rgram;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

public class FeedActivity extends AppCompatActivity  {
    public ViewPager vp;
    private BottomNavigationBar mBottomNavigationBar;
    List<Fragment> list=new ArrayList<>();
    String[] titles={"feed","other1","other2","post","profile"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        //set title
        setTitle("Feed");
        setBottomNavigationBar();

        vp = (ViewPager) findViewById(R.id.view_pager);
        list.add(new FeedFragment());
        list.add(new OtherFragment());
        list.add(new PostFragment());
        list.add(new OtherFragment());
        list.add(new ProfileFragment());
        vp.setAdapter(new FeedAdapter(getSupportFragmentManager(),list));

        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                mBottomNavigationBar.selectTab(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    private void setBottomNavigationBar() {

        mBottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_bar);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_DEFAULT);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.setBarBackgroundColor(android.R.color.white);

        mBottomNavigationBar
                .setActiveColor(R.color.colorAccent) //设置选中的颜色
                .setInActiveColor(R.color.colorPrimary);//未选中颜色

        mBottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_launcher_background,
                "feed"))//添加图标和文字
                .addItem(new BottomNavigationItem(R.drawable.ic_launcher_background, "other1"))
                .addItem(new BottomNavigationItem(R.drawable.ic_launcher_background,"post"))
                .addItem(new BottomNavigationItem(R.drawable.ic_launcher_background, "other2"))
                .addItem(new BottomNavigationItem(R.drawable.ic_launcher_background, "profile"))
                .initialise();

        //设置点击事件
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){

            @Override
            public void onTabSelected(int position) {
                vp.setCurrentItem(position);
                Log.e( "1","get viewpager current item=" + position);
            }
            @Override
            public void onTabUnselected(int position) {
            }
            @Override
            public void onTabReselected(int position) {
            }
        });
    }





}
