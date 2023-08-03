package cloud.suratdishut.absen.utils;

import android.annotation.SuppressLint;
import android.text.format.DateFormat;
import android.util.Log;

import cloud.suratdishut.absen.config.Const;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class ConverterData {
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat dateFormat = new SimpleDateFormat(Const.DEFAULT_DATE_FORMAT);

    public String convertDateFormat1(String dates){
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = new Date();
        try {
            date = dateFormat.parse(dates);
        } catch (ParseException e) {
            Log.e("ParseException", e.getMessage());
        }

        String newDate = (String) DateFormat.format(Const.DATE_FORMAT_1, date);
        return newDate;
    }

    public String convertDateFormat2(String dates){
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = new Date();
        try {
            date = dateFormat.parse(dates);
        } catch (ParseException e) {
            Log.e("ParseException", e.getMessage());
        }

        String newDate = (String) DateFormat.format(Const.DATE_FORMAT_2, date);
        return newDate;
    }

    public String convertTimeFormat1(String dates){
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = new Date();
        try {
            date = dateFormat.parse(dates);
        } catch (ParseException e) {
            Log.e("ParseException", e.getMessage());
        }

        String newDate = (String) DateFormat.format(Const.TIME_FORMAT_1, date);
        return newDate;
    }

    public String convertTimeFormat2(String dates){
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = new Date();
        try {
            date = dateFormat.parse(dates);
        } catch (ParseException e) {
            Log.e("ParseException", e.getMessage());
        }

        String newDate = (String) DateFormat.format(Const.TIME_FORMAT_2, date);
        return newDate;
    }

}
