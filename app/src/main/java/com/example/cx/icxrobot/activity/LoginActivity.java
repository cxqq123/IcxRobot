package com.example.cx.icxrobot.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.cx.icxrobot.R;
import com.example.cx.icxrobot.daohelper.UserDaoHelper;
import com.example.cx.icxrobot.me;
import com.example.cx.icxrobot.server.ServerManager;
import com.example.cx.icxrobot.util.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Context mContext;
    private RelativeLayout rlMainBack;
    private EditText etName;
    private EditText etPassword;
    private Button btnLogin;

    private ServerManager serverManager = ServerManager.getServerManager();

    private String username , password = "";

    private static final int LOGIN_WHAT = 1;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LOGIN_WHAT:
                    boolean isLogin = (boolean) msg.obj;
                    handlerLogin(isLogin);
                    break;
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#393A3F")); //更改状态栏的颜色
        setContentView(R.layout.activity_login);
        mContext = LoginActivity.this;
        ServerManager.setContext(mContext);
        initView();
        setListener();
    }

    private void initView(){
        rlMainBack = (RelativeLayout) findViewById(R.id.rl_main_back);
        etName = (EditText) findViewById(R.id.et_name);
        if(!Utils.isNullOrEmpty(etName.getText().toString())){
            String str = etName.getText().toString();
            etName.setSelection(str.length());
        }
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);

        //先写死
        etName.setText("cx");
        etPassword.setText("123");
        etName.setSelection(etName.getText().length());

//        serverManager.start(); //启动线程
    }

    private void setListener(){
        rlMainBack.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void goHome(){
        Intent intent = new Intent(mContext, MessageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_main_back:
                finish();
                break;

            case R.id.btn_login:
                username = etName.getText().toString();
                password = etPassword.getText().toString();
                if(Utils.getNetworkState(mContext) == 0){
                    Toast.makeText(mContext,"当前无网络连接,请连接网络", Toast.LENGTH_LONG).show();
                }else{
//                    loginTask(username,password);
                    boolean isHasUser = UserDaoHelper.querySingelUser(mContext,username,password);
                    if(isHasUser){
                        //说明数据库中有该人,登录成功
                        Toast.makeText(mContext, "登录成功" , Toast.LENGTH_SHORT).show();
                        goHome();
                        me.user = username;
                    }else{
                        //没有，可以提示前去注册页面，前去注册
                        Toast.makeText(mContext, "该用户账号不存在,可以前去注册页面去注册账号" , Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void handlerLogin(boolean isLogin){
        if (isLogin) {
            serverManager.setUsername(username);
            goHome();
            finish();
        } else {
            etName.setText("");
            etPassword.setText("");
            Toast.makeText(mContext,"用户名与密码不正确", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginTask(final String userName , final String password){
        new Thread(){
            @Override
            public void run() {
                boolean isLogin = false;
                super.run();
                if (userName == null || userName.length() > 10 || password.length() > 20) {
                    return;
                }
                // send msg to servers
                String msg = "[LOGIN]:[" + userName + ", " + password + "]";
                serverManager.sendMessage(mContext , msg);
                // get msg from servers return
                String ack = serverManager.getMessage();
                Log.e("cx" ,"ack :" + ack);
                // deal msg
                if (ack == null) {
                    return ;
                }
                serverManager.setMessage(null);
                String p = "\\[ACKLOGIN\\]:\\[(.*)\\]";
                Pattern pattern = Pattern.compile(p);
                Matcher matcher = pattern.matcher(ack);
                isLogin = matcher.find() && matcher.group(1).equals("1");

                Message message = new Message();
                message.what = LOGIN_WHAT;
                message.obj = isLogin;
                mHandler.sendMessage(message);
                return ;
            }
        }.start();
    }
}
