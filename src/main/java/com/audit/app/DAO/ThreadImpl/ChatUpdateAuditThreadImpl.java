package com.audit.app.DAO.ThreadImpl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.audit.app.Entities.ChatAudit;

public class ChatUpdateAuditThreadImpl implements Runnable {
	
	private SessionFactory sessionFactory;
	private ChatAudit dto;
	
	public ChatUpdateAuditThreadImpl(SessionFactory sessionFactory, ChatAudit dto) {
		this.sessionFactory = sessionFactory;
		this.dto = dto;
	}
	

	public void run() {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			Query query = session.createQuery("from ChatAudit where sessionId=:session_id AND userUid=:uid AND status=:status");
			query.setInteger("session_id", dto.getSessionId());
			query.setString("uid", dto.getUserUid());
			query.setString("status", dto.getStatus());
			List<ChatAudit> audit = query.list();
			if(CollectionUtils.isEmpty(audit)) {
				session.persist(dto);
			}
			session.getTransaction().commit();
		} catch (Exception ex) {
			System.out.println(ex.getLocalizedMessage());
		} finally {
			if(session != null) {
				session.close();
			}
		}
	}
}
