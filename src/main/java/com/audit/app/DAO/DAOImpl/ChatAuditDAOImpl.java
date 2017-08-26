package com.audit.app.DAO.DAOImpl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.audit.app.Common.ApiStatus;
import com.audit.app.DAO.IDAO.IChatAuditDAO;
import com.audit.app.DAO.ThreadImpl.ChatAuditThreadImpl;
import com.audit.app.DAO.ThreadImpl.ChatUpdateAuditThreadImpl;
import com.audit.app.DTO.ChatAuditDTO;
import com.audit.app.Entities.ChatAudit;

@Service
@Transactional
public class ChatAuditDAOImpl implements IChatAuditDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ThreadPoolTaskExecutor executor;

	public ApiStatus saveChatAudits(ChatAudit dto) {
		ApiStatus status = new ApiStatus();
		ChatAuditThreadImpl impl = new ChatAuditThreadImpl(sessionFactory, dto);
		executor.execute(impl);
		status.setCode(200);
		status.setMessage("Success");
		status.setData(dto);
		return status;
	}

	public ApiStatus updateUnique(ChatAudit dto) {
		ApiStatus status = new ApiStatus();
		ChatUpdateAuditThreadImpl impl = new ChatUpdateAuditThreadImpl(sessionFactory, dto);
		executor.execute(impl);
		status.setCode(200);
		status.setMessage("Success");
		status.setData(dto);
		return status;
	}
	
	
}
