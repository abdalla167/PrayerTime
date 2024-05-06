
package com.example.prayertime.widget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import android.widget.RemoteViews;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;

import com.example.prayertime.R;
import com.example.prayertime.activities.SplashScreen_Activity;
import com.example.prayertime.classes.AthanService;
import com.example.prayertime.classes.UserConfig;

import java.text.DecimalFormat;
import java.text.NumberFormat;


public class LargeWidgetProviderService extends JobIntentService {

    private NumberFormat formatter = new DecimalFormat("00");

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {

    }

    @Override
    public void onCreate()
    {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        AthanService.refreshTime();
        pushWidgetUpdate(this);
        return Service.START_STICKY;
    }

    public void pushWidgetUpdate(Context context)
    {
        try{
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.large_widget_layout);

            Intent intent = new Intent(context, SplashScreen_Activity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            remoteViews.setOnClickPendingIntent(R.id.widgetMainBackground, pendingIntent);



                remoteViews.setTextViewText(R.id.fajr_label, context.getResources().getString(R.string.fajr_widget));
                remoteViews.setTextViewText(R.id.shorouk_label, context.getResources().getString(R.string.shorouk_widget));
                remoteViews.setTextViewText(R.id.duhr_label, context.getResources().getString(R.string.duhr_widget));
                remoteViews.setTextViewText(R.id.asr_label, context.getResources().getString(R.string.asr_widget));
                remoteViews.setTextViewText(R.id.maghrib_label, context.getResources().getString(R.string.maghrib_widget));
                remoteViews.setTextViewText(R.id.ishaa_label, context.getResources().getString(R.string.ishaa_widget));

                remoteViews.setTextViewText(R.id.missing_to, context.getResources().getString(R.string.missing_to));
                remoteViews.setTextViewText(R.id.missing_salat, getNextPrayerName(context));


            if(AthanService.missing_hours_to_nextPrayer == 0 && AthanService.missing_minutes_to_nextPrayer == 0)
            {

                    remoteViews.setTextViewText(R.id.missing_time, context.getResources().getString(R.string.minute));

            }
            else
            {
                remoteViews.setTextViewText(R.id.missing_time, formatter.format(AthanService.missing_hours_to_nextPrayer)+":"+formatter.format(AthanService.missing_minutes_to_nextPrayer));//+":"+formatter.format(AthanService.missing_seconds_to_nextPrayer)
            }

            remoteViews.setTextViewText(R.id.fajrTime, AthanService.prayerTimes.getFajrFinalTime());
            remoteViews.setTextViewText(R.id.shoroukTime, AthanService.prayerTimes.getShorou9FinalTime());
            remoteViews.setTextViewText(R.id.duhrTime, AthanService.prayerTimes.getDuhrFinalTime());
            remoteViews.setTextViewText(R.id.asrTime, AthanService.prayerTimes.getAsrFinalTime());
            remoteViews.setTextViewText(R.id.maghribTime, AthanService.prayerTimes.getMaghribFinalTime());
            remoteViews.setTextViewText(R.id.ishaaTime, AthanService.prayerTimes.getIshaaFinalTime());


            if(AthanService.ifActualSalatTime == true)
            {

                    remoteViews.setTextViewText(R.id.missing_to, context.getResources().getString(R.string.its_the_hour_of));
                    remoteViews.setTextViewText(R.id.missing_salat, getNextPrayerName(context));
                    remoteViews.setTextViewText(R.id.missing_time, getNextPrayerName(context));

            }

            setActualPrayerTextColor(remoteViews , AthanService.actualPrayerCode);

            ComponentName myWidget = new ComponentName(context, LargeWidgetProvider.class);
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            manager.updateAppWidget(myWidget, remoteViews);
        }
        catch(Exception ex)
        {
        }
    }

