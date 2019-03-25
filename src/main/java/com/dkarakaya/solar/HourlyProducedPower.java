package com.dkarakaya.solar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.dkarakaya.models.SolarPanel;
import com.dkarakaya.models.Weather;
import com.dkarakaya.services.SolarPanelService;
import com.dkarakaya.util.Constants;
import com.dkarakaya.util.Util;

public class HourlyProducedPower {

	SolarPanelService solarPanelService = new SolarPanelService();

	public Map<String, Map<String, Double>> hourlyProducedPowerAmount(List<Weather> forecasts) {

		List<SolarPanel> solarPanels = new ArrayList<SolarPanel>(solarPanelService.getAllSolarPanels());

		Map<String, Map<String, Double>> hourlyPower = new TreeMap<String, Map<String, Double>>();

		boolean isLastPeriodOfDay = false;

		for (int i = 0; i < forecasts.size() - 1; i++) {

			Map<String, Double> manufacturerToProducedPow = new TreeMap<String, Double>();

			// Check if during next 3 hours sun will not sink. is so calculate power for 3
			// hours
			isLastPeriodOfDay = Util.isLastPeriodOfDay(forecasts.get(i).getDatetime()) ? true : false;

			for (int j = 0; j < solarPanels.size(); j++) {

				if (!isLastPeriodOfDay) {

					manufacturerToProducedPow.put(solarPanels.get(j).getName(),
							solarPanelService.getProducedPowerForAnHour(solarPanels.get(j).getTempNOCT(),
									solarPanels.get(j).getTempCoef(), solarPanels.get(j).getPowSTC(),
									forecasts.get(i).getTemp().getTempAvgFor3Hours(), forecasts.get(i).getCloudiness())
									* 3);
				} else {

					manufacturerToProducedPow.put(solarPanels.get(j).getName(),
							solarPanelService.getProducedPowerForAnHour(solarPanels.get(j).getTempNOCT(),
									solarPanels.get(j).getTempCoef(), solarPanels.get(j).getPowSTC(),
									forecasts.get(i).getTemp().getTempAvgFor3Hours(),
									forecasts.get(i).getCloudiness()));
				}
			}

			if (!isLastPeriodOfDay) {

				hourlyPower.put(
						forecasts.get(i).getDatetime() + " - "
								+ Util.getTimeOfDatetime(forecasts.get(i + 1).getDatetime()),
						manufacturerToProducedPow);
			} else {

				hourlyPower.put(forecasts.get(i).getDatetime() + " - " + forecasts.get(i + 1).getDatetime(),
						manufacturerToProducedPow);
			}

		}
		return hourlyPower;
	}

	public void printHourlyProducedPowerAmount(Map<String, Map<String, Double>> hourlyPower) {

		System.out.println("For " + Constants.City
				+ " getting the forecasted power amount that will be produced in 3-hour periods by different solar panels...\n");
		for (Map.Entry<String, Map<String, Double>> entry : hourlyPower.entrySet()) {
			System.out.println(entry.getKey() + ": ");
			for (Map.Entry<String, Double> manufacturerToPow : entry.getValue().entrySet()) {
				System.out.println(" - " + manufacturerToPow.getKey() + " : "
						+ String.format("%.2f", manufacturerToPow.getValue()) + " W");
			}
			System.out.println("\n");
		}
	}
}
