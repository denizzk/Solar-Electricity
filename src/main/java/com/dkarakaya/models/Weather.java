package com.dkarakaya.models;

import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Weather {
	
	@SerializedName("main")
	@Expose
	private Temp temp;
	@SerializedName("dt_txt")
	@Expose
	private String datetime;
	@SerializedName("clouds")
	@Expose
	private Map<String, Double> cloudiness;
	
	public Temp getTemp() {
		return temp;
	}
	public void setTemp(Temp temp) {
		this.temp = temp;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public double getCloudiness() {
		return cloudiness.get("all").doubleValue();
	}
	public void setCloudiness(Map<String, Double> cloudiness) {
		this.cloudiness = cloudiness;
	}	
}
