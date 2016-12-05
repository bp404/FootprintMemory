package com.bp404.footprintmemory;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private int i = 0;
    private  String Country;//国家信息
    private  String Province;//省信息
    private  String City;//城市信息
    private  String District;//城区信息
    private  String Road;//街道信息

    public  String nicknameStr;


    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，
                    //详见定位类型表
                    amapLocation.getLatitude();//获取经度
                    amapLocation.getLongitude();//获取纬度
                    amapLocation.getAccuracy();//获取精度信息
                    latitudeStr = String.valueOf(amapLocation.getLatitude());
                    longitudeStr = String.valueOf(amapLocation.getLongitude());

                    Date date = new Date(amapLocation.getTime());
                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为
                    //false，则没有此结果
                    amapLocation.getCountry();//国家信息
                    amapLocation.getProvince();//省信息
                    amapLocation.getCity();//城市信息
                    amapLocation.getDistrict();//城区信息
                    amapLocation.getRoad();//街道信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码
                    latitude.setText("经度:"+latitudeStr);
                    longitude.setText("纬度:"+longitudeStr);
                    Country = String.valueOf(amapLocation.getCountry());
                    Province = String.valueOf(amapLocation.getProvince());
                    City = String.valueOf(amapLocation.getCity());
                    District = String.valueOf(amapLocation.getDistrict());
                    Road = String.valueOf(amapLocation.getRoad());

                    String placeStr = Country+Province+City+District+Road;
                    place.setText(placeStr);

                    Message message = new Message();
                    message.obj = latitude;
                    handler.sendMessage(message);

                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError",
                            "location Error, ErrCode:"
                                    + amapLocation.getErrorCode() + ", errInfo:"
                                    + amapLocation.getErrorInfo());
                }
            }
        }
    };
        public TextView num;
        public TextView place;
    public TextView nicknametv;
        public TextView longitude;
        public TextView latitude;
        public String longitudeStr;
        public String latitudeStr;
        //声明AMapLocationClient类对象
        public AMapLocationClient mLocationClient = null;
        //声明AMapLocationClientOption对象
        public AMapLocationClientOption mLocationOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        nicknametv = (TextView)findViewById(R.id.nickname);
        nicknametv.setText(nicknameStr);
        Intent intent =getIntent();
        //getXxxExtra方法获取Intent传递过来的数据
        String msg=intent.getStringExtra("nickname");
        nicknametv.setText(msg);
        place = (TextView)findViewById(R.id.place);
        longitude = (TextView)findViewById(R.id.longitude);
        num = (TextView)findViewById(R.id.num);
        latitude = (TextView)findViewById(R.id.latitude);
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Battery_Saving，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否允许模拟位置,默认为false，允许模拟位置
        mLocationOption.setMockEnable(false);
        mLocationOption.setOnceLocation(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒
        mLocationOption.setHttpTimeOut(10000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

        new Thread(new ThreadShow()).start();
    }
    // handler类接收数据
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                num.setText("第"+Integer.toString(i++)+"次定位");
            }
        };
    };

    // 线程类
    class ThreadShow implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (true) {
                try {
                    mLocationClient.startLocation();
                    //

                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                    Thread.sleep(8000);
                    mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
                    mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}

