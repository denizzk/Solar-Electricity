package com.dkarakaya.util;

public class Constants {
	

	public static final String URL = "http://api.openweathermap.org/data/2.5/forecast";
	public static final String APIKEY = "86040eaf2e79aba376e32473f8a7d95b";
	public static String City = "Stuttgart";
	public static String TempType = "metric"; // for 'Celsius'
	public static String ForecastCount = "40"; // Max value is 40 because of limitation of api.

	public static final double POWCOSTPERKWH = 0.295;
	public static final double POWCOSTPERWH = POWCOSTPERKWH / 1000;
}
