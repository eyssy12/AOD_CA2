package Utilities;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public abstract class DateUtility 
{
	public static final String STRING_DATE_FORMAT = "dd-MMM-YYYY";
	public static final String STRING_TIME_FORMAT = "HH.mm a";
	public static final String STRING_HOUR_DATE_FORMAT = STRING_TIME_FORMAT + " 'on' " + STRING_DATE_FORMAT;
	
	public enum Format 
	{
		DATE_ONLY, TIME_ONLY, DATE_TIME;
		
		public String toString()
		{
			if(this.ordinal() == 0)
				return STRING_DATE_FORMAT;
			else if(this.ordinal() == 1)
				return STRING_TIME_FORMAT;
			else
				return STRING_HOUR_DATE_FORMAT;
		}
	}
	
	public static String getCalendarAsString(Calendar date, Format formatType) 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatType.toString());
		return dateFormat.format(date.getTime());
	}
}
