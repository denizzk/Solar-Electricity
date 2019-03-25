package com.dkarakaya.models;

public class SolarPanel {
	
	private int id;
	private String name;
	private String serial;
	private double tempNOCT;
	private double tempCoef;
	private double powSTC;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public double getTempNOCT() {
		return tempNOCT;
	}
	public void setTempNOCT(double tempNOCT) {
		this.tempNOCT = tempNOCT;
	}
	public double getTempCoef() {
		return tempCoef;
	}
	public void setTempCoef(double tempCoef) {
		this.tempCoef = tempCoef;
	}
	public double getPowSTC() {
		return powSTC;
	}
	public void setPowSTC(double powSTC) {
		this.powSTC = powSTC;
	}
	
	@Override
	public String toString() {
		return "SolarPanel Id=" + getId() + ", Name=" + getName() + ", Serial=" + getSerial()
				+ ", TempNOCT=" + getTempNOCT() + ", TempCoef=" + getTempCoef() + ", PowSTC="
				+ getPowSTC();
	}
	
	
}
