/**
 * 
 */
package com.appoint.app.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appoint.app.model.Appointment;

/**
 * @author Shubham
 *
 */
@Repository
public class AppointmentRepository extends AbstractRepository {
	
	@Autowired
	EntityManager entityManager;

	public List<Appointment> getAppointmentByDate(Date datetime){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Appointment> cq = criteriaBuilder.createQuery(Appointment.class);
		Root<Appointment> from = cq.from(Appointment.class);
		cq.select(from).where(criteriaBuilder.equal(from.get("date"),datetime));
		List<Appointment> list = entityManager.createQuery(cq).getResultList();
		return list;
	}
	
	public Appointment loadAppointment(Long appointmentId) {
		return entityManager.find(Appointment.class, appointmentId);
	}
	
	public void update(Appointment appointment) {
		getSession().update(appointment);
	}
}
