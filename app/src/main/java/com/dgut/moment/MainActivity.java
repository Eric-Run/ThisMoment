package com.dgut.moment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dgut.moment.Bean.Bill;
import com.dgut.moment.Bean.BillDetail;
import com.dgut.moment.Bean.Diary;
import com.dgut.moment.Util.ConvertUtil;
import com.dgut.moment.Util.ViewCenterUtils;

import org.litepal.LitePal;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int requestcode = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //测试数据库
 /*       String TAG = "TESTDB";
        //Dairy
        //增
        Diary diary = new Diary();
        diary.setTitle("测试日记");
        diary.setContent("测试Diary表的日记");
        diary.setDate("2020-04-13");
        diary.setMood("happy");
        diary.setWeather("sunny");
        Log.d(TAG,"\n准备加入数据库的diary：\n"+diary.toString());
        diary.save();
        Log.d(TAG,"\ndiary已提交");
        //查
        List<Diary> diaries = LitePal.where("id = ?","1").find(Diary.class);
        Log.d(TAG,"\n查找id=1的diary：\n"+diaries.toString());
        diaries.clear();
        diaries = LitePal.findAll(Diary.class);
        Log.d(TAG,"\n查找所有diary：\n"+diaries.toString());
        //改
        Diary diary1 = LitePal.find(Diary.class,1);
        diary1.setTitle("测试修改日记");
        diary1.save();
        diary1 = LitePal.find(Diary.class,1);
        Log.d(TAG,"\n修改后的diary：\n"+diary1.toString());
        diary1.setWeather("rainy");
        diary1.updateAll("title = ?","测试修改日记");
        diaries.clear();
        diaries = LitePal.where("title = ?","测试修改日记").find(Diary.class);
        Log.d(TAG,"\n再次修改后的diary：\n"+diaries.toString());
        //删
        Diary diary2 = new Diary();
        diary2.setTitle("测试日记2");
        diary2.setContent("测试Diary表的日记2");
        diary2.setDate("2020-04-14");
        diary2.setMood("scared");
        diary2.setWeather("sunny");
        diary2.save();
        Diary diary3 = new Diary();
        diary3.setTitle("测试日记3");
        diary3.setContent("测试Diary表的日记3");
        diary3.setDate("2020-04-15");
        diary3.setMood("happy");
        diary3.setWeather("rainy");
        diary3.save();
        diaries.clear();
        diaries = LitePal.findAll(Diary.class);
        Log.d(TAG,"\n新增两条diary：\n"+diaries.toString());
        LitePal.delete(Diary.class,1);
        diaries.clear();
        diaries = LitePal.findAll(Diary.class);
        Log.d(TAG,"\n删除id为1的diary：\n"+diaries.toString());
        LitePal.deleteAll(Diary.class,"mood = ?","scared");
        diaries.clear();
        diaries = LitePal.findAll(Diary.class);
        Log.d(TAG,"\n删除mood为scared的diary：\n"+diaries.toString());

        //Bill
        BillDetail detail = new BillDetail();
        detail.setBday("2020-04-13");
        detail.setTag("修罗");
        detail.setSum(-71);
        detail.save();
        BillDetail detail1 = new BillDetail();
        detail1.setBday("2020-04-13");
        detail1.setTag("魅语");
        detail1.setSum(-71);
        detail1.save();
        Bill bill = new Bill();
        bill.setBillday("2020-04-13");
        bill.setOutgo(detail.getSum()+detail1.getSum());
        bill.save();
        BillDetail detail2 = new BillDetail();
        detail2.setBday("2020-03-23");
        detail2.setTag("萤火");
        detail2.setSum(-71);
        detail2.save();
        Bill bill1 = new Bill();
        bill1.setBillday("2020-03-23");
        bill1.setOutgo(detail2.getSum());
        bill1.save();
        List<Bill> bills = LitePal.findAll(Bill.class);
        Log.d(TAG,"\n查找所有Bill：\n"+bills.toString());
//        LitePal.deleteAll(BillDetail.class,"tag = ?","修罗");
        BillDetail detail3 = LitePal.find(BillDetail.class,1);
        List<Bill> bills1 = LitePal.where("billday = ?",detail3.getBday()).find(Bill.class);
        bills1.get(0).setOutgo(bills1.get(0).getOutgo()-detail3.getSum());
        bills1.get(0).save();
        LitePal.delete(BillDetail.class,1);
        bills.clear();
        bills = LitePal.findAll(Bill.class);
        Log.d(TAG,"\n删除id为1的Bill：\n"+bills.toString());
*/

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
        CardView cardView = findViewById(R.id.diaryBtn);
        int[] viewCenter = ViewCenterUtils.getViewCenter(cardView);
        Intent intent=new Intent(this,DiaryActivity.class);
        intent.putExtra("x", viewCenter[0]);
        intent.putExtra("y", viewCenter[1]);
        startActivity(intent);
        //overridePendingTransition(R.anim.anim_in,R.anim.anim_in);

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
