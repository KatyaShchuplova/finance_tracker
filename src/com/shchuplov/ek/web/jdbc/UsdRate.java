package com.shchuplov.ek.web.jdbc;

import java.util.Date;

public class UsdRate {
	
	private Date date;
	private double usdRate;
	
	public UsdRate(Date date, double usdRate) {
		this.date = date;
		this.usdRate = usdRate;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getUsdRate() {
		return usdRate;
	}
	public void setUsdRate(double usdRate) {
		this.usdRate = usdRate;
	}
	@Override
	public String toString() {
		return "UsdRate [date=" + date + ", usdRate=" + usdRate + "]";
	}
	
}
