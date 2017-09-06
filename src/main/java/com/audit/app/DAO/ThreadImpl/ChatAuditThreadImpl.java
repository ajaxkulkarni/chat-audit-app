package com.audit.app.DAO.ThreadImpl;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.audit.app.Entities.ChatAudit;

public class ChatAuditThreadImpl implements Runnable {
	private static Logger LOG = Logger.getLogger(ChatAuditThreadImpl.class);

	private SessionFactory sessionFactory;
	private ChatAudit dto;
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

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
			LOG.error(ex.getMessage());
			System.out.println(ex.getLocalizedMessage());
		} finally {
			session.close();
		}
	}
}
