package com.dgut.moment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

import com.dgut.moment.Util.ViewCenterUtils;

public class PlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        ConstraintLayout ll=findViewById(R.id.planLayout);
        ViewCenterUtils.setActivityStartAnim(this, ll, getIntent());
    }
}
