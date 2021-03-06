package com.audit.app.DAO.IDAO;

import com.audit.app.Common.ApiStatus;
import com.audit.app.DTO.ChatAuditDTO;
import com.audit.app.Entities.ChatAudit;

public interface IChatAuditDAO {
	ApiStatus saveChatAudits(ChatAudit dto);
	ApiStatus updateUnique(ChatAudit auditDTO);
}
