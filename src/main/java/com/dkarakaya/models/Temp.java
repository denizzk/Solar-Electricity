package com.dkarakaya.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Temp {

	@SerializedName("temp")
	@Expose
	private Double curTemp;
	@SerializedName("temp_min")
	@Expose
	private Double tempMin;
	@SerializedName("temp_max")
	@Expose
	private Double tempMax;

	public Double getCurTemp() {
		return curTemp;
	}

	public void setCurTemp(Double curTemp) {
		this.curTemp = curTemp;
	}

	public Double getTempMin() {
		return tempMin;
	}

	public void setTempMin(Double tempMin) {
		this.tempMin = tempMin;
	}

	public Double getTempMax() {
		return tempMax;
	}

	public void setTempMax(Double tempMax) {
		this.tempMax = tempMax;
	}

	public Double getTempAvgFor3Hours() {
		return (getTempMax() + getTempMin()) / 2;
	}

}
