/**
 * 
 */
package com.appoint.app.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.appoint.app.core.Constants;
import com.appoint.app.model.Appointment;
import com.appoint.app.model.PatientFile;
import com.appoint.app.model.Prescription;

/**
 * @author Shubham
 *
 */
@Repository
public class AppointmentRepository extends AbstractRepository {

	public List<Appointment> getAppointmentByDate(Date datetime){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Appointment> cq = criteriaBuilder.createQuery(Appointment.class);
		Root<Appointment> from = cq.from(Appointment.class);
		cq.select(from).where(criteriaBuilder.and(criteriaBuilder.equal(from.get("date"),datetime),
				criteriaBuilder.or(criteriaBuilder.equal(from.get("status"), Constants.SCHEDULED),criteriaBuilder.equal(from.get("status"), Constants.TREATED))));
		return entityManager.createQuery(cq).getResultList();
	}
	
	public void startTreatment(Long appointmentId) {
		Appointment appointment = loadAppointment(appointmentId);
		appointment.setStatus(Constants.TREATED);
		getSession().update(appointment);
	}
	
	public Appointment loadAppointment(Long appointmentId) {
		return entityManager.find(Appointment.class, appointmentId);
	}
	
	public PatientFile loadPatientFile(Long fileId) {
		return entityManager.find(PatientFile.class, fileId);
	}
	
	public void savePatientFile(PatientFile patientFile) {
		getSession().saveOrUpdate(patientFile);
	}
	
	public void savePrescription(Prescription prescription) {
		getSession().saveOrUpdate(prescription);
	}
	
	public void update(Appointment appointment) {
		getSession().update(appointment);
	}
	
	public void createAppointment(Appointment appointment) {
		getSession().save(appointment);
	}
	
	public void deleteAppointment(Long appointmentId) {
		Appointment appointment = loadAppointment(appointmentId);
		appointment.setStatus(Constants.CANCELLED);
		getSession().update(appointment);
	}
}
