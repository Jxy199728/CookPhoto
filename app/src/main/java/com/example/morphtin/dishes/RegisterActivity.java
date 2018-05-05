package com.example.morphtin.dishes;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


public class RegisterActivity extends AppCompatActivity {

    EditText userName, userPsw, user_code;
    TextView tv_code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        innitView();

    }

    //    初始化界面
    public void innitView() {
        userName = (EditText) findViewById(R.id.userName);
        userPsw = (EditText) findViewById(R.id.userPsw);

        user_code = (EditText) findViewById(R.id.user_code);
        tv_code = (TextView) findViewById(R.id.tv_code);
        findViewById(R.id.tv_code).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFatal()) {
                    BmobSMS.requestSMSCode(RegisterActivity.this, userName.getText().toString(), "验证码模板", new RequestSMSCodeListener() {

                        @Override
                        public void done(Integer integer, cn.bmob.sms.exception.BmobException e) {
                            if (e == null) {//验证码发送成功
                                Log.i("bmob", "短信id：");//用于查询本次短信发送详情
                                tv_code.setEnabled(false);
                                getCode(tv_code);
                                handler.post(t);
                            } else {
                                Toast.makeText(RegisterActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.userRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFatal()) {
                    BmobSMS.verifySmsCode(RegisterActivity.this, userName.getText().toString(), user_code.getText().toString(), new VerifySMSCodeListener() {

                        @Override
                        public void done(cn.bmob.sms.exception.BmobException ex) {
                            if (ex == null) {//短信验证码已验证成功
                                CheckIsDataAlreadyInDBorNot(userName.getText().toString());
                            } else {
                                Toast.makeText(RegisterActivity.this, "验证码错误！", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }

    // 校验数据合法性
    public boolean isFatal() {
        if (userName.getText().toString().equals("") || userPsw.getText().toString().equals("") || user_code.getText().toString().equals("")) {
            Toast.makeText(this, "请输入完整信息，再进行注册！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    //检验用户名是否已存在
    public void CheckIsDataAlreadyInDBorNot(String username) {

        BmobQuery<UserInfo> query = new BmobQuery<UserInfo>();
        query.addWhereEqualTo("user_name", username);
//返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
//执行查询方法
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> object, BmobException e) {
                if (e == null) {
                    if (object.size() > 0) {
                        Toast.makeText(RegisterActivity.this, "该用户名已被注册，注册失败", Toast.LENGTH_SHORT).show();
                    } else {
                        register(userName.getText().toString(), userPsw.getText().toString());

                    }
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }


    //向数据库插入数据
    public void register(String username, String password) {
        UserInfo user = new UserInfo();
//注意：不能调用gameScore.setObjectId("")方法
        user.user_name = username;
        user.user_psw = password;
        user.save(new SaveListener<String>() {

            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }


    /*
* 倒计时
* */
    public static Handler handler;
    public static Thread t;
    private static int oldtime = 60;//s
    private static int time = oldtime;//s

    //倒计时
    public static void getCode(final TextView tv) {
        handler = new Handler();
        time = oldtime;
        t = new Thread() {
            @Override
            public void run() {
                super.run();
                time--;
                if (time <= 0) {
                    tv.setEnabled(true);
                    time = oldtime;
                    tv.setText("重新获取");
                    return;
                }
                tv.setText(time + "s重新获取");
                if (handler != null)
                    handler.postDelayed(this, 1000);
            }
        };
    }

}
