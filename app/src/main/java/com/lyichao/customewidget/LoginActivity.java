package com.lyichao.customewidget;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private LoginButton mButton;
    private EditText txt_mobi;
    private EditText txt_password;
    private CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mButton = findViewById(R.id.btn_login);
        txt_mobi = findViewById(R.id.txt_mobi);
        txt_password = findViewById(R.id.txt_password);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkout(txt_mobi.getText().toString(),txt_password.getText().toString());
            }
        });

    }

    private void checkout(String txt_mobi, String txt_password) {
        System.out.println("触发===============>    "+"txt_mobi:"+txt_mobi+" "+"txt_password:"+txt_password);
        if (txt_mobi.equals("lyichao") && txt_password.equals("123456")){
            //模拟网络请求登陆
            mCountDownTimer = new MyCountDownTimer(2000,1000);
            mCountDownTimer.start();
        }else {
            Toast.makeText(this, "账号密码有误", Toast.LENGTH_SHORT).show();

        }

    }

    private class MyCountDownTimer extends CountDownTimer{

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mButton.showLoading();
        }

        @Override
        public void onFinish() {

            Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
            mButton.showButtonText();
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();


        }
    }



}
