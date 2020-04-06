package com.dgut.moment.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.dgut.moment.Bean.Diary;
import com.dgut.moment.R;
import com.dgut.moment.Util.DatePickerUtil;

import java.lang.reflect.Field;

import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
public class PlanDetailFragment extends Fragment {
    private String mTitle;
    private Switch reminder;
    private LinearLayout reminderLayout;

    public static PlanDetailFragment getInstance(String title) {
        PlanDetailFragment sf = new PlanDetailFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_plan_detail, null);

        reminder = v.findViewById(R.id.plan_remind);
        reminderLayout = v.findViewById(R.id.plan_remind_layout);

        reminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    reminderLayout.setVisibility(View.VISIBLE);
                }else {
                    reminderLayout.setVisibility(View.GONE);
                }
            }
        });



        return v;
    }

}
