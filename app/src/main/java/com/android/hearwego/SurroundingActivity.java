package com.android.hearwego;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SurroundingActivity extends AppCompatActivity {

    private View decorView; //full screen 객체 선언
    private int	uiOption; //full screen 객체 선언

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.surrounding);

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

        ImageButton button_facility = findViewById(R.id.menu_facility); //메뉴1: 공공시설 이미지 버튼 객체 참조
        ImageButton button_subway = findViewById(R.id.menu_subway); //메뉴2: 지하철 이미지 버튼 객체 참조
        ImageButton button_toilet = findViewById(R.id.menu_toilet); //메뉴3: 화장실 이미지 버튼 객체 참조
        ImageButton button_hospital = findViewById(R.id.menu_hospital); //메뉴4: 병원 이미지 버튼 객체 참조
        ImageButton button_pharmacy = findViewById(R.id.menu_pharmacy); //메뉴5: 약국 이미지 버튼 객체 참조

    }
}
