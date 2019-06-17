/**
 * 
 */
package com.appoint.app.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appoint.app.model.Patients;
import com.appoint.app.model.User;

/**
 * @author Shubham
 *
 */
@Repository
public class PatientRepository extends AbstractRepository{

	@Autowired
	EntityManager entityManager;

	public List<Patients> fetchPatientsByPhone(String phone){
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Patients> cq = criteriaBuilder.createQuery(Patients.class);
		Root<Patients> from = cq.from(Patients.class);
		cq.select(from);
		Path<User> user = from.get("user");
		Path<String> phone_num = user.get("phone");
		cq.where(criteriaBuilder.equal(phone_num, phone));
		Query query = entityManager.createQuery(cq);
		List<Patients> list = query.getResultList();
		return list;
	}
	
	public boolean savePatient(Patients patient){
		getSession().saveOrUpdate(patient);
		return true;
	}
	
	public boolean deletePatient(String patientId){
		Patients patient = getSession().load(Patients.class, Long.parseLong(patientId));
		getSession().delete(patient);
		return true;
	}

}
