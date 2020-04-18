package com.dgut.moment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dgut.moment.Bean.User;
import com.dgut.moment.Util.ToastUtil;
import com.dgut.moment.Util.ViewCenterUtils;

import org.litepal.LitePal;

public class MeActivity extends AppCompatActivity {

    private ImageView userIconIv;
    private TextView usernameTv;
    private TextView diarycountTv;
    private TextView billcountTv;
    private TextView plancountTv;
    private LinearLayout modifyDpwLo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        ConstraintLayout ll=findViewById(R.id.meLayout);
        ViewCenterUtils.setActivityStartAnim(this, ll, getIntent());

        userIconIv = findViewById(R.id.userIcon);
        usernameTv = findViewById(R.id.me_username);
        diarycountTv = findViewById(R.id.me_diarycount);
        billcountTv = findViewById(R.id.me_billcount);
        plancountTv = findViewById(R.id.me_plancount);
        modifyDpwLo = findViewById(R.id.me_modify_dpw);

        initView();
        setListener();

    }

    private void initView(){

        User user = LitePal.find(User.class,1);
        usernameTv.setText(user.getUsername());
        diarycountTv.setText(user.getDiarycount()+"");
        billcountTv.setText(user.getBillcount()+"");
        plancountTv.setText(user.getPlancount()+"");

        Glide.with(this).load(R.drawable.default_profile3).into(userIconIv);

    }

    private void setListener(){

        modifyDpwLo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder customizeDialog =
                        new AlertDialog.Builder(MeActivity.this);
                final View dialogView = LayoutInflater.from(MeActivity.this)
                        .inflate(R.layout.dialog_me_modifydpw,null);

                LinearLayout linearLayout = dialogView.findViewById(R.id.dialog_pw_lo);
                final User user = LitePal.find(User.class,1);
                if("-1".equals(user.getDpassword()) | "0".equals(user.getDpassword())){
                    linearLayout.setVisibility(View.GONE);
                }
                customizeDialog.setTitle("修改日记本密码");
                customizeDialog.setView(dialogView);
                customizeDialog.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 获取EditView中的输入内容
                                EditText edit_text1 = (EditText) dialogView.findViewById(R.id.dialog_pw1);
                                EditText edit_text2 = (EditText) dialogView.findViewById(R.id.dialog_pw2);
                                EditText edit_text = (EditText) dialogView.findViewById(R.id.dialog_pw);
                                String pw,pw1,pw2;
                                pw = edit_text.getText().toString();
                                pw2 = edit_text2.getText().toString();
                                pw1 = edit_text1.getText().toString();
                                if("-1".equals(user.getDpassword()) | pw.equals(user.getDpassword()) | "0".equals(user.getDpassword())){
                                    if(pw1.equals(pw2)){
                                        user.setDpassword(pw1);
                                        user.save();
                                        ToastUtil.ToastCenter(MeActivity.this,"密码设置成功，请妥善保管");
                                    }else {
                                        ToastUtil.ToastCenter(MeActivity.this,"两次密码输入不相同，请重新设置");
                                    }
                                }else {
                                    ToastUtil.ToastCenter(MeActivity.this,"原密码输入错误，请重新设置");
                                }
                            }
                        });
                customizeDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                customizeDialog.show();
            }
        });
    }
}
