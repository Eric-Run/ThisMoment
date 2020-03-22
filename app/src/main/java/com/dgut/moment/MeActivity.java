package com.dgut.moment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;

import com.dgut.moment.Util.ViewCenterUtils;

public class MeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        ConstraintLayout ll=findViewById(R.id.meLayout);
        ViewCenterUtils.setActivityStartAnim(this, ll, getIntent());
    }
}
