package com.audit.app.DAO.ThreadImpl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.audit.app.Common.ApiStatus;
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
