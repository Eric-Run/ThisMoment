package com.dgut.moment.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dgut.moment.Adapter.BillCheckAdapter;
import com.dgut.moment.Adapter.DiaryCheckAdapter;
import com.dgut.moment.Bean.BillCheckDetail;
import com.dgut.moment.Bean.BillCheckItem;
import com.dgut.moment.R;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@SuppressLint("ValidFragment")
public class DiaryCheckFragment extends Fragment {
    private String mTitle;
    private RecyclerView DiaryCheckRv;

    public static DiaryCheckFragment getInstance(String title) {
        DiaryCheckFragment sf = new DiaryCheckFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_diary_check, null);
        DiaryCheckRv = v.findViewById(R.id.diary_check_rv);

        showLocalDiary();
        return v;
    }

    //显示本地日记
    private void showLocalDiary(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DiaryCheckRv.setLayoutManager(layoutManager);
        DiaryCheckAdapter adapter = new DiaryCheckAdapter();
        DiaryCheckRv.setAdapter(adapter);
    }
}
