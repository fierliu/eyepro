package application;

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
        return time;
	}
}
