package com.audit.app.Services.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.audit.app.Common.ApiStatus;
import com.audit.app.DAO.IDAO.IStatusAuditDAO;
import com.audit.app.DTO.ChatStatusAuditDTO;
import com.audit.app.Entities.StatusAudit;
import com.audit.app.Services.IService.IStatusAudiService;

@Service
public class StatusAudiService implements IStatusAudiService {
	
	@Autowired
	private IStatusAuditDAO _dao;
	
	public ApiStatus saveStatusAudits(ChatStatusAuditDTO dto) {
		return _dao.saveStatusAudits(new StatusAudit(dto));
	}
}
