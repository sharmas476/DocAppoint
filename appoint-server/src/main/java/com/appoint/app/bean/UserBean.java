package com.appoint.app.bean;

import java.util.HashSet;
import java.util.Set;

import com.appoint.app.model.Patients;

public class UserBean {
	private Long id;

    private String name;

    private String phone;
    
	private String email;

    private String password;
    
    private Set<Patients> patient = new HashSet<Patients>();

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Patients> getPatient() {
		return patient;
	}

	public void setPatient(Set<Patients> patient) {
		this.patient = patient;
	}


}
