package com.lyft.BasicClasses;

import java.util.Date;

public class DrivingLicenseInfo {
	private String licenseID;
	private Date startDate, dueDate;
	public DrivingLicenseInfo(String licenseID, Date startDate, Date dueDate) {
		this.licenseID = licenseID;
		this.startDate = startDate;
		this.dueDate = dueDate;
	}
	
	public String getLicenseID() {
		return licenseID;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	
	public Date getDueDate() {
		return dueDate;
	}
}
