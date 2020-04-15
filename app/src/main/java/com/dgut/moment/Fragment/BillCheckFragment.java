package com.dgut.moment.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.dgut.moment.Adapter.BillCheckAdapter;
import com.dgut.moment.Bean.Bill;
import com.dgut.moment.Bean.BillDetail;
import com.dgut.moment.R;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

@SuppressLint("ValidFragment")
public class BillCheckFragment extends Fragment {
    private String mTitle;
    private TextView billDate;
    private TextView incomeTv;
    private TextView outgoTv;
    private RecyclerView billRv;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView nodataIv;

    List<Bill> bills = new ArrayList<>();


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
        incomeTv = v.findViewById(R.id.income);
        outgoTv = v.findViewById(R.id.outgo);
        swipeRefreshLayout = v.findViewById(R.id.bill_refresh_layout);
        nodataIv = v.findViewById(R.id.bill_nodata);


        initMonthBill();

        showDailyBill();

        setRefresh();

        return v;
    }

    //显示日账单
    private void showDailyBill(){
        Log.d("Bill_Check",billDate.getText().toString());
        bills.clear();
        bills = LitePal.where("billday like ?","%"+billDate.getText().toString()+"%").find(Bill.class);
        List<BillDetail> billDetails = new ArrayList<>();
        /*
//        List<List<BillDetail>> bill = new ArrayList<>();

//        bills.add(new Bill("03-04",100,100));
//        bills.add(new Bill("03-05",120,110));
//        bills.add(new Bill("03-06",130,120));
//        bills.add(new Bill("03-07",140,130));

        billDetails.add(new BillDetail("衣服",-100));
        billDetails.add(new BillDetail("午饭",-10));
        billDetails.add(new BillDetail("兼职",120));
        billDetails.add(new BillDetail("房租",-1130));

        bills.add(new Bill("03-04",100,100, billDetails));
        bills.add(new Bill("03-05",120,110, billDetails));
        bills.add(new Bill("03-06",130,120, billDetails));
        bills.add(new Bill("03-07",140,130, billDetails));
        bills.add(new Bill("03-08",140,130, billDetails));*/

        float in = 0;
        float out = 0;
        if(!bills.isEmpty()) {
            nodataIv.setVisibility(View.GONE);
            billRv.setVisibility(View.VISIBLE);
            Log.d("Bill_Check",bills.toString());

            for (Bill bill:bills){
                in = in + bill.getIncome();
                out = out + bill.getOutgo();
            }
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            billRv.setLayoutManager(layoutManager);
            BillCheckAdapter adapter = new BillCheckAdapter(bills);
            billRv.setAdapter(adapter);
        }else {
            //提示无数据
            nodataIv.setVisibility(View.VISIBLE);
            billRv.setVisibility(View.GONE);

        }
        //月支出/收入
        incomeTv.setText(in+"");
        outgoTv.setText(out+"");

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
                showDailyBill();
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

    private void setRefresh(){

        swipeRefreshLayout.setColorSchemeResources(R.color.basic2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showDailyBill();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
