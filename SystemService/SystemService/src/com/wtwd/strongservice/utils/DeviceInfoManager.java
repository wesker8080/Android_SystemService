package com.wtwd.strongservice.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by wesker on 2017/11/2116:04.
 */

public class DeviceInfoManager {
    private static ActivityManager mActivityManager;

    public synchronized static ActivityManager getActivityManager(Context context) {
        if (mActivityManager == null) {
            mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        }
        return mActivityManager;
    }
    /**   * 计算已使用内存的百分比，并返回。
     * *   * @param context   *      可传入应用程序上下文。
     * * @return 已使用内存的百分比，以字符串形式返回。
     * */
    public static String getUsedPercentValue(Context context) {
        long totalMemorySize = getTotalMemory();
        long availableSize = getAvailableMemory(context) / 1024;
        int percent = (int) ((totalMemorySize - availableSize) / (float) totalMemorySize * 100);
        Log.e("wesker", "percent:" + percent + "%");
        return percent + "%";
    }
    /**
     *  获取系统总内存,返回字节单位为KB
     * @return 系统总内存
     * */
    public static long getTotalMemory() {
        long totalMemorySize = 0;
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();      //将非数字的字符替换为空
            totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll("\\D+", ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("wesker", "total:" + totalMemorySize);
        return totalMemorySize;
    }
    /**   * 获取当前可用内存，返回数据以字节为单位。
     * @param context 可传入应用程序上下文。
     * @return 当前可用内存。
     * */
    public static long getAvailableMemory(Context context) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        getActivityManager(context).getMemoryInfo(mi);
        return mi.availMem;
    }
     /* 获取当前进程的CPU使用率   * @return CPU的使用率   */
     public static float getCurProcessCpuRate()  {
         float totalCpuTime1 = getTotalCpuTime();
         float processCpuTime1 = getAppCpuTime();
         try    {
             Thread.sleep(360);
         }
         catch (Exception e)    {    }
         float totalCpuTime2 = getTotalCpuTime();
         float processCpuTime2 = getAppCpuTime();
         float cpuRate = 100 * (processCpuTime2 - processCpuTime1) / (totalCpuTime2 - totalCpuTime1);
         Log.e("wesker","rate:"+cpuRate);
         return cpuRate;
     }
    /**   * 获取当前进程的CPU使用时间   * @return 当前进程的CPU使用时间   */
    public static long getAppCpuTime()  {
        // 获取应用占用的CPU时间
        String[] cpuInfos = null;
        try {
            int pid = android.os.Process.myPid();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader( new FileInputStream("/proc/" + pid + "/stat")), 1000);
            String load = reader.readLine();
            reader.close();
            cpuInfos = load.split(" ");
        }
        catch (IOException ex)    { ex.printStackTrace();}
        long appCpuTime = Long.parseLong(cpuInfos[13])
                + Long.parseLong(cpuInfos[14])
                + Long.parseLong(cpuInfos[15])
                + Long.parseLong(cpuInfos[16]);
        return appCpuTime;
    }
    /**   * 获取总的CPU使用率   * @return CPU使用率   */
    public static float getTotalCpuRate() {
        float totalCpuTime1 = getTotalCpuTime();
        float totalUsedCpuTime1 = totalCpuTime1 - sStatus.idletime;
        try {      Thread.sleep(360);    }
        catch (InterruptedException e)
        {      e.printStackTrace();    }
        float totalCpuTime2 = getTotalCpuTime();
        float totalUsedCpuTime2 = totalCpuTime2 - sStatus.idletime;
        float cpuRate = 100 * (totalUsedCpuTime2 - totalUsedCpuTime1) / (totalCpuTime2 - totalCpuTime1);
        Log.e("wesker","rate:"+cpuRate);
        return cpuRate;
    }
    /**   * 获取系统总CPU使用时间   * @return 系统CPU总的使用时间   */
    public static long getTotalCpuTime()  {
        String[] cpuInfos = null;
        try    {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/stat")), 1000);
            String load = reader.readLine();
            reader.close();
            cpuInfos = load.split(" ");
        }    catch (IOException ex)
        {      ex.printStackTrace();    }
        //   long totalCpu = Long.parseLong(cpuInfos[2])
        //    + Long.parseLong(cpuInfos[3]) + Long.parseLong(cpuInfos[4])
        //  + Long.parseLong(cpuInfos[6]) + Long.parseLong(cpuInfos[5])
        //    + Long.parseLong(cpuInfos[7]) + Long.parseLong(cpuInfos[8]);
        sStatus.usertime = Long.parseLong(cpuInfos[2]);
        sStatus.nicetime = Long.parseLong(cpuInfos[3]);
        sStatus.systemtime = Long.parseLong(cpuInfos[4]);
        sStatus.idletime = Long.parseLong(cpuInfos[5]);
        sStatus.iowaittime = Long.parseLong(cpuInfos[6]);
        sStatus.irqtime = Long.parseLong(cpuInfos[7]);
        sStatus.softirqtime = Long.parseLong(cpuInfos[8]);
        return sStatus.getTotalTime();
    }
    static Status sStatus = new Status();



    static class Status {
        public long usertime;
        public long nicetime;
        public long systemtime;
        public long idletime;
        public long iowaittime;
        public long irqtime;
        public long softirqtime;
        public long getTotalTime() {
            return (usertime + nicetime + systemtime + idletime + iowaittime + irqtime + softirqtime);
        }
    }
}
