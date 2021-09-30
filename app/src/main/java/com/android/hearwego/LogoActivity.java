package com.android.hearwego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class LogoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo);

        ImageButton button_login = findViewById(R.id.google_login); // 구글 로그인 이미지 버튼 객체 참조

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogoActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
