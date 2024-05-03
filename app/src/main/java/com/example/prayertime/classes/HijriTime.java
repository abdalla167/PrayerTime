
package com.example.prayertime.classes;

import android.content.Context;


import com.example.prayertime.R;


import org.joda.time.Chronology;
import org.joda.time.LocalDate;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.chrono.IslamicChronology;


import java.io.IOException;
import java.text.DecimalFormat;

import java.util.Calendar;

public class HijriTime {

	private Calendar calendar;//Calendar object
    private int day;//actual day
    private int month;//actual month
    private int year;//actual year
    private DecimalFormat dayFormatter;//day formatter
    private DecimalFormat yearFormatter;//year formatter
    private Context context;
    
    public HijriTime(Calendar cal , Context context) throws IOException{
    	this.calendar = cal;
    	this.context = context;

        try{
        this.calendar.add(Calendar.DAY_OF_MONTH, (int)Double.parseDouble(UserConfig.getSingleton().getHijri()));
        }catch(Exception e){
        	this.calendar = cal;
        }
        
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
    	this.month = calendar.get(Calendar.MONTH) + 1;
        this.year = calendar.get(Calendar.YEAR);
    }

    public HijriTime(Calendar cal) throws IOException{
        this.calendar = cal;

        try{
            this.calendar.add(Calendar.DAY_OF_MONTH, (int)Double.parseDouble(UserConfig.getSingleton().getHijri()));
        }catch(Exception e){
            this.calendar = cal;
        }

        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.month = calendar.get(Calendar.MONTH) + 1;
        this.year = calendar.get(Calendar.YEAR);
    }
    
    public String getMonth(int i){//get translated month name
        String month1;
        month1 = "";
        int j = i;
        switch (j) {
        case 12:
            month1 = context.getResources().getString(R.string.dhul_hijja);
            break;
        case 1:
            month1 = context.getResources().getString(R.string.muharram);
            break;
        case 2:
            month1 = context.getResources().getString(R.string.safar);
            break;
        case 3:
            month1 = context.getResources().getString(R.string.rabi_ul_awwal);
            break;
        case 4:
            month1 = context.getResources().getString(R.string.rabi_ul_attani);
            break;
        case 5:
            month1 = context.getResources().getString(R.string.jumadal_ula);
            break;
        case 6:
            month1 = context.getResources().getString(R.string.jumadal_attani);
            break;
        case 7:
            month1 = context.getResources().getString(R.string.rajab);
            break;
        case 8:
            month1 = context.getResources().getString(R.string.shaban);
            break;
        case 9:
            month1 = context.getResources().getString(R.string.ramadan);
            break;
        case 10:
            month1 = context.getResources().getString(R.string.shawwal);
            break;
        case 11:
            month1 = context.getResources().getString(R.string.dhul_qi_ada);
            break;
        }
        return month1;
    }

    public String getDay(int i) throws IOException {//get translated day name
        String day = "";
        int j = i;
        switch (j) {
        case 1:
            day = context.getResources().getString(R.string.SUNDAY);
            break;
        case 2:
            day = context.getResources().getString(R.string.MONDAY);
            break;
        case 3:
            day = context.getResources().getString(R.string.TUESDAY);
            break;
        case 4:
            day = context.getResources().getString(R.string.WEDNESDAY);
            break;
        case 5:
            day = context.getResources().getString(R.string.TUESDAY);
            break;
        case 6:
            day = context.getResources().getString(R.string.FRIDAY);
            break;
        case 7:
            day = context.getResources().getString(R.string.SATURDAY);
            break;
        }
        return day;
    }


    public String getHijriTime() {////get final translated hijri date
        this.dayFormatter = new DecimalFormat("00");
        this.yearFormatter = new DecimalFormat("0000");

        Chronology iSOChronology = ISOChronology.getInstanceUTC();//get ISOChronology instance
        Chronology islamicChronology = IslamicChronology.getInstanceUTC();//get IslamicChronology instance

        LocalDate localDateISOChronology = new LocalDate(year, month, day, iSOChronology);//get local date
        LocalDate HijriDate = new LocalDate(localDateISOChronology.toDate(), islamicChronology);//get hijri date



        	return dayFormatter.format(HijriDate.getDayOfMonth()) + " " + getMonth(HijriDate.getMonthOfYear()) + " " + yearFormatter.format(HijriDate.getYear());

        
    }

    public boolean isRamadan()
    {
        this.dayFormatter = new DecimalFormat("00");
        this.yearFormatter = new DecimalFormat("0000");

        Chronology iSOChronology = ISOChronology.getInstanceUTC();//get ISOChronology instance
        Chronology islamicChronology = IslamicChronology.getInstanceUTC();//get IslamicChronology instance

        LocalDate localDateISOChronology = new LocalDate(year, month, day, iSOChronology);//get local date
        LocalDate HijriDate = new LocalDate(localDateISOChronology.toDate(), islamicChronology);//get hijri date

        if(HijriDate.getMonthOfYear() == 9)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
