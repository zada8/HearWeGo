package com.android.hearwego;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        ImageButton button_navi = findViewById(R.id.help); //메뉴1: 길 안내 이미지 버튼 객체 참조
        ImageButton button_surround = findViewById(R.id.logout); //메뉴2: 주변시설 이미지 버튼 객체 참조
        ImageButton button_bookmark = findViewById(R.id.withdraw); //메뉴3: 즐겨찾기 이미지 버튼 객체 참조
        ImageButton button_setting = findViewById(R.id.previous); //메뉴4: 설정 이미지 버튼 객체 참조
    }

}