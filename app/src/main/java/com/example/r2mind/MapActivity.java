package com.example.r2mind;


import android.graphics.Color;
import android.graphics.PointF;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.CircleOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.widget.LocationButtonView;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private MapView mapView;
    //내위치 찾기 버튼
    private LocationButtonView locationButtonView;
    //LocationManager 의 상위호환 (저전력, 정확도 향상, 새로운 기능, API 간소화, 범용화)
    private FusedLocationSource locationSource;
    //마커
    private Marker myMarker = new Marker();
    private Marker setMarker = new Marker();
    //원그리기
    private CircleOverlay touchCircleOverlay = new CircleOverlay();
    private CircleOverlay myCircleOverlay = new CircleOverlay();
    private CircleOverlay fixCircleOverlay = new CircleOverlay();

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    ///// 실험할려는 버튼과 텍스트뷰
    private Button homeLocation;
    private Button schoolLocation;
    private Button specLocation;
    private TextView viewLat;
    private TextView viewLng;
    private Button circle20;
    private Button circle40;
    private Button circle60;
    private Button circle80;
    private int radius = 20;
    ///////////////////////
    //카메라 이동에 사용
    private CameraUpdate cameraUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        hideNavigationBar();
        init();

        mapView = findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);

        //비동기로 naverMap 객체를 얻기위한 함수
        mapView.getMapAsync(this);
        locationButtonView = findViewById(R.id.locationbuttonView);
        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);
    }
    private void init(){
        homeLocation = (Button)findViewById(R.id.setHome);
        schoolLocation = (Button)findViewById(R.id.setSchool);
        specLocation = (Button)findViewById(R.id.setPlace);
        viewLat = (TextView)findViewById(R.id.locationLat);
        viewLng = (TextView)findViewById(R.id.location_Lng);
        circle20 = (Button)findViewById(R.id.c20);
        circle20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radius = 20;
                Toast.makeText(getApplicationContext(), "원의 반경이 20m로 설정되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        circle40 = (Button)findViewById(R.id.c40);
        circle40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radius = 40;
                Toast.makeText(getApplicationContext(), "원의 반경이 40m로 설정되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        circle60 = (Button)findViewById(R.id.c60);
        circle60.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radius = 60;
                Toast.makeText(getApplicationContext(), "원의 반경이 60m로 설정되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        circle80 = (Button)findViewById(R.id.c80);
        circle80.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radius = 80;
                Toast.makeText(getApplicationContext(), "원의 반경이 80m로 설정되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.actionbar_nosetting, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onMapReady(@NonNull final NaverMap naverMap) {
        naverMap.setMapType(NaverMap.MapType.Hybrid);
        locationButtonView.setMap(naverMap);
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.NoFollow);

        fixCircleOverlay.setCenter(new LatLng(36.54393309566764, 128.79502300564747));
        fixCircleOverlay.setColor(Color.argb(50,0,0,255));
        fixCircleOverlay.setRadius(40);
        fixCircleOverlay.setMap(naverMap);
        naverMap.addOnLocationChangeListener(new NaverMap.OnLocationChangeListener() {
            @Override
            public void onLocationChange(@NonNull Location location) {
                LatLng latlng = new LatLng(36.54393309566764, 128.79502300564747);
                double distance = latlng.distanceTo(new LatLng(location.getLatitude(),location.getLongitude()));
                if((int)distance > 20){
                    Toast.makeText(getApplicationContext(),"20m 벗어났다 친구야!!!!" , Toast.LENGTH_SHORT).show();
                }

                myCircleOverlay.setCenter(new LatLng(location.getLatitude(), location.getLongitude()));
                myCircleOverlay.setRadius(radius);
                myCircleOverlay.setColor(Color.argb(50, 0, 255, 0));
                myCircleOverlay.setMap(naverMap);
            }
        });

        naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {
                myMarker.setPosition(new LatLng(latLng.latitude, latLng.longitude));
                myMarker.setCaptionText("누른곳");
                myMarker.setMap(naverMap);

                touchCircleOverlay.setCenter(new LatLng(latLng.latitude, latLng.longitude));
                touchCircleOverlay.setRadius(radius);
                touchCircleOverlay.setColor(Color.argb(50, 255, 0, 0));
                touchCircleOverlay.setMap(naverMap);

                viewLat.setText("위도 ="+ latLng.latitude);
                viewLng.setText("경도 ="+ latLng.longitude);
            }
        });
        homeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMarker.setPosition(new LatLng(35.8270163642008,128.5297437081843));
                setMarker.setCaptionText("집");
                setMarker.setMap(naverMap);
                cameraUpdate = CameraUpdate.scrollTo(new LatLng(35.8270163642008,128.5297437081843));
                naverMap.moveCamera(cameraUpdate);
                Toast.makeText(getApplicationContext(), "집을 선택하셨습니다.", Toast.LENGTH_SHORT).show();

                viewLat.setText("위도 =35.8270163642008");
                viewLng.setText("경도 =128.5297437081843");
            }
        });
        schoolLocation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setMarker.setPosition(new LatLng(36.54358194590261, 128.79609904977818));
                setMarker.setCaptionText("학교");
                setMarker.setMap(naverMap);
                cameraUpdate = CameraUpdate.scrollTo(new LatLng(36.54358194590261,128.79609904977818));
                naverMap.moveCamera(cameraUpdate);
                Toast.makeText(getApplicationContext(), "학교를 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                viewLat.setText("위도 =36.54358194590261");
                viewLng.setText("경도 =128.79609904977818");
            }
        });
        specLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMarker.setPosition(new LatLng(36.544531101315485, 128.7932009444453));
                setMarker.setCaptionText("모임장소");
                setMarker.setMap(naverMap);
                cameraUpdate = CameraUpdate.scrollTo(new LatLng(36.544531101315485,128.7932009444453));
                naverMap.moveCamera(cameraUpdate);
                Toast.makeText(getApplicationContext(), "모임장소를 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                viewLat.setText("위도 =36.544531101315485");
                viewLng.setText("경도 =128.7932009444453");
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void hideNavigationBar() {
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.d("is on?", "Turning immersive mode mode off. ");
        } else {
            Log.d("is on?", "Turning immersive mode mode on.");
        }
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);
    }
}
