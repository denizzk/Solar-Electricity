package com.dkarakaya.solar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.dkarakaya.models.SolarPanel;
import com.dkarakaya.util.Constants;

import services.SolarPanelService;

public class DailyGeneratedElectricityMonetaryValue {

	SolarPanelService solarPanelService = new SolarPanelService();

	public Map<String, Map<String, Double>> dailyGeneratedElectricityMonetaryValue(
			double[][] dailyProducedPowerSumList) {

		List<SolarPanel> solarPanels = new ArrayList<SolarPanel>(solarPanelService.getAllSolarPanels());

		Map<String, Map<String, Double>> dailyGeneratedElectricityMonetaryVal = new TreeMap<String, Map<String, Double>>();

		for (int i = 0; i < dailyProducedPowerSumList.length; i++) {

			Map<String, Double> manufacturerToGeneratedElectricityMonetaryVal = new TreeMap<String, Double>();

			for (int j = 0; j < solarPanels.size(); j++) {

				manufacturerToGeneratedElectricityMonetaryVal.put(solarPanels.get(j).getName(),
						dailyProducedPowerSumList[i][j] * Constants.POWCOSTPERWH);

			}
			dailyGeneratedElectricityMonetaryVal.put("Day " + (i + 1), manufacturerToGeneratedElectricityMonetaryVal);
		}
		return dailyGeneratedElectricityMonetaryVal;
	}

	public void printdailyGeneratedElectricityMonetaryValue(
			Map<String, Map<String, Double>> dailyGeneratedElectricityMonetaryVal) {

		System.out.println("For " + Constants.City
				+ " getting the forecasted daily profit that will be produced by different solar panels ...\n");
		for (Map.Entry<String, Map<String, Double>> entry : dailyGeneratedElectricityMonetaryVal.entrySet()) {

			System.out.println(entry.getKey() + ": ");

			for (Map.Entry<String, Double> manufacturerToGeneratedElectricityMonetaryVal : entry.getValue()
					.entrySet()) {

				System.out.println(" - " + manufacturerToGeneratedElectricityMonetaryVal.getKey() + " : "
						+ String.format("%.2f", manufacturerToGeneratedElectricityMonetaryVal.getValue()) + " €");
			}
			System.out.println("\n");
		}
	}
}
