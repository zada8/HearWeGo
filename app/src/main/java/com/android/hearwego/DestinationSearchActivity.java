package com.android.hearwego;

import android.Manifest;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DestinationSearchActivity extends AppCompatActivity implements TMapGpsManager.onLocationChangedCallback {

    //stt
    Intent intent;
    SpeechRecognizer mRecognizer;
    Button sttBtn;
    Button startBtn;
    TextView textView;
    final int PERMISSION = 1;
    //
    TMapView tMapView = null;
    TMapGpsManager tMapGpsManager = null;
    TMapData tMapData = null;

    String addressText;
    Double latitude;
    Double longitude;
    String locname;
    Geocoder geocoder = new Geocoder(this);
    List<Address> list;

    private static String API_KEY = "l7xx59d0bb77ddfc45efb709f48d1b31715c";

    TextToSpeech textToSpeech;

    private View decorView; //full screen 객체 선언
    private int	uiOption; //full screen 객체 선언


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.destination_search);

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



        //TextToSpeech 기본 설정
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.KOREAN);
                }
            }
        });

        //현재 위치 받아오기
        tMapData = new TMapData();

        tMapView = new TMapView(this);
        tMapView.setSKTMapApiKey(API_KEY);

        //TmapGps 설정
        tMapGpsManager = new TMapGpsManager(this);
        tMapGpsManager.setMinTime(1000);
        tMapGpsManager.setMinDistance(5);
        tMapGpsManager.setProvider(tMapGpsManager.NETWORK_PROVIDER);
        tMapGpsManager.OpenGps();

        //버튼 선언
        ImageButton nowgps_btn = findViewById(R.id.button1_nowgps);
        ImageButton previous = findViewById(R.id.previous);
        ImageButton home = findViewById(R.id.home);

        //현재위치확인 버튼 누르면 현재위치를 음성으로 안내할 수 있게 구현
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

        //이전 버튼 선언
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DestinationSearchActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //홈 버튼 선언
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DestinationSearchActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //stt
        // 퍼미션 체크
        if ( Build.VERSION.SDK_INT >= 23 ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO},PERMISSION);
        }
        // xml의 버튼과 텍스트 뷰 연결
        textView = (TextView)findViewById(R.id.sttResult_des);
        sttBtn = (Button) findViewById(R.id.mic_button_des);
        startBtn = (Button) findViewById(R.id.search_start_button);

        // RecognizerIntent 객체 생성
        intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");

        // 버튼을 클릭 이벤트 - 객체에 Context와 listener를 할당한 후 실행
        sttBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                textToSpeech.speak("목적지 주소를 음성으로 입력해주세요", TextToSpeech.QUEUE_FLUSH, null);
                // 딜레이를 1초 주기
                textToSpeech.playSilence(1000, TextToSpeech.QUEUE_ADD, null);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRecognizer = SpeechRecognizer.createSpeechRecognizer(DestinationSearchActivity.this);
                        mRecognizer.setRecognitionListener(listener);
                        mRecognizer.startListening(intent);
                    }
                }, 3000); //딜레이 타임 조절
/*
  */
            }
        });

    }

    //stt
    //RecognizerLntent 객체에 할당할 listener 생성
    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            Toast.makeText(getApplicationContext(), "음성 인식을 시작합니다.",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            //인식 결과가 준비되면 호출
            //말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줍니다.
            ArrayList<String> matches =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            for (int i = 0; i < matches.size(); i++){
                addressText = matches.get(i);
                textView.setText(addressText);   // 음성 인식한 데이터를 text로 변환해 표시
            }
            TextView t = (TextView) findViewById(R.id.sttResult_des);
            String tInput = t.getText().toString();
            textToSpeech.speak(tInput + "으로 목적지가 입력 되었습니다.", TextToSpeech.QUEUE_FLUSH, null);
            try {
                list = geocoder.getFromLocationName(addressText,10);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("TAG","주소 변환에서 에러발생");
            }
            latitude = list.get(0).getLatitude();
            longitude = list.get(0).getLongitude();
            locname = list.get(0).getFeatureName();
            list.remove(0);

        }
        @Override
        public void onBeginningOfSpeech() {}

        @Override
        public void onRmsChanged(float rmsdB) {}

        @Override
        public void onBufferReceived(byte[] buffer) {}

        @Override
        public void onEndOfSpeech() {       // 음성 인식이 제대로 되었을 때의 동작
            startBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    Intent intent = new Intent(DestinationSearchActivity.this, RouteGuideActivity.class);
                    intent.putExtra("name", locname);
                    intent.putExtra("latitude",latitude.toString());
                    intent.putExtra("longitude",longitude.toString());
                    startActivity(intent);

                }
            });

        }

        @Override
        public void onError(int error) {
            String message;

            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER 가 바쁨";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버가 이상함";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    break;
                default:
                    message = "알 수 없는 오류임";
                    break;
            }

            Toast.makeText(getApplicationContext(), "에러가 발생했습니다. : "
                    + message,Toast.LENGTH_SHORT).show();
        }



        @Override
        public void onPartialResults(Bundle partialResults) {}

        @Override
        public void onEvent(int eventType, Bundle params) {}
    };

    @Override
    public void onLocationChange(Location location) {
        tMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
        Log.d("현재위치-Destination", tMapView.getLocationPoint().toString());
    }

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
