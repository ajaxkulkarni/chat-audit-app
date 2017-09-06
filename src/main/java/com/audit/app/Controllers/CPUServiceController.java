package com.audit.app.Controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.audit.app.Services.IService.ICPULogService;

@Component
@Path("/cpu/log")
public class CPUServiceController {
	
	@Autowired
	private ICPULogService service;

	@GET
	@Path("/load")
	@Produces(MediaType.APPLICATION_JSON)
	public double getData() {
		return service.getCPULoad();
	}
}
