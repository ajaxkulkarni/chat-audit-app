package com.audit.app.DAO.IDAO;

import com.audit.app.Common.ApiStatus;
import com.audit.app.Entities.StatusAudit;

public interface IStatusAuditDAO {
	ApiStatus saveStatusAudits(StatusAudit dto);
}
