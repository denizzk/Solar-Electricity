package com.dkarakaya.solar;

import java.util.Map;
import java.util.TreeMap;

import com.dkarakaya.util.Constants;

public class HourlyGeneratedElectricityMonetaryValue {

	public Map<String, Map<String, Double>> hourlyGeneratedElectricityMonetaryVal(
			Map<String, Map<String, Double>> hourlyPower) {

		Map<String, Map<String, Double>> hourlyGeneratedElectricityMonetaryVal = new TreeMap<String, Map<String, Double>>();

		for (Map.Entry<String, Map<String, Double>> entry : hourlyPower.entrySet()) {

			Map<String, Double> manufacturerToGeneratedElectricityMonetaryVal = new TreeMap<String, Double>();

			for (Map.Entry<String, Double> manufacturerToPow : entry.getValue().entrySet()) {

				manufacturerToGeneratedElectricityMonetaryVal.put(manufacturerToPow.getKey(),
						manufacturerToPow.getValue() * Constants.POWCOSTPERWH);
			}
			hourlyGeneratedElectricityMonetaryVal.put(entry.getKey(), manufacturerToGeneratedElectricityMonetaryVal);
		}
		return hourlyGeneratedElectricityMonetaryVal;
	}

	public void printHourlyGeneratedElectricityMonetaryVal(
			Map<String, Map<String, Double>> hourlyGeneratedElectricityMonetaryVal) {

		System.out.println("For " + Constants.City
				+ " getting the forecasted profit that will be produced by different solar panels in 3-hour periods ...\n");
		for (Map.Entry<String, Map<String, Double>> entry : hourlyGeneratedElectricityMonetaryVal.entrySet()) {

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
