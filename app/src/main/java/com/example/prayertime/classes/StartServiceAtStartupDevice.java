
package com.example.prayertime.classes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class StartServiceAtStartupDevice extends BroadcastReceiver{
	@Override
	public void onReceive(Context context, Intent intent) {
		
		if(intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED))
		   {
			   context.startService(new Intent(context, AthanService.class));

			   AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
			   Intent AthanServiceBroasdcastReceiverIntent = new Intent(context, RefreshDayServiceManager.class);
			   PendingIntent pi = PendingIntent.getBroadcast(context, 70, AthanServiceBroasdcastReceiverIntent, PendingIntent.FLAG_IMMUTABLE);

			   am.cancel(pi);

			   Calendar calendar = Calendar.getInstance();
			   calendar.set(Calendar.HOUR_OF_DAY, 00);
			   calendar.set(Calendar.MINUTE, 01);
			   calendar.set(Calendar.SECOND, 00);

			   am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
		   }
	}
}
