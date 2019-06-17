/**
 * 
 */
package com.appoint.app.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Shubham
 *
 */
@Entity
@Table(name = "APPOINTMENT")
public class Appointment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4015992255331594428L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="APPOINTMENT_ID")
	private Long appointmentId;
	
	@Column(name="DATE")
	@Temporal(value = TemporalType.DATE)
	private Date date;
	
	@Column(name="TIME")
	@Temporal(value = TemporalType.TIME)
	private Date time;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinColumn(name="PATIENT_ID")
	private Patients patients;

	public Long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Patients getPatients() {
		return patients;
	}

	public void setPatients(Patients patients) {
		this.patients = patients;
	}
	
	

}
