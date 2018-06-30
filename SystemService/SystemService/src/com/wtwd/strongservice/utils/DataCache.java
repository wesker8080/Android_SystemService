package com.wtwd.strongservice.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wesker on 2017/11/2216:07.
 */

public class DataCache {
    private static final String SHARENAME = "wtwd";
    private static volatile DataCache sInstance;

    private static SharedPreferences sPreferences;

    private DataCache(Context context) {
        if (sPreferences == null) {
            sPreferences = context.getSharedPreferences(SHARENAME, Context.MODE_MULTI_PROCESS|Context.MODE_WORLD_READABLE);
        }
    }
    public static DataCache getInstance(Context context) {
        DataCache instance = sInstance;
        if (sInstance == null) {
            synchronized (DataCache.class) {
                instance = sInstance;
                if (instance == null) {
                    instance = new DataCache(context);
                    sInstance = instance;
                }
            }
        }
        return instance;
    }
    public int getFirstInWeek() {
        return sPreferences.getInt("firstWeek",0);
    }

    public void setFirstInWeek(int str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putInt("firstWeek",str);
        editor.commit();
    }
    public int getFirstInMonth() {
        return sPreferences.getInt("firstMonth",0);
    }

    public void setFirstInMonth(int str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putInt("firstMonth",str);
        editor.commit();
    }
    public int getFirstInDay() {
        return sPreferences.getInt("firstDay",0);
    }

    public void setFirstInDay(int str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putInt("firstDay",str);
        editor.commit();
    }
    public boolean isFirstIn() {
        return sPreferences.getBoolean("isFirst", true);
    }

    public void setFirstInfalse() {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putBoolean("isFirst", false);
        editor.commit();
    }


    /********************************月数据流量*******************************/
    public String getMonthMobileRxBytes() {
        return sPreferences.getString("monthMobileRxBytes","null");
    }

    public void setMonthMobileRxBytes(String str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString("monthMobileRxBytes",str);
        editor.commit();
    }

    //月数据网络接收流量
    public String getMonthMobileTxBytes() {
        return sPreferences.getString("monthMobileTxBytes","null");
    }

    public void setMonthMobileTxBytes(String str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString("monthMobileTxBytes",str);
        editor.commit();
    }
    //初始月数据网络发送流量
    public long getInitMonMobileRxBytes() {
        return sPreferences.getLong("initMonMobileRxBytes",0);
    }

    public void setInitMonMobileRxBytes(long str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putLong("initMonMobileRxBytes",str);
        editor.commit();
    }


    //初始月数据网络接收流量
    public long getInitMonMobileTxBytes() {
        return sPreferences.getLong("initMonMobileTxBytes",0);
    }

    public void setInitMonMobileTxBytes(long str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putLong("initMonMobileTxBytes",str);
        editor.commit();
    }
    /********************************月数据流量*******************************/

    /********************************月WIFI流量*******************************/
    public String getMonthWifiRxBytes() {
        return sPreferences.getString("monthWifiRxBytes","null");
    }

    public void setMonthWifiRxBytes(String str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString("monthWifiRxBytes",str);
        editor.commit();
    }

    //月数据网络接收流量
    public String getMonthWifiTxBytes() {
        return sPreferences.getString("monthWifiTxBytes","null");
    }

    public void setMonthWifiTxBytes(String str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString("monthWifiTxBytes",str);
        editor.commit();
    }
    //初始月WIFI发送流量
    public long getInitMonWifiRxBytes() {
        return sPreferences.getLong("initMonWifiRxBytes",0);
    }

    public void setInitMonWifiRxBytes(long str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putLong("initMonWifiRxBytes",str);
        editor.commit();
    }


    //初始月WIFI接收流量
    public long getInitMonWifiTxBytes() {
        return sPreferences.getLong("initMonWifiTxBytes",0);
    }

    public void setInitMonWifiTxBytes(long str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putLong("initMonWifiTxBytes",str);
        editor.commit();
    }
    /********************************月WIFI流量*******************************/
    /********************************天数据流量*******************************/
    public long getInitDayMobileRxBytes() {
        return sPreferences.getLong("initDayMobileRxBytes",0);
    }

