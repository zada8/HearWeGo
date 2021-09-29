package com.android.hearwego;

import android.os.Bundle;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;

public class Logo extends AppCompatActivity {

    RelativeLayout layout01 = (RelativeLayout) findViewById(R.id.status_bar);
    RelativeLayout layout02 = (RelativeLayout) findViewById(R.id.container_logo);
    RelativeLayout layout03 = (RelativeLayout) findViewById(R.id.container_google_login);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);
    }
}
