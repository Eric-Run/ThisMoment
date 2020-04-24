package com.dgut.moment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.dgut.moment.Bean.Bill;
import com.dgut.moment.Bean.BillDetail;
import com.dgut.moment.Bean.Diary;
import com.dgut.moment.Bean.User;
import com.dgut.moment.Util.ConvertUtil;
import com.dgut.moment.Util.ToastUtil;
import com.dgut.moment.Util.ViewCenterUtils;

import org.litepal.LitePal;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int requestcode = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始用户
        List<User> users = LitePal.findAll(User.class);
        if(users.isEmpty()){
            User user = new User();
            user.setBillcount(0);
            user.setDiarycount(0);
            user.setPlancount(0);
            user.setDpassword("-1");
            user.setUsername("用户007");

            user.save();
        }else {
            Log.d("USER",users.toString());
        }

        if(Build.VERSION.SDK_INT >= 23){
            int checkWriteStoragePermission = ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.READ_CALENDAR);
            int checkWriteStoragePermission1 = ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.WRITE_CALENDAR);
            //如果没有被授予
            if(checkWriteStoragePermission != PackageManager.PERMISSION_GRANTED){
                //请求权限,此处可以同时申请多个权限
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALENDAR},  requestcode);
                return;
            }else{
                // do something....
            }
            if(checkWriteStoragePermission1 != PackageManager.PERMISSION_GRANTED){
                //请求权限,此处可以同时申请多个权限
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_CALENDAR},  requestcode);
                return;
            }else{
                // do something....
            }
        }else {
            // do something....
        }




    }

    public void diaryBtn(View view) {
        final User user = LitePal.find(User.class,1);
        if("-1".equals(user.getDpassword())) {
            ToastUtil.ToastCenter(this,"您还未设置密码，请前往\"我的\"界面进行设置");
            CardView cardView = findViewById(R.id.diaryBtn);
            int[] viewCenter = ViewCenterUtils.getViewCenter(cardView);
            Intent intent = new Intent(this, DiaryActivity.class);
            intent.putExtra("x", viewCenter[0]);
            intent.putExtra("y", viewCenter[1]);
            startActivity(intent);
            //overridePendingTransition(R.anim.anim_in,R.anim.anim_in);
        }else if("0".equals(user.getDpassword())){
            CardView cardView = findViewById(R.id.diaryBtn);
            int[] viewCenter = ViewCenterUtils.getViewCenter(cardView);
            Intent intent = new Intent(this, DiaryActivity.class);
            intent.putExtra("x", viewCenter[0]);
            intent.putExtra("y", viewCenter[1]);
            startActivity(intent);
        }else{

            final View dialogView = LayoutInflater.from(MainActivity.this)
                    .inflate(R.layout.dialog_diary_pw,null);
            AlertDialog.Builder inputDialog =
                    new AlertDialog.Builder(MainActivity.this);
            inputDialog.setTitle("请输入日记本密码：").setView(dialogView);
            inputDialog.setPositiveButton("确定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditText editText = dialogView.findViewById(R.id.dialog_dpw);
                            if(user.getDpassword().equals(editText.getText().toString())){
                                CardView cardView = findViewById(R.id.diaryBtn);
                                int[] viewCenter = ViewCenterUtils.getViewCenter(cardView);
                                Intent intent = new Intent(MainActivity.this, DiaryActivity.class);
                                intent.putExtra("x", viewCenter[0]);
                                intent.putExtra("y", viewCenter[1]);
                                startActivity(intent);
                            }else {
                                ToastUtil.ToastCenter(MainActivity.this,"密码输入错误，请重试！");
                            }
                        }
                    });
            inputDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            inputDialog.show();
        }

    }

    public void billBtn(View view) {
        CardView cardView = findViewById(R.id.billBtn);
        int[] viewCenter = ViewCenterUtils.getViewCenter(cardView);
        Intent intent=new Intent(this,BillActivity.class);
        intent.putExtra("x", viewCenter[0]);
        intent.putExtra("y", viewCenter[1]);
        startActivity(intent);
    }

    public void planBtn(View view) {
        CardView cardView = findViewById(R.id.planBtn);
        int[] viewCenter = ViewCenterUtils.getViewCenter(cardView);
        Intent intent=new Intent(this,PlanActivity.class);
        intent.putExtra("x", viewCenter[0]);
        intent.putExtra("y", viewCenter[1]);
        startActivity(intent);
    }

    public void meBtn(View view) {
        CardView cardView = findViewById(R.id.meBtn);
        int[] viewCenter = ViewCenterUtils.getViewCenter(cardView);
        Intent intent=new Intent(this,MeActivity.class);
        intent.putExtra("x", viewCenter[0]);
        intent.putExtra("y", viewCenter[1]);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 123:
                if(grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // do something....
                }else{
                    Toast.makeText(MainActivity.this, "获取权限失败!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
