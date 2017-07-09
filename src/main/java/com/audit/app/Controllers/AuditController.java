package com.audit.app.Controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.audit.app.Common.ApiStatus;
import com.audit.app.DTO.ChatAuditDTO;
import com.audit.app.DTO.ChatStatusAuditDTO;
import com.audit.app.Services.IService.IChatAuditService;
import com.audit.app.Services.IService.IStatusAudiService;

@Component
@Path("/audit")
public class AuditController {
	
	@Autowired
	private IChatAuditService _service;
	
	@Autowired
	private IStatusAudiService _stausService;
	
	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiStatus logAudit(ChatAuditDTO dto) {
		return _service.saveChatAudits(dto);
	}
	
	@POST
	@Path("/status/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiStatus logStatusAudit(ChatStatusAuditDTO dto) {
		return _stausService.saveStatusAudits(dto);
	}
}
