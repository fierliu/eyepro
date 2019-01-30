package com.allan.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetTime {
//	Date time;
	public static String getSharpAndHalfTime(){
		Calendar calendar = Calendar.getInstance();
        Date date = (Date) calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String time = format.format(date);
        //after 12:00, the sharp time spoke by spvoice can pronounce 'hundred'
        if(time.substring(2).equals(":00")) time = time.substring(0,2) + " o'clock";
        //before 10:00, the sharp time spoke by spvoice can pronounce 'zero'
        if(time.substring(0,1).equals("0")) time = time.substring(1);
        return time;
	}

	public static String getDifferenceDays(String goalDate) throws ParseException{
		Long diffDays = 0L;

		Calendar calendar = Calendar.getInstance();
		Long timeNow = calendar.getTimeInMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        calendar.setTime(format.parse(goalDate));//目标日期
        Long timeGoal = calendar.getTimeInMillis();
        diffDays = (timeGoal - timeNow)/ (1000*3600*24);
//        System.out.println(diffDays);
        return diffDays.toString();
	}

}
