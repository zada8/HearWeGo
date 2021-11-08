package com.android.hearwego;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class RouteGuideActivity extends AppCompatActivity implements TMapGpsManager.onLocationChangedCallback{

    private View decorView; //full screen 객체 선언
    private int	uiOption; //full screen 객체 선언

    /*텍스트뷰 선언*/
    TextView destination_text;

    String appKey = "l7xx59d0bb77ddfc45efb709f48d1b31715c"; //appKey

    /*TMAP 필요 변수 선언*/
    TMapGpsManager tMapGpsManager = null;
    TMapView tMapView = null;
    TMapData tMapData = null;
    TMapPoint nowPoint = null;

    /*SKT 타워 위도와 현재 위치의 위도를 비교하기 위한 변수*/
    String SKT_latitude = Double.toString(37.566474);
    String n_latitude = null;

    /*TTS 변수 설정*/
    TextToSpeech textToSpeech;

    /*버튼 선언*/
    Button nowgps_btn;
    Button button_previous;
    Button button_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.route_guide);

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
                if(status != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.KOREAN);
                }
            }
        });

        /*TMAP 기본 설정*/
        tMapData = new TMapData();
        tMapView = new TMapView(this);
        tMapView.setSKTMapApiKey(appKey);
        tMapGpsManager = new TMapGpsManager(this);
        tMapGpsManager.setMinTime(1000);
        tMapGpsManager.setMinDistance(5);
        tMapGpsManager.setProvider(tMapGpsManager.NETWORK_PROVIDER);
        tMapGpsManager.OpenGps();

        /*버튼 설정*/
        Button nowgps_btn = findViewById(R.id.button2_nowgps);
        Button button_previous = findViewById(R.id.previous); //이전 이미지 버튼 객체 참조
        Button button_home = findViewById(R.id.home); // 홈 이미지 버튼 객체 참조

        /*인텐트 받아들임*/
        Intent intent = getIntent();
        String nameData = intent.getStringExtra("name");
        //Double latitude = intent.getDoubleExtra("latitude", 0);
        //Double longitude = intent.getDoubleExtra("longitude", 0);

        destination_text = findViewById(R.id.destination_text);
        destination_text.setText(nameData);

        /*현재 위치 확인 버튼 누를 시*/
        nowgps_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TMapPoint nowpoint = tMapView.getLocationPoint();

                tMapData.convertGpsToAddress(nowpoint.getLatitude(), nowpoint.getLongitude(), new TMapData.ConvertGPSToAddressListenerCallback() {
                    @Override
                    public void onConvertToGPSToAddress(String s) {
                        textToSpeech.setPitch(1.5f);
                        textToSpeech.setSpeechRate(1.0f);
                        textToSpeech.speak("현재 위치는 "+s+"입니다", TextToSpeech.QUEUE_FLUSH, null);
                    }
                });
            }
        });

        //이전 버튼 누를 시 화면 전환
        button_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //홈 버튼 누를 시 화면 전환
        button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RouteGuideActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }

    /*보행자 경로 JSON 파일을 가져오는 함수*/
    public void getRoute(){

    }

    /*Asynctask 클래스 NetworkTask 생성*/
    public class NetworkTask extends AsyncTask<Void, Void, String>{
        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values){
            this.url = url;
            this.values = values;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String result; //요청 결과를 저장하는 변수
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); //url로부터 결과를 얻어온다.
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            try{
                //전체 데이터를 제이슨 객체로 변환
                JSONObject root = new JSONObject(s);
                System.out.println("제일 상위" + root);

                //전체 데이터중에 features 리스트의 첫번째 객체를 가지고 오기
                JSONObject features = (JSONObject)root.getJSONArray("features").get(0);
                System.out.println("상위에서 첫번째 리스트 : " + features);

                //리스트의 첫번째 객체에 있는 geometry 가져오기
                JSONObject geometry = features.getJSONObject("geometry");
                System.out.println("리스트에서 geometry 객체 : " + geometry);

                //최종적으로 위도와 경도를 가져온다.
                //String latitude = geometry.getJ
                //
                // SONArray("coordinates").get(0).toString();
                //String longtitude = geometry.getJSONArray("coordinates").get(1).toString();
                //textView.setMovementMethod(new ScrollingMovementMethod());
                //textView.setText(root.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /*사용자 위치가 변경되면 실행되는 함수*/
    @Override
    public void onLocationChange(Location location) {
        //현재 위치의 위도, 경도를 받아옴
        tMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
        nowPoint = tMapView.getLocationPoint();
        Log.d("현재위치", nowPoint.toString());

        /*Tmap 기본 위치가 SKT 타워로 설정되어있음.
         * SKT 타워 주변의 병원이 뜨지 않게 만들기 위해서
         * SKT 타워 경도와 진짜 현재 위치의 경도를 비교*/
        n_latitude = Double.toString(nowPoint.getLatitude());
        if (n_latitude.equals(SKT_latitude) == true) {
            Log.d("현재위치-SKT타워O", "현재위치가 SKT 타워로 설정되어있습니다.");
        } else {
            //현재 위치 탐색 완료
            Log.d("현재위치-SKT타워X", "실행되었습니다.");
        }
    }


}