package com.dgut.moment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dgut.moment.Bean.BillDetail;
import com.dgut.moment.Fragment.PlanModifyFragment;
import com.dgut.moment.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class PlanItemAdapter extends RecyclerView.Adapter<PlanItemAdapter.ViewHolder> {

    private Context mcontext;
    public List<BillDetail> billDetails;
    private int Size = 4;

    public PlanItemAdapter(List<BillDetail> billDetails) {
        this.billDetails = billDetails;
    }

    public PlanItemAdapter() {
    }

    public PlanItemAdapter(int size) {
        Size = size;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout PlanItem;
        TextView PlanContent;

        public ViewHolder(View view){
            super(view);

            PlanItem = view.findViewById(R.id.plan_item);
            PlanContent = view.findViewById(R.id.plan_item_content);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mcontext ==null){
            mcontext = parent.getContext();
        }
        View view = LayoutInflater.from(mcontext).inflate(R.layout.plan_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.PlanItem.setOnLongClickListener(new View.OnLongClickListener() {
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
                            Toast.makeText(mcontext,holder.PlanContent.getText()+"已被删除",Toast.LENGTH_SHORT).show();
                        }else if(item.getItemId()==R.id.bill_change){
                            PlanModifyFragment planDetailFragment = new PlanModifyFragment();

                            FragmentTransaction transaction = ((FragmentActivity)mcontext).getSupportFragmentManager().beginTransaction();
                            transaction.addToBackStack(null).replace(R.id.planLayout,planDetailFragment);
                            transaction.setCustomAnimations(R.anim.anim_in,R.anim.anim_out,R.anim.anim_in,R.anim.anim_out)
                                    .commit();
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return false;
            }
        });

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
        //billDetails.remove(position);
        //删除动画
        Size--;
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

}
