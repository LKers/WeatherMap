package com.weathermap.android;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public LocationClient mLocationClient;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: " + "start!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());

        List<String> permissionList = new ArrayList<>();
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this, permissions, 1);
        }else{
            requestLocation();
        }


    //    setContentView(R.layout.activity_main);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        /*
        if(prefs.getString("weather",null) != null){
            Intent intent = new Intent(this,WeatherActivity.class);
            startActivity(intent);
            finish();
        }*/
    }
    private void requestLocation(){
       initLocation();
        mLocationClient.start();
    }

    private void initLocation() {
        LocationClientOption option=new LocationClientOption();
//        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String[] permissions,  int[] grantResults) {
        switch(requestCode){
            case 1:
                if(grantResults.length > 0){
                    for(int result : grantResults){
                        if(result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this, "必须同意所有授权才能使用", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                }else{
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {


            Log.d(TAG, "onReceiveLocation: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            Intent intent=new Intent(MainActivity.this,MyService.class);
            intent.putExtra("province",bdLocation.getProvince());
            intent.putExtra("city",bdLocation.getCity());
            intent.putExtra("county",bdLocation.getDistrict());
            Log.d(TAG, "onReceiveLocation: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+bdLocation.getProvince());
            Log.d(TAG, "onReceiveLocation: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+bdLocation.getCity());
            Log.d(TAG, "onReceiveLocation: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+bdLocation.getDistrict());
            Log.d(TAG, "onReceiveLocation: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            startService(intent);
         /*  if(bdLocation.getLocType() == BDLocation.TypeGpsLocation || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                navigateTo(bdLocation);
                Log.d(TAG, "onReceiveLocation: " + bdLocation.getLatitude() +"  " +bdLocation.getLongitude() );
            }*/
/*
            StringBuilder sb = new StringBuilder();
            sb.append("纬度:" ).append(bdLocation.getLatitude()).append("\n");
            sb.append("经度:").append(bdLocation.getLongitude()).append("\n");
            sb.append("国家:" ).append(bdLocation.getCountry()).append("\n");
            sb.append("省:" ).append(bdLocation.getProvince()).append("\n");
            sb.append("市:" ).append(bdLocation.getCity()).append("\n");
            sb.append("区:" ).append(bdLocation.getDistrict()).append("\n");
            sb.append("街道:" ).append(bdLocation.getStreet()).append("\n");
            sb.append("getBuildingName:" ).append(bdLocation.getBuildingName()).append("\n");
            sb.append("getCityCode:" ).append(bdLocation.getCityCode()).append("\n");
            sb.append("getAddrStr:" ).append(bdLocation.getAddrStr()).append("\n");
            sb.append("getFloor:" ).append(bdLocation.getFloor()).append("\n");
            sb.append("getTime:" ).append(bdLocation.getTime()).append("\n");
            sb.append("getGpsCheckStatus:" ).append(bdLocation.getGpsCheckStatus()).append("\n");
            sb.append("getOperators:" ).append(bdLocation.getOperators()).append("\n");
            sb.append("定位方式:");
            if(bdLocation.getLocType() == BDLocation.TypeGpsLocation){
                sb.append("GPS");
            }else if(bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                sb.append("网络");
            }

            Toast.makeText(MainActivity.this, ""+ sb, Toast.LENGTH_SHORT).show();
*/
        }
    }

}

