package com.appoint.app.repository;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AbstractRepository {

	@Autowired
	EntityManager entityManager;
	
	public Session getSession() {
		return entityManager.unwrap(Session.class);
	}
}
