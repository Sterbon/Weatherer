package com.sterbon.weatherer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Utsav on 8/26/2017.
 */

public class Weather {
    private String mIcon;
    private long mTime;
    private double mTemp;
    private double mHumid;
    private double mPrecip;
    private String mSum;

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String timezone) {
        mTimezone = timezone;
    }

    private String mTimezone;

    public String getIcon() {


        return mIcon;
    }


    public void setIcon(String icon) {
        mIcon = icon;
    }

    public int getIconId(){

        int iconId = R.drawable.clear_day;

        if (mIcon.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        }
        else if (mIcon.equals("clear-night")) {
            iconId = R.drawable.clear_night;
        }
        else if (mIcon.equals("rain")) {
            iconId = R.drawable.rain;
        }
        else if (mIcon.equals("snow")) {
            iconId = R.drawable.snow;
        }
        else if (mIcon.equals("sleet")) {
            iconId = R.drawable.sleet;
        }
        else if (mIcon.equals("wind")) {
            iconId = R.drawable.wind;
        }
        else if (mIcon.equals("fog")) {
            iconId = R.drawable.fog;
        }
        else if (mIcon.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        }
        else if (mIcon.equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        }
        else if (mIcon.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;
        }

        return iconId;
    }

    public long getTime() {
        return mTime;
    }

    public String getFormattedTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        Date mdate = new Date(getTime()*1000);
        formatter.setTimeZone(TimeZone.getTimeZone(getTimezone()));
        String timeS= formatter.format(mdate);

        return timeS;
    }
    public void setTime(long time) {
        mTime = time;
    }

    public double getTemp() {
        return Math.round((mTemp-32)/1.8);
    }

    public void setTemp(double temp) {
        mTemp = temp;
    }

    public double getHumid() {
        return mHumid*100;
    }

    public void setHumid(double humid) {
        mHumid = humid;
    }

    public double getPrecip() {
        return mPrecip;
    }

    public void setPrecip(double precip) {
        mPrecip = precip*100;
    }

    public String getSum() {
        return mSum;
    }

    public void setSum(String sum) {
        mSum = sum;
    }
}