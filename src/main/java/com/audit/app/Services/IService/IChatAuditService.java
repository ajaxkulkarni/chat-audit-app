package com.audit.app.Services.IService;

import com.audit.app.Common.ApiStatus;
import com.audit.app.DTO.ChatAuditDTO;

public interface IChatAuditService {
	ApiStatus saveChatAudits(ChatAuditDTO dto);
	ApiStatus updateUnique(ChatAuditDTO dto);
}
