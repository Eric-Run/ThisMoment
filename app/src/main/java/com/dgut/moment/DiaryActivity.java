package com.dgut.moment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

import com.dgut.moment.Util.ViewCenterUtils;

public class DiaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        ConstraintLayout ll=findViewById(R.id.diaryLayout);
        ViewCenterUtils.setActivityStartAnim(this, ll, getIntent());
    }
}
