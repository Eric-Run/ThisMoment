package com.dgut.moment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dgut.moment.Util.ViewCenterUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void diaryBtn(View view) {
        CardView cardView = findViewById(R.id.diaryBtn);
        int[] viewCenter = ViewCenterUtils.getViewCenter(cardView);
        Intent intent=new Intent(this,DiaryActivity.class);
        intent.putExtra("x", viewCenter[0]);
        intent.putExtra("y", viewCenter[1]);
        startActivity(intent);
        //overridePendingTransition(R.anim.anim_in,R.anim.anim_in);

    }

    public void billBtn(View view) {
        CardView cardView = findViewById(R.id.billBtn);
        int[] viewCenter = ViewCenterUtils.getViewCenter(cardView);
        Intent intent=new Intent(this,BillActivity.class);
        intent.putExtra("x", viewCenter[0]);
        intent.putExtra("y", viewCenter[1]);
        startActivity(intent);
    }

    public void planBtn(View view) {
        CardView cardView = findViewById(R.id.planBtn);
        int[] viewCenter = ViewCenterUtils.getViewCenter(cardView);
        Intent intent=new Intent(this,PlanActivity.class);
        intent.putExtra("x", viewCenter[0]);
        intent.putExtra("y", viewCenter[1]);
        startActivity(intent);
    }

    public void meBtn(View view) {
        CardView cardView = findViewById(R.id.meBtn);
        int[] viewCenter = ViewCenterUtils.getViewCenter(cardView);
        Intent intent=new Intent(this,MeActivity.class);
        intent.putExtra("x", viewCenter[0]);
        intent.putExtra("y", viewCenter[1]);
        startActivity(intent);
    }
}
