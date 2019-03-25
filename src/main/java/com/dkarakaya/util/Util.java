package com.dkarakaya.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.dkarakaya.models.Weather;

public class Util {

	public static boolean isSameDay(String d1, String d2) {

		boolean isSameDay = false;

		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();

		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d1);
			Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d2);
			cal1.setTime(date1);
			cal2.setTime(date2);
			isSameDay = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return isSameDay;
	}

	public static boolean isBetweenSunriseAndSunset(String d) {

		boolean isBetweenSunriseAndSunset = false;

		Calendar cal = Calendar.getInstance();

		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d);
			cal.setTime(date);
			isBetweenSunriseAndSunset = cal.get(Calendar.HOUR_OF_DAY) <= 18 && cal.get(Calendar.HOUR_OF_DAY) >= 6;

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return isBetweenSunriseAndSunset;
	}

	public static boolean isLastPeriodOfDay(String d) {

		boolean isLastPeriodOfDay = false;

		Calendar cal = Calendar.getInstance();

		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d);
			cal.setTime(date);
			isLastPeriodOfDay = cal.get(Calendar.HOUR_OF_DAY) == 18;

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return isLastPeriodOfDay;
	}

	public static String getTimeOfDatetime(String d) {

		Calendar cal = Calendar.getInstance();
		String time = null;
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d);
			cal.setTime(date);
			time = String.format("%02d", cal.get(Calendar.HOUR_OF_DAY)) + ":00:00";

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return time;
	}
	
	public static String getDateOfDatetime(String d) {

		Calendar cal = Calendar.getInstance();
		String sdate = null;
		try {
			Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(d);
			cal.setTime(date);
			sdate =cal.get(Calendar.YEAR) + cal.get(Calendar.MONTH) + String.format("%02d", cal.get(Calendar.DAY_OF_MONTH));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return sdate;
	}

	public static int getDayCount(List<Weather> w) {

		int dayCount = 1;

		for (int i = 0; i < w.size() - 1; i++) {

			if (!isSameDay(w.get(i).getDatetime(), w.get(i + 1).getDatetime()))
				dayCount++;
		}
		return dayCount;
	}

	public static List<Weather> forecastsBetweenSunriseAndSunset(List<Weather> fList) {

		List<Weather> forecasts = new ArrayList<Weather>();

		for (int i = 0; i < fList.size(); i++) {

			if (isBetweenSunriseAndSunset(fList.get(i).getDatetime())) {
				forecasts.add(fList.get(i));
			}
		}

		return forecasts;
	}
}
