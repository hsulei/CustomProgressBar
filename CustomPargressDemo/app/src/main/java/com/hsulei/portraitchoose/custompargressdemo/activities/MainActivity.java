package com.hsulei.portraitchoose.custompargressdemo.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hsulei.portraitchoose.custompargressdemo.R;
import com.hsulei.portraitchoose.custompargressdemo.view.HorizontalProgressBar;

public class MainActivity extends AppCompatActivity {
    private HorizontalProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBar= (HorizontalProgressBar) findViewById(R.id.bar);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(50);
    }
}
