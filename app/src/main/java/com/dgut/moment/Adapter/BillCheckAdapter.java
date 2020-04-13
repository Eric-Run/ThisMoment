package com.dgut.moment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dgut.moment.Bean.Bill;
import com.dgut.moment.R;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BillCheckAdapter extends RecyclerView.Adapter<BillCheckAdapter.ViewHolder> {

    private Context mcontext;
    public List<Bill> bills;
    private int Size = 10;

    public BillCheckAdapter(List<Bill> bills) {

        Collections.sort(bills); //排序
        this.bills = bills;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView billDay;
        TextView day_income;
        TextView day_outgo;
        RecyclerView bill_detail_rv;

        public ViewHolder(View view){
            super(view);
            billDay = view.findViewById(R.id.billday);
            day_income = view.findViewById(R.id.day_income);
            day_outgo = view.findViewById(R.id.day_outgo);
            bill_detail_rv = view.findViewById(R.id.bill_detail_rv);

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mcontext ==null){
            mcontext = parent.getContext();
        }
        View view = LayoutInflater.from(mcontext).inflate(R.layout.bill_check_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(bills != null){
            Bill bill = bills.get(position);
            holder.billDay.setText(bill.getBillday().substring(5));
            holder.day_income.setText(bill.getIncome()+"");
            holder.day_outgo.setText(bill.getOutgo()+"");

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mcontext);
            holder.bill_detail_rv.setLayoutManager(linearLayoutManager);
            holder.bill_detail_rv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mcontext).build());//添加分割线
            BillCheckItemAdapter adapter = new BillCheckItemAdapter(bill.getBilldetail());
            holder.bill_detail_rv.setAdapter(adapter);

        }
    }

    @Override
    public int getItemCount() {
        if(bills !=null){
            return bills.size();
        }else {
            return Size; //返回数组长度
        }
    }

}
