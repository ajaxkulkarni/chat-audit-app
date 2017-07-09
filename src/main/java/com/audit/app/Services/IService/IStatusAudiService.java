package com.audit.app.Services.IService;

import com.audit.app.Common.ApiStatus;
import com.audit.app.DTO.ChatStatusAuditDTO;

public interface IStatusAudiService {
	ApiStatus saveStatusAudits(ChatStatusAuditDTO dto);
}
