/**
 * 
 */
package com.appoint.app.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Shubham
 *
 */
@Entity
@Table(name = "PATIENTS")
public class Patients implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2026327818025902840L;
	
	public Patients( String name, int age,
			String gender, String email, User user	) {
		super();
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.email = email;
		this.user = user;
	}
	
	public Patients() {
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PATIENT_ID")
	private Long patientId;	
	
	@NotNull
    @Size(min=3, max = 50)
	@Column(name="NAME")
	private String name;
	
	@NotNull
	@Column(name="AGE")
	private int age;
	
	@NotNull
	@Column(name="GENDER")
	private String gender;

	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="EMAIL")
	private String email;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinColumn(name="USER_ID")
	private User user;
	
	@OneToMany(mappedBy = "patients")
	private Set<Appointment> appointment = new HashSet<>();
	
	@OneToOne(mappedBy = "patient")
	private PatientFile patientFile;
	
	@OneToMany(mappedBy = "patient")
	private Set<Prescription> prescription = new HashSet<>();

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Appointment> getAppointment() {
		return appointment;
	}

	public void setAppointment(Set<Appointment> appointment) {
		this.appointment = appointment;
	}

	public PatientFile getPatientFile() {
		return patientFile;
	}

	public void setPatientFile(PatientFile patientFile) {
		this.patientFile = patientFile;
	}

	public Set<Prescription> getPrescription() {
		return prescription;
	}

	public void setPrescription(Set<Prescription> prescription) {
		this.prescription = prescription;
	}

	

}
