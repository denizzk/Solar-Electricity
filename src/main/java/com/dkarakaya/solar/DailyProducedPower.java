package com.dkarakaya.solar;

import java.util.ArrayList;
import java.util.List;

import com.dkarakaya.models.SolarPanel;
import com.dkarakaya.models.Weather;
import com.dkarakaya.util.Constants;
import com.dkarakaya.util.Util;

import services.SolarPanelService;

public class DailyProducedPower {

	SolarPanelService solarPanelService = new SolarPanelService();

	public double[][] dailyProducedPowerAmount(List<Weather> forecasts) {

		List<SolarPanel> solarPanels = new ArrayList<SolarPanel>(solarPanelService.getAllSolarPanels());

		double[][] dailyProducedPowerSumList = new double[Util.getDayCount(forecasts)][solarPanels.size()];
		int curDay = 0;

		for (int i = 0; i < forecasts.size() - 1; i++) {

			if (Util.isBetweenSunriseAndSunset(forecasts.get(i).getDatetime())) {

				if (Util.isSameDay(forecasts.get(i).getDatetime(), forecasts.get(i + 1).getDatetime())) {

					for (int j = 0; j < solarPanels.size(); j++) {

						// Check if during next 3 hours sun will not sink. is so calculate power for 3
						// hours
						if (Util.isBetweenSunriseAndSunset(forecasts.get(i + 1).getDatetime())) {

							dailyProducedPowerSumList[curDay][j] += solarPanelService.getProducedPowerForAnHour(
									solarPanels.get(j).getTempNOCT(), solarPanels.get(j).getTempCoef(),
									solarPanels.get(j).getPowSTC(), forecasts.get(i).getTemp().getTempAvgFor3Hours(),
									forecasts.get(i).getCloudiness()) * 3;
						} else {

							dailyProducedPowerSumList[curDay][j] += solarPanelService.getProducedPowerForAnHour(
									solarPanels.get(j).getTempNOCT(), solarPanels.get(j).getTempCoef(),
									solarPanels.get(j).getPowSTC(), forecasts.get(i).getTemp().getTempAvgFor3Hours(),
									forecasts.get(i).getCloudiness());
						}

					}
				}
			} else {
				if (!Util.isSameDay(forecasts.get(i).getDatetime(), forecasts.get(i + 1).getDatetime())) {
					curDay++;
				}
			}
		}

		return dailyProducedPowerSumList;
	}

	public void printDailyProducedPowerAmount(double[][] dailyProducedPowerSumList) {

		List<SolarPanel> solarPanels = new ArrayList<SolarPanel>(solarPanelService.getAllSolarPanels());

		System.out.println("For " + Constants.City
				+ " getting the forecasted daily total power amount that will be produced by solar panel types...\n");
		for (int i = 0; i < dailyProducedPowerSumList.length; i++) {

			System.out.println("Day " + (i + 1) + ":");

			for (int j = 0; j < solarPanels.size(); j++) {

				System.out.println(" - Produced By " + solarPanels.get(j).getName() + " Total: "
						+ String.format("%.2f", dailyProducedPowerSumList[i][j]) + " W");
			}
			System.out.println("\n");
		}
	}
}
