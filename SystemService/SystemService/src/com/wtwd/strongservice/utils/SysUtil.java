package com.wtwd.strongservice.utils;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.TrafficStats;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by wesker on 2017/11/2114:36.
 */

public  class SysUtil {
    private static LocationManager locationManager;
    private static Context mContext;
    private static final int count = 5;//两次获取总流量的时间
    private  static Location location = null;
    private  static Location location1 = null;

    /**
     * 获取总的接受数据包数
     * TrafficStats这个类是专门统计流量的
     * @return
     */
    public static long getCurrentTotalRxBytes(){
         return TrafficStats.getTotalRxBytes();
    }
    public static long getCurrentTotalTxBytes(){
         return TrafficStats.getTotalTxBytes();
    }
    public static long getCurrentMobileRxBytes(){
        return TrafficStats.getMobileRxBytes();
    }
    public static long getCurrentMobileTxBytes(){
        return TrafficStats.getMobileTxBytes();
    }
    /**
     * 核心方法，得到当前网速,
     * 先获取此刻的总流量，譬如说00:00:00时刻的总使用流量，然后再统计00:00:01时刻的总使用流量，那么两者相减便是这1秒钟所使用的流量
     * 这里提供核心方法 具体实现可以用handler或timer去实现
     * @return traffic_data /count count获取流量差值之前的时间
     */
    private static int getNetSpeed() {
        long traffic_data = TrafficStats.getTotalRxBytes() - getCurrentTotalRxBytes();
        return (int)traffic_data /count ;
    }

    public static Map<String,String> getGpsLocation(Context context) {
        mContext = context;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        //接着需要选择一个位置提供器来确定设备当前的位置，一个有三种方式。
        //获取有哪些位置提供器可用
        List<String> providers = locationManager.getProviders(true);//传入 true 就表示只有启用的位置提供器才会被返回。
        System.out.println(providers);//输出开启了哪些位置提供器
        String provider = null;
        String provider1 = null;
        /*if (providers.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        }else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            provider1 = LocationManager.NETWORK_PROVIDER;
        } else {
            Toast.makeText(mContext, "没有可用的位置提供器", Toast.LENGTH_LONG).show();
            //return;
        }*/
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setSpeedRequired(true);
        provider = locationManager.getBestProvider(criteria,true);
        if(provider != null){
             location = locationManager.getLastKnownLocation(provider);//通过provider获取当前位置
            if(location == null){
                location = locationManager.getLastKnownLocation("network");
            }
        }
        //监听器：监听位置的变化
        //每隔5秒检测一次移动距离，当移动距离大于1米就调用监听器的onLocationChanged方法
        locationManager.requestLocationUpdates("network", 5000, 0, listener);
        if (location != null) {
            return showLocation(location);
        }
        return null;
    }
    public static String getIccid(Context context){
        TelephonyManager telManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
       return telManager.getSimSerialNumber();  //取出ICCID:集成电路卡识别码（固化在手机SIM卡中,就是SIM卡的序列号）
    }

    static LocationListener listener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            System.out.println("状态改变");
        }

        @Override
        public void onProviderEnabled(String provider) {
            System.out.println("提供器启动");
        }

        @Override
        public void onProviderDisabled(String provider) {
            System.out.println("提供器关闭");
        }

        @Override
        public void onLocationChanged(Location location) {
            System.out.println("位置改变");
            showLocation(location);
        }
    };
    //显示经纬度的函数
    private static Map<String,String> showLocation(Location location) {
        Map<String,String> gpsMap = new HashMap<>();
        gpsMap.put("latitude", String.valueOf(location.getLatitude()));
        gpsMap.put("longitude", String.valueOf(location.getLongitude()));
        gpsMap.put("provider",location.getProvider());
        gpsMap.put("accuracy", String.valueOf(location.getAccuracy()));
        String currentPosition = "纬度：" + location.getLatitude() + "\n" + "经度：" + location.getLongitude();//获取经纬度
        Toast.makeText(mContext, currentPosition, Toast.LENGTH_LONG).show();
        return gpsMap;
    }
    public static int getInitWeek(){
        long time=System.currentTimeMillis();
        Date weekDate=new Date(time);
        SimpleDateFormat format=new SimpleDateFormat("E");
        String week = format.format(weekDate);
        int currentWeek = 0;
        switch (week){
            case "周一":currentWeek = 1;break;
            case "周二":currentWeek =  2;break;
            case "周三":currentWeek =  3;break;
            case "周四":currentWeek =  4;break;
            case "周五":currentWeek =  5;break;
            case "周六":currentWeek =  6;break;
            case "周日":currentWeek =  7;break;
        }
        Log.e("wesker","currentWeek:"+currentWeek);
        return currentWeek;
    }
}
