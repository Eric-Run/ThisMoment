package com.dgut.moment.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import com.dgut.moment.Adapter.BillCheckAdapter;
import com.dgut.moment.Bean.BillCheckDetail;
import com.dgut.moment.Bean.BillCheckItem;
import com.dgut.moment.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

@SuppressLint("ValidFragment")
public class BillCheckFragment extends Fragment {
    private String mTitle;
    private TextView billDate;
    private RecyclerView billRv;

    public static BillCheckFragment getInstance(String title) {
        BillCheckFragment sf = new BillCheckFragment();
        sf.mTitle = title;
        return sf;
    }

    public static BillCheckFragment getFragment(){
        return new BillCheckFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_bill_check, null);

        billRv = v.findViewById(R.id.billRv);
        billDate = v.findViewById(R.id.billDate);

        initMonthBill();

        showDailyBill();

        return v;
    }

    //显示日账单
    private void showDailyBill(){
        List<BillCheckItem> billCheckItems = new ArrayList<>();
        List<BillCheckDetail> billCheckDetails = new ArrayList<>();
//        List<List<BillCheckDetail>> bill = new ArrayList<>();

//        billCheckItems.add(new BillCheckItem("03-04",100,100));
//        billCheckItems.add(new BillCheckItem("03-05",120,110));
//        billCheckItems.add(new BillCheckItem("03-06",130,120));
//        billCheckItems.add(new BillCheckItem("03-07",140,130));

        billCheckDetails.add(new BillCheckDetail("衣服","-100.0"));
        billCheckDetails.add(new BillCheckDetail("午饭","-10.0"));
        billCheckDetails.add(new BillCheckDetail("兼职","120.0"));
        billCheckDetails.add(new BillCheckDetail("房租","-1130.0"));

        billCheckItems.add(new BillCheckItem("03-04",100,100,billCheckDetails));
        billCheckItems.add(new BillCheckItem("03-05",120,110,billCheckDetails));
        billCheckItems.add(new BillCheckItem("03-06",130,120,billCheckDetails));
        billCheckItems.add(new BillCheckItem("03-07",140,130,billCheckDetails));
        billCheckItems.add(new BillCheckItem("03-08",140,130,billCheckDetails));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        billRv.setLayoutManager(layoutManager);
        BillCheckAdapter adapter = new BillCheckAdapter(billCheckItems);
        billRv.setAdapter(adapter);
    }

    //初始化月账单及监听月份选择
    private void initMonthBill(){
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM");
        Date curDate =  new Date(System.currentTimeMillis());
        billDate.setText(formatter.format(curDate));
        billDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDatePickDlg();
            }
        });
    }

    //显示时间选择器
    public void showDatePickDlg () {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String month;
                int i = monthOfYear+1;
                if(i<10){

                    month = "0" + i;
                }else {
                    month = i + "";
                }
                BillCheckFragment.this.billDate.setText(year + "-" + month);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

        DatePicker dp = findDatePicker((ViewGroup) datePickerDialog.getWindow().getDecorView());
        if (dp != null) {
            ((ViewGroup)((ViewGroup) dp.getChildAt(0)).getChildAt(0)).getChildAt(2).setVisibility(View.GONE);
        }

    }

    //返回时间选择器的子控件（用于隐藏子控件）
    private DatePicker findDatePicker(ViewGroup group) {
        if (group != null) {
            for (int i = 0, j = group.getChildCount(); i < j; i++) {
                View child = group.getChildAt(i);
                if (child instanceof DatePicker) {
                    return (DatePicker) child;
                } else if (child instanceof ViewGroup) {
                    DatePicker result = findDatePicker((ViewGroup) child);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }
}
