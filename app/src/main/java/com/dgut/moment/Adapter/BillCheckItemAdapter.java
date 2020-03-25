package com.dgut.moment.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dgut.moment.Bean.BillCheckDetail;
import com.dgut.moment.Bean.BillCheckItem;
import com.dgut.moment.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
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
        LinearLayout bill_detail_layout;

        public ViewHolder(View view){
            super(view);
            bill_detail_tag = view.findViewById(R.id.bill_detail_tag);
            bill_detail_num = view.findViewById(R.id.bill_detail_num);
            bill_detail_layout = view.findViewById(R.id.bill_detail_layout);

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        if(billCheckDetails != null){
            BillCheckDetail billCheckDetail = billCheckDetails.get(position);
            Log.d("AAAAA",billCheckDetail.getTag());
            holder.bill_detail_tag.setText(billCheckDetail.getTag());
            holder.bill_detail_num.setText(billCheckDetail.getNum()+"");

            holder.bill_detail_layout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(mcontext,v);
                    popupMenu.getMenuInflater().inflate(R.menu.bill_item_menu,popupMenu.getMenu());

                    //弹出式菜单的菜单项点击事件
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if(item.getItemId()==R.id.bill_delete) {
                                removeData(position);
                                Toast.makeText(mcontext,holder.bill_detail_tag.getText()+"的账单已被删除",Toast.LENGTH_SHORT).show();
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                    return false;
                }
            });
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

    public void removeData(int position) {
        billCheckDetails.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

}
