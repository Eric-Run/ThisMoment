package com.dgut.moment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.dgut.moment.Util.ViewCenterUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import java.util.HashMap;
import java.util.Map;

public class PlanActivity extends AppCompatActivity {

    private TextView test;
    private com.haibin.calendarview.CalendarView calendarView;

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

        calendarView = findViewById(R.id.plan_calendar);

        int year = calendarView.getCurYear();
        int month = calendarView.getCurMonth();

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