    public void setInitDayMobileRxBytes(long str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putLong("initDayMobileRxBytes",str);
        editor.commit();
    }
    public long getInitDayMobileTxBytes() {
        return sPreferences.getLong("initDayMobileTxBytes",0);
    }

    public void setInitDayMobileTxBytes(long str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putLong("initDayMobileTxBytes",str);
        editor.commit();
    }
    public String getDayMobileRxBytes() {
        return sPreferences.getString("dayMobileRxBytes","null");
    }

    public void setDayMobileRxBytes(String str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString("dayMobileRxBytes",str);
        editor.commit();
    }
    public String getDayMobileTxBytes() {
        return sPreferences.getString("dayMobileTxBytes","null");
    }

    public void setDayMobileTxBytes(String str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString("dayMobileTxBytes",str);
        editor.commit();
    }
    /********************************天数据流量*******************************/
    /********************************天WIFI流量*******************************/
    public long getInitDayWifiRxBytes() {
        return sPreferences.getLong("initDayWifiRxBytes",0);
    }

    public void setInitDayWifiRxBytes(long str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putLong("initDayWifiRxBytes",str);
        editor.commit();
    }
    public long getInitDayWifiTxBytes() {
        return sPreferences.getLong("initDayWifiTxBytes",0);
    }

    public void setInitDayWifiTxBytes(long str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putLong("initDayWifiTxBytes",str);
        editor.commit();
    }
    public String getDayWifiRxBytes() {
        return sPreferences.getString("dayWifiRxBytes","null");
    }

    public void setDayWifiRxBytes(String str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString("dayWifiRxBytes",str);
        editor.commit();
    }
    public String getDayWifiTxBytes() {
        return sPreferences.getString("dayWifiTxBytes","null");
    }

    public void setDayWifiTxBytes(String str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString("dayWifiTxBytes",str);
        editor.commit();
    }
    /********************************天WIFI流量*******************************/

    /********************************周数据流量*******************************/
    public long getInitWeekMobileRxBytes() {
        return sPreferences.getLong("initWeekMobileRxBytes",0);
    }

    public void setInitWeekMobileRxBytes(long str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putLong("initWeekMobileRxBytes",str);
        editor.commit();
    }
    public long getInitWeekMobileTxBytes() {
        return sPreferences.getLong("initWeekMobileTxBytes",0);
    }

    public void setInitWeekMobileTxBytes(long str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putLong("initWeekMobileTxBytes",str);
        editor.commit();
    }

    public String getWeekMobileRxBytes() {
        return sPreferences.getString("weekMobileRxBytes","null");
    }

    public void setWeekMobileRxBytes(String str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString("weekMobileRxBytes",str);
        editor.commit();
    }
    public String getWeekMobileTxBytes() {
        return sPreferences.getString("weekMobileTxBytes","null");
    }

    public void setWeekMobileTxBytes(String str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString("weekMobileTxBytes",str);
        editor.commit();
    }
    /********************************周数据流量*******************************/


    /********************************周WIFI流量*******************************/
    public long getInitWeekWifiRxBytes() {
        return sPreferences.getLong("initWeekWifiRxBytes",0);
    }

    public void setInitWeekWifiRxBytes(long str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putLong("initWeekWifiRxBytes",str);
        editor.commit();
    }
    public long getInitWeekWifiTxBytes() {
        return sPreferences.getLong("initWeekWifiTxBytes",0);
    }

    public void setInitWeekWifiTxBytes(long str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putLong("initWeekWifiTxBytes",str);
        editor.commit();
    }

    public String getWeekWifiRxBytes() {
        return sPreferences.getString("weekWifiRxBytes","null");
    }

    public void setWeekWifiRxBytes(String str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString("weekWifiRxBytes",str);
        editor.commit();
    }
    public String getWeekWifiTxBytes() {
        return sPreferences.getString("weekWifiTxBytes","null");
    }

    public void setWeekWifiTxBytes(String str) {
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString("weekWifiTxBytes",str);
        editor.commit();
    }
    /********************************周WIFI流量*******************************/
}
