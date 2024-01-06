package com.prodapt.billingsystem.utility;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.Calendar;

public class UtilityMethods {

    public static Timestamp addMinutes(Timestamp date, int numOfMin) { //changed
        Long milliSecInAnHour = Long.valueOf(60*1000);
        Timestamp newTS = new Timestamp(date.getTime());
        long milliSecToAdd = milliSecInAnHour * numOfMin;
        long newTimeMilliSec = newTS.getTime();
        newTS.setTime(newTimeMilliSec + milliSecToAdd);
        return newTS;
    }
    public static Timestamp addDays(Timestamp timestamp, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(timestamp);// w ww.  j ava  2  s  .co m
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return new Timestamp(cal.getTime().getTime());
    }

    public static long nosOfDays(Timestamp timestamp1, Timestamp timestamp2){
        Duration duration = Duration.between(timestamp1.toInstant(), timestamp2.toInstant());
        long nosOfDays = duration.toDays();
        return nosOfDays;
    }



}
