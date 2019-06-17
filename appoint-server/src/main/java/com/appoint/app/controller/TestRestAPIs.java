package com.appoint.app.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appoint.app.bean.AppointmentBean;
import com.appoint.app.bean.PatientsBean;
import com.appoint.app.bean.TimeOffBean;
import com.appoint.app.model.Patients;
import com.appoint.app.repository.PatientRepository;
import com.appoint.app.service.AppointmentService;
import com.appoint.app.service.PatientService;
import com.appoint.app.service.TimeOffService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TestRestAPIs {
	
	@Autowired
	PatientRepository patientRepository;
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	AppointmentService appointmentService;
	
	@Autowired
	TimeOffService timeOffService;
	
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
	public List<PatientsBean> fetchMyPatients(@RequestBody String phone){
		return patientService.fetchPatientsByPhone(phone);
	}
	
	@PostMapping("/api/test/saveUser")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public boolean saveNewPatient(@RequestBody Patients patient){
		return patientService.savePatient(patient);
	}
	
	@PutMapping("/api/test/updateUser")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public boolean editPatient(@RequestBody Patients patient){
		return patientService.savePatient(patient);
	}
	
	@DeleteMapping("/api/test/deleteUser/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public boolean deletePatient(@PathVariable("id") String patientId){
		return patientService.deletePatient(patientId);
	}
	
	@PostMapping("/api/test/appointmentSchedule")
	@PreAuthorize("hasRole('ADMIN')")
	public List<AppointmentBean> showDoctorAppointment(@RequestBody Date date){
		return appointmentService.getAppointmentByDate(date);
	}
	
	@PutMapping("/api/test/modifyAppointment")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean modifyAppointment(@RequestBody AppointmentBean appointmentBean){
		return appointmentService.modifyAppointment(appointmentBean);
	}
	
	@GetMapping("/api/test/getTimeoffs")
	@PreAuthorize("hasRole('ADMIN')")
	public List<TimeOffBean> getTimeOffs(){
		return timeOffService.getCurrentTimeOffs();
	}
	
	@GetMapping("/api/test/getTimeoffByMonth/{month}/{year}")
	@PreAuthorize("hasRole('ADMIN')")
	public List<TimeOffBean> getTimeOffByMonth(@PathVariable("month") String month, @PathVariable("year") String year){
		return timeOffService.getTimeOffByMonth(month, year);
	}
	
	@GetMapping("/api/test/getAllTimeoffs")
	@PreAuthorize("hasRole('ADMIN')")
	public List<TimeOffBean> getAllTimeOffs(){
		return timeOffService.getAllTimeOffs();
	}
	
	@PutMapping("/api/test/modifyTimeOffs")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean modifyTimeOffs(@RequestBody TimeOffBean timeOffBean){
		return timeOffService.modifyTimeOff(timeOffBean);
	}
	
	@PostMapping("/api/test/createTimeoffs")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean createTimeOff(@RequestBody TimeOffBean timeOffBean){
		return timeOffService.createTimeOff(timeOffBean);
	}
	
	@PostMapping("/api/test/deleteTimeoff")
	@PreAuthorize("hasRole('ADMIN')")
	public boolean createTimeOff(@RequestBody Long timeOffId){
		return timeOffService.deleteTimeOff(timeOffId);
	}
 	
}