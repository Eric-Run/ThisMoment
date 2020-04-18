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

import com.dgut.moment.Bean.Bill;
import com.dgut.moment.Bean.BillDetail;
import com.dgut.moment.Bean.Diary;
import com.dgut.moment.Bean.User;
import com.dgut.moment.R;

import org.litepal.LitePal;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

public class BillCheckItemAdapter extends RecyclerView.Adapter<BillCheckItemAdapter.ViewHolder> {

    private Context mcontext;
    public List<BillDetail> billDetails;
    private int Size = 10;

    public BillCheckItemAdapter(List<BillDetail> billDetails) {
        this.billDetails = billDetails;
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
        View view = LayoutInflater.from(mcontext).inflate(R.layout.bill_check_detail,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        if(!billDetails.isEmpty()){
            BillDetail billDetail = billDetails.get(position);
            Log.d("AAAAA", billDetail.getTag());
            holder.bill_detail_tag.setText(billDetail.getTag());
            holder.bill_detail_num.setText(billDetail.getSum()+"");

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
        if(billDetails !=null){
            return billDetails.size();
        }else {
            return Size; //返回数组长度
        }
    }

    public void removeData(int position) {

        //删除数据库数据
        LitePal.delete(BillDetail.class,billDetails.get(position).getId());
        List<Bill> bills = LitePal.where("billday = ?",billDetails.get(position).getBday()).find(Bill.class);
        if (bills.get(0).getBilldetail().isEmpty()) {
            LitePal.deleteAll(Bill.class, "billday = ?", billDetails.get(position).getBday());
        } else {
            float sum = billDetails.get(position).getSum();
            if (sum > 0) {
                bills.get(0).setIncome(bills.get(0).getIncome() - sum);
            } else {
                bills.get(0).setOutgo(bills.get(0).getOutgo() + sum);
            }
            bills.get(0).save();
        }

        User user = LitePal.find(User.class,1);
        user.setBillcount(user.getBillcount()-1);
        user.save();
        Log.d("Bill_Add","用户："+user.getUsername()+"减少了一条账单");

        billDetails.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

}