    private void setActualPrayerTextColor(RemoteViews remoteViews , int nextPrayercode){
        remoteViews.setInt(R.id.fajr_label, "setTextColor", android.graphics.Color.WHITE);
        remoteViews.setInt(R.id.fajrTime, "setTextColor", android.graphics.Color.WHITE);
        remoteViews.setInt(R.id.shorouk_label, "setTextColor", android.graphics.Color.WHITE);
        remoteViews.setInt(R.id.shoroukTime, "setTextColor", android.graphics.Color.WHITE);
        remoteViews.setInt(R.id.duhr_label, "setTextColor", android.graphics.Color.WHITE);
        remoteViews.setInt(R.id.duhrTime, "setTextColor", android.graphics.Color.WHITE);
        remoteViews.setInt(R.id.asr_label, "setTextColor", android.graphics.Color.WHITE);
        remoteViews.setInt(R.id.asrTime, "setTextColor", android.graphics.Color.WHITE);
        remoteViews.setInt(R.id.maghrib_label, "setTextColor", android.graphics.Color.WHITE);
        remoteViews.setInt(R.id.maghribTime, "setTextColor", android.graphics.Color.WHITE);
        remoteViews.setInt(R.id.ishaa_label, "setTextColor", android.graphics.Color.WHITE);
        remoteViews.setInt(R.id.ishaaTime, "setTextColor", android.graphics.Color.WHITE);

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

        remoteViews.setInt(R.id.widget_background_color, "setBackgroundResource", backgroundColor);
        remoteViews.setInt(R.id.fajrLinearLayout, "setBackgroundResource", backgroundColor);
        remoteViews.setInt(R.id.shorou9LinearLayout, "setBackgroundResource", backgroundColor);
        remoteViews.setInt(R.id.duhrLinearLayout, "setBackgroundResource", backgroundColor);
        remoteViews.setInt(R.id.asrLinearLayout, "setBackgroundResource", backgroundColor);
        remoteViews.setInt(R.id.maghribLinearLayout, "setBackgroundResource", backgroundColor);
        remoteViews.setInt(R.id.ishaaLinearLayout, "setBackgroundResource", backgroundColor);

        if(AthanService.isFajrForNextDay == false || AthanService.ifActualSalatTime == true)
        {
            switch (nextPrayercode) {
                case 1020:
                    remoteViews.setInt(R.id.fajr_label, "setTextColor", android.graphics.Color.BLACK);
                    remoteViews.setInt(R.id.fajrTime, "setTextColor", android.graphics.Color.BLACK);
                    remoteViews.setInt(R.id.fajrLinearLayout, "setBackgroundResource", R.drawable.widget_actual_prayer_background);break;
                case 1021:
                    remoteViews.setInt(R.id.shorouk_label, "setTextColor", android.graphics.Color.BLACK);
                    remoteViews.setInt(R.id.shoroukTime, "setTextColor", android.graphics.Color.BLACK);
                    remoteViews.setInt(R.id.shorou9LinearLayout, "setBackgroundResource", R.drawable.widget_actual_prayer_background);break;
                case 1022:
                    remoteViews.setInt(R.id.duhr_label, "setTextColor", android.graphics.Color.BLACK);
                    remoteViews.setInt(R.id.duhrTime, "setTextColor", android.graphics.Color.BLACK);
                    remoteViews.setInt(R.id.duhrLinearLayout, "setBackgroundResource", R.drawable.widget_actual_prayer_background);break;
                case 1023:
                    remoteViews.setInt(R.id.asr_label, "setTextColor", android.graphics.Color.BLACK);
                    remoteViews.setInt(R.id.asrTime, "setTextColor", android.graphics.Color.BLACK);
                    remoteViews.setInt(R.id.asrLinearLayout, "setBackgroundResource", R.drawable.widget_actual_prayer_background);break;
                case 1024:
                    remoteViews.setInt(R.id.maghrib_label, "setTextColor", android.graphics.Color.BLACK);
                    remoteViews.setInt(R.id.maghribTime, "setTextColor", android.graphics.Color.BLACK);
                    remoteViews.setInt(R.id.maghribLinearLayout, "setBackgroundResource", R.drawable.widget_actual_prayer_background);break;
                case 1025:
                    remoteViews.setInt(R.id.ishaa_label, "setTextColor", android.graphics.Color.BLACK);
                    remoteViews.setInt(R.id.ishaaTime, "setTextColor", android.graphics.Color.BLACK);
                    remoteViews.setInt(R.id.ishaaLinearLayout, "setBackgroundResource", R.drawable.widget_actual_prayer_background);break;
                default:break;
            }
        }
    }

    private String getNextPrayerName(Context context){
        String nextPrayerName = context.getString(R.string.not_set);
        switch (AthanService.actualPrayerCode) {
            case 1020:
                nextPrayerName = context.getString(R.string.fajr); break;
            case 1021:
                nextPrayerName = context.getString(R.string.shorouk); break;
            case 1022:
                nextPrayerName = context.getString(R.string.duhr); break;
            case 1023:
                nextPrayerName = context.getString(R.string.asr); break;
            case 1024:
                nextPrayerName = context.getString(R.string.maghrib); break;
            case 1025:
                nextPrayerName = context.getString(R.string.ishaa); break;
            default: context.getString(R.string.not_set); break;
        }
        return nextPrayerName;
    }

}
