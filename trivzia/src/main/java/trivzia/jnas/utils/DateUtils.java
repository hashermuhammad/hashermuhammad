package trivzia.jnas.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils
{

	public String getCurrentDateTimeString()
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date sdate2 = new Date();
		String dateToString = df.format(sdate2);
		return dateToString;
	}
	
	
	public String getCurrentDateString()
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date sdate2 = new Date();
		String dateToString = df.format(sdate2);
		return dateToString;
	}

	
	
}
