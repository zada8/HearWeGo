package com.android.hearwego;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity {
    private View decorView; //full screen 객체 선언
    private int	uiOption; //full screen 객체 선언
    final String TAG = "BookmarkActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_bookmark);

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

        Button[] button_bookmark = new Button[10];
        button_bookmark[0] = findViewById(R.id.bookmark1);
        button_bookmark[1] = findViewById(R.id.bookmark2);
        button_bookmark[2] = findViewById(R.id.bookmark3);
        button_bookmark[3] = findViewById(R.id.bookmark4);
        button_bookmark[4] = findViewById(R.id.bookmark5);
        button_bookmark[5] = findViewById(R.id.bookmark6);
        button_bookmark[6] = findViewById(R.id.bookmark7);
        button_bookmark[7] = findViewById(R.id.bookmark8);
        button_bookmark[8] = findViewById(R.id.bookmark9);
        button_bookmark[9] = findViewById(R.id.bookmark10);

        /*
        CollectionReference cref = ((LogoActivity) LogoActivity.context_logo).db.

                collection("users");

        Query name = cref.document(((LogoActivity) LogoActivity.context_logo).userID).collection("name");
        button_bookmark[0].setText(name.toString());
        Query keywords = ((LogoActivity) LogoActivity.context_logo).keywordref;*/



        Button button_save = findViewById(R.id.save_bookmark); //즐겨찾기 등록 버튼 참조
        Button button_previous = findViewById(R.id.previous); //이전 버튼 객체 참조
        Button button_home = findViewById(R.id.home); // 홈 버튼 객체 참조

        //즐겨찾기 버튼
        button_bookmark[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookmarkActivity.this, BookmarkEdit.class);
                /*intent.putExtra("keyword", ((LogoActivity) LogoActivity.context_logo).user.keyword.get(0));
                intent.putExtra("locname", ((LogoActivity) LogoActivity.context_logo).user.locname.get(0));
                intent.putExtra("latitude", ((LogoActivity) LogoActivity.context_logo).user.latitude.get(0));
                intent.putExtra("longtitude", ((LogoActivity) LogoActivity.context_logo).user.longtitude.get(0));*/
                startActivity(intent);
                finish();
            }
        });

        //즐겨찾기 등록 버튼 누를 시 화면 전환
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookmarkActivity.this, AddBookmarkByAddressActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //이전 버튼 누를 시 화면 전환
        button_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookmarkActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //홈 버튼 누를 시 화면 전환
        button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookmarkActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}