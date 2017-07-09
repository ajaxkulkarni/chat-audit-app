package com.audit.app.DAO.ThreadImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.audit.app.Entities.ChatAudit;

public class ChatAuditThreadImpl implements Runnable {
	
	private SessionFactory sessionFactory;
	private ChatAudit dto;
	
	public ChatAuditThreadImpl(SessionFactory sessionFactory, ChatAudit dto) {
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
