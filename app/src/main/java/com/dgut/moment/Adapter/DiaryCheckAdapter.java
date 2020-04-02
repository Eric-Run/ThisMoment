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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dgut.moment.Bean.Diary;
import com.dgut.moment.Fragment.DiaryDetailFragment;
import com.dgut.moment.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class DiaryCheckAdapter extends RecyclerView.Adapter<DiaryCheckAdapter.ViewHolder> {

    private Context mcontext;
    public List<Diary> diaries;
    private int Size = 10;
    private Diary diary;


    public DiaryCheckAdapter(List<Diary> diaries) {
        this.diaries = diaries;
    }

    public DiaryCheckAdapter(){

    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView DiaryCheckDate;
        TextView DiaryCheckTitle;
        ImageView DiaryCheckMood;
        CardView DiaryCheckItem;

        public ViewHolder(View view){
            super(view);

            DiaryCheckDate = view.findViewById(R.id.diary_check_date);
            DiaryCheckTitle = view.findViewById(R.id.diary_check_title);
            DiaryCheckMood = view.findViewById(R.id.diary_check_mood);
            DiaryCheckItem = view.findViewById(R.id.diary_check_item);

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mcontext ==null){
            mcontext = parent.getContext();
        }
        View view = LayoutInflater.from(mcontext).inflate(R.layout.fr_diary_check_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        if(diaries != null){
            diary = diaries.get(position);
            holder.DiaryCheckTitle.setText(diary.getTitle());
            holder.DiaryCheckDate.setText(diary.getDate());
            //根据图片名称获取图片资源
            int mood = mcontext.getResources().getIdentifier(diary.getMood(), "mipmap", mcontext.getPackageName());
            holder.DiaryCheckMood.setImageResource(mood);
        }

        holder.DiaryCheckItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(mcontext,"进入日记详情页面",Toast.LENGTH_SHORT).show();
                DiaryDetailFragment diaryDetailFragment = new DiaryDetailFragment();
                Bundle bundle = new Bundle();//声明一个Bundle对象
                bundle.putSerializable("diary",diary);   //向下传递单词信息
                diaryDetailFragment.setArguments(bundle);

                FragmentTransaction transaction = ((FragmentActivity)mcontext).getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null) .replace(R.id.diary_check_layout,diaryDetailFragment);
                transaction.setCustomAnimations(R.anim.anim_in,R.anim.anim_out,R.anim.anim_in,R.anim.anim_out)
                        .commit();

            }
        });

        holder.DiaryCheckItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                final String confirmWord = holder.DiaryCheckTitle.getText().toString();

                AlertDialog.Builder builder=new AlertDialog.Builder(mcontext);
                builder.setMessage("确定将标题为《"+confirmWord+"》移除吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Diary_Check","确认删除:"+confirmWord);
                        removeData(position);
                        Toast.makeText(mcontext,"《"+confirmWord+"》已被删除",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Diary_Check","取消删除:"+confirmWord);
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
        if(diaries!=null){
            return diaries.size();
        }else {
            return Size; //返回数组长度
        }
    }

    public void removeData(int position) {
        Size--;
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

}
