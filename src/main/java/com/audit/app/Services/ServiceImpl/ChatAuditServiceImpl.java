package com.audit.app.Services.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.audit.app.Common.ApiStatus;
import com.audit.app.DAO.IDAO.IChatAuditDAO;
import com.audit.app.DTO.ChatAuditDTO;
import com.audit.app.Entities.ChatAudit;
import com.audit.app.Services.IService.IChatAuditService;

@Service
public class ChatAuditServiceImpl implements IChatAuditService {

	@Autowired
	private IChatAuditDAO _dao;
	
	public ApiStatus saveChatAudits(ChatAuditDTO dto) {
		return _dao.saveChatAudits(new ChatAudit(dto));
	}
}
