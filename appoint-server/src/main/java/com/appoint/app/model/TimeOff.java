/**
 * 
 */
package com.appoint.app.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Shubham
 *
 */
@Entity
@Table(name = "TIME_OFF")
public class TimeOff implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 582938492923952983L;

	@Id
	@Column(name = "TIME_OFF_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long timeOffId;
	
	@Column(name="START_DATE")
	@Temporal(value = TemporalType.DATE)
	private Date startDate;
	
	@Column(name="START_TIME")
	@Temporal(value = TemporalType.TIME)
	private Date startTime;
	
	@Column(name="END_DATE")
	@Temporal(value = TemporalType.DATE)
	private Date endDate;
	
	@Column(name="END_TIME")
	@Temporal(value = TemporalType.TIME)
	private Date endTime;
	
	@Column(name="FULL_DAY")
	private boolean fullDay;
	
	@Column(name="DESCRIPTION")
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

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public boolean isFullDay() {
		return fullDay;
	}

	public void setFullDay(boolean fullDay) {
		this.fullDay = fullDay;
	}

	@Override
	public String toString() {
		return "TimeOff [timeOffId=" + timeOffId + ", StartDate=" + startDate + ", startTime=" + startTime
				+ ", endDate=" + endDate + ", endTime=" + endTime + ", fullDay=" + fullDay + "]";
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
