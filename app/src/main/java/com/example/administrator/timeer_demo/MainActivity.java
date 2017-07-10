package com.example.administrator.timeer_demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText mEt;
    private TextView mTv;
    private Button mStart,mStop,mJump;
    private int i=0;
    private Timer timer;
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mEt = (EditText) findViewById(R.id.mEt);
        mStart= (Button) findViewById(R.id.mStart);
        mStop= (Button) findViewById(R.id.mStop);
        mJump= (Button) findViewById(R.id.mJump);
        mTv= (TextView) findViewById(R.id.mTv);

        mStart.setOnClickListener(this);
        mStop.setOnClickListener(this);
        mJump.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mStart:
                mTv.setText(mEt.getText().toString().trim());
                i=Integer.parseInt(mTv.getText().toString());
                startTimer();

                break;
            case R.id.mStop:
                stopTimer();
                break;
            case R.id.mJump:
                Intent intent=new Intent(MainActivity.this,BActivity.class);
                startActivity(intent);
                stopTimer();
                finish();
                break;
        }
    }
    /*
    *开启timer倒计时
     */
    private void startTimer(){

        if (timer ==null){
            timer=new Timer();
        }
        timerTask = new TimerTask(){

            @Override
            public void run() {
                i--;
                Message msg = handler.obtainMessage();
                msg.arg1=i;
                handler.sendMessage(msg);
            }
        };
        //安排在指定时间内执行任务，第一个参数是任务，第二个参数是几秒后执行，第三个参数是间隔多久执行一次
        timer.schedule(timerTask,1000);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mTv.setText(msg.arg1+"");
            startTimer();
            if(i <= 0){
                timer.cancel();
                Intent intent=new Intent(MainActivity.this,BActivity.class);
                startActivity(intent);
                finish();
//                mTv.setVisibility(View.GONE);
            }
        }
    };
    private void stopTimer(){
        if (timer!=null){
            timer.cancel();
        }
    }
}
