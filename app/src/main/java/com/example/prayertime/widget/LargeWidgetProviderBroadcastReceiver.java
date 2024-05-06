
package com.example.prayertime.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LargeWidgetProviderBroadcastReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		context.startService(new Intent(context, LargeWidgetProviderService.class));
	}
}
