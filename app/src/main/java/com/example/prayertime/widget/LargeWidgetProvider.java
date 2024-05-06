
package com.example.prayertime.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class LargeWidgetProvider extends AppWidgetProvider {

	public static Boolean isEnabled = false;

    @Override
	public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

		context.startService(new Intent(context, LargeWidgetProviderService.class));

		isEnabled = true;

		AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, LargeWidgetProviderBroadcastReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 19 , intent, PendingIntent.FLAG_UPDATE_CURRENT);

		//After 1 minute
		am.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 60000, pi); // update widget every minute
    }

	@Override
	public void onEnabled(Context context) {
		context.startService(new Intent(context, LargeWidgetProviderService.class));
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds){

    	isEnabled = false;

		AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, LargeWidgetProviderBroadcastReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 19 , intent, PendingIntent.FLAG_UPDATE_CURRENT);
		am.cancel(pi);
	}
       
}
