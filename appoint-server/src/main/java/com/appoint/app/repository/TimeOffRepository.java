/**
 * 
 */
package com.appoint.app.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.appoint.app.model.TimeOff;

/**
 * @author Shubham
 *
 */
@Repository
public class TimeOffRepository extends AbstractRepository{

	@Autowired
	EntityManager em;
	
	public List<TimeOff> getCurrentTimeOffs() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TimeOff> criteria = cb.createQuery(TimeOff.class);
		Root<TimeOff> from = criteria.from(TimeOff.class);
		criteria.select(from);
		criteria.where(cb.greaterThanOrEqualTo(from.<Date>get("startDate"), new Date()));
		TypedQuery<TimeOff> query = em.createQuery(criteria);
		return query.getResultList();
	}
	
	public List<TimeOff> getAllTimeOffs() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TimeOff> criteria = cb.createQuery(TimeOff.class);
		Root<TimeOff> from = criteria.from(TimeOff.class);
		criteria.select(from);
		TypedQuery<TimeOff> query = em.createQuery(criteria);
		return query.getResultList();
	}
	
	public List<TimeOff> getTimeOffsFromDate(Date date) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TimeOff> criteria = cb.createQuery(TimeOff.class);
		Root<TimeOff> from = criteria.from(TimeOff.class);
		criteria.select(from);
		criteria.where(cb.greaterThanOrEqualTo(from.<Date>get("startDate"), date));
		TypedQuery<TimeOff> query = em.createQuery(criteria);
		return query.getResultList();
	}
	
	public List<TimeOff> getTimeOffsForDate(Date date) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<TimeOff> criteria = cb.createQuery(TimeOff.class);
		Root<TimeOff> from = criteria.from(TimeOff.class);
		criteria.select(from);
		criteria.where(cb.or(cb.equal(from.<Date>get("startDate"), date),cb.equal(from.<Date>get("endDate"), date)));
		TypedQuery<TimeOff> query = em.createQuery(criteria);
		return query.getResultList();
	}
	
	public void modifyTimeOff(TimeOff timeOff) {
		getSession().update(timeOff);
	}
	
	public void createTimeOff(TimeOff timeOff) {
		getSession().save(timeOff);
	}
	
	public void deleteTimeOff(Long timeOffId) {
		getSession().delete(em.find(TimeOff.class,timeOffId));
	}
	
	public List<TimeOff> getTimeOffByMonth(String month, String year){
		Query query = em.createNativeQuery("SELECT * FROM testdb.time_off where MONTH(start_date)= :month AND YEAR(start_date)= :year", TimeOff.class);
		query.setParameter("month", month);
		query.setParameter("year", year);
		List<TimeOff> obj = query.getResultList();
		return obj;
	}
	
	public TimeOff loadTimeOff(Long timeOffId) {
		return em.find(TimeOff.class, timeOffId);
	}
}
