package com.appoint.app.bean;

import java.util.Date;

public class TimeOffBean {

	private Long timeOffId;
	
	private Date startDate;
	
	private String startTime;
	
	private Date endDate;
	
	private String endTime;
	
	private boolean fullDay;
	
	private String description;

	public Long getTimeOffId() {
		return timeOffId;
	}

	public void setTimeOffId(Long timeOffId) {
		this.timeOffId = timeOffId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public boolean isFullDay() {
		return fullDay;
	}

	public void setFullDay(boolean fullDay) {
		this.fullDay = fullDay;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
