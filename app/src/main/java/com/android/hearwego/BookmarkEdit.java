package com.android.hearwego;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.GeoPoint;

import static java.lang.Double.parseDouble;

public class BookmarkEdit extends AppCompatActivity {
    private View decorView; //full screen 객체 선언
    private int	uiOption; //full screen 객체 선언
    final String TAG = "BookmarkEdit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookmark_edit);

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

        Intent gintent = getIntent();
        String keyword = gintent.getStringExtra("keyword");
        String locname = gintent.getStringExtra("locname");
        double latitude = gintent.getDoubleExtra("latitude",0);
        double longitude = gintent.getDoubleExtra("longitude",0);
        GeoPoint geoPoint = new GeoPoint(latitude, longitude);


        TextView keyword_text = findViewById(R.id.keyword_text);
        TextView locname_text = findViewById(R.id.locname_text);
        keyword_text.setText(keyword);
        locname_text.setText(locname);


        Button button_delete_bookmark = findViewById(R.id.delete_bookmark);
        Button button_set_destination = findViewById(R.id.set_destination);

        // 삭제
        button_delete_bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ((LogoActivity) LogoActivity.context_logo).ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        LogoActivity.user = documentSnapshot.toObject(User.class);
                    }
                });
                LogoActivity.user.keywords.remove(keyword);
                LogoActivity.user.locnames.remove(keyword);
                LogoActivity.user.geopoints.remove(keyword);
                ((LogoActivity) LogoActivity.context_logo).ref.delete();
                ((LogoActivity) LogoActivity.context_logo).db.collection("users")
                        .document(((LogoActivity) LogoActivity.context_logo).userID)
                        .set(LogoActivity.user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                            }
                        });
                ((LogoActivity) LogoActivity.context_logo).ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        LogoActivity.user = documentSnapshot.toObject(User.class);
                    }
                });


                Intent intent = new Intent(BookmarkEdit.this, BookmarkActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //목적지 설정
        button_set_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookmarkEdit.this, RouteGuideActivity.class);
                intent.putExtra("name", locname);
                intent.putExtra("latitude", Double.toString(latitude));
                intent.putExtra("longitude", Double.toString(longitude));
                startActivity(intent);
                finish();
            }
        });

        Button button_previous = findViewById(R.id.previous); //이전 이미지 버튼 객체 참조
        Button button_home = findViewById(R.id.home); // 홈 이미지 버튼 객체 참조


        //이전 버튼 누를 시 화면 전환
        button_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookmarkEdit.this, BookmarkActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //홈 버튼 누를 시 화면 전환
        button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookmarkEdit.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}