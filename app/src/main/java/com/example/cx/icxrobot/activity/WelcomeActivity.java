package com.example.cx.icxrobot.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cx.icxrobot.R;
import com.example.cx.icxrobot.util.StatusBarUtil;


public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener{


    private TextView tvLanguage;
    private Button btnLogin;
    private Button btnReg;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(WelcomeActivity.this, Color.parseColor("#000000"));
        setContentView(R.layout.activity_welcome);
        initView();
        setListener();
    }

    public void initView(){
        tvLanguage = (TextView) findViewById(R.id.tv_language);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReg = (Button) findViewById(R.id.btn_reg);
    }

    public void setListener(){
        btnLogin.setOnClickListener(this);
        btnReg.setOnClickListener(this);
    }


    private void goLogin(){
        Intent intent = new Intent(WelcomeActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    private void goReg(){
        Intent intent = new Intent(WelcomeActivity.this,RegActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                goLogin();
                break;
            case R.id.btn_reg:
                goReg();
                break;
        }
    }
}
