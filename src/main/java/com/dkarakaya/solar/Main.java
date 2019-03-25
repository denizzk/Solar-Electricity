package com.dkarakaya.solar;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.concurrent.Future;

import com.dkarakaya.models.Forecast;
import com.dkarakaya.util.Constants;
import com.dkarakaya.util.Util;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Main {

	public static void main(String[] args) {

		final DailyProducedPower dailyProducedPower = new DailyProducedPower();
		final HourlyProducedPower hourlyProducedPower = new HourlyProducedPower();
		final DailyGeneratedElectricityMonetaryValue dailyGeneratedElectricityMonetaryValue = new DailyGeneratedElectricityMonetaryValue();
		final HourlyGeneratedElectricityMonetaryValue hourlyGeneratedElectricityMonetaryValue = new HourlyGeneratedElectricityMonetaryValue();

		Future<HttpResponse<JsonNode>> future = Unirest.get(Constants.URL).header("accept", "application/json")
				.queryString("appid", Constants.APIKEY).queryString("q", Constants.City)
				.queryString("units", Constants.TempType).queryString("cnt", Constants.ForecastCount)
				.asJsonAsync(new Callback<JsonNode>() {

					public void failed(UnirestException e) {
						System.out.println("The request has failed");
					}

					public void completed(HttpResponse<JsonNode> response) {
						JsonNode body = response.getBody();

						Gson gson = new Gson();
						Forecast forecast = gson.fromJson(body.toString(), Forecast.class);

						Scanner sc = new Scanner(System.in);
						int choice = 0;

						Map<String, Map<String, Double>> hourlyPower;
						double[][] dailyProducedPowerSumList;

						while (choice != 5) {

							System.out.println("______________________________");
							System.out.println("\n1- Daily Produced Power");
							System.out.println("2- Hourly Produced Power");
							System.out.println("3- Daily Electricity Profit");
							System.out.println("4- Hourly Electricity Profit");
							System.out.println("______________________________");

							choice = sc.nextInt();

							switch (choice) {
							case 1:
								dailyProducedPowerSumList = dailyProducedPower
										.dailyProducedPowerAmount(forecast.getWeather());
								dailyProducedPower.printDailyProducedPowerAmount(dailyProducedPowerSumList);
								break;
							case 2:
								hourlyPower = new TreeMap<String, Map<String, Double>>(
										hourlyProducedPower.hourlyProducedPowerAmount(
												Util.forecastsBetweenSunriseAndSunset(forecast.getWeather())));
								hourlyProducedPower.printHourlyProducedPowerAmount(hourlyPower);
								break;
							case 3:
								dailyProducedPowerSumList = dailyProducedPower
										.dailyProducedPowerAmount(forecast.getWeather());

								Map<String, Map<String, Double>> dailyGeneratedElectricityMonetaryVal = new TreeMap<String, Map<String, Double>>(
										dailyGeneratedElectricityMonetaryValue
												.dailyGeneratedElectricityMonetaryValue(dailyProducedPowerSumList));
								dailyGeneratedElectricityMonetaryValue.printdailyGeneratedElectricityMonetaryValue(
										dailyGeneratedElectricityMonetaryVal);
								break;
							case 4:
								hourlyPower = new TreeMap<String, Map<String, Double>>(
										hourlyProducedPower.hourlyProducedPowerAmount(
												Util.forecastsBetweenSunriseAndSunset(forecast.getWeather())));

								Map<String, Map<String, Double>> hourlyGeneratedElectricityMonetaryVal = new TreeMap<String, Map<String, Double>>(
										hourlyGeneratedElectricityMonetaryValue
												.hourlyGeneratedElectricityMonetaryVal(hourlyPower));
								hourlyGeneratedElectricityMonetaryValue.printHourlyGeneratedElectricityMonetaryVal(
										hourlyGeneratedElectricityMonetaryVal);
								break;
							default:
								System.exit(0);
								break;
							}
						}
					}

					public void cancelled() {
						System.out.println("The request has been cancelled");
					}

				});
	}

}
