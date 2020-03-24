package com.dgut.moment.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dgut.moment.Bean.BillCheckDetail;
import com.dgut.moment.Bean.BillCheckItem;
import com.dgut.moment.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class BillCheckItemAdapter extends RecyclerView.Adapter<BillCheckItemAdapter.ViewHolder> {

    private Context mcontext;
    public List<BillCheckDetail> billCheckDetails;
    private int Size = 10;

    public BillCheckItemAdapter(List<BillCheckDetail> billCheckDetails) {
        this.billCheckDetails = billCheckDetails;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView bill_detail_tag;
        TextView bill_detail_num;

        public ViewHolder(View view){
            super(view);
            bill_detail_tag = view.findViewById(R.id.bill_detail_tag);
            bill_detail_num = view.findViewById(R.id.bill_detail_num);

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mcontext ==null){
            mcontext = parent.getContext();
        }
        View view = LayoutInflater.from(mcontext).inflate(R.layout.fr_bill_check_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(billCheckDetails != null){
            BillCheckDetail billCheckDetail = billCheckDetails.get(position);
            Log.d("AAAAA",billCheckDetail.getTag());
            holder.bill_detail_tag.setText(billCheckDetail.getTag());
            holder.bill_detail_num.setText(billCheckDetail.getNum()+"");

        }
    }

    @Override
    public int getItemCount() {
        if(billCheckDetails!=null){
            return billCheckDetails.size();
        }else {
            return Size; //返回数组长度
        }
    }

}
