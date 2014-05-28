package com.lyft.BasicClasses;

public class DriverInfo {
	private CarInfo carInfo;
	private DrivingLicenseInfo drivingLicenseInfo;
	private int yearsOfDriving; // ???
	
	public DriverInfo(CarInfo carInfo, DrivingLicenseInfo drivingLicenseInfo,
			int yearsOfDriving) {
		super();
		this.carInfo = carInfo;
		this.drivingLicenseInfo = drivingLicenseInfo;
		this.yearsOfDriving = yearsOfDriving;
	}

	public CarInfo getCarInfo() {
		return carInfo;
	}

	public void setCarInfo(CarInfo carInfo) {
		this.carInfo = carInfo;
	}

	public DrivingLicenseInfo getDrivingLicenseInfo() {
		return drivingLicenseInfo;
	}

	public void setDrivingLicenseInfo(DrivingLicenseInfo drivingLicenseInfo) {
		this.drivingLicenseInfo = drivingLicenseInfo;
	}

	public int getYearsOfDriving() {
		return yearsOfDriving;
	}

	public void setYearsOfDriving(int yearsOfDriving) {
		this.yearsOfDriving = yearsOfDriving;
	}
}
