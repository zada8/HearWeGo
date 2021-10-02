package com.android.hearwego;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {

    private View decorView; //full screen 객체 선언
    private int	uiOption; //full screen 객체 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        ActionBar actionBar = getSupportActionBar(); //액션바(패키지명) 숨김처리
        actionBar.hide();

        /*전체 화면 모드 -> 소프트 키 없앰*/
        decorView = getWindow().getDecorView();
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH )
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN )
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT )
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility( uiOption );


        ImageButton button_help = findViewById(R.id.help); //도움말 이미지 버튼 객체 참조
        ImageButton btn_logout = findViewById(R.id.btn_logout); //로그아웃 이미지 버튼 객체 참조
        ImageButton btn_withdraw = findViewById(R.id.btn_withdraw); //회원탈퇴 이미지 버튼 객체 참조
        ImageButton button_previous = findViewById(R.id.previous); //이전 이미지 버튼 객체 참조
        ImageButton button_home = findViewById(R.id.home); // 홈 이미지 버튼 객체 참조

        //이전 버튼 누를 시 화면 전환
        button_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //홈 버튼 누를 시 화면 전환
        button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //로그아웃 버튼 클릭시 동작
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(SettingActivity.this, "로그아웃", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SettingActivity.this,LogoActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //회원탈퇴 버튼 클릭시 동작
        btn_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LogoActivity)LogoActivity.context_logo).withdraw();
                Intent intent = new Intent(SettingActivity.this, LogoActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}
