package com.dgut.moment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.dgut.moment.Fragment.BillAddFragment;
import com.dgut.moment.Fragment.BillCheckFragment;
import com.dgut.moment.Fragment.DiaryAddFragment;
import com.dgut.moment.Fragment.DiaryCheckFragment;
import com.dgut.moment.Util.ViewCenterUtils;
import com.dgut.moment.Util.ViewFindUtils;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.widget.MsgView;

import java.util.ArrayList;

public class DiaryActivity extends AppCompatActivity {

    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private String[] mTitles = {"我的日记", " 写日记 "};
    private View mDecorView;
    private SegmentTabLayout mTabLayout_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        ConstraintLayout ll=findViewById(R.id.diaryLayout);
        ViewCenterUtils.setActivityStartAnim(this, ll, getIntent());

        mFragments.add(DiaryCheckFragment.getInstance("This is DiaryCheck"));
        mFragments.add(DiaryAddFragment.getInstance("This is DiaryAdd"));

        mDecorView = getWindow().getDecorView();

        mTabLayout_3 = ViewFindUtils.find(mDecorView, R.id.tl_3);

        tl_3();

        //显示未读红点
        //mTabLayout_3.showDot(1);

        //设置未读消息红点
        //mTabLayout_3.showDot(2);
        MsgView rtv_3_2 = mTabLayout_3.getMsgView(2);
        if (rtv_3_2 != null) {
            rtv_3_2.setBackgroundColor(Color.parseColor("#6D8FB0"));
        }
    }

    private void tl_3() {
        final ViewPager vp_3 = ViewFindUtils.find(mDecorView, R.id.vp_2);
        vp_3.setAdapter(new DiaryActivity.MyPagerAdapter(getSupportFragmentManager()));

        mTabLayout_3.setTabData(mTitles);
        mTabLayout_3.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vp_3.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });

        vp_3.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout_3.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        vp_3.setCurrentItem(0);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }
}
