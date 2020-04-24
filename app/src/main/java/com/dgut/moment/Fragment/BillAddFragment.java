package com.dgut.moment.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dgut.moment.Bean.Bill;
import com.dgut.moment.Bean.BillDetail;
import com.dgut.moment.Bean.User;
import com.dgut.moment.R;
import com.dgut.moment.Util.ConvertUtil;
import com.dgut.moment.Util.DatePickerUtil;
import com.dgut.moment.Util.ToastUtil;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.Fragment;

@SuppressLint("ValidFragment")
public class BillAddFragment extends Fragment {
    private String mTitle;
    private TextView billAddDate;
    private EditText billAddRemark;
    private EditText billAddSum;
    private RadioGroup billAddType;
    private Button billAddClear;
    private Button billAddSave;


    public static BillAddFragment getInstance(String title) {
        BillAddFragment sf = new BillAddFragment();
        sf.mTitle = title;
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fr_bill_add, null);

        billAddDate = v.findViewById(R.id.bill_add_date);
        billAddRemark = v.findViewById(R.id.bill_add_remark);
        billAddSum = v.findViewById(R.id.bill_add_sum);
        billAddType = v.findViewById(R.id.bill_add_type);
        billAddSave = v.findViewById(R.id.bill_add_save);

        //billAddType.getCheckedRadioButtonId();

        //设置光标
        clickShowCursor(billAddRemark);
        clickShowCursor(billAddSum);
        //设置日期
        initAddDate();
        //监听保存按钮
        listenSaveBtn();

        return v;
    }

    //设置初始日期，监听选择日期
    private void initAddDate(){
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
        Date curDate =  new Date(System.currentTimeMillis());
        billAddDate.setText(formatter.format(curDate));
        billAddDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerUtil().showDatePickDlg(billAddDate,getContext());
            }
        });
    }

    //设置EditText点击后显示光标
    private void clickShowCursor(final EditText editText){
        editText.setCursorVisible(false);
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {
                    editText.setCursorVisible(true);// 再次点击显示光标
                }
                return false;
            }
        });
    }

    //监听保存按钮
    private void listenSaveBtn(){
        billAddSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if("".equals(billAddRemark.getText().toString())){
                    ToastUtil.ToastCenter(getContext(),"还没填写备注！");
                }else if("".equals(billAddSum.getText().toString())){
                    ToastUtil.ToastCenter(getContext(),"还没填写金额！");
                }else {
                    final BillDetail billDetail = new BillDetail();
                    billDetail.setTag(billAddRemark.getText().toString());

                    int id = billAddType.getCheckedRadioButtonId();
                    if (id == R.id.bill_add_in) {
                        billDetail.setSum(ConvertUtil.convertToFloat(billAddSum.getText().toString(), -1));
                    } else if (id == R.id.bill_add_out) {
                        String number = "-" + billAddSum.getText().toString();
                        billDetail.setSum(ConvertUtil.convertToFloat(number, -1));
                    }
                    Log.d("Bill_Add", billDetail.getSum() + "");

                    billDetail.setBday(billAddDate.getText().toString());
                    billDetail.save();
                    Log.d("Bill_Add", "保存一条账单详情" + billDetail.toString());
                    User user = LitePal.find(User.class, 1);
                    user.setBillcount(user.getBillcount() + 1);
                    user.save();
                    Log.d("Bill_Add", "用户：" + user.getUsername() + "增加了一条账单");

                    float sum = ConvertUtil.convertToFloat(billAddSum.getText().toString(), -1);

                    List<Bill> bills = LitePal.where("billday = ?", billDetail.getBday()).find(Bill.class);
                    if (!bills.isEmpty()) {
                        if (billDetail.getSum() > 0) {
                            bills.get(0).setIncome(bills.get(0).getIncome() + sum);
                        } else {
                            bills.get(0).setOutgo(bills.get(0).getOutgo() + sum);
                        }
                        bills.get(0).save();
                        Log.d("Bill_Add", "保存一条账单" + bills.get(0).toString());

                    } else {
                        Bill bill = new Bill();
                        bill.setBid(ConvertUtil.convertToInt(billDetail.getBday().replace("-", ""), -1));
                        bill.setBillday(billDetail.getBday());
                        if (billDetail.getSum() > 0) {
                            bill.setIncome(sum);
                        } else {
                            bill.setOutgo(sum);
                        }
                        bill.save();
                        Log.d("Bill_Add", "保存一条账单" + bill.toString());
                    }
                    ToastUtil.ToastCenter(getContext(), "成功记下一笔账");
                    billAddRemark.setText("");
                    billAddSum.setText("");
                }
            }
        });
    }
}
