package com.depas.test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeTest {

	public boolean isSameWeekDay(int weekDay, Calendar cal){
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek==weekDay;
	}

	public boolean isSameMonthDay(int monthDay, Calendar cal){
		return monthDay == cal.get(Calendar.DAY_OF_MONTH);
	}

	public boolean isLastDayOfMonth(Calendar cal){
	    int lastDayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	    cal.get(Calendar.DAY_OF_MONTH);
		return lastDayOfMonth==cal.get(Calendar.DAY_OF_MONTH);
	}

	// time - HH:MM:AM/PM
	public boolean isSameTime(String time, Calendar scheduleCal){

		String scheduleTime = (scheduleCal.get(Calendar.HOUR)==0 ? "12" : String.valueOf(scheduleCal.get(Calendar.HOUR))) + ":" + (scheduleCal.get(Calendar.MINUTE)==0 ? "00" : String.valueOf(scheduleCal.get(Calendar.MINUTE))) + ":" + (scheduleCal.get(Calendar.AM_PM)==Calendar.AM ? "AM" : "PM");
		return (time.equals(scheduleTime) ? true : false);
	}

    // returns java time from string date (MM/DD/YYYY)
	public long getDateFromString(String dateString) {
		Calendar c = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
		String[] parts = dateString.split("/");
		c.set(Calendar.YEAR, Integer.parseInt(parts[2]));
		c.set(Calendar.MONTH, Integer.parseInt(parts[0])-1);
		c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(parts[1]));
		c.set(Calendar.HOUR,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);
		c.set(Calendar.MILLISECOND,0);
		return c.getTimeInMillis();
	}

	public static void main(String[] args) {
		DateTimeTest dateTimeTest = new DateTimeTest();


		String buildDate = "01/23/2015";
		long oleDate=0;
		for (int i = 0; i < 10; i++) {
			long newDate = dateTimeTest.getDateFromString(buildDate);
			if (newDate!=oleDate){
				System.out.println("mismatch new date: " + newDate + " ole date: " + oleDate);
				oleDate=newDate;
			}
		}

		DateTimeFormatter fmt = DateTimeFormat.shortDateTime();


		String fmtBuildDateTime = fmt.print(oleDate);
		System.out.println("build date time is " + fmtBuildDateTime);


//
//	    Calendar cal = new GregorianCalendar(2008, Calendar.JULY, 5, 12, 00);
//	    //Calendar cal = new GregorianCalendar();
//
//	    String scheduleFireTime = (cal.get(Calendar.HOUR)==0 ? "12" : String.valueOf(cal.get(Calendar.HOUR))) + ":" + (cal.get(Calendar.MINUTE)==0 ? "00" : String.valueOf(cal.get(Calendar.MINUTE))) + ":" + (cal.get(Calendar.AM_PM)==Calendar.AM ? "AM" : "PM");
//
//	    System.out.println("Format test for scheduleFireTime: " + scheduleFireTime);
//
//	    // Get the number of days in that month
//	    int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH); // 28
//
//	    System.out.println("Last day of the month: " + days);
//
//	    int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 1=Sunday, 2=Monday, ...
//
//	    System.out.println("Day of week: " + dayOfWeek);
//
//
//	    // start test
//	    System.out.println("\n##################### Start Same Week Test ########################\n");
//
//
//	    System.out.println("Testing weekday should be false: " + dateTimeTest.isSameWeekDay(3,cal));
//	    System.out.println("Testing weekday should be true: " + dateTimeTest.isSameWeekDay(7,cal));
//
//	    cal = new GregorianCalendar(2008, Calendar.FEBRUARY, 29);
//	    System.out.println("Testing weekday should be false: " + dateTimeTest.isSameWeekDay(3,cal));
//	    System.out.println("Testing weekday should be true: " + dateTimeTest.isSameWeekDay(6,cal));
//
//
//	    System.out.println("\n##################### Start Same Time Test ########################\n");
//	    cal = new GregorianCalendar(2008, Calendar.JULY, 5, 14, 15);
//	    System.out.println("Testing time should be false: " + dateTimeTest.isSameTime("3:14:AM",cal));
//	    System.out.println("Testing time should be true: " + dateTimeTest.isSameTime("2:15:PM",cal));
//
//	    cal = new GregorianCalendar(2008, Calendar.JULY, 5, 14, 0);
//	    System.out.println("Testing time should be true: " + dateTimeTest.isSameTime("2:00:PM",cal));
//
//	    System.out.println("\n##################### Start Same Month Day Test ########################\n");
//	    System.out.println("Testing month day should be false: " + dateTimeTest.isSameMonthDay(4,cal));
//	    System.out.println("Testing month day should be true: " + dateTimeTest.isSameMonthDay(5,cal));
//
//	    System.out.println("\n##################### Start Last Day of Month Test ########################\n");
//	    System.out.println("Testing last day of month false: " + dateTimeTest.isLastDayOfMonth(cal));
//
//	    cal = new GregorianCalendar(2008, Calendar.JULY, 31, 14, 0);
//	    System.out.println("Testing last day of month true: " + dateTimeTest.isLastDayOfMonth(cal));
//
//	    cal = new GregorianCalendar(2008, Calendar.FEBRUARY, 28, 14, 0);
//	    System.out.println("Testing last day of month false: " + dateTimeTest.isLastDayOfMonth(cal));
//	    cal = new GregorianCalendar(2008, Calendar.FEBRUARY, 29, 14, 0);
//	    System.out.println("Testing last day of month true: " + dateTimeTest.isLastDayOfMonth(cal));



		String fmtDateTime = fmt.print(1377496800000L);

		System.out.println("date time is " + fmtDateTime);

		DateTime dateTime = fmt.parseDateTime("11/1/17 11:59 PM");

		System.out.println("date time is " + dateTime.getMillis());

		System.out.println("hour of day " + dateTime.getHourOfDay());

		String date = "2013-08-05 17:42:53,002";
		System.out.println("date in " + date);
//		date = date.split(",")[0];
//		System.out.println("date parsed " + date);

		DateTimeFormatter fmt2 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss,SSS");
		dateTime = fmt2.parseDateTime(date);
		System.out.println("date time is " + dateTime.getMillis());
		fmtDateTime = fmt2.print(dateTime.getMillis());

		System.out.println("date time is " + fmtDateTime);


		date = "02/24/2017 12:00";
		System.out.println("date in " + date);
//		date = date.split(",")[0];
//		System.out.println("date parsed " + date);

		DateTimeFormatter fmt3 = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm");
		dateTime = fmt3.parseDateTime(date);
		System.out.println("date time is " + dateTime.getMillis());
		fmtDateTime = fmt3.print(dateTime.getMillis());

		System.out.println("date time is " + fmtDateTime);

	      // create time zone object
	      TimeZone timezone = TimeZone.getTimeZone("Europe/Paris");
//	      TimeZone timezone = TimeZone.getTimeZone("UTC");

	      // checking offset value for date
	      System.out.println("Offset value:" +
	      timezone.getOffset(Calendar.ZONE_OFFSET));


	      DateTimeFormatter fmt4 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
	      date = "2009-04-22 13:12:34";
	      dateTime = fmt4.parseDateTime(date);
	      System.out.println("date time is " + dateTime.getMillis());



		long currTime = System.currentTimeMillis();
		currTime = 1509536760947L;
		System.out.println("current date time is " + currTime);
		fmtDateTime = fmt2.print(currTime);
		System.out.println("current date time is " + fmtDateTime);
		fmtDateTime = fmt2.print(currTime + DateTimeConstants.MILLIS_PER_HOUR);
		System.out.println("current date time plus one hour " + fmtDateTime);


	}
}
