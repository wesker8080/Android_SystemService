package com.wtwd.strongservice.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import com.wtwd.strongservice.AutoReceiver;
import com.wtwd.strongservice.utils.Utils;

/**
 * http://blog.csdn.net/liuzg1220
 * 
 * @author henry
 *
 */
public class Service1 extends Service {
	private static long CYCLE_TIME =   60 *1000;//周期执行时间


	/**
	 * 使用aidl 启动Service2
	 */
	private StrongService startS2 = new StrongService.Stub() {
		@Override
		public void stopService() throws RemoteException {
			Intent i = new Intent(getBaseContext(), Service2.class);
			getBaseContext().stopService(i);
		}

		@Override
		public void startService() throws RemoteException {
			Intent i = new Intent(getBaseContext(), Service2.class);
			getBaseContext().startService(i);
		}
	};

	/**
	 * 在内存紧张的时候，系统回收内存时，会回调OnTrimMemory， 重写onTrimMemory当系统清理内存时从新启动Service2
	 */
	@Override
	public void onTrimMemory(int level) {
		/*
		 * 启动service2
		 */
		startService2();

	}

	@Override
	public void onDestroy() {
		Log.e("wesker","service1 destory");
		super.onDestroy();
		startService2();
	}

	@Override
	public void onCreate() {
		startService2();
	}

	/**
	 * 判断Service2是否还在运行，如果不是则启动Service2
	 */
	private void startService2() {

		boolean isRun = Utils.isServiceWork(Service1.this,
				"com.wtwd.strongservice.service.Service2");
		if (!isRun) {
			try {
				startS2.startService();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		startTask();
		return START_STICKY;
	}
	public void startTask(){
		AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		long triggerAtTime = SystemClock.elapsedRealtime() + CYCLE_TIME;
		Intent intent2 = new Intent(this, AutoReceiver.class);
		intent2.setAction("android.system.service.do");
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent2, 0);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
	}
	@Override
	public IBinder onBind(Intent intent) {
		return (IBinder) startS2;
	}
}
