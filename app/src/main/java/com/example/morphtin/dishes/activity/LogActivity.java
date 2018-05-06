package com.example.morphtin.dishes.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.morphtin.dishes.R;

import java.util.List;

import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class LogActivity extends AppCompatActivity {
    EditText userName, userPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        requestPermission();
        Bmob.initialize(this, "a008ec6eea323ad7d45c10b7dca35e16");
        BmobSMS.initialize(this, "a008ec6eea323ad7d45c10b7dca35e16");

        innitView();
        findViewById(R.id.userLogo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFatal()) {
                    logo(userName.getText().toString(), userPsw.getText().toString());
                }
            }
        });
    }

    //    初始化界面
    public void innitView() {
        userName = (EditText) findViewById(R.id.userName);
        userPsw = (EditText) findViewById(R.id.userPsw);
        findViewById(R.id.userRegis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogActivity.this, RegisterActivity.class));
            }
        });
    }

    //    校验数据是否空
    public boolean isFatal() {
        if (userName.getText().toString().equals("") || userPsw.getText().toString().equals("")) {
            Toast.makeText(this, "请输入完整信息，再进行登录！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //    登录验证
    public void logo(final String userName, final String userPsw) {
        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        query.addWhereEqualTo("user_name", userName);
//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
//执行查询方法
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> object, BmobException e) {
                if (e == null) {
                    if (object.size() > 0) {
                        for (UserInfo user : object) {
                            if (user.user_psw.equals(userPsw)) {
                                Toast.makeText(LogActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        Toast.makeText(LogActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(LogActivity.this, "该用户名不存在！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }


    //Android sdk6.0
    private static final int READ_PHONE_STATE = 222;

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, READ_PHONE_STATE);
                return;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            //就像onActivityResult一样这个地方就是判断你是从哪来的。
            case READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    // getPicture();
                } else {
                    // Permission Denied
                    Toast.makeText(LogActivity.this, "很遗憾你把手机权限禁用了！", Toast.LENGTH_SHORT).show();

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
