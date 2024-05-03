
package com.example.prayertime.classes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.prayertime.widget.LargeWidgetProvider;
import com.example.prayertime.widget.LargeWidgetProviderService;
import com.example.prayertime.widget.SmallWidgetProvider;
import com.example.prayertime.widget.SmallWidgetProviderService;


public class RefreshDayServiceManager extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 25, new Intent(context, AthanServiceBroasdcastReceiver.class), PendingIntent.FLAG_IMMUTABLE);
        am.cancel(pi);
        PendingIntent piRefreshWidget = PendingIntent.getBroadcast(context, 37 , new Intent(context, RefreshWidgetAfter15Minutes.class), PendingIntent.FLAG_IMMUTABLE);
        am.cancel(piRefreshWidget);

        if(LargeWidgetProvider.isEnabled){
            context.startService(new Intent(context, LargeWidgetProviderService.class));
        }

        if(SmallWidgetProvider.isEnabled){
            context.startService(new Intent(context, SmallWidgetProviderService.class));
        }

        context.startService(new Intent(context,AthanService.class));
    }
}
