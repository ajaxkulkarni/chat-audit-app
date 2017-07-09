package com.audit.app.DAO.DAOImpl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.audit.app.Common.ApiStatus;
import com.audit.app.DAO.IDAO.IStatusAuditDAO;
import com.audit.app.DAO.ThreadImpl.StatusAuditThreadImpl;
import com.audit.app.Entities.StatusAudit;

@Service
@Transactional
public class StatusAuditDAO implements IStatusAuditDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ThreadPoolTaskExecutor executor;

	public ApiStatus saveStatusAudits(StatusAudit dto) {
		ApiStatus status = new ApiStatus();
		StatusAuditThreadImpl impl = new StatusAuditThreadImpl(sessionFactory, dto);
		executor.execute(impl);
		status.setCode(200);
		status.setMessage("Success");
		status.setData(dto);
		return status;
	}

}
