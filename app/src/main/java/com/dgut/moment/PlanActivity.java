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
import android.widget.ImageView;
import android.widget.TextView;

import com.dgut.moment.Adapter.PlanItemAdapter;
import com.dgut.moment.Bean.Plan;
import com.dgut.moment.Fragment.PlanAddFragment;
import com.dgut.moment.Fragment.PlanModifyFragment;
import com.dgut.moment.Util.ConvertUtil;
import com.dgut.moment.Util.ViewCenterUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanActivity extends AppCompatActivity implements PlanAddFragment.PlanAddListener, PlanModifyFragment.PlanModifyListener {

    private static final String TAG="PlanActivity";
    private TextView test;
    private com.haibin.calendarview.CalendarView calendarView;
    private TextView DateTv;
    private TextView YearTv;
    private TextView LunarTv;
    private RecyclerView PlanRv;
    private Button CreateBtn;
    private Button TestBtn;
    private ImageView nodataIv;

    List<Plan> plans = new ArrayList<>();
    String planTime;

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
        nodataIv = findViewById(R.id.plan_nodata);

        TestBtn = findViewById(R.id.plan_test);

        initView();  //初始化界面
        showPlanList(); //初始化计划列表
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

        if(month < 10 && day < 10) {
            planTime = year + "-0" + month + "-0" + day;
        }else if(month < 10 && day > 9){
            planTime = year + "-0" + month + "-" + day;
        }else if(month > 9 && day < 10){
            planTime = year + "-" + month + "-0" + day;
        }else {
            planTime = year + "-" + month + "-" + day;
        }
        PlanRv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(PlanActivity.this).build()); //划分割线

    }

    private void showPlanList(){

        plans.clear();
        Log.d("Plan_","planTime:"+planTime);
        plans = LitePal.where("plantime like ?","%"+planTime+"%").find(Plan.class);

        if(!plans.isEmpty()) {
            PlanRv.setVisibility(View.VISIBLE);
            nodataIv.setVisibility(View.GONE);
            Log.d("Plan_","plans:"+plans.toString());
            //初始化计划列表
            LinearLayoutManager layoutManager = new LinearLayoutManager(PlanActivity.this);
            PlanRv.setLayoutManager(layoutManager);
            PlanItemAdapter adapter = new PlanItemAdapter(plans); //传入日期数据
            PlanRv.setAdapter(adapter);
        }else {
            nodataIv.setVisibility(View.VISIBLE);
            PlanRv.setVisibility(View.GONE);
        }

        List<Plan> plans1 = LitePal.order("plantime desc").find(Plan.class);
        if(!plans1.isEmpty()) {
            String time = plans1.get(0).getPlantime().substring(0, 10);
            Log.d("Plan_", "time:" + time);
            int count = 0;
            Map<String, Calendar> map = new HashMap<>();
            for (int i = 0; i <= plans1.size(); i++) {
                if (i != plans1.size()) {

                    if (time.equals(plans1.get(i).getPlantime().substring(0, 10))) {
                        if(plans1.get(i).getIsfinished() == 0) {
                            count++;
                        }
                    } else {
                        if(count != 0) {
                            map.put(getSchemeCalendar(time, count).toString(),
                                    getSchemeCalendar(time, count));
                        }
                        Log.d("Plan_", "count:" + count + "  time:" + time);

                        time = plans1.get(i).getPlantime().substring(0, 10);
                        if(plans1.get(i).getIsfinished() == 0) {
                            count = 1;
                        }else {
                            count = 0;
                        }
                    }
                } else {
                    if(count != 0) {
                        map.put(getSchemeCalendar(time, count).toString(),
                                getSchemeCalendar(time, count));
                    }
                }
            }
            calendarView.setSchemeDate(map);
            Log.d("Plan_", "plans1:" + plans1.toString());
        }

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
                int year = calendar.getYear();
                DateTv.setText(calendar.getMonth()+"月"+calendar.getDay()+"日");
                YearTv.setText(calendar.getYear()+"");
                LunarTv.setText(calendar.getLunar()+"");

                if(month < 10 && day < 10) {
                    planTime = year + "-0" + month + "-0" + day;
                }else if(month < 10 && day > 9){
                    planTime = year + "-0" + month + "-" + day;
                }else if(month > 9 && day < 10){
                    planTime = year + "-" + month + "-0" + day;
                }else {
                    planTime = year + "-" + month + "-" + day;
                }

                //计划列表随日期改标
//                PlanRv.removeItemDecoration(new HorizontalDividerItemDecoration.Builder(PlanActivity.this).build());
                showPlanList();
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

    private Calendar getSchemeCalendar(String time, int count) {
        Calendar calendar = new Calendar();
        calendar.setYear(ConvertUtil.convertToInt(time.substring(0,4),0));
        calendar.setMonth(ConvertUtil.convertToInt(time.substring(5,7),0));
        calendar.setSchemeColor(0xFFd0505d);
        calendar.setDay(ConvertUtil.convertToInt(time.substring(8,10),0));
        calendar.setScheme(count+"");
        return calendar;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Plan_","Plan->onResume");
        showPlanList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Plan_","Plan->onPause");

    }

    @Override
    public void planisAdd() {
        Log.d("Plan_","Plan->planisAdd");
        showPlanList();
    }

    @Override
    public void planisModified() {
        Log.d("Plan_","Plan->planisModified");
        showPlanList();
    }
}
