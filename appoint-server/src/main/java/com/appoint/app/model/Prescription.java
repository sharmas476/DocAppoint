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
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Shubham
 *
 */
@Entity
@Table(name = "PRESCRIPTION")
public class Prescription implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3851643457240297552L;
	
	public Prescription() {
		
	}
	
	public Prescription(Patients patient, Date date, String prescriptionDetails) {
		super();
		this.date = date;
		this.prescriptionDetails = prescriptionDetails;
		this.patient = patient;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRES_ID")
	private Long prescriptionId;
	
	@Column(name="DATETIME")
	@Temporal(value = TemporalType.DATE)
	private Date date;
	
	@Column(name = "PRESCRIPTION")
	private String prescriptionDetails;
	
	@OneToOne
	@JoinColumn(name = "PATIENT_ID")
	Patients patient;

	public Long getPrescriptionId() {
		return prescriptionId;
	}

	public void setPrescriptionId(Long prescriptionId) {
		this.prescriptionId = prescriptionId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getPrescriptionDetails() {
		return prescriptionDetails;
	}

	public void setPrescriptionDetails(String prescription) {
		this.prescriptionDetails = prescription;
	}

	public Patients getPatient() {
		return patient;
	}

	public void setPatient(Patients patient) {
		this.patient = patient;
	}
	
	
	
}
