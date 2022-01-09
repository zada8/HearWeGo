package com.android.hearwego;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class HelpActivity extends AppCompatActivity {

    private View decorView; //full screen 객체 선언
    private int	uiOption; //full screen 객체 선언

    TextToSpeech textToSpeech;

    int choice = 0; //안내 버튼 선택

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);


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

        /*TextToSpeech 기본 설정*/
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.KOREAN);
                    textToSpeech.setPitch(1.5f);
                    textToSpeech.setSpeechRate(1.0f);
                }
            }
        });


        Button button_help_navi = findViewById(R.id.help_navi); //길안내 버튼 객체 참조
        Button button_help_surrounding = findViewById(R.id.help_surrounding); //주변시설 버튼 객체 참조
        Button button_help_bookmark = findViewById(R.id.help_bookmark); //즐겨찾기 버튼 객체 참조
        Button button_help_setting = findViewById(R.id.help_setting); //설정 버튼 객체 참조

        Button button_previous = findViewById(R.id.previous); //이전 버튼 객체 참조
        Button button_home = findViewById(R.id.home); // 홈 버튼 객체 참조

        button_help_navi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    choice=R.raw.help_navi;
            textToSpeech.speak(readTxt(), TextToSpeech.QUEUE_FLUSH, null);
        }
        });

        button_help_surrounding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                choice=R.raw.help_surrounding;
                textToSpeech.speak(readTxt(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        button_help_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                choice=R.raw.help_bookmark;
                textToSpeech.speak(readTxt(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        button_help_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                choice=R.raw.help_setting;
                textToSpeech.speak(readTxt(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });


        //이전 버튼 누를 시 화면 전환
        button_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpActivity.this, SettingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //홈 버튼 누를 시 화면 전환
        button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private String readTxt() {//txt를 string으로
        String data = null;
        @SuppressLint("ResourceType") InputStream inputStream = getResources().openRawResource(choice);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int i;
        try {
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }

            data = new String(byteArrayOutputStream.toByteArray());
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    //tts
    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(textToSpeech!=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
            textToSpeech = null;
        }
    }
}

