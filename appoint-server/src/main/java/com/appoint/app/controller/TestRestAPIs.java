package com.appoint.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.appoint.app.model.Patients;
import com.appoint.app.repository.PatientRepository;
import com.appoint.app.service.PatientService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TestRestAPIs {
	
	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	PatientService patientService;
	
	@GetMapping("/api/test/user")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String userAccess() {
		return ">>> User Contents!";
	}
	
	@GetMapping("/api/test/user1")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public String userAccess1() {
		return ">>> User Contents!";
	}

	@GetMapping("/api/test/pm")
	@PreAuthorize("hasRole('PM') or hasRole('ADMIN')")
	public String projectManagementAccess() {
		return ">>> Project Management Board";
	}
	
	@GetMapping("/api/test/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return ">>> Admin Contents";
	}
	
	@PostMapping("/api/test/fetchUser")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public List<Patients> fetchMyPatients(@RequestBody String phone){
		return patientRepository.fetchPatientsByPhone(phone);
	}
	
	@PostMapping("/api/test/saveUser")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public boolean saveNewPatient(@RequestBody Patients patient){
		return patientService.savePatient(patient);
	}
	
	@DeleteMapping("/api/test/deleteUser/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public boolean deletePatient(@PathVariable("id") String patientId){
		return patientService.deletePatient(patientId);
	}
 	
}