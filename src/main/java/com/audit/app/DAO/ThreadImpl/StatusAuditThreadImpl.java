package com.audit.app.DAO.ThreadImpl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

import com.audit.app.Entities.StatusAudit;

public class StatusAuditThreadImpl implements Runnable {
	private static Logger LOG = Logger.getLogger(StatusAuditThreadImpl.class);
	private SessionFactory sessionFactory;
	private StatusAudit dto;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public StatusAuditThreadImpl(SessionFactory sessionFactory, StatusAudit dto) {
		this.sessionFactory = sessionFactory;
		this.dto = dto;
	}

	public void run() {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			@SuppressWarnings("unchecked")
			List<StatusAudit> temp = (List<StatusAudit>) session
					.createSQLQuery(
							"SELECT * FROM status_audit AS sa WHERE sa.`status` = '"+dto.getStatus().trim()+"' AND sa.user_uid = '"+dto.getUser_uid()+"' AND DATE(sa.status_time) = DATE('"+format.format(dto.getStatus_time())+"')")
					.setResultTransformer(Transformers.aliasToBean(StatusAudit.class))
					.list();

			if (temp == null || temp.size() == 0) {
				session.persist(dto);
				session.getTransaction().commit();
			}
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
			System.out.println(ex.getLocalizedMessage());
		} finally {
			session.close();
		}
	}
}
