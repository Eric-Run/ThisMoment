package com.dgut.moment.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dgut.moment.R;
import com.dgut.moment.Util.ViewCenterUtils;

import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
public class DiaryDetailFragment extends Fragment {
    private String mTitle;

    public static DiaryDetailFragment getInstance(String title) {
        DiaryDetailFragment sf = new DiaryDetailFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_diary_check_detail, null);





        return v;
    }
}
