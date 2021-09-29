package com.android.hearwego;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RouteChoiceActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_WALK=120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_choice);

        Button walk_button = findViewById(R.id.walk_button);
        walk_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), RouteGuideActivity.class);
                startActivityForResult(intent, REQUEST_CODE_WALK);
            }
        });
    }
}