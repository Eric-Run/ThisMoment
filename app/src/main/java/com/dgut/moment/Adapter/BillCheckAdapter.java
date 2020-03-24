package com.dgut.moment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dgut.moment.Bean.BillCheckDetail;
import com.dgut.moment.Bean.BillCheckItem;
import com.dgut.moment.R;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BillCheckAdapter extends RecyclerView.Adapter<BillCheckAdapter.ViewHolder> {

    private Context mcontext;
    public List<BillCheckItem> billCheckItems;
    private int Size = 10;

    public BillCheckAdapter(List<BillCheckItem> billCheckItems) {
        this.billCheckItems = billCheckItems;
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
        View view = LayoutInflater.from(mcontext).inflate(R.layout.fr_bill_check_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(billCheckItems != null){
            BillCheckItem billCheckItem = billCheckItems.get(position);
            holder.billDay.setText(billCheckItem.getBillDay());
            holder.day_income.setText(billCheckItem.getDayIncome()+"");
            holder.day_outgo.setText(billCheckItem.getDayOutgo()+"");

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mcontext);
            holder.bill_detail_rv.setLayoutManager(linearLayoutManager);
            holder.bill_detail_rv.addItemDecoration(new HorizontalDividerItemDecoration.Builder(mcontext).build());//添加分割线
            BillCheckItemAdapter adapter = new BillCheckItemAdapter(billCheckItem.getBillCheckDetails());
            holder.bill_detail_rv.setAdapter(adapter);
        }
    }

    @Override
    public int getItemCount() {
        if(billCheckItems!=null){
            return billCheckItems.size();
        }else {
            return Size; //返回数组长度
        }
    }

}
