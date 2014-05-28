package com.lyft.BasicClasses;

public class CarInfo {
	private String brandName;
	private String plateNumber;
	private int nseats;
	private String picDir;
	
	public CarInfo(String brandName, String plateNumber, int nseats,
			String picDir) {
		this.brandName = brandName;
		this.plateNumber = plateNumber;
		this.nseats = nseats;
		this.picDir = picDir;
	}

	public String getBrandName() {
		return brandName;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public int getNseats() {
		return nseats;
	}

	public String getPicDir() {
		return picDir;
	}

	public void setPicDir(String picDir) {
		this.picDir = picDir;
	}
}
