
package com.example.prayertime.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

public class MyWidgetProvider2 extends AppWidgetProvider {

	@Override
	public void onUpdate(final Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		context.startService(new Intent(context, MyWidgetProviderService2.class));
	}

	@Override
	public void onEnabled(Context context) {

	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds){

	}
       
}
