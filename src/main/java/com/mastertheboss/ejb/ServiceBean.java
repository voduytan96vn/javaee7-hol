package com.mastertheboss.ejb;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.mastertheboss.model.SimpleProperty;

@Stateless

public class ServiceBean {

	@Inject
	private Event<SimpleProperty> propEventSrc;

	@Inject
	private EntityManager em;

	public void put(SimpleProperty p) {

		em.persist(p);
		propEventSrc.fire(p);
	}

	public void delete(SimpleProperty p) {

		Query query = em.createQuery("delete FROM SimpleProperty p where p.key='" + p.getKey() + "'");

		query.executeUpdate();
		propEventSrc.fire(p);

	}

}