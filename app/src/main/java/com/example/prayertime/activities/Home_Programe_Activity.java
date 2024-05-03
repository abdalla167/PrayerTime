package com.example.prayertime.activities;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.example.prayertime.R;
import com.example.prayertime.classes.AthanService;
import com.example.prayertime.classes.PreferenceHandler;
import com.example.prayertime.classes.UserConfig;


public class Home_Programe_Activity extends TabActivity {

	private static final int PERMISSION_REQUEST_CODE = 101;
	private TabHost tabHost;
	private TabSpec prayersTabSpec;
	private TabSpec kiblaTabSpec;
	private TabSpec settingsTabSpec;

	private Intent prayers_activity_intent;
	private Intent kibla_activity_intent;

	private int drawablePrayersTab;
	private int drawableKiblaTab;
	private int drawableSettingsTab;

	private String prayers;
	private String kibla;
	private String settings;
	private String info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_programe_activity);

		String permission1 = Manifest.permission.ACCESS_FINE_LOCATION;
		if (ContextCompat.checkSelfPermission(getApplicationContext(), permission1) != PackageManager.PERMISSION_GRANTED) {
			if (!ActivityCompat.shouldShowRequestPermissionRationale(Home_Programe_Activity.this, permission1)) {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
					requestPermissions(new String[]{permission1}, 1);
				}
			}
		}


		changeMainBackground();

		this.prayers = getResources().getString(R.string.prayers);
		this.kibla = getResources().getString(R.string.kibla);
		this.settings = getResources().getString(R.string.settings);

		this.tabHost = (TabHost) findViewById(android.R.id.tabhost);

		this.drawablePrayersTab = R.drawable.prayers_tab_style;
		this.drawableKiblaTab = R.drawable.kibla_tab_style;
		this.drawableSettingsTab = R.drawable.settings_tab_style;

		this.prayersTabSpec = this.tabHost.newTabSpec(prayers);
		this.kiblaTabSpec = this.tabHost.newTabSpec(kibla);
		this.settingsTabSpec = this.tabHost.newTabSpec(settings);


		this.prayersTabSpec.setIndicator(getIndicator("", drawablePrayersTab));
		this.kiblaTabSpec.setIndicator(getIndicator("", drawableKiblaTab));
		this.settingsTabSpec.setIndicator(getIndicator("", drawableSettingsTab));

		this.prayers_activity_intent = new Intent().setClass(Home_Programe_Activity.this, Prayers_Activity.class);
		this.kibla_activity_intent = new Intent().setClass(Home_Programe_Activity.this, Kibla_Activity.class);
		Intent settings_activity_intent = new Intent().setClass(Home_Programe_Activity.this, Settings_Activity.class);

		this.prayersTabSpec.setContent(prayers_activity_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		this.kiblaTabSpec.setContent(kibla_activity_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		this.settingsTabSpec.setContent(settings_activity_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

		this.tabHost.addTab(prayersTabSpec);
		this.tabHost.addTab(kiblaTabSpec);
		this.tabHost.addTab(settingsTabSpec);

		this.tabHost.getTabWidget().setCurrentTab(0);


		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
			if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
				requestNotificationPermission();
			}

		}




	}

	private void requestNotificationPermission() {
		if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.POST_NOTIFICATIONS)) {
			// Show an explanation to the user *asynchronously* -- don't block
			// this thread waiting for the user's response! After the user
			// sees the explanation, try again to request the permission.
			Toast.makeText(this, "Notification permission is required to show notifications", Toast.LENGTH_LONG).show();
		}

		// No explanation needed; request the permission
		ActivityCompat.requestPermissions(this,
				new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
				PERMISSION_REQUEST_CODE);
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == PERMISSION_REQUEST_CODE) {
			// If request is cancelled, the result arrays are empty.
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				// Permission was granted, yay! Show the notification.
				//showNotification();
			} else {
				// Permission denied, boo! Disable the functionality that depends on this permission.
				//Toast.makeText(this, "Permission denied to show notifications", Toast.LENGTH_SHORT).show();
			}
		}
	}


	private View getIndicator(String tabTitle, int tabImage)
	{
	    View localView = View.inflate(this, R.layout.tabs_menu_background, null);
	    
	    if (tabImage != 0){
	      ((ImageView)localView.findViewById(R.id.tab_icon)).setImageResource(tabImage);
	    }
	    return localView;
	  }
	
	public void changeMainBackground()
	{
		    Drawable image;
			switch (AthanService.nextPrayerCode) {
	   		case 1020:
	   			image = getResources().getDrawable(R.drawable.fajernew); break;
	   		case 1021:
	   			image = getResources().getDrawable(R.drawable.fajernew); break;
	   		case 1022:
	   			image = getResources().getDrawable(R.drawable.fajernew); break;
	   		case 1023:
	   			image = getResources().getDrawable(R.drawable.fajernew); break;
	   		case 1024:
	   			image = getResources().getDrawable(R.drawable.fajernew); break;
	   		case 1025:
	   			image = getResources().getDrawable(R.drawable.fajernew); break;
	   		default: image = getResources().getDrawable(R.drawable.background); break;
	   		}
			LinearLayout layout = (LinearLayout)this.findViewById(R.id.mainBackground);
			layout.setBackgroundDrawable(image);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		// check if the request code is same as what is passed  here it is 70
		if(requestCode == 70)
		{
			if (resultCode == RESULT_OK)
			{
				UserConfig.getSingleton().setBattery_optimization("yes");
				PreferenceHandler.getSingleton().addUserConfig(UserConfig.getSingleton());


					Toast.makeText(this, getResources().getString(R.string.optimized), Toast.LENGTH_SHORT).show();

			}
			else
			{
				UserConfig.getSingleton().setBattery_optimization("no");
				PreferenceHandler.getSingleton().addUserConfig(UserConfig.getSingleton());


					Toast.makeText(this, getResources().getString(R.string.battery_optimization_warning), Toast.LENGTH_LONG).show();

			}
		}
	}
}
