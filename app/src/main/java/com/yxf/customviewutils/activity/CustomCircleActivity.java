package com.yxf.customviewutils.activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yxf.customviewutils.R;
import com.yxf.customviewutils.widget.CustomCircle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CustomCircleActivity extends AppCompatActivity {

    private boolean isRunning=false;
    CustomCircle customCircle;
    TextView txtProgress;

    ExecutorService executors= Executors.newCachedThreadPool();
    private Handler mMainHandler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_circle);
        txtProgress = findViewById(R.id.txt_progress);
        customCircle = findViewById(R.id.custom_circle);
        txtProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    stop();
                    isRunning=false;
                }else {
                    start();
                    isRunning=true;
                }
            }
        });
    }

    private void start(){
        executors.execute(getRunnable());
    }

    private void stop(){
//        executors.shutdown();
    }

    private Runnable getRunnable(){
        return new Runnable() {
            @Override
            public void run() {
                int i=0;
                while (i<=100) {
                    try {
                        customCircle.setProgress(i);
                        Thread.sleep(1000);
                        final int finalI = i;
                        mMainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                txtProgress.setText(finalI +"%");
                            }
                        });
                        i++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
}
