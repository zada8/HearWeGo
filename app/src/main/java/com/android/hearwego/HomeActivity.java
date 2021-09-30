package com.android.hearwego;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        ImageButton button_navi = findViewById(R.id.menu_navi); //메뉴1: 길 안내 이미지 버튼 객체 참조
        ImageButton button_surround = findViewById(R.id.menu_surround); //메뉴2: 주변시설 이미지 버튼 객체 참조
        ImageButton button_bookmark = findViewById(R.id.menu_bookmark); //메뉴3: 즐겨찾기 이미지 버튼 객체 참조
        ImageButton button_setting = findViewById(R.id.menu_setting); //메뉴4: 설정 이미지 버튼 객체 참조

        button_navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, DestinationSearchAcitivity.class);
                startActivity(intent);
                finish();
            }
        });

        button_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}