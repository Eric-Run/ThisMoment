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
import com.dgut.moment.Bean.Diary;
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
    private List<Diary> diaries = new ArrayList<>();

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

        //获取日记数据
        diaries.add(new Diary("一","一切都是那么美好","happy","sunny","2019-02-03"));
        diaries.add(new Diary("二","两个世界","notbad","rainy","2019-03-24"));
        diaries.add(new Diary("三","三阳开泰","scared","cloudy","2019-04-25"));
        diaries.add(new Diary("四","四喜临门","upset","snowy","2019-05-16"));
        diaries.add(new Diary("五","五福临门","notbad","rainy","2019-06-13"));
        diaries.add(new Diary("六","六六大顺","scared","rainy","2019-07-23"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        DiaryCheckRv.setLayoutManager(layoutManager);
        DiaryCheckAdapter adapter = new DiaryCheckAdapter(diaries);
        DiaryCheckRv.setAdapter(adapter);
    }
}
