package com.yxf.customviewutils;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yxf.customviewutils.activity.CustomCircleActivity;
import com.yxf.customviewutils.activity.NumberProgressActivity;
import com.yxf.customviewutils.widget.WaveView;

public class MainActivity extends AppCompatActivity {
    WaveView waveview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_circle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CustomCircleActivity.class));
            }
        });

        findViewById(R.id.btn_progressBar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NumberProgressActivity.class));
            }
        });
        waveview = findViewById(R.id.waveview);
        waveview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!waveview.mIsRunning) {
                    waveview.start();
                }else {
                    waveview.stop();
                }
            }
        });


    }


}
