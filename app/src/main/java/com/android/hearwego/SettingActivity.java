package com.android.hearwego;

import android.os.Bundle;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    RelativeLayout layout01 = (RelativeLayout) findViewById(R.id.bar_top);
    RelativeLayout layout02 = (RelativeLayout) findViewById(R.id.help);
    RelativeLayout layout03 = (RelativeLayout) findViewById(R.id.logout);
    RelativeLayout layout04 = (RelativeLayout) findViewById(R.id.withdraw);
    RelativeLayout layout05 = (RelativeLayout) findViewById(R.id.previous);
    RelativeLayout layout06 = (RelativeLayout) findViewById(R.id.home);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
    }
}
