package com.yxf.customviewutils.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yxf.customviewutils.R;
import com.yxf.customviewutils.widget.NumberProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class NumberProgressActivity extends AppCompatActivity {
    NumberProgressBar progressBar;
    Button btnStart;
    private Timer timer;
    private TimerTask mTimeTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_progress);
        progressBar = findViewById(R.id.number_progress_bar);
        btnStart = findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startprogress();
            }
        });
    }

    private void startprogress() {
        timer = new Timer();
        mTimeTask = new TimerTask() {
            @Override
            public void run() {
                progressBar.incrementProgressBy(1);
            }
        };
        timer.schedule(mTimeTask, 1000, 300);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimeTask != null) {
            mTimeTask.cancel();
        }
        if (timer != null) {
            timer.cancel();
        }

    }
}
