package com.android.hearwego;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private View 	decorView; //full screen 객체 선언
    private int	uiOption; //full screen 객체 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

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
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION); //소프트 키 숨기기


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