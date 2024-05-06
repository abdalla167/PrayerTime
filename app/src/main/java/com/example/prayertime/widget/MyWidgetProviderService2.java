
package com.example.prayertime.widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.widget.RemoteViews;


import androidx.annotation.Nullable;

import com.example.prayertime.R;
import com.example.prayertime.activities.SplashScreen_Activity;
import com.example.prayertime.classes.AthanService;
import com.example.prayertime.classes.UserConfig;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class MyWidgetProviderService2 extends Service {

    private NumberFormat formatter = new DecimalFormat("00");
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate()
    {
        pushWidgetUpdate(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        pushWidgetUpdate(this);
        return Service.START_STICKY;
    }

    public void pushWidgetUpdate(Context context)
    {
        try{
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout_2);

            Intent intent = new Intent(context, SplashScreen_Activity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            remoteViews.setOnClickPendingIntent(R.id.widgetMainBackground2, pendingIntent);


                remoteViews.setTextViewText(R.id.fajr_label, context.getResources().getString(R.string.fajr_widget));
                remoteViews.setTextViewText(R.id.duhr_label, context.getResources().getString(R.string.duhr_widget));
                remoteViews.setTextViewText(R.id.asr_label, context.getResources().getString(R.string.asr_widget));
                remoteViews.setTextViewText(R.id.maghrib_label, context.getResources().getString(R.string.maghrib_widget));
                remoteViews.setTextViewText(R.id.ishaa_label, context.getResources().getString(R.string.ishaa_widget));


            remoteViews.setTextViewText(R.id.fajrTime, AthanService.prayerTimes.getFajrFinalTime());
            remoteViews.setTextViewText(R.id.duhrTime, AthanService.prayerTimes.getDuhrFinalTime());
            remoteViews.setTextViewText(R.id.asrTime, AthanService.prayerTimes.getAsrFinalTime());
            remoteViews.setTextViewText(R.id.maghribTime, AthanService.prayerTimes.getMaghribFinalTime());
            remoteViews.setTextViewText(R.id.ishaaTime, AthanService.prayerTimes.getIshaaFinalTime());

            setActualPrayerTextColor(remoteViews , AthanService.actualPrayerCode);

            ComponentName myWidget = new ComponentName(context, MyWidgetProvider2.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            manager.updateAppWidget(myWidget, remoteViews);
        }
        catch(Exception ex)
        {
        }
    }

    private void setActualPrayerTextColor(RemoteViews remoteViews , int actualPrayercode){
        remoteViews.setInt(R.id.fajr_label, "setTextColor", Color.WHITE);
        remoteViews.setInt(R.id.fajrTime, "setTextColor", Color.WHITE);
        remoteViews.setInt(R.id.duhr_label, "setTextColor", Color.WHITE);
        remoteViews.setInt(R.id.duhrTime, "setTextColor", Color.WHITE);
        remoteViews.setInt(R.id.asr_label, "setTextColor", Color.WHITE);
        remoteViews.setInt(R.id.asrTime, "setTextColor", Color.WHITE);
        remoteViews.setInt(R.id.maghrib_label, "setTextColor", Color.WHITE);
        remoteViews.setInt(R.id.maghribTime, "setTextColor", Color.WHITE);
        remoteViews.setInt(R.id.ishaa_label, "setTextColor", Color.WHITE);
        remoteViews.setInt(R.id.ishaaTime, "setTextColor", Color.WHITE);

        int backgroundColor = R.drawable.blue_background;
        switch (UserConfig.getSingleton().getWidget_background_color())
        {
            case "silver":backgroundColor = R.drawable.silver_backgroud;break;
            case "gray":backgroundColor = R.drawable.gray_background;break;
            case "black":backgroundColor = R.drawable.black_background;break;
            case "red":backgroundColor = R.drawable.red_background;break;
            case "maroon":backgroundColor = R.drawable.maroon_background;break;
            case "yellow":backgroundColor = R.drawable.yellow_background;break;
            case "olive":backgroundColor = R.drawable.olive_background;break;
            case "lime":backgroundColor = R.drawable.lime_background;break;
            case "green":backgroundColor = R.drawable.green_backgroud;break;
            case "aqua":backgroundColor = R.drawable.aqua_backgroud;break;
            case "teal":backgroundColor = R.drawable.teal_background;break;
            case "blue":backgroundColor = R.drawable.blue_background;break;
            case "navy":backgroundColor = R.drawable.navy_background;break;
            case "fuchsia":backgroundColor = R.drawable.fuchsia_background;break;
            case "purple":backgroundColor = R.drawable.purple_background;break;
            default: backgroundColor = R.drawable.blue_background;break;
        }
        remoteViews.setInt(R.id.widget2_background_color, "setBackgroundResource", backgroundColor);
        remoteViews.setInt(R.id.fajrLinearLayout, "setBackgroundResource", backgroundColor);
        remoteViews.setInt(R.id.duhrLinearLayout, "setBackgroundResource", backgroundColor);
        remoteViews.setInt(R.id.asrLinearLayout, "setBackgroundResource", backgroundColor);
        remoteViews.setInt(R.id.maghribLinearLayout, "setBackgroundResource", backgroundColor);
        remoteViews.setInt(R.id.ishaaLinearLayout, "setBackgroundResource", backgroundColor);

        if(AthanService.isFajrForNextDay == false || AthanService.ifActualSalatTime == true)
        {
            if(actualPrayercode == 1021)
            {
                actualPrayercode = 1022;
            }

            switch (actualPrayercode) {
                case 1020:
                    remoteViews.setInt(R.id.fajr_label, "setTextColor", Color.BLACK);
                    remoteViews.setInt(R.id.fajrTime, "setTextColor", Color.BLACK);
                    remoteViews.setInt(R.id.fajrLinearLayout, "setBackgroundResource", R.drawable.widget_actual_prayer_background);break;
                case 1022:
                    remoteViews.setInt(R.id.duhr_label, "setTextColor", Color.BLACK);
                    remoteViews.setInt(R.id.duhrTime, "setTextColor", Color.BLACK);
                    remoteViews.setInt(R.id.duhrLinearLayout, "setBackgroundResource", R.drawable.widget_actual_prayer_background);break;
                case 1023:
                    remoteViews.setInt(R.id.asr_label, "setTextColor", Color.BLACK);
                    remoteViews.setInt(R.id.asrTime, "setTextColor", Color.BLACK);
                    remoteViews.setInt(R.id.asrLinearLayout, "setBackgroundResource", R.drawable.widget_actual_prayer_background);break;
                case 1024:
                    remoteViews.setInt(R.id.maghrib_label, "setTextColor", Color.BLACK);
                    remoteViews.setInt(R.id.maghribTime, "setTextColor", Color.BLACK);
                    remoteViews.setInt(R.id.maghribLinearLayout, "setBackgroundResource", R.drawable.widget_actual_prayer_background);break;
                case 1025:
                    remoteViews.setInt(R.id.ishaa_label, "setTextColor", Color.BLACK);
                    remoteViews.setInt(R.id.ishaaTime, "setTextColor", Color.BLACK);
                    remoteViews.setInt(R.id.ishaaLinearLayout, "setBackgroundResource", R.drawable.widget_actual_prayer_background);break;
                default:break;
            }
        }
    }

}
