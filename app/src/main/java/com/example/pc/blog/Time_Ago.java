package com.example.pc.blog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tech 1 on 7/27/2015.
 */
public class Time_Ago {

    public String Get_Time(String d,String M,String y,String h,String m) {
        String dateStart = d+"/"+M+"/"+y+" "+h+":"+m+":00";
        String dateStop = "01/15/2012 10:31:48";
        String Time="";
        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Date d1 = null;
        Date d2 = null;
        try {

            Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR);
            if (c.get(Calendar.AM_PM) == 1) {
                hour = 12 + c.get(Calendar.HOUR);
            }
            dateStop = String.valueOf(c.get(Calendar.DAY_OF_MONTH)) + "/" +
                    String.valueOf(c.get(Calendar.MONTH)) + "/" + String.valueOf(c.get(Calendar.YEAR)) + " "
                    + String.valueOf(hour) + ":" + String.valueOf(c.get(Calendar.MINUTE)) + ":00";
            System.out.println("Date start " + dateStart);
            System.out.println("Date End " + dateStop);

            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);


            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            if(diffDays==0){
                if(diffHours==0){
                    Time = diffMinutes+" m";
                }
                else {
                    Time = diffHours+" h "+diffMinutes+" m";
                }
            }
            else {
                Time = diffDays+" d "+diffHours+" h "+diffMinutes+" m";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Time;
    }

    public static String getDifference(Date sessionStart, Date sessionEnd) {
        if(sessionStart == null)
            return "[corrupted]";

        Calendar startDateTime = Calendar.getInstance();
        startDateTime.setTime(sessionStart);

        Calendar endDateTime = Calendar.getInstance();
        endDateTime.setTime(sessionEnd);

        long milliseconds1 = startDateTime.getTimeInMillis();
        long milliseconds2 = endDateTime.getTimeInMillis();
        long diff = milliseconds2 - milliseconds1;

        long hours = diff / (60 * 60 * 1000);
        long minutes = diff / (60 * 1000);
        minutes = minutes - 60 * hours;
        long seconds = diff / (1000);

        if (hours > 0) {
            return hours + " hours " + minutes + " minutes";
        } else {
            if (minutes > 0)
                return minutes + " minutes";
            else {
                return seconds + " seconds";
            }
        }
    }
}
