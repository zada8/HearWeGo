package com.android.hearwego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RouteGuideActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_WALK = 120;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_guide);


    }
}