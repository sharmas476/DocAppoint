/**
 * 
 */
package com.appoint.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Shubham
 *
 */
@Entity
@Table(name = "PATIENT_FILE")
public class PatientFile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 448731125524295706L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FILE_ID")
	private Long fileId;
	
	@Column(name = "OCCUPATION")
	private String occupation;
	
	@Column(name = "P_C")
	private String pC;
	
	@Column(name = "married_since")
	private String marriedSince;
	
	@OneToOne
	@JoinColumn(name = "PATIENT_ID")
	Patients patient;

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getpC() {
		return pC;
	}

	public void setpC(String pC) {
		this.pC = pC;
	}

	public String getMarriedSince() {
		return marriedSince;
	}

	public void setMarriedSince(String marriedSince) {
		this.marriedSince = marriedSince;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Patients getPatient() {
		return patient;
	}

	public void setPatient(Patients patient) {
		this.patient = patient;
	}
	
}
