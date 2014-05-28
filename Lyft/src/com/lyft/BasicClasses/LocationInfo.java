package com.lyft.BasicClasses;

public class LocationInfo {
	private int latitude;
	private int longitude;
	
	public LocationInfo(int latitude, int longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	public int getLatitude() {
		return latitude;
	}
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	public int getLongitude() {
		return longitude;
	}
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	
}
