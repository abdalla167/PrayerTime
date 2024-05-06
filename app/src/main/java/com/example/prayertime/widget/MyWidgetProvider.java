
package com.example.prayertime.widget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class MyWidgetProvider extends AppWidgetProvider {

	public static Boolean isEnabled = false;

       @Override
       public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		   context.startService(new Intent(context, MyWidgetProviderService.class));
    }

	@Override
	public void onEnabled(Context context) {

		isEnabled = true;

		AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, MyWidgetProviderBroadcastReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0 , intent, 0);

		am.cancel(pi);

		//After 1 minute
		am.setRepeating(AlarmManager.RTC, System.currentTimeMillis() , 60 * 1000 , pi); // update widget every minute
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds){
		isEnabled = false;
	}
       
}
