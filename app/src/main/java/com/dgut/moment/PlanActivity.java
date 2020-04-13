package com.dgut.moment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dgut.moment.Adapter.PlanItemAdapter;
import com.dgut.moment.Fragment.PlanAddFragment;
import com.dgut.moment.Util.ViewCenterUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PlanActivity extends AppCompatActivity {

    private static final String TAG="PlanActivity";
    private TextView test;
    private com.haibin.calendarview.CalendarView calendarView;
    private TextView DateTv;
    private TextView YearTv;
    private TextView LunarTv;
    private RecyclerView PlanRv;
    private Button CreateBtn;
    private Button TestBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        FrameLayout ll=findViewById(R.id.planLayout);
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
        CreateBtn = findViewById(R.id.plan_create);

        TestBtn = findViewById(R.id.plan_test);

        initView();  //初始化界面

        setListener();  //设置监听器


    }

    //初始化界面
    private void initView(){

        int year = calendarView.getCurYear();
        int month = calendarView.getCurMonth();
        int day = calendarView.getCurDay();

        //显示年、月、日
        DateTv.setText(month+"月"+day+"日");
        YearTv.setText(year+"");
        LunarTv.setText("今日");

        //初始化计划列表
        LinearLayoutManager layoutManager = new LinearLayoutManager(PlanActivity.this);
        PlanRv.setLayoutManager(layoutManager);
        PlanItemAdapter adapter = new PlanItemAdapter(month); //传入日期数据
        PlanRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(PlanActivity.this).build()); //划分割线
        PlanRv.setAdapter(adapter);

        //模拟标记
        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year, month, 3, "20").toString(),
                getSchemeCalendar(year, month, 3, "20"));
        map.put(getSchemeCalendar(year, month, 15, "5").toString(),
                getSchemeCalendar(year, month, 15, "5"));
        calendarView.setSchemeDate(map);

    }

    //设置监听器
    private void setListener(){

        //选择日子
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

                //计划列表随日期改标
//                PlanRv.removeItemDecoration(new HorizontalDividerItemDecoration.Builder(PlanActivity.this).build());
                LinearLayoutManager layoutManager = new LinearLayoutManager(PlanActivity.this);
                PlanRv.setLayoutManager(layoutManager);
                PlanItemAdapter adapter = new PlanItemAdapter(month);
                PlanRv.setAdapter(adapter);
            }
        });

        //创建计划跳转
        CreateBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PlanAddFragment planAddFragment = new PlanAddFragment();
                FragmentTransaction transaction = ((FragmentActivity)PlanActivity.this).getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null).replace(R.id.planLayout,planAddFragment);
                transaction.setCustomAnimations(R.anim.anim_in,R.anim.anim_out)
                        .commit();
            }
        });

        //测试定时提醒
        TestBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG,"try to set reminder");
                SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                Date curDate =  new Date(System.currentTimeMillis());
//                clockManager.addAlarm(buildIntent(1),curDate);
//                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);;
//                alarmManager.set(AlarmManager.RTC_WAKEUP,curDate.getTime(),buildIntent(1));

                //添加系统日历事件
//                CalendarReminderUtils.addCalendarEvent(PlanActivity.this,"Test0458","finally",curDate.getTime()+(60 * 1000),0);

            }
        });

    }

    private PendingIntent buildIntent(int id) {
//        Intent intent = new Intent();
//        intent.putExtra(ClockReceiver.EXTRA_EVENT_ID, id);
//        intent.setClass(this, ClockReceiver.class);
        Intent  intent=new Intent();
        intent.setAction("com.example.myapp.RING");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(PlanActivity.this,0x102,intent,0);

        return pendingIntent;
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
