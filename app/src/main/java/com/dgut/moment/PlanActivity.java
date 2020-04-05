package com.dgut.moment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.dgut.moment.Adapter.PlanItemAdapter;
import com.dgut.moment.Util.ViewCenterUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.haibin.calendarview.LunarUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PlanActivity extends AppCompatActivity {

    private TextView test;
    private com.haibin.calendarview.CalendarView calendarView;
    private TextView DateTv;
    private TextView YearTv;
    private TextView LunarTv;
    private RecyclerView PlanRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        ConstraintLayout ll=findViewById(R.id.planLayout);
        ViewCenterUtils.setActivityStartAnim(this, ll, getIntent());

        /*calendarView = findViewById(R.id.plan_calendar);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                int realMonth = month + 1;
                test.setText(year+"-"+realMonth+"-"+dayOfMonth);

            }
        });*/

        DateTv = findViewById(R.id.plan_date);
        YearTv = findViewById(R.id.plan_year);
        LunarTv = findViewById(R.id.plan_lunar);
        calendarView = findViewById(R.id.plan_calendar);
        PlanRv = findViewById(R.id.plan_rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        PlanRv.setLayoutManager(layoutManager);
        PlanItemAdapter adapter = new PlanItemAdapter();
        PlanRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build()); //划分割线
        PlanRv.setAdapter(adapter);


        int year = calendarView.getCurYear();
        int month = calendarView.getCurMonth();
        int day = calendarView.getCurDay();

        DateTv.setText(month+"月"+day+"日");
        YearTv.setText(year+"");
        LunarTv.setText("今日");



        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year, month, 3, "20").toString(),
                getSchemeCalendar(year, month, 3, "20"));
        map.put(getSchemeCalendar(year, month, 15, "5").toString(),
                getSchemeCalendar(year, month, 15, "5"));
        calendarView.setSchemeDate(map);

        calendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {

                Log.d("Plan_view",calendar.getYear()+"");
                Log.d("Plan_view",calendar.getMonth()+"");
                Log.d("Plan_view",calendar.getDay()+"");
                int month = calendar.getMonth();
                int day = calendar.getDay();
                DateTv.setText(calendar.getMonth()+"月"+calendar.getDay()+"日");
                YearTv.setText(calendar.getYear()+"");
                LunarTv.setText(calendar.getLunar()+"");
            }
        });
    }

    private Calendar getSchemeCalendar(int year, int month, int day, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setSchemeColor(0xFFd0505d);
        calendar.setDay(day);
        calendar.setScheme(text);
        return calendar;
    }
}
