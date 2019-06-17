package com.appoint.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.appoint.app.bean.PatientsBean;
import com.appoint.app.core.CommonService;
import com.appoint.app.model.Patients;
import com.appoint.app.model.User;
import com.appoint.app.repository.PatientRepository;
import com.appoint.app.repository.UserRepository;

@Service
public class PatientService extends CommonService{

	@Autowired
	PatientRepository patientRepo;
	
	@Autowired
	UserRepository userRepository;
	
	@Transactional
	public List<PatientsBean> fetchPatientsByPhone(String phone){
		List<Patients> patientList = patientRepo.fetchPatientsByPhone(phone);
		List<PatientsBean> beanList = new ArrayList<PatientsBean>();
		for(Patients patient : patientList) {
			beanList.add(createBean(patient));
		}
		return beanList;
	}
	
	@Transactional
	public boolean savePatient(Patients patient) {
		Optional<User> optional = userRepository.findByPhone(getUsername());
		optional.ifPresent(user -> {
			patient.setUser(user);
		});
		return patientRepo.savePatient(patient);
	}
	
	@Transactional
	public boolean deletePatient(String patientId) {
		return patientRepo.deletePatient(patientId);
	}
	
	private PatientsBean createBean(Patients patient) {
		PatientsBean patientB = new PatientsBean();
		patientB.setAddress(patient.getAddress());
		patientB.setAge(patient.getAge());
		patientB.setEmail(patient.getEmail());
		patientB.setGender(patient.getGender());
		patientB.setName(patient.getName());
		patientB.setPatientId(patient.getPatientId());
		return patientB;
	}
}
