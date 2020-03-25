package com.dgut.moment.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dgut.moment.R;
import com.dgut.moment.Util.DatePickerUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

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

                billAddRemark.setText("");
                billAddSum.setText("");
                Toast.makeText(getActivity(),"成功记下一笔账",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
