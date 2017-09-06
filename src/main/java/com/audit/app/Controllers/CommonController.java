package com.audit.app.Controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

@Component
@Path("/common")
public class CommonController {

	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@GET
	@Path("/time")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, String> getTime() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("data", format.format(new Date()));
		return map;
	}
}
