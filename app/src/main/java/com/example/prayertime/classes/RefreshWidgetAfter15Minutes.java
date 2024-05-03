package com.example.prayertime.classes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.example.prayertime.widget.LargeWidgetProvider;
import com.example.prayertime.widget.LargeWidgetProviderService;
import com.example.prayertime.widget.SmallWidgetProvider;
import com.example.prayertime.widget.SmallWidgetProviderService;


public class RefreshWidgetAfter15Minutes  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::MyWakelockTag1");
        wakeLock.acquire(10000);

        AthanService.getNextPrayer(false);
        AthanService.actualPrayerCode = AthanService.nextPrayerCode;
        AthanService.ifActualSalatTime = false;

        if(LargeWidgetProvider.isEnabled){
            context.startService(new Intent(context, LargeWidgetProviderService.class));
        }

        if(SmallWidgetProvider.isEnabled){
            context.startService(new Intent(context, SmallWidgetProviderService.class));
        }
    }
}
