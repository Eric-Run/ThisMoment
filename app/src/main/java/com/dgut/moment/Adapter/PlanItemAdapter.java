package com.dgut.moment.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dgut.moment.Bean.BillDetail;
import com.dgut.moment.Bean.Plan;
import com.dgut.moment.Fragment.PlanModifyFragment;
import com.dgut.moment.R;
import com.dgut.moment.View.RoundCheckBox;

import org.litepal.LitePal;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class PlanItemAdapter extends RecyclerView.Adapter<PlanItemAdapter.ViewHolder> {

    private Context mcontext;
    public List<Plan> plans;
    private int Size = 4;

    public PlanItemAdapter(List<Plan> plans) {
        this.plans = plans;
    }

    public PlanItemAdapter() {
    }

    public PlanItemAdapter(int size) {
        Size = size;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        LinearLayout PlanItem;
        TextView PlanContent;
        TextView PlanTime;
        RoundCheckBox PlanCb;

        public ViewHolder(View view){
            super(view);

            PlanItem = view.findViewById(R.id.plan_item);
            PlanContent = view.findViewById(R.id.plan_item_content);
            PlanTime = view.findViewById(R.id.plan_item_time);
            PlanCb = view.findViewById(R.id.plan_item_cb);
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

        if(!plans.isEmpty()){
            Plan plan = plans.get(position);
            holder.PlanTime.setText(plan.getPlantime());
            holder.PlanContent.setText(plan.getContent());
            if(plan.getIsfinished()==1){
                holder.PlanCb.setChecked(true);
            }
            holder.PlanCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Plan plan = LitePal.find(Plan.class,plans.get(position).getId());
                    plan.setIsfinished(isChecked ? 1 : 0);
                    plan.save();
                }
            });
        }

        holder.PlanItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlanModifyFragment planDetailFragment = new PlanModifyFragment();
                Bundle bundle = new Bundle();//声明一个Bundle对象
                bundle.putSerializable("plan",plans.get(position));   //向下传递单词信息
                planDetailFragment.setArguments(bundle);
                FragmentTransaction transaction = ((FragmentActivity)mcontext).getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null).replace(R.id.planLayout,planDetailFragment);
                transaction.setCustomAnimations(R.anim.anim_in,R.anim.anim_out,R.anim.anim_in,R.anim.anim_out)
                        .commit();
            }
        });
        holder.PlanItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                /*PopupMenu popupMenu = new PopupMenu(mcontext,v);
                popupMenu.getMenuInflater().inflate(R.menu.plan_item_menu,popupMenu.getMenu());

                //弹出式菜单的菜单项点击事件
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.plan_delete) {
                            removeData(position);
                            Toast.makeText(mcontext,holder.PlanContent.getText()+"已被删除",Toast.LENGTH_SHORT).show();
                        }else if(item.getItemId()==R.id.plan_change){
                            PlanModifyFragment planDetailFragment = new PlanModifyFragment();
                            Bundle bundle = new Bundle();//声明一个Bundle对象
                            bundle.putSerializable("plan",plans.get(position));   //向下传递单词信息
                            planDetailFragment.setArguments(bundle);
                            FragmentTransaction transaction = ((FragmentActivity)mcontext).getSupportFragmentManager().beginTransaction();
                            transaction.addToBackStack(null).replace(R.id.planLayout,planDetailFragment);
                            transaction.setCustomAnimations(R.anim.anim_in,R.anim.anim_out,R.anim.anim_in,R.anim.anim_out)
                                    .commit();
                        }
                        return false;
                    }
                });
                popupMenu.show();*/
                final String confirmWord = holder.PlanContent.getText().toString();

                AlertDialog.Builder builder=new AlertDialog.Builder(mcontext);
                builder.setMessage("确定将\""+confirmWord+"\"移除吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        removeData(position);
                        Toast.makeText(mcontext,"《"+confirmWord+"》已被删除",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Plan_","取消删除:"+confirmWord);
                    }
                });
                AlertDialog alert=builder.create();
                alert.show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        if(plans !=null){
            return plans.size();
        }else {
            return Size; //返回数组长度
        }
    }

    public void removeData(int position) {
        //删除数据库数据
        LitePal.delete(Plan.class,plans.get(position).getId());
        //删除当前list子数据
        plans.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

}
