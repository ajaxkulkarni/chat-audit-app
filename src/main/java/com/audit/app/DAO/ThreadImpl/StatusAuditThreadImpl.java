package com.audit.app.DAO.ThreadImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.audit.app.Entities.StatusAudit;

public class StatusAuditThreadImpl implements Runnable  {
	private SessionFactory sessionFactory;
	private StatusAudit dto;
	
	public StatusAuditThreadImpl(SessionFactory sessionFactory, StatusAudit dto) {
		this.sessionFactory = sessionFactory;
		this.dto = dto;
	}
	
	public void run() {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.persist(dto);
			session.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex.getLocalizedMessage());
		} finally {
			session.close();
		}
	}
}
