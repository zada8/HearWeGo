package com.android.hearwego;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        ImageButton button_help = findViewById(R.id.help); //도움말 이미지 버튼 객체 참조
        ImageButton button_logout = findViewById(R.id.logout); //로그아웃 이미지 버튼 객체 참조
        ImageButton button_withdraw = findViewById(R.id.withdraw); //회원탈퇴 이미지 버튼 객체 참조
        ImageButton button_previous = findViewById(R.id.previous); //이전 이미지 버튼 객체 참조
        ImageButton button_home = findViewById(R.id.home); // 홈 이미지 버튼 객체 참조
    }

}
