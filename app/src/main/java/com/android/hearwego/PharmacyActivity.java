package com.android.hearwego;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;
import com.skt.Tmap.poi_item.TMapPOIItem;

import java.util.ArrayList;

public class PharmacyActivity extends AppCompatActivity implements TMapGpsManager.onLocationChangedCallback {

    private View decorView; //full screen 객체 선언
    private int	uiOption; //full screen 객체 선언

    String appKey = "l7xx59d0bb77ddfc45efb709f48d1b31715c"; //appKey

    /*TMAP 필요 변수 선언*/
    TMapGpsManager tMapGps = null;
    TMapView tMapView = null;
    TMapData tMapData = null;
    TMapPoint nowPoint = null;

    /*SKT 타워 위도와 현재 위치의 위도를 비교하기 위한 변수*/
    String SKT_latitude = Double.toString(37.566474);
    String n_latitude = null;

    /*버튼선언*/
    Button button_pharmacy1;
    Button button_pharmacy2;
    Button button_pharmacy3;
    Button button_pharmacy4;
    Button button_pharmacy5;
    Button button_pharmacy6;
    Button button_pharmacy7;
    Button button_pharmacy8;
    Button button_pharmacy9;
    Button button_pharmacy10;

    /*switch문 사용 위한 변수*/
    int num=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_pharmacy);

        /*Tmap 기본설정*/
        tMapData = new TMapData();
        tMapView = new TMapView(this);
        tMapView.setSKTMapApiKey(appKey);

        /*위치 권한 요청*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        /*TMapGPS 설정*/
        tMapGps = new TMapGpsManager(this);
        tMapGps.setMinTime(1000);
        tMapGps.setMinDistance(5);
        tMapGps.setProvider(tMapGps.NETWORK_PROVIDER);
        tMapGps.OpenGps();

        ActionBar actionBar = getSupportActionBar(); //액션바(패키지명) 숨김처리
        actionBar.hide();

        /*전체 화면 모드 -> 소프트 키 없앰*/
        decorView = getWindow().getDecorView();
        uiOption = getWindow().getDecorView().getSystemUiVisibility();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            uiOption |= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            uiOption |= View.SYSTEM_UI_FLAG_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            uiOption |= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOption);

        /*카테고리 버튼 id 지정*/
        button_pharmacy1 = findViewById(R.id.pharmacy1);
        button_pharmacy2 = findViewById(R.id.pharmacy2);
        button_pharmacy3 = findViewById(R.id.pharmacy3);
        button_pharmacy4 = findViewById(R.id.pharmacy4);
        button_pharmacy5 = findViewById(R.id.pharmacy5);
        button_pharmacy6 = findViewById(R.id.pharmacy6);
        button_pharmacy7 = findViewById(R.id.pharmacy7);
        button_pharmacy8 = findViewById(R.id.pharmacy8);
        button_pharmacy9 = findViewById(R.id.pharmacy9);
        button_pharmacy10 = findViewById(R.id.pharmacy10);

        Button button_previous = findViewById(R.id.previous); //이전 이미지 버튼 객체 참조
        Button button_home = findViewById(R.id.home); // 홈 이미지 버튼 객체 참조

        //이전 버튼 누를 시 화면 전환
        button_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PharmacyActivity.this, SurroundingActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //홈 버튼 누를 시 화면 전환
        button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PharmacyActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onLocationChange(Location location) {
        tMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
        nowPoint = tMapView.getLocationPoint();
        Log.d("카테고리-현재위치", nowPoint.toString());

        /*Tmap 기본 위치가 SKT 타워로 설정되어있음.
         * SKT 타워 주변의 병원이 뜨지 않게 만들기 위해서
         * SKT 타워 경도와 진짜 현재 위치의 경도를 비교*/
        n_latitude = Double.toString(nowPoint.getLatitude());
        if(n_latitude.equals(SKT_latitude) == true){
            Log.d("현재위치-SKT타워O", "실행되었습니다.");
        } else{
            //현재 위치 탐색 완료 후 주변 공공기관 찾기 시작
            Log.d("현재위치-SKT타워X", "실행되었습니다.");
            //주변 반경 2km 지정, 가까운 순서대로 출력, 버튼이 10개라 10개의 공공기관을 가져온다.
            tMapData.findAroundNamePOI(nowPoint, "약국", 2, 20, new TMapData.FindAroundNamePOIListenerCallback() {
                @Override
                public void onFindAroundNamePOI(ArrayList<TMapPOIItem> arrayList) {
                    for(int i = 0;i<20;i++){
                        num = num+1;
                        if(num==10){
                            break;
                        }
                        TMapPOIItem item = arrayList.get(i);
                        Log.d("약국-현재위치이름", item.getPOIName());
                        if(item.getPOIName().contains("주차장")){
                            num=num-1;
                            continue;
                        }
                        switch (num){
                            case 0:
                                button_pharmacy1.setText(item.getPOIName());
                                break;
                            case 1:
                                button_pharmacy2.setText(item.getPOIName());
                                break;
                            case 2:
                                button_pharmacy3.setText(item.getPOIName());
                                break;
                            case 3:
                                button_pharmacy4.setText(item.getPOIName());
                                break;
                            case 4:
                                button_pharmacy5.setText(item.getPOIName());
                                break;
                            case 5:
                                button_pharmacy6.setText(item.getPOIName());
                                break;
                            case 6:
                                button_pharmacy7.setText(item.getPOIName());
                                break;
                            case 7:
                                button_pharmacy8.setText(item.getPOIName());
                                break;
                            case 8:
                                button_pharmacy9.setText(item.getPOIName());
                                break;
                            case 9:
                                button_pharmacy10.setText(item.getPOIName());
                                break;
                            default:
                                Log.d("약국-오류", "해당하는 버튼이 없습니다.");
                        }
                    }
                }
            });
        }
    }
}

