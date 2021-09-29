package com.android.hearwego;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LogoActivity extends AppCompatActivity {

    RelativeLayout status_bar = (RelativeLayout) findViewById(R.id.status_bar);
    RelativeLayout container_logo = (RelativeLayout) findViewById(R.id.container_logo);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);
    }
}
