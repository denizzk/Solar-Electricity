package com.dkarakaya.models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Forecast {
	
	@SerializedName("cnt")
	@Expose
	private Integer count;
	@SerializedName("list")
	@Expose
	private List<Weather> weather = null;
	
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public List<Weather> getWeather() {
		return weather;
	}
	public void setWeather(List<Weather> weather) {
		this.weather = weather;
	}
	
}
