package com.audit.app.Controllers;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
		ApiStatus stat = null;
		try{
			stat = _service.saveChatAudits(dto);
		}catch(Exception ex){
			stat.setCode(512);
			stat.setMessage(ex.getLocalizedMessage());
			stat.setData(null);
		}
		return stat;
	}
	
	@POST
	@Path("/updateUnique")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiStatus getAudit(ChatAuditDTO dto) {
		ApiStatus stat = null;
		try{
			stat = _service.updateUnique(dto);
		}catch(Exception ex){
			stat.setCode(512);
			stat.setMessage(ex.getLocalizedMessage());
			stat.setData(null);
		}
		return stat;
	}
	
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public String test() {
		return "Test Successful..";
	}
	
	@POST
	@Path("/status/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ApiStatus logStatusAudit(ChatStatusAuditDTO dto) {
		ApiStatus stat = null;
		try{
			stat = _stausService.saveStatusAudits(dto);
		}catch(Exception ex){
			stat.setCode(512);
			stat.setMessage(ex.getLocalizedMessage());
			stat.setData(null);
		}
		return stat;
	}
}
