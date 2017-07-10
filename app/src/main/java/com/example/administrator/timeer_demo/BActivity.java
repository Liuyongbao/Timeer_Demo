package com.example.administrator.timeer_demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.TextView;

import com.example.administrator.timeer_demo.fragment.AFragment;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class BActivity extends FragmentActivity {

    private String url="https://api.douban.com/v2/movie/top250?start=0&count=10";
    private TextView mTv_b;
    private AFragment aFragment;
    private FragmentManager fm=getSupportFragmentManager();
    private String tag="a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        initView();
        initGet();
    }

    private void initView() {
        mTv_b= (TextView) findViewById(R.id.mTv_b);
        aFragment=new AFragment();
        aFragment= (AFragment) fm.findFragmentById(R.id.mTv_frag);

    }

    private void initGet() {
        //创建一个okhttpclient的实例
        OkHttpClient ok = new OkHttpClient();
        //创建一个request的对象
        Request request = new Request.Builder()
                .url(url)
                .build();
        //创建一个call的对象
        Call call=ok.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("aa", ""+e );
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()){
                    final String s=response.body().string();
                    Log.e("ss", ""+s );
                    Message msg=handler.obtainMessage();
                    msg.what=1;
                    msg.obj=s;
                    handler.sendMessage(msg);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mTv_b.setText(s);
//                        }
//                    });
                }
            }
        });

    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                String ss= (String) msg.obj;
                Log.e("bb", ""+ss);
                mTv_b.setText(ss);
            }
        }
    };
}
