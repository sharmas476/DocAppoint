/**
 * 
 */
package com.appoint.app.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Shubham
 *
 */
public class PatientFileBean {
	
	private Long patientId;
	
	private Long fileId;
	
	private String occupation;
	
	private String pC;
	
	private String marriedSince;
	
	private String name;
	
	private String age;
	
	private String gender;
	
	private String contactDetails;
	
	private Set<PrescriptionBean> prescriptionList = new HashSet<>();

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Set<PrescriptionBean> getPrescriptionList() {
		return prescriptionList;
	}

	public void setPrescriptionList(Set<PrescriptionBean> prescriptionList) {
		this.prescriptionList = prescriptionList;
	}

}
